package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.dao.GameSeriesResultDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dto.game.BuySellDto;
import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.GameDto;
import com.stockholdergame.server.dto.game.GameEventDto;
import com.stockholdergame.server.dto.game.PriceOperationDto;
import com.stockholdergame.server.dto.game.event.UserNotification;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.gamecore.BeforeCommitListener;
import com.stockholdergame.server.gamecore.Compensation;
import com.stockholdergame.server.gamecore.CompetitorAccount;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.MovePerformer;
import com.stockholdergame.server.gamecore.exceptions.GameIsFinishedException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import com.stockholdergame.server.gamecore.impl.MovePerformerImpl;
import com.stockholdergame.server.gamecore.model.BuySellAction;
import com.stockholdergame.server.gamecore.model.MoveData;
import com.stockholdergame.server.gamecore.model.PriceChangeAction;
import com.stockholdergame.server.gamecore.model.result.BuySellActionResult;
import com.stockholdergame.server.gamecore.model.result.BuySellStepResult;
import com.stockholdergame.server.gamecore.model.result.MoveResult;
import com.stockholdergame.server.gamecore.model.result.PriceChangeActionResult;
import com.stockholdergame.server.gamecore.model.result.PriceChangeStepResult;
import com.stockholdergame.server.gamecore.model.result.RepurchaseResult;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.CompetitorAccountExtraData;
import com.stockholdergame.server.model.game.CompetitorCard;
import com.stockholdergame.server.model.game.CompetitorMove;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.model.game.GameSeries;
import com.stockholdergame.server.model.game.GameStateExtraData;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.Move;
import com.stockholdergame.server.model.game.MoveStep;
import com.stockholdergame.server.model.game.SharePrice;
import com.stockholdergame.server.model.game.SharePricePk;
import com.stockholdergame.server.model.game.ShareQuantity;
import com.stockholdergame.server.model.game.ShareQuantityPk;
import com.stockholdergame.server.model.game.StepType;
import com.stockholdergame.server.model.game.move.MoveExtraData;
import com.stockholdergame.server.model.game.move.ValidationResult;
import com.stockholdergame.server.model.game.result.CompetitorDiff;
import com.stockholdergame.server.model.game.result.CompetitorResult;
import com.stockholdergame.server.model.game.result.GameSeriesResult;
import com.stockholdergame.server.model.game.variant.GameVariantInfo;
import com.stockholdergame.server.model.game.variant.PriceOperation;
import com.stockholdergame.server.services.UserInfoAware;
import com.stockholdergame.server.services.event.BusinessEventBuilder;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.services.game.GameVariantService;
import com.stockholdergame.server.services.game.PlayGameService;
import com.stockholdergame.server.services.game.ScoreService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.services.messaging.MessagingService;
import com.stockholdergame.server.services.security.DeniedForRemovedUser;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import com.stockholdergame.server.util.game.GameStateCreator;
import com.stockholdergame.server.util.game.MoveValidator;
import com.stockholdergame.server.util.registry.Registry;
import com.stockholdergame.server.util.registry.RegistryAlmostFullException;
import com.stockholdergame.server.util.registry.impl.AdvancedTimeSizeEvictionRegistry;
import com.stockholdergame.server.util.registry.impl.RegistryImpl;
import org.apache.commons.lang.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import static com.stockholdergame.server.i18n.ErrorsResourceBundleKeys.ILLEGAL_GAME_STATUS;
import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.MOVE_NOT_ENDED_PROPERLY;
import static com.stockholdergame.server.localization.MessageHolder.getMessage;
import static com.stockholdergame.server.model.game.GameStatus.FINISHED;
import static com.stockholdergame.server.model.game.GameStatus.RUNNING;
import static com.stockholdergame.server.model.game.StepType.FIRST_BUY_SELL_STEP;
import static com.stockholdergame.server.model.game.StepType.LAST_BUY_SELL_STEP;
import static com.stockholdergame.server.model.game.StepType.PRICE_CHANGE_STEP;
import static com.stockholdergame.server.model.game.StepType.REPURCHASE_STEP;
import static com.stockholdergame.server.model.game.StepType.ZERO_STEP;

/**
 * Play service implementation.
 */
@Service("playGameService")
public class PlayGameServiceImpl extends UserInfoAware implements PlayGameService {

    private static Logger LOGGER = LogManager.getLogger(PlayGameService.class);

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameSeriesResultDao gameSeriesResultDao;

    @Autowired
    private GameVariantService gameVariantService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private MailPreparationService mailPreparationService;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private MessageChannel exceptionHandlingChannel;

    @Value("${game.registry.max.size}")
    private int gameRegistryMaxSize = 100;

    @Value("${game.registry.fill.factor}")
    private double gameRegistryFillFactor = 0.8;

    @Value("${game.registry.eviction.timeout}")
    private long gameRegistryEvictionTimeout = 3600000L;

    public void setGameRegistryMaxSize(int gameRegistryMaxSize) {
        this.gameRegistryMaxSize = gameRegistryMaxSize;
    }

    public void setGameRegistryFillFactor(double gameRegistryFillFactor) {
        this.gameRegistryFillFactor = gameRegistryFillFactor;
    }

    public void setGameRegistryEvictionTimeout(long gameRegistryEvictionTimeout) {
        this.gameRegistryEvictionTimeout = gameRegistryEvictionTimeout;
    }

    @Autowired
    private MessagingService messagingService;

    private AdvancedTimeSizeEvictionRegistry<Long, GameState> gameRegistry =
        new AdvancedTimeSizeEvictionRegistry<>(gameRegistryMaxSize, gameRegistryFillFactor, gameRegistryEvictionTimeout);

    private Registry<Long, MovePerformer> movePerformerRegistry = new RegistryImpl<>();

    @Override
    @DeniedForRemovedUser
    public GameDto doMove(DoMoveDto doMoveDto) {
        UserInfo userInfo = getCurrentUser();
        return doMove(doMoveDto, userInfo);
    }

    @Override
    @Transactional
    public GameDto doMove(DoMoveDto doMoveDto, UserInfo userInfo) {
        Long userId = userInfo.getId();
        Long gameId = doMoveDto.getGameId();
        GameState gameState = getGameFromRegistry(gameId, userId);
        ValidationResult validationResult = checkMoveData(doMoveDto, gameState, userId);
        if (!validationResult.isSuccess()) {
            throw new BusinessException(BusinessExceptionType.MOVE_VALIDATION_FAILED, validationResult.getError().name(),
                    Arrays.toString(validationResult.getParameters()));
        }
        try {
            doMove(doMoveDto, gameState);
        } catch (BusinessException e) {
            //tryToRepare(gameId, userInfo);
            throw e;
        }
        notifyCompetitors(gameState, userInfo);

        return gameService.getGameById(gameId, userInfo);
    }

    private void tryToRepare(Long gameId, UserInfo userInfo) {
        // todo - temporary solution
        removeGameFromRegistry(gameId);
        Long gamerId = userInfo.getId();
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
        if (gamerAccount.getIsBot()) {
            Long[] ids = new Long[] {gameId, gamerId};
            publishEvent(BusinessEventBuilder.<Long[]>initBuilder().setType(BusinessEventType.PLAY_BOT).setPayload(ids).toEvent());
        }
    }

    @Override
    public void removeGameFromRegistry(Long gameId) {
        gameRegistry.remove(gameId);
    }

    @Override
    public void clearGameRegistry() {
        gameRegistry.evict();
    }

    private void notifyCompetitors(GameState gameState, UserInfo userInfo) {
        Set<CompetitorAccount> accounts = gameState.getCompetitorAccounts();
        List<String> playerNames = new ArrayList<>();
        for (CompetitorAccount account : accounts) {
            playerNames.add(account.<CompetitorAccountExtraData>getExtraData().getUserName());
        }
        for (CompetitorAccount account : accounts) {
            GameStateExtraData extraData = gameState.getExtraData();
            CompetitorAccountExtraData accountExtraData = account.getExtraData();
            Long gameId = extraData.getGameId();
            Long gamerId = accountExtraData.getGamerId();

            if (gamerId.equals(userInfo.getId())) {
                continue;
            }

            if (accountExtraData.isBot()) {
                Long[] ids = new Long[] {gameId, gamerId};
                publishEvent(BusinessEventBuilder.<Long[]>initBuilder().setType(BusinessEventType.PLAY_BOT).setPayload(ids).toEvent());
            } else {
                String userName = userInfo.getUserName();
                GameEventDto gameEventDto = new GameEventDto(gameId, userName);
                sendNotification(gamerId,
                        extraData.getGameStatus().equals(FINISHED) ? GameEventType.GAME_FINISHED : GameEventType.MOVE_DONE, gameEventDto);
                GamerAccount recipient = gamerAccountDao.findByPrimaryKey(gamerId);
                if (!userSessionTrackingService.isUserOnline(recipient.getUserName())) {
                    mailPreparationService.prepareMoveDoneMessage(recipient.getUserName(), recipient.getEmail(),
                            userName, gameState.getMovesQuantity(), playerNames, recipient.getLocale());
                }
            }
        }
    }

    private <T> void sendNotification(Long gamerId, GameEventType userNotificationType,
                                      T notificationBody) {
        UserNotification<T> notification =
                new UserNotification<>(null, userNotificationType, notificationBody);
        messagingService.send(gamerId, notification);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void doMove(DoMoveDto doMoveDto, GameState gameState) {
        MovePerformer movePerformer = getMovePerformer(gameState.<GameStateExtraData>getExtraData().getGameVariantId());
        try {
            GameVariantInfo gameVariantInfo = gameVariantService.getGameVariantInfo(gameState.<GameStateExtraData>getExtraData().getGameVariantId());
            int currentMoveOrder = gameState.getCurrentMoveOrder();
            movePerformer.doMove(createMoveData(doMoveDto, gameState, gameVariantInfo), gameState);
            if (gameState.isFinished()) {
                finishGame(gameState);
            }
            // remove used card
            CompetitorAccountExtraData competitorAccountExtraData = gameState.getCompetitorAccount(currentMoveOrder).getExtraData();
            competitorAccountExtraData.removeCard(doMoveDto.getAppliedCardId());
        } catch (NotEnoughSharesException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(BusinessExceptionType.NOT_ENOUGH_SHARES);
        } catch (SharesLockedException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(BusinessExceptionType.SHARES_LOCKED);
        } catch (GameIsFinishedException e) {
            throw new BusinessException(BusinessExceptionType.GAME_FINISHED);
        } catch (ShareNotFoundException | SharePriceAlreadyChangedException e) {
            throw new ApplicationException(e);
        } catch (NotEnoughFundsException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(BusinessExceptionType.NOT_ENOUGH_FUNDS);
        }
    }

    private void finishGame(GameState gameState) {
        Game game = gameDao.findByPrimaryKey(gameState.<GameStateExtraData>getExtraData().getGameId());

        Date finishedTime = new Date();

        game.setGameStatus(FINISHED);
        game.setFinishedTime(finishedTime);
        Integer winnerMoveOrder = determineWinner(gameState);

        GameSeriesResult gameSeriesResult;
        GameSeries gameSeries = game.getGameSeries();
        Long gameSeriesId = gameSeries.getId();
        gameSeriesResult = gameSeriesResultDao.findByPrimaryKey(gameSeriesId);
        if (gameSeriesResult == null) {
            gameSeriesResult = new GameSeriesResult();
            gameSeriesResult.setGameSeriesId(gameSeriesId);
            gameSeriesResult.setGameVariantId(gameSeries.getGameVariantId());
            gameSeriesResult.setSwitchMoveOrder(gameSeries.getSwitchMoveOrder());
            gameSeriesResult.setPlayWithBot(gameSeries.getPlayWithBot());
            gameSeriesResult.setRulesVersion(gameSeries.getRulesVersion());
            gameSeriesResult.setCompetitorsQuantity(gameSeries.getCompetitorsQuantity());

            gameSeriesResultDao.create(gameSeriesResult);

            gameSeriesResult.setCompetitorResults(new HashSet<CompetitorResult>(gameSeries.getCompetitorsQuantity()));
            gameSeriesResult.setCompetitorDiffs(new HashSet<CompetitorDiff>(gameSeries.getCompetitorsQuantity()));
        }

        Set<CompetitorResult> gameCompetitorResults = new HashSet<>(game.getCompetitors().size());
        Set<CompetitorDiff> gameCompetitorDiffs = new HashSet<>(game.getCompetitors().size());
        for (Competitor competitor : game.getCompetitors()) {
            CompetitorResult competitorResult = new CompetitorResult();
            competitorResult.setGameId(game.getId());
            competitorResult.setGameSeriesId(gameSeriesId);
            competitorResult.setGamerId(competitor.getGamerId());
            competitorResult.setMoveOrder(competitor.getMoveOrder());
            competitorResult.setTotalFunds(gameState.getCompetitorAccount(competitor.getMoveOrder()).getTotal());
            if (competitor.getMoveOrder().equals(winnerMoveOrder)) {
                competitorResult.setTotalPoints(1f);
                competitorResult.setWinner(true);
            } else {
                competitorResult.setOut(gameState.getCompetitorAccount(competitor.getMoveOrder()).isOut());
                competitorResult.setTotalPoints(0f);
            }
            gameCompetitorResults.add(competitorResult);

            for (Competitor competitor1 : game.getCompetitors()) {
                if (competitor.getGamerId().equals(competitor1.getGamerId())) {
                    continue;
                }
                CompetitorDiff competitorDiff = new CompetitorDiff();
                competitorDiff.setGameId(game.getId());
                competitorDiff.setGameSeriesId(gameSeriesId);
                competitorDiff.setFirstGamerId(competitor.getGamerId());
                competitorDiff.setSecondGamerId(competitor1.getGamerId());
                int total = gameState.getCompetitorAccount(competitor.getMoveOrder()).getTotal();
                int total1 = gameState.getCompetitorAccount(competitor1.getMoveOrder()).getTotal();
                competitorDiff.setFundsAbsoluteDiff(total - total1);
                if (total != 0 && total1 != 0) {
                    competitorDiff.setFundsRelativeDiff((double) (total / total1));
                }

                gameCompetitorDiffs.add(competitorDiff);
            }
        }
        gameSeriesResult.getCompetitorResults().addAll(gameCompetitorResults);
        gameSeriesResult.getCompetitorDiffs().addAll(gameCompetitorDiffs);

        gameSeriesResultDao.update(gameSeriesResult);

        updateScores(gameSeriesResult, gameCompetitorResults);

        gameSeries.setFinishedTime(finishedTime);

        int gamesQuantity = gameDao.countGamesInSeries(gameSeriesId);

        Set<CompetitorResult> gameSeriesCompetitorResults = filterGameSeriesCompetitorResults(gameSeriesResult.getCompetitorResults());
        if (gameSeriesCompetitorResults.isEmpty()) {
            for (CompetitorResult gameCompetitorResult : gameCompetitorResults) {
                CompetitorResult cr = DtoMapper.map(gameCompetitorResult, CompetitorResult.class);
                cr.setGameId(null);
                cr.setMoveOrder(null);
                gameSeriesResult.getCompetitorResults().add(cr);
            }

            gameSeriesResultDao.update(gameSeriesResult);
        } else {
            for (CompetitorResult gameSeriesCompetitorResult : gameSeriesCompetitorResults) {
                for (CompetitorResult gameCompetitorResult : gameCompetitorResults) {
                    if (gameSeriesCompetitorResult.getGamerId().equals(gameCompetitorResult.getGamerId())) {
                        gameSeriesCompetitorResult.setTotalFunds(gameSeriesCompetitorResult.getTotalFunds() + gameCompetitorResult.getTotalFunds());
                        gameSeriesCompetitorResult.setTotalPoints(gameSeriesCompetitorResult.getTotalPoints()
                                + gameCompetitorResult.getTotalPoints());
                    }
                }
            }

            float greatestPoints = 1f;
            CompetitorResult resultWithGreatestPoints = null;
            for (CompetitorResult gameSeriesCompetitorResult : gameSeriesCompetitorResults) {
                gameSeriesCompetitorResult.setWinner(false);
                float currentPoints = gameSeriesCompetitorResult.getTotalPoints();
                if (currentPoints > greatestPoints) {
                    greatestPoints = currentPoints;
                    resultWithGreatestPoints = gameSeriesCompetitorResult;
                }
            }

            if (resultWithGreatestPoints != null) {
                resultWithGreatestPoints.setWinner(true);
            } else {
                for (CompetitorResult gameSeriesCompetitorResult : gameSeriesCompetitorResults) {
                    gameSeriesCompetitorResult.setDraw(true);
                }
            }

            gameSeriesResultDao.update(gameSeriesResult);
        }

        Set<CompetitorDiff> gameSeriesCompetitorDiffs = filterGameSeriesCompetitorDiffs(gameSeriesResult.getCompetitorDiffs());
        if (gameSeriesCompetitorDiffs.isEmpty()) {
            for (CompetitorDiff gameCompetitorDiff : gameCompetitorDiffs) {
                CompetitorDiff cd = DtoMapper.map(gameCompetitorDiff, CompetitorDiff.class);
                cd.setGameId(null);
                gameSeriesResult.getCompetitorDiffs().add(cd);
            }

            gameSeriesResultDao.update(gameSeriesResult);
        } else {
            for (CompetitorDiff gameSeriesCompetitorDiff : gameSeriesCompetitorDiffs) {
                for (CompetitorResult gameSeriesCompetitorResult : gameSeriesCompetitorResults) {
                    for (CompetitorResult gameSeriesCompetitorResult1 : gameSeriesCompetitorResults) {
                        if (gameSeriesCompetitorResult.getGamerId().equals(gameSeriesCompetitorDiff.getFirstGamerId())
                                && gameSeriesCompetitorResult1.getGamerId().equals(gameSeriesCompetitorDiff.getSecondGamerId())) {
                            gameSeriesCompetitorDiff.setFundsAbsoluteDiff(
                                    gameSeriesCompetitorResult.getTotalFunds() - gameSeriesCompetitorResult1.getTotalFunds());
                            gameSeriesCompetitorDiff.setFundsRelativeDiff(
                                    (double) (gameSeriesCompetitorResult.getTotalFunds()
                                            / (gameSeriesCompetitorResult1.getTotalFunds() == 0 ? 1 : gameSeriesCompetitorResult1.getTotalFunds())));
                        }
                    }
                }
            }

            gameSeriesResultDao.update(gameSeriesResult);
        }

        if (gameSeries.getSwitchMoveOrder() && gamesQuantity < gameSeries.getCompetitorsQuantity()) {
            publishEvent(BusinessEventBuilder.<Long>initBuilder()
                    .setType(BusinessEventType.SWITCH_MOVE_ORDER).setPayload(game.getId()).toEvent());
        } else {
            gameSeries.setCompleted(true);
            gameSeriesResult.setFinishedTime(game.getFinishedTime());
        }

        gameSeriesResultDao.update(gameSeriesResult);
    }

    private Set<CompetitorDiff> filterGameSeriesCompetitorDiffs(Set<CompetitorDiff> competitorDiffs) {
        Set<CompetitorDiff> gameSeriesCompetitorDiffs = new HashSet<>();
        for (CompetitorDiff competitorDiff : competitorDiffs) {
            if (competitorDiff.getGameId() == null) {
                gameSeriesCompetitorDiffs.add(competitorDiff);
            }
        }
        return gameSeriesCompetitorDiffs;
    }

    private Set<CompetitorResult> filterGameSeriesCompetitorResults(Set<CompetitorResult> competitorResults) {
        Set<CompetitorResult> gameSeriesCompetitorResults = new HashSet<>();
        for (CompetitorResult competitorResult : competitorResults) {
            if (competitorResult.getGameId() == null) {
                gameSeriesCompetitorResults.add(competitorResult);
            }
        }
        return gameSeriesCompetitorResults;
    }

    private MoveData createMoveData(DoMoveDto doMoveDto, GameState gameState, GameVariantInfo gameVariantInfo) {
        Set<BuySellAction> firstBuySellActions = new HashSet<>();
        Set<BuySellDto> buySellDtos = doMoveDto.getFirstBuySellActions();
        for (BuySellDto buySellDto : buySellDtos) {
            firstBuySellActions.add(new BuySellAction(buySellDto.getShareId(), buySellDto.getBuySellQuantity()));
        }

        Set<PriceChangeAction> priceChangeActions = new TreeSet<>();
        Long cardId = gameState.getCurrentCompetitorState().<CompetitorAccountExtraData>getExtraData().getCardId(doMoveDto.getAppliedCardId());

        MoveExtraData moveExtraData = new MoveExtraData();

        moveExtraData.setAppliedCardId(doMoveDto.getAppliedCardId());

        Set<Long> priceOperationIds = gameVariantInfo.getPriceOperationIds(cardId);
        for (Long priceOperationId : priceOperationIds) {
            Long shareId = gameVariantInfo.getShareIdForPriceOperation(cardId, priceOperationId);
            if (shareId == null) {
                for (PriceOperationDto priceOperation : doMoveDto.getPriceOperations()) {
                    if (priceOperationId.equals(priceOperation.getPriceOperationId())) {
                        shareId = priceOperation.getShareId();
                    }
                }
            }
            moveExtraData.getPriceOperationMap().put(shareId, priceOperationId);

            PriceOperation priceOperation = gameVariantInfo.getPriceOperation(cardId, priceOperationId);
            priceChangeActions.add(new PriceChangeAction(shareId, priceOperation.getPriceOperationType().getOperation(),
                    priceOperation.getOperandValue()));
        }

        Set<BuySellAction> lastBuySellActions = new HashSet<>();
        buySellDtos = doMoveDto.getLastBuySellActions();
        for (BuySellDto buySellDto : buySellDtos) {
            lastBuySellActions.add(new BuySellAction(buySellDto.getShareId(), buySellDto.getBuySellQuantity()));
        }

        MoveData<MoveExtraData> moveData = new MoveData<>(firstBuySellActions, priceChangeActions, lastBuySellActions);
        moveData.setExtraData(moveExtraData);

        return moveData;
    }

    private MovePerformer getMovePerformer(Long gameVariantId) {
        if (!movePerformerRegistry.contains(gameVariantId)) {
            MovePerformer movePerformer = new MovePerformerImpl();
            movePerformer.addBeforeCommitListener(new BeforeCommitListener() {
                @Override
                public void beforeCommit(MoveData moveData, GameState gameState, MoveResult moveResult) {
                    updateGame(moveData, gameState, moveResult);
                }
            });
            movePerformerRegistry.put(gameVariantId, movePerformer);
        }
        return movePerformerRegistry.get(gameVariantId);
    }

    private void updateGame(MoveData moveData, GameState gameState, MoveResult moveResult) {
        GameStateExtraData gameStateExtraData = gameState.getExtraData();
        Long gameId = gameStateExtraData.getGameId();
        int currentMoveNumber = gameState.getCurrentMoveNumber();
        int currentMoveOrder = gameState.getCurrentMoveOrder();

        Game game = gameDao.findByPrimaryKey(gameId);
        Move move = getOrCreateLastMove(game, currentMoveNumber);

        Competitor currentCompetitor = getCurrentCompetitor(game, currentMoveOrder);
        Map<Integer, CompetitorMove> lastCompetitorMoves = getLastCompetitorMoves(game);

        CompetitorMove competitorMove = createCompetitorMove(moveData, moveResult, gameState, currentCompetitor);
        competitorMove.setMove(move);
        move.getCompetitorMoves().add(competitorMove);

        SortedMap<Integer, RepurchaseResult> repurchaseResultMap = moveResult.getPriceChangeStepResult().getRepurchaseResult();
        if (repurchaseResultMap.size() > 0) {
            for (Competitor competitor : game.getCompetitors()) {
                if (repurchaseResultMap.containsKey(competitor.getMoveOrder())) {
                    if (gameState.getCompetitorAccount(competitor.getMoveOrder()).isOut()) {
                        competitor.setOut(true);
                        competitor.setTotalFunds(gameState.getCompetitorAccount(competitor.getMoveOrder()).getTotal());
                    }
                    addRepurchaseStep(competitor.getMoveOrder(), moveResult, gameState, competitorMove, lastCompetitorMoves);
                }
            }
        }

        createCompensations(moveResult, gameState, competitorMove, lastCompetitorMoves);

        gameDao.update(game);
    }

    private Map<Integer, CompetitorMove> getLastCompetitorMoves(Game game) {
        TreeSet<Move> sortedMoves = new TreeSet<>(game.getMoves());
        Move lastMove = sortedMoves.pollLast();
        Move prevMove = sortedMoves.last();
        Set<Move> lastMoves = new TreeSet<>();
        lastMoves.add(prevMove);
        lastMoves.add(lastMove);

        Map<Integer, CompetitorMove> lastCompetitorMoves = new TreeMap<>();
        for (Move move : lastMoves) {
            Set<CompetitorMove> competitorMoves = move.getCompetitorMoves();
            for (CompetitorMove competitorMove : competitorMoves) {
                lastCompetitorMoves.put(competitorMove.getMoveOrder(), competitorMove);
            }
        }

        return lastCompetitorMoves;
    }

    private void createCompensations(MoveResult moveResult, GameState gameState, CompetitorMove competitorMove,
                                     Map<Integer, CompetitorMove> lastCompetitorMoves) {
        PriceChangeStepResult stepResult = moveResult.getPriceChangeStepResult();
        TreeSet<MoveStep> moveSteps = new TreeSet<>(competitorMove.getSteps());
        MoveStep priceChangeStep = findPriceStep(moveSteps);
        for (CompetitorAccount account : gameState.getCompetitorAccounts()) {
            int moveOrder = account.getMoveOrder();
            if (moveOrder == gameState.getCurrentCompetitorState().getMoveOrder()) {
                continue;
            }
            Map<Long, Compensation> compensationMap = new TreeMap<>();
            for (PriceChangeActionResult priceChangeActionResult : stepResult.getPriceChangeActionResults()) {
                if (priceChangeActionResult.getCompetitorCompensationsMap().containsKey(moveOrder)) {
                    compensationMap.put(priceChangeActionResult.getShareId(),
                            priceChangeActionResult.getCompetitorCompensationsMap().get(moveOrder));
                }
            }
            if (!compensationMap.isEmpty()) {
                createCompetitorCompensations(moveOrder, compensationMap, lastCompetitorMoves, priceChangeStep);
            }
        }
    }

    private void createCompetitorCompensations(int moveOrder, Map<Long, Compensation> compensationMap,
                                               Map<Integer, CompetitorMove> lastCompetitorMoves, MoveStep step) {
        CompetitorMove lastMove = lastCompetitorMoves.get(moveOrder);
        MoveStep compensationStep = new MoveStep();
        compensationStep.setStepType(StepType.COMPENSATION_STEP);
        compensationStep.setOriginalStep(step);
        compensationStep.setCompetitorMove(lastMove);

        Set<com.stockholdergame.server.model.game.Compensation> compensations = new HashSet<>();
        for (Map.Entry<Long, Compensation> entry : compensationMap.entrySet()) {
            compensationStep.setCashValue(entry.getValue().getCashValue());
            compensations.add(new com.stockholdergame.server.model.game.Compensation(null, entry.getKey(), entry.getValue().getCompensationSum(),
                    compensationStep));
        }

        compensationStep.setCompensations(compensations);
        lastMove.getSteps().add(compensationStep);
    }

    private MoveStep findPriceStep(Set<MoveStep> moveSteps) {
        for (MoveStep moveStep : moveSteps) {
            if (moveStep.getStepType().equals(PRICE_CHANGE_STEP) || moveStep.getStepType().equals(ZERO_STEP)) {
                return moveStep;
            }
        }
        throw new ApplicationException(getMessage(MOVE_NOT_ENDED_PROPERLY));
    }

    private void addRepurchaseStep(int moveOrder, MoveResult moveResult, GameState gameState, CompetitorMove competitorMove,
                                   Map<Integer, CompetitorMove> lastCompetitorMoves) {
        TreeSet<MoveStep> moveSteps = new TreeSet<>(competitorMove.getSteps());
        MoveStep priceChangeStep = findPriceStep(moveSteps);

        PriceChangeStepResult priceChangeStepResult = moveResult.getPriceChangeStepResult();
        if (priceChangeStepResult.getRepurchaseResult().get(moveOrder) == null) {
            return;
        }

        CompetitorMove lastMove = lastCompetitorMoves.get(moveOrder);
        MoveStep repurchaseStep = new MoveStep();
        repurchaseStep.setStepType(REPURCHASE_STEP);
        repurchaseStep.setOriginalStep(priceChangeStep);
        repurchaseStep.setCompetitorMove(lastMove);
        repurchaseStep.setCashValue(priceChangeStepResult.getRepurchaseResult().get(moveOrder).getCompetitorCash());

        Set<ShareQuantity> shareQuantities = new HashSet<>();
        for (Long shareId : gameState.getShareIds()) {
            BuySellActionResult buySellActionResult =
                    priceChangeStepResult.getRepurchaseResult().get(moveOrder).getBuySellActionResult(shareId);
            ShareQuantityPk qpk = new ShareQuantityPk();
            qpk.setShareId(shareId);
            ShareQuantity sq = new ShareQuantity();
            sq.setId(qpk);
            sq.setStep(repurchaseStep);
            sq.setQuantity(buySellActionResult.getQuantity());
            sq.setBuySellQuantity(buySellActionResult.getBuySellQuantity());
            shareQuantities.add(sq);
        }
        repurchaseStep.setShareQuantities(shareQuantities);
        lastMove.getSteps().add(repurchaseStep);
    }

    private CompetitorMove createCompetitorMove(MoveData moveData, MoveResult moveResult, GameState gameState, Competitor competitor) {
        MoveExtraData moveExtraData = (MoveExtraData) moveData.getExtraData();
        for (CompetitorCard competitorCard : competitor.getCompetitorCards()) {
            if (moveExtraData.getAppliedCardId().equals(competitorCard.getId())) {
                competitorCard.setApplied(true);
                break;
            }
        }

        CompetitorMove competitorMove = new CompetitorMove();
        competitorMove.setMoveOrder(competitor.getMoveOrder());
        competitorMove.setAppliedCardId(moveExtraData.getAppliedCardId());
        competitorMove.setFinishedTime(new Date());

        if (competitorMove.getSteps() == null) {
            competitorMove.setSteps(CollectionsUtil.<MoveStep>newList());
        }

        if (!gameState.isLastMove()) {
            MoveStep before = createStep(moveResult.getFirstBuySellStepResult(), gameState, competitorMove, FIRST_BUY_SELL_STEP);
            competitorMove.getSteps().add(before);
        }

        MoveStep priceChange = createPriceChangeStep(moveData, moveResult, gameState, competitorMove);
        competitorMove.getSteps().add(priceChange);

        if (!gameState.isLastMove()) {
            MoveStep after = createStep(moveResult.getLastBuySellStepResult(), gameState, competitorMove, LAST_BUY_SELL_STEP);
            competitorMove.getSteps().add(after);
        }

        competitorMove.setCompetitor(competitor);
        return competitorMove;
    }

    private MoveStep createPriceChangeStep(MoveData moveData, MoveResult moveResult, GameState gameState, CompetitorMove competitorMove) {
        MoveExtraData moveExtraData = (MoveExtraData) moveData.getExtraData();

        MoveStep priceChange = new MoveStep();
        priceChange.setStepType(PRICE_CHANGE_STEP);
        priceChange.setCompetitorMove(competitorMove);
        PriceChangeStepResult stepResult = moveResult.getPriceChangeStepResult();

        priceChange.setCashValue(stepResult.getCompetitorCash(gameState.getCurrentMoveOrder()));

        Set<SharePrice> sharePrices = new HashSet<>();
        for (Long shareId : gameState.getShareIds()) {
            SharePricePk spk = new SharePricePk();
            spk.setShareId(shareId);
            SharePrice sp = new SharePrice();
            sp.setId(spk);
            sp.setStep(priceChange);
            sp.setPrice(stepResult.getPriceChangeActionResult(shareId).getNewPrice());
            sp.setPriceOperationId(moveExtraData.getPriceOperationMap().get(shareId));
            sharePrices.add(sp);
        }
        priceChange.setSharePrices(sharePrices);

        if (gameState.isLastMove()) {
            Set<ShareQuantity> shareQuantities = new HashSet<>();
            for (Long shareId : gameState.getShareIds()) {
                ShareQuantityPk qpk = new ShareQuantityPk();
                qpk.setShareId(shareId);
                ShareQuantity sq = new ShareQuantity();
                sq.setId(qpk);
                sq.setStep(priceChange);
                sq.setQuantity(gameState.getCurrentCompetitorState().getShareQuantity(shareId));
                shareQuantities.add(sq);
            }
            priceChange.setShareQuantities(shareQuantities);
        }

        return priceChange;
    }

    private MoveStep createStep(BuySellStepResult stepResult, GameState gameState, CompetitorMove competitorMove, StepType stepType) {
        MoveStep step = new MoveStep();
        step.setStepType(stepType);
        step.setCompetitorMove(competitorMove);

        step.setCashValue(stepResult.getCompetitorCash(gameState.getCurrentMoveOrder()));

        Set<ShareQuantity> shareQuantities = new HashSet<>();
        for (Long shareId : gameState.getShareIds()) {
            ShareQuantityPk qpk = new ShareQuantityPk();
            qpk.setShareId(shareId);
            ShareQuantity sq = new ShareQuantity();
            sq.setId(qpk);
            sq.setStep(step);
            sq.setQuantity(stepResult.getBuySellActionResult(shareId).getQuantity());
            sq.setBuySellQuantity(stepResult.getBuySellActionResult(shareId).getBuySellQuantity());
            shareQuantities.add(sq);
        }
        step.setShareQuantities(shareQuantities);
        return step;
    }

    private Integer determineWinner(GameState gameState) {
        int greatestTotal = 0;
        int winnerMoveOrder = 0;
        for (CompetitorAccount competitorAccount : gameState.getCompetitorAccounts()) {
            int total = competitorAccount.getTotal();
            if (total > greatestTotal) {
                greatestTotal = total;
                winnerMoveOrder = competitorAccount.getMoveOrder();
            }
        }
        return winnerMoveOrder;
    }

    private Competitor getCurrentCompetitor(Game game, int currentMoveOrder) {
        for (Competitor competitor : game.getCompetitors()) {
            if (competitor.getMoveOrder() == currentMoveOrder) {
                return competitor;
            }
        }
        throw new ApplicationException("No competitors with move order " + currentMoveOrder);
    }

    private Move getOrCreateLastMove(Game game, int currentMoveNumber) {
        TreeSet<Move> sortedMoves = new TreeSet<>(game.getMoves());
        Move lastMove = sortedMoves.last();

        if (lastMove.getMoveNumber() != currentMoveNumber) {
            Move move = new Move();
            move.setMoveNumber(currentMoveNumber);
            move.setGame(game);
            move.setCompetitorMoves(CollectionsUtil.<CompetitorMove>newSet());
            game.getMoves().add(move);
            return move;
        } else {
            return lastMove;
        }
    }

    private void updateScores(GameSeriesResult gameSeriesResult, Set<CompetitorResult> gameCompetitorResults) {
        for (CompetitorResult gameCompetitorResult : gameCompetitorResults) {
            for (CompetitorResult gameCompetitorResult1 : gameCompetitorResults) {
                if (gameCompetitorResult.getGamerId().equals(gameCompetitorResult1.getGamerId())
                        || (gameCompetitorResult.getOut() && gameCompetitorResult1.getOut())) {
                    continue;
                }
                scoreService.updateScore(gameCompetitorResult.getGamerId(), gameCompetitorResult1.getGamerId(),
                        gameSeriesResult.getGameVariantId(), gameCompetitorResult.getMoveOrder(),
                        gameCompetitorResult.getWinner() || gameCompetitorResult.getTotalFunds() > gameCompetitorResult1.getTotalFunds(),
                        gameCompetitorResult.getOut(), gameSeriesResult.getRulesVersion());
            }
        }
    }

    private GameState getGameFromRegistry(Long gameId, Long gamerId) {
        if (!gameRegistry.contains(gameId)) {
            Game game = gameDao.findGameByIdAndUserIdAndStatus(gameId, gamerId, RUNNING);
            if (game == null) {
                throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
            } else {
                putGame(game);
            }
        }
        GameState gameState = gameRegistry.get(gameId);
        if (gameState.<GameStateExtraData>getExtraData().getGamerIds().contains(gamerId)) {
            return gameState;
        } else {
            throw new BusinessException(BusinessExceptionType.GAME_NOT_FOUND, gameId);
        }
    }

    private ValidationResult checkMoveData(DoMoveDto doMoveDto, GameState gameState, Long currentUserId) {
        GameVariantInfo gameVariantInfo = gameVariantService.getGameVariantInfo(gameState.<GameStateExtraData>getExtraData().getGameVariantId());
        return MoveValidator.validate(doMoveDto, gameState, gameVariantInfo, currentUserId);
    }

    private void putGame(Game game) {
        Validate.notNull(game);

        if (!GameStatus.RUNNING.equals(game.getGameStatus())) {
            throw new ApplicationException(getMessage(ILLEGAL_GAME_STATUS, game.getGameStatus().name()));
        }

        if (!gameRegistry.contains(game.getId())) {
            putInRegistry(game);
        }
    }

    private void putInRegistry(Game game) {
        GameState gameState = GameStateCreator.createGameState(game);
        try {
            gameRegistry.put(gameState.<GameStateExtraData>getExtraData().getGameId(), gameState);
        } catch (RegistryAlmostFullException e) {
            Message message = MessageBuilder.withPayload(e).build();
            exceptionHandlingChannel.send(message);
        }
    }
}
