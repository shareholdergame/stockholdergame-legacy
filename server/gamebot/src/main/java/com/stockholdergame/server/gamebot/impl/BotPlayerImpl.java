package com.stockholdergame.server.gamebot.impl;

import com.stockholdergame.server.dto.game.BuySellDto;
import com.stockholdergame.server.dto.game.CompetitorMoveDto;
import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.MoveDto;
import com.stockholdergame.server.dto.game.MoveStepDto;
import com.stockholdergame.server.dto.game.PriceOperationDto;
import com.stockholdergame.server.dto.game.SharePriceDto;
import com.stockholdergame.server.dto.game.ShareQuantityDto;
import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.dto.game.variant.CardGroupDto;
import com.stockholdergame.server.dto.game.variant.CardOperationDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.gamebot.BotApplicationException;
import com.stockholdergame.server.gamebot.BotPlayer;
import com.stockholdergame.server.gamebot.GameAnalyzer;
import com.stockholdergame.server.gamebot.GameBotBusinessDelegate;
import com.stockholdergame.server.gamebot.MoveDecisionMaker;
import com.stockholdergame.server.gamebot.domain.BotGameHolder;
import com.stockholdergame.server.gamebot.domain.CardForceMatrix;
import com.stockholdergame.server.gamebot.domain.MoveAdvice;
import com.stockholdergame.server.gamebot.domain.MoveDecision;
import com.stockholdergame.server.gamecore.CompetitorAccount;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.MovePerformer;
import com.stockholdergame.server.gamecore.impl.MovePerformerImpl;
import com.stockholdergame.server.gamecore.model.BuySellAction;
import com.stockholdergame.server.gamecore.model.MoveData;
import com.stockholdergame.server.gamecore.model.PriceChangeAction;
import com.stockholdergame.server.model.game.CompetitorAccountExtraData;
import com.stockholdergame.server.model.game.GameStateExtraData;
import com.stockholdergame.server.model.game.StepType;
import com.stockholdergame.server.model.game.variant.GameVariantInfo;
import com.stockholdergame.server.model.game.variant.PriceOperation;
import com.stockholdergame.server.model.game.variant.PriceOperationType;
import com.stockholdergame.server.util.registry.impl.AdvancedTimeSizeEvictionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
@Component
public class BotPlayerImpl implements BotPlayer {

    @Value("${game.registry.max.size}")
    private int gameRegistryMaxSize = 100;

    @Value("${game.registry.fill.factor}")
    private double gameRegistryFillFactor = 0.8;

    @Value("${game.registry.eviction.timeout}")
    private long gameRegistryEvictionTimeout = 3600000L;

    private AdvancedTimeSizeEvictionRegistry<Long, BotGameHolder> games =
            new AdvancedTimeSizeEvictionRegistry<>(gameRegistryMaxSize, gameRegistryFillFactor, gameRegistryEvictionTimeout);

    @Autowired
    private GameBotBusinessDelegate businessDelegate;

    @Autowired
    private GameAnalyzer gameAnalyzer;

    @Autowired
    private MoveDecisionMaker moveDecisionMaker;

    private MovePerformer movePerformer = new MovePerformerImpl();

    @Override
    public void play(Long gameId, Long botId) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new BotApplicationException(e);
        }

        BotGameHolder botGameHolder;
        if (!games.contains(gameId)) {
            botGameHolder = loadGame(gameId, botId);
            games.put(gameId, botGameHolder);
        } else {
            botGameHolder = games.get(gameId);
            updateGame(botGameHolder, botId);
        }

        if (((CompetitorAccountExtraData) botGameHolder.getGameState().getCurrentCompetitorState().getExtraData()).getGamerId().equals(botId)) {
            doMove(botGameHolder, botId);
            if (botGameHolder.getGameState().isFinished()) {
                games.remove(gameId);
            }
        }
    }

    private void updateGame(BotGameHolder botGameHolder, Long botId) {
        GameState gameState = botGameHolder.getGameState();
        Long gameId = gameState.<GameStateExtraData>getExtraData().getGameId();
        Set<MoveDto> lastMoves = businessDelegate.loadLastMoves(gameState.getCurrentMoveNumber(), gameState.getCurrentMoveOrder(), gameId, botId);
        List<MoveData> moveDataList = convertToMoveData(lastMoves, botGameHolder.getGameState());
        if (!moveDataList.isEmpty()) {
            synchronizeGameState(gameState, moveDataList);
        }
    }

    private void doMove(BotGameHolder botGameHolder, Long botId) {
        CardForceMatrix cardForceMatrix = gameAnalyzer.analyzeCards(botGameHolder.getCards(), botGameHolder.getGameState().getShareIds());

        MoveAdvice moveAdvice = gameAnalyzer.analyzePosition(botGameHolder.getGameState(), cardForceMatrix);
        MoveDecision moveDecision = moveDecisionMaker.makeMoveDecision(moveAdvice, cardForceMatrix, botGameHolder);

        try {
            CompetitorAccount competitorAccount = botGameHolder.getGameState().getCurrentCompetitorState();
            DoMoveDto doMoveDto = generateMove(moveDecision, botGameHolder);
            businessDelegate.doMove(doMoveDto, botId);
            botGameHolder.getGameState().commit();
            Long appliedCardId = doMoveDto.getAppliedCardId();
            competitorAccount.<CompetitorAccountExtraData>getExtraData().removeCard(appliedCardId);
            botGameHolder.removeCard(appliedCardId);
        } catch (BotApplicationException e) {
            botGameHolder.getGameState().rollback();
            throw e;
        } catch (Exception e) {
            botGameHolder.getGameState().rollback();
            throw new BotApplicationException(e);
        }
    }

    private DoMoveDto generateMove(MoveDecision moveDecision, BotGameHolder botGameHolder) {
        GameState gameState = botGameHolder.getGameState();
        Long gameId = ((GameStateExtraData) gameState.getExtraData()).getGameId();
        CompetitorAccount botAccount = gameState.getCurrentCompetitorState();

        DoMoveDto doMoveDto = new DoMoveDto();
        doMoveDto.setGameId(gameId);

        Set<BuySellDto> firstBuySellDtos = new HashSet<>();
        List<Map.Entry<Long, Float>> firstStepSortedEntries = new ArrayList<>(moveDecision.getFirstStepShares().entrySet());
        Comparator<Map.Entry<Long, Float>> comparator = new Comparator<Map.Entry<Long, Float>>() {
            @Override
            public int compare(Map.Entry<Long, Float> e, Map.Entry<Long, Float> e1) {
                float r = e.getValue() - e1.getValue();
                return r > 0 ? 1 : r == 0 ? 0 : -1;
            }
        };
        Collections.sort(firstStepSortedEntries, comparator);
        try {
            for (Map.Entry<Long, Float> entry : firstStepSortedEntries) {
                Long shareId = entry.getKey();
                float percent = entry.getValue();
                int shareQuantity = botAccount.getShareQuantity(shareId);
                int cash = botAccount.getCash();
                int sharePrice = gameState.getSharePrice(shareId);
                int buySellQuantity = percent <= 0 ? (int) Math.floor(shareQuantity * percent)
                        : (int) Math.floor((cash * percent) / sharePrice);

                BuySellDto buySellDto = new BuySellDto();
                buySellDto.setShareId(shareId);
                buySellDto.setBuySellQuantity(buySellQuantity);
                firstBuySellDtos.add(buySellDto);

                gameState.buySellShare(shareId, buySellQuantity);
            }
        } catch (Exception e) {
            throw new BotApplicationException(e);
        }
        doMoveDto.setFirstBuySellActions(firstBuySellDtos);

        Set<PriceOperationDto> priceOperations = new HashSet<>();
        CardDto cardDto = moveDecision.getSuggestedCard();
        Set<CardOperationDto> cardOperations = cardDto.getCardOperations();
        Map<Long, Long> sharesOperationMap = new HashMap<>(moveDecision.getSharesOperationMap());
        for (CardOperationDto cardOperation : cardOperations) {
            Long shareId = cardOperation.getShareId();
            Long priceOperationId = cardOperation.getPriceOperationId();
            if (shareId == null) {
                shareId = sharesOperationMap.get(priceOperationId);
            }

            PriceOperationDto priceOperation = new PriceOperationDto();
            priceOperation.setShareId(shareId);
            priceOperation.setPriceOperationId(priceOperationId);
            priceOperations.add(priceOperation);

            try {
                gameState.setSharePrice(shareId, PriceOperationType.signOf(cardOperation.getOperation()).getOperation(),
                        cardOperation.getOperandValue());
            } catch (Exception e) {
                throw new BotApplicationException(e);
            }
        }
        doMoveDto.setAppliedCardId(botGameHolder.getCompetitorCardIdByCardId(cardDto.getId()));
        doMoveDto.setPriceOperations(priceOperations);

        Set<BuySellDto> lastBuySellDtos = new HashSet<>();
        List<Map.Entry<Long, Float>> lastStepSortedEntries = new ArrayList<>(moveDecision.getLastStepShares().entrySet());
        Collections.sort(lastStepSortedEntries, comparator);
        try {
            for (Map.Entry<Long, Float> entry : lastStepSortedEntries) {
                Long shareId = entry.getKey();
                float percent = entry.getValue();
                int shareQuantity = botAccount.getShareQuantity(shareId);
                int lockedShareQuantity = botAccount.getLockedShareQuantity(shareId);
                int cash = botAccount.getCash();
                int sharePrice = gameState.getSharePrice(shareId);
                int buySellQuantity = percent <= 0 ? (int) Math.floor((shareQuantity - lockedShareQuantity) * entry.getValue())
                        : (int) Math.floor((cash * percent) / sharePrice);

                BuySellDto buySellDto = new BuySellDto();
                buySellDto.setShareId(shareId);
                buySellDto.setBuySellQuantity(buySellQuantity);
                lastBuySellDtos.add(buySellDto);

                gameState.buySellShare(shareId, buySellQuantity);
            }
        } catch (Exception e) {
            throw new BotApplicationException(e);
        }
        doMoveDto.setLastBuySellActions(lastBuySellDtos);


        return doMoveDto;
    }

    private List<MoveData> convertToMoveData(Set<MoveDto> moves, GameState gameState) {
        int currentMoveNumber = gameState.getCurrentMoveNumber();
        int currentMoveOrder = gameState.getCurrentMoveOrder();
        GameVariantInfo gameVariantInfo = businessDelegate.getGameVariantInfo(((GameStateExtraData) gameState.getExtraData()).getGameVariantId());

        List<MoveData> moveDataList = new ArrayList<>();
        for (MoveDto move : moves) {
            if (move.getMoveNumber() < currentMoveNumber) {
                continue;
            }

            Set<CompetitorMoveDto> competitorMoves = move.getCompetitorMoves();
            for (CompetitorMoveDto competitorMove : competitorMoves) {
                if (move.getMoveNumber() == currentMoveNumber && competitorMove.getMoveOrder() < currentMoveOrder) {
                    continue;
                }

                CompetitorAccount competitorAccount = gameState.getCompetitorAccount(competitorMove.getMoveOrder());
                CompetitorAccountExtraData competitorAccountExtraData = competitorAccount.getExtraData();

                Set<BuySellAction> firstStepBuySellActions = new TreeSet<>();
                Set<PriceChangeAction> priceChangeActions = new TreeSet<>();
                Set<BuySellAction> lastStepBuySellActions = new TreeSet<>();

                Set<MoveStepDto> moveSteps = competitorMove.getSteps();
                for (MoveStepDto moveStep : moveSteps) {
                    if (moveStep.getStepType().equalsIgnoreCase(StepType.FIRST_BUY_SELL_STEP.name())) {
                        Set<ShareQuantityDto> shareQuantities = moveStep.getShareQuantities();
                        for (ShareQuantityDto shareQuantity : shareQuantities) {
                            BuySellAction buySellAction = new BuySellAction(shareQuantity.getId(), shareQuantity.getBuySellQuantity());
                            firstStepBuySellActions.add(buySellAction);
                        }
                    } else if (moveStep.getStepType().equalsIgnoreCase(StepType.PRICE_CHANGE_STEP.name())) {
                        Set<SharePriceDto> sharePrices = moveStep.getSharePrices();
                        for (SharePriceDto sharePrice : sharePrices) {
                            if (sharePrice.getPriceOperationId() != null) {
                                Long cardId = competitorAccountExtraData.getAvailableCards().get(competitorMove.getAppliedCardId());
                                PriceOperation priceOperation = gameVariantInfo.getPriceOperation(cardId, sharePrice.getPriceOperationId());
                                PriceChangeAction priceChangeAction = new PriceChangeAction(sharePrice.getId(),
                                        priceOperation.getPriceOperationType().getOperation(), priceOperation.getOperandValue());
                                priceChangeActions.add(priceChangeAction);
                            }
                        }
                    } else if (moveStep.getStepType().equalsIgnoreCase(StepType.LAST_BUY_SELL_STEP.name())) {
                        Set<ShareQuantityDto> shareQuantities = moveStep.getShareQuantities();
                        for (ShareQuantityDto shareQuantity : shareQuantities) {
                            BuySellAction buySellAction = new BuySellAction(shareQuantity.getId(), shareQuantity.getBuySellQuantity());
                            lastStepBuySellActions.add(buySellAction);
                        }
                    }
                }
                MoveData moveData = new MoveData(firstStepBuySellActions, priceChangeActions, lastStepBuySellActions);
                moveDataList.add(moveData);
            }
        }
        return moveDataList;
    }

    private void synchronizeGameState(GameState gameState, List<MoveData> moveDataList) {
        gameState.rollback();
        for (MoveData moveData : moveDataList) {
            applyMoveData(gameState, moveData);
        }
    }

    private void applyMoveData(GameState gameState, MoveData moveData) {
        try {
            movePerformer.doMove(moveData, gameState);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private BotGameHolder loadGame(Long gameId, Long botId) {
        GameState gameState = businessDelegate.getGame(gameId, botId);
        if (gameState != null) {
            GameVariantDto gameVariant = businessDelegate.getGameVariant(((GameStateExtraData) gameState.getExtraData()).getGameVariantId());
            Map<Long, CardDto> cards = buildCardsMap(gameVariant, getBotCardsMap(gameState, botId));
            return new BotGameHolder(gameState, cards);
        } else {
            return null;
        }
    }

    private Map<Long, Long> getBotCardsMap(GameState gameState, Long botId) {
        Set<CompetitorAccount> competitorAccounts = gameState.getCompetitorAccounts();
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            if (((CompetitorAccountExtraData) competitorAccount.getExtraData()).getGamerId().equals(botId)) {
                return ((CompetitorAccountExtraData) competitorAccount.getExtraData()).getAvailableCards();
            }
        }
        return null;
    }

    private Map<Long, CardDto> buildCardsMap(GameVariantDto gameVariant, Map<Long, Long> botCards) {
        Map<Long, List<Long>> turnedOutMap = turnOut(botCards);
        Map<Long, CardDto> cardsMap = new HashMap<>();
        for (CardGroupDto cardGroupDto : gameVariant.getCardGroups()) {
            for (CardDto cardDto : cardGroupDto.getCards()) {
                if (turnedOutMap.containsKey(cardDto.getId())) {
                    for (Long competitorCardId : turnedOutMap.get(cardDto.getId())) {
                        cardsMap.put(competitorCardId, cardDto);
                    }
                }
            }
        }
        return cardsMap;
    }

    private Map<Long, List<Long>> turnOut(Map<Long, Long> m) {
        Map<Long, List<Long>> tom = new HashMap<>();
        for (Map.Entry<Long, Long> entry : m.entrySet()) {
            if (!tom.containsKey(entry.getValue())) {
                tom.put(entry.getValue(), new ArrayList<Long>());
            }
            tom.get(entry.getValue()).add(entry.getKey());
        }
        return tom;
    }
}
