package com.stockholdergame.server.web.controller;

import com.google.common.collect.Sets;
import com.stockholdergame.server.dto.game.*;
import com.stockholdergame.server.dto.game.lite.CompetitorLite;
import com.stockholdergame.server.dto.game.lite.GameLite;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.dto.game.result.CompetitorDiffDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.facade.GameFacade;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.session.UserSessionUtil;
import com.stockholdergame.server.web.dto.*;
import com.stockholdergame.server.web.dto.game.*;
import com.stockholdergame.server.web.dto.game.InvitationAction;
import com.stockholdergame.server.web.dto.player.Player;
import com.stockholdergame.server.web.dto.swaggerstub.ResponseWrapperGameListResponse;
import com.stockholdergame.server.web.dto.swaggerstub.ResponseWrapperGameSet;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "/", authorizations = { @Authorization("Bearer") }, tags = "Game API")
@Controller
@RequestMapping("/game")
public class GameController {

    private GameVariantMapHolder holder;

    @Autowired
    private GameFacade gameFacade;

    @PostConstruct
    private void postConstruct() {
        holder = new GameVariantMapHolder(gameFacade);
    }

    @ApiOperation("Create new game")
    @RequestMapping(value = "/new", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<Long> startGame(@RequestBody NewGame newGame) {
        GameInitiationDto gameInitiationDto = new GameInitiationDto();
        gameInitiationDto.setGameVariantId(matchCardOptionToGameVariant(newGame.cardOption));
        gameInitiationDto.setOffer(false);
        gameInitiationDto.setSwitchMoveOrder(true);
        gameInitiationDto.setInvitedUsers(newGame.invitedPlayers != null ? new ArrayList<>(newGame.invitedPlayers) : null);
        gameInitiationDto.setPlayWithComputer(newGame.playWithComputer);
        GameStatusDto gameStatusDto = gameFacade.initiateGame(gameInitiationDto);
        return ResponseWrapper.ok(gameStatusDto.getGameId());
    }

    @ApiOperation("Accept/reject/cancel invitation")
    @RequestMapping(value = "/{gameSetId}/invitation", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> performInvitationAction(@PathVariable("gameSetId") Long gameSetId,
                                                      @RequestParam("action") InvitationAction invitationAction) {
        if (invitationAction.equals(InvitationAction.accept)) {
            gameFacade.joinToGameByGameSetId(gameSetId);
        } else if (invitationAction.equals(InvitationAction.reject)) {
            ChangeInvitationStatusDto changeInvitationStatusDto = new ChangeInvitationStatusDto();
            changeInvitationStatusDto.setGameSetId(gameSetId);
            changeInvitationStatusDto.setStatus(InvitationStatus.REJECTED.name());
            gameFacade.changeInvitationStatus(changeInvitationStatusDto);
        } else if (invitationAction.equals(InvitationAction.cancel)) {
            ChangeInvitationStatusDto changeInvitationStatusDto = new ChangeInvitationStatusDto();
            changeInvitationStatusDto.setGameSetId(gameSetId);
            changeInvitationStatusDto.setStatus(InvitationStatus.CANCELLED.name());
            gameFacade.changeInvitationStatus(changeInvitationStatusDto);
        } else {
            return ResponseWrapper.error(ErrorBody.of("Unknown action"));
        }
        return ResponseWrapper.ok();
    }

    @ApiOperation("Make a turn")
    @RequestMapping(value = "/{gameId}/turn", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> doTurn(@PathVariable("gameId") Long gameId, @RequestBody Turn turn) {
        gameFacade.doMove(buildDoMoveDto(gameId, turn));
        return ResponseWrapper.ok();
    }

    @ApiOperation(value = "Get game by identifier")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = ResponseWrapperGameSet.class)})
    @RequestMapping(value = "/{gameId}/report", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<GameSet> getGameReport(@PathVariable("gameId") Long gameId) {
        GameDto gameDto = gameFacade.getGameById(gameId);
        return ResponseWrapper.ok(convertToGameSet(gameDto));
    }

    @ApiOperation("Get games list")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = ResponseWrapperGameListResponse.class)})
    @RequestMapping(value = "/{gameOptionFilter}/{gameStatus}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<GameListResponse> getGames(@PathVariable("gameOptionFilter") GameOptionFilter gameOptionFilter,
                                                      @PathVariable("gameStatus") GameStatus gameStatus,
                                                      @RequestParam(value = "playerNamePrefix", required = false) String playerNamePrefix,
                                                      @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
                                                      @RequestParam(value = "ipp", defaultValue = "10", required = false) int itemsPerPage) {
        return ResponseWrapper.ok(convertToGameListResponse(
                gameFacade.getGames(buildGameFilterDto(gameOptionFilter, gameStatus, playerNamePrefix, offset, itemsPerPage)),
                offset, itemsPerPage));
    }

    private GameListResponse convertToGameListResponse(GamesList games, int offset, int itemsPerPage) {
        return GameListResponse.of(buildGamesList(games), Pagination.of(games.getTotalCount(), offset, itemsPerPage));
    }

    private List<GameSet> buildGamesList(GamesList games) {
        final String userName = UserSessionUtil.getCurrentUser().getUserName();
        List<GameSet> gameList = new ArrayList<>();
        games.getGames().forEach(gameLite -> {
            Game game = new Game();
            game.id = gameLite.getId();
            game.letter = GameLetter.valueOf(gameLite.getGameLetter());
            game.status = convertGameStatus(gameLite.getGameStatus());
            if (!gameLite.getGameStatus().equals(com.stockholdergame.server.model.game.GameStatus.OPEN.name())) {
                game.players = buildPlayersOrder(gameLite.getCompetitors());
            }
            if (gameLite.getGameStatus().equals(com.stockholdergame.server.model.game.GameStatus.RUNNING.name())) {
                game.position = buildPosition(gameLite, userName);
            } else if (gameLite.getGameStatus().equals(com.stockholdergame.server.model.game.GameStatus.FINISHED.name())) {
                game.result = buildResult(gameLite);
            }

            GameSet gameSet = new GameSet();
            gameSet.id = gameLite.getGameSeriesId();
            gameSet.options = buildGameOptions(gameLite.getGameVariantId());
            gameSet.label = gameLite.getLabel();
            gameSet.status = convertGameStatus(gameLite.getGameStatus());
            gameSet.players = buildPlayers(gameLite.getCompetitors());
            gameSet.games = new HashSet<>();
            gameSet.games.add(game);

            gameList.add(gameSet);
        });
        return gameList;
    }

    private Set<GameResult> buildResult(GameLite gameLite) {
        Set<GameResult> gameResultSet = Sets.newTreeSet(Comparator.comparingInt(o -> o.turnOrder));
        gameLite.getCompetitors().forEach(competitorLite -> {
            PlayerResult playerResult = new PlayerResult();
            playerResult.name = competitorLite.getUserName();
            playerResult.bankrupt = competitorLite.isOut();
            playerResult.winner = competitorLite.isWinner();
            playerResult.totalPoints = competitorLite.isWinner() ? 1 : 0;
            playerResult.totalFunds = competitorLite.getTotalFunds();

            GameResult gameResult = new GameResult();
            gameResult.turnOrder = competitorLite.getMoveOrder();
            gameResult.result = playerResult;
            gameResultSet.add(gameResult);
        });
        return gameResultSet;
    }

    private boolean isMyTurn(GameLite gameLite, String userName, int turn) {
        for (CompetitorLite competitor : gameLite.getCompetitors()) {
            if (competitor.getUserName().equals(userName) && turn == competitor.getMoveOrder()) {
                return true;
            }
        }
        return false;
    }

    private Set<GamePlayer> buildPlayersOrder(Set<CompetitorLite> competitors) {
        Set<GamePlayer> gamePlayers = Sets.newTreeSet(Comparator.comparingInt(o -> o.turnOrder));
        competitors.forEach(competitorLite -> {
            GamePlayer gamePlayer = new GamePlayer();
            gamePlayer.name = competitorLite.getUserName();
            gamePlayer.turnOrder = competitorLite.getMoveOrder();
            gamePlayers.add(gamePlayer);
        });
        return gamePlayers;
    }

    private PlayPosition buildPosition(GameLite gameLite, String userName) {
        PlayPosition playPosition = new PlayPosition();
        Pair<Integer, Integer> currentRoundTurn = calculateCurrentTurn(gameLite.getCompetitorsQuantity(),
                gameLite.getLastMoveNumber(), gameLite.getLastMoveOrder());
        playPosition.round = currentRoundTurn.getLeft();
        playPosition.turn = currentRoundTurn.getRight();
        playPosition.myTurn = isMyTurn(gameLite, userName, playPosition.turn);
        return playPosition;
    }

    private Pair<Integer, Integer> calculateCurrentTurn(int competitorsQuantity, int lastMoveNumber, int lastMoveOrder) {
        int round = lastMoveNumber;
        int turn = lastMoveOrder;
        if (lastMoveOrder == competitorsQuantity) {
            round++;
            turn = 1;
        } else {
            turn++;
        }
        return Pair.of(round, turn);
    }

    private Set<GameSetPlayer> buildPlayers(Set<CompetitorLite> competitors) {
        Set<GameSetPlayer> players = new HashSet<>();
        competitors.forEach(competitorDto -> {
            GameSetPlayer gameSetPlayer = new GameSetPlayer();
            Player player = new Player();
            player.name = competitorDto.getUserName();
            player.removed = competitorDto.isRemoved();
            player.bot = competitorDto.isBot();
            gameSetPlayer.player = player;
            gameSetPlayer.initiator = competitorDto.isInitiator();
            if (competitorDto.isInvitation()) {
                PlayerInvitation invitation = new PlayerInvitation();
                invitation.statusSetAt = getStatusChangedTime(competitorDto);
                invitation.invitationStatus = convertInvitationStatus(competitorDto.getInvitationStatus());
                gameSetPlayer.invitation = invitation;
            }
            players.add(gameSetPlayer);
        });
        return players;
    }

    private LocalDateTime getStatusChangedTime(CompetitorLite competitorDto) {
        Date date = null;
        switch (InvitationStatus.valueOf(competitorDto.getInvitationStatus())) {
            case CREATED:
                date = competitorDto.getInvitationCreated();
                break;
            case ACCEPTED:
                date = competitorDto.getJoined();
        }

        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    private com.stockholdergame.server.web.dto.game.InvitationStatus convertInvitationStatus(String invitationStatus) {
        return invitationStatus.equals("CREATED") ? com.stockholdergame.server.web.dto.game.InvitationStatus.PENDING :
                com.stockholdergame.server.web.dto.game.InvitationStatus.valueOf(invitationStatus);
    }

    private GameFilterDto buildGameFilterDto(GameOptionFilter gameOptionFilter,
                                             GameStatus gameStatus,
                                             String playerNamePrefix,
                                             int offset, int itemsPerPage) {
        GameFilterDto gameFilterDto = new GameFilterDto();
        gameFilterDto.setGameStatus(gameStatus.equals(GameStatus.CREATED) ? "OPEN" : gameStatus.name());
        gameFilterDto.setGameVariantId(convertToGameVariant(gameOptionFilter));
        gameFilterDto.setPlayersNumber(
                gameOptionFilter.equals(GameOptionFilter.game_3x5) || gameOptionFilter.equals(GameOptionFilter.game_4x6) ? 2 : null);
        gameFilterDto.setUserName(playerNamePrefix);
        gameFilterDto.setInitiator(true);
        gameFilterDto.setNotInitiator(true);
        gameFilterDto.setOffset(offset);
        gameFilterDto.setMaxResults(itemsPerPage);
        return gameFilterDto;
    }

    private Long convertToGameVariant(GameOptionFilter gameOptionFilter) {
        CardOption cardOption = gameOptionFilter.getCardOption();
        if (cardOption != null) {
            return matchCardOptionToGameVariant(cardOption);
        }
        return null;
    }

    private DoMoveDto buildDoMoveDto(Long gameId, Turn turn) {
        DoMoveDto doMoveDto = new DoMoveDto();
        doMoveDto.setGameId(gameId);
        doMoveDto.setAppliedCardId(turn.cardStep.playerCardId);
        doMoveDto.setFirstBuySellActions(buildBuySellActions(turn.firstBuySellStep));
        doMoveDto.setPriceOperations(buildPriceOperations(turn.cardStep));
        doMoveDto.setLastBuySellActions(buildBuySellActions(turn.lastBuySellStep));
        return doMoveDto;
    }

    private Set<PriceOperationDto> buildPriceOperations(CardStep cardStep) {
        Set<PriceOperationDto> priceOperationDtos = new HashSet<>();
        cardStep.operations.forEach(cardOperation -> {
            PriceOperationDto priceOperationDto = new PriceOperationDto();
            priceOperationDto.setShareId(cardOperation.shareId);
            priceOperationDto.setPriceOperationId(cardOperation.priceOperationId);
            priceOperationDtos.add(priceOperationDto);
        });
        return priceOperationDtos;
    }

    private Set<BuySellDto> buildBuySellActions(Map<Long, Integer> buySellOperations) {
        Set<BuySellDto> buySellDtos = new HashSet<>();
        buySellOperations.forEach((shareId, amount) -> {
            BuySellDto buySellDto = new BuySellDto();
            buySellDto.setShareId(shareId);
            buySellDto.setBuySellQuantity(amount);
            buySellDtos.add(buySellDto);
        });
        return buySellDtos;
    }

    private GameSet convertToGameSet(GameDto gameDto) {
        GameSet gameSet = new GameSet();
        gameSet.id = gameDto.getGameSeriesId();
        gameSet.status = convertGameStatus(gameDto.getGameStatus());
        gameSet.options = buildGameOptions(gameDto.getGameVariantId());
        gameSet.label = gameDto.getLabel();
        gameSet.players = buildPlayers(gameDto);
        gameSet.games = buildGames(gameDto);
        if (gameDto.getGameStatus().equals(GameStatus.FINISHED.name())) {
            gameSet.result = buildGameSetResult(gameDto);
        }
        return gameSet;
    }

    private Set<PlayerResult> buildGameSetResult(GameDto gameDto) {
        Set<PlayerResult> playerResultSet = Sets.newHashSet();
        gameDto.getGameSeriesCompetitorResults().forEach(competitorResultDto -> {
            PlayerResult playerResult = new PlayerResult();
            playerResult.name = competitorResultDto.getUserName();
            playerResult.totalFunds = competitorResultDto.getTotalFunds();
            playerResult.totalPoints = competitorResultDto.getTotalPoints();
            playerResult.winner = competitorResultDto.getWinner();
            playerResult.bankrupt = competitorResultDto.getOut();
            playerResult.fundsDifference = gameDto.getGameSeriesCompetitorDiffs().stream()
                    .filter(competitorDiffDto -> competitorResultDto.getUserName().equals(competitorDiffDto.getFirstUserName()))
                    .map(CompetitorDiffDto::getFundsAbsoluteDiff).findFirst().orElse(0);
            playerResultSet.add(playerResult);
        });
        return playerResultSet;
    }

    private TreeSet<ReportRound> buildRounds(GameDto gameDto) {
        TreeSet<ReportRound> rounds = new TreeSet<>();
        gameDto.getMoves().forEach(moveDto -> {
            ReportRound round = new ReportRound();
            round.round = moveDto.getMoveNumber();
            round.turns = buildTurns(gameDto, moveDto.getCompetitorMoves());
            rounds.add(round);
        });
        return rounds;
    }

    private TreeSet<ReportTurn> buildTurns(GameDto gameDto, Set<CompetitorMoveDto> competitorMoves) {
        TreeSet<ReportTurn> reportTurns = new TreeSet<>();
        competitorMoves.forEach(competitorMoveDto -> {
            ReportTurn turn = new ReportTurn();
            turn.round = competitorMoveDto.getMoveNumber();
            turn.turn = competitorMoveDto.getMoveOrder();
            turn.appliedCardId = competitorMoveDto.getAppliedCardId();
            turn.cardId = getCardId(gameDto, competitorMoveDto.getAppliedCardId());
            turn.finishedTime = competitorMoveDto.getFinishedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            turn.steps = buildTurnSteps(competitorMoveDto.getSteps());
            /*if (turn.round != 0) {
                turn.total = calculateTotal(turn);
            }*/
            reportTurns.add(turn);
        });
        return reportTurns;
    }

    /*private long calculateTotal(ReportTurn turn) {
        ReportStep priceStep = turn.steps.get(StepType.PRICE_CHANGE_STEP);
        ReportStep lastStep = turn.steps.get(StepType.LAST_BUY_SELL_STEP);
        long total = 0;
        for (Map.Entry<Long, ShareAmount> shareAmountEntry : lastStep.shares.entrySet()) {
            total = total + shareAmountEntry.getValue().amount * priceStep.sharePrices.get(shareAmountEntry.getKey()).price;
        }
        return total + lastStep.cash;
    }*/

    private Long getCardId(GameDto gameDto, Long appliedCardId) {
        for (CompetitorDto competitor : gameDto.getCompetitors()) {
            for (CompetitorCardDto competitorCard : competitor.getCompetitorCards()) {
                if (competitorCard.getId().equals(appliedCardId)) {
                    return competitorCard.getCardId();
                }
            }
        }
        return null;
    }

    private Map<StepType, ReportStep> buildTurnSteps(Set<MoveStepDto> steps) {
        MoveStepDto priceChangeStep = steps.stream()
                .filter(moveStepDto -> moveStepDto.getStepType().equalsIgnoreCase(StepType.PRICE_CHANGE_STEP.name()))
                .findFirst().orElse(null);
        if (priceChangeStep != null && CollectionUtils.isNotEmpty(priceChangeStep.getShareQuantities())) {
            MoveStepDto newLastBuySellStep = new MoveStepDto();
            newLastBuySellStep.setStepType(StepType.LAST_BUY_SELL_STEP.name());
            newLastBuySellStep.setShareQuantities(priceChangeStep.getShareQuantities());
            newLastBuySellStep.setCashValue(priceChangeStep.getCashValue());
            steps.add(newLastBuySellStep);
        }

        Map<StepType, ReportStep> reportSteps = new HashMap<>();
        Set<StepType> mainStepTypes = Sets.newHashSet(StepType.ZERO_STEP, StepType.FIRST_BUY_SELL_STEP,
                StepType.PRICE_CHANGE_STEP, StepType.LAST_BUY_SELL_STEP);
        Set<StepType> coRepStepTypes = Sets.newHashSet(StepType.COMPENSATION_STEP, StepType.REPURCHASE_STEP);
        List<MoveStepDto> coRepSteps = steps.stream()
                .filter(moveStepDto -> coRepStepTypes.contains(StepType.valueOf(moveStepDto.getStepType())))
                .sorted((o1, o2) -> {
                    int result = StepType.valueOf(o1.getStepType()).ordinal() - StepType.valueOf(o2.getStepType()).ordinal();
                    if (result == 0) {
                        result = (int) (o1.getOriginalStepId() - o2.getOriginalStepId());
                    }
                    return result;
                }).collect(Collectors.toList());
        MoveStepDto lastStep = coRepSteps.size() > 0 ? coRepSteps.get(coRepSteps.size() - 1) : null;

        steps.forEach(moveStepDto -> {
            if (mainStepTypes.contains(StepType.valueOf(moveStepDto.getStepType()))) {
                ReportStep step = new ReportStep();
                step.stepType = StepType.valueOf(moveStepDto.getStepType());
                step.cash = moveStepDto.getCashValue();
                step.shares = buildShares(moveStepDto.getShareQuantities());
                step.sharePrices = buildSharePrices(moveStepDto.getSharePrices());
                if (step.stepType.equals(StepType.LAST_BUY_SELL_STEP) && lastStep != null) {
                    step.cash = lastStep.getCashValue();
                    if (StepType.valueOf(lastStep.getStepType()).equals(StepType.REPURCHASE_STEP)) {
                        lastStep.getShareQuantities().forEach(shareQuantityDto -> {
                            if (step.shares.get(shareQuantityDto.getId()).amount != shareQuantityDto.getQuantity()) {
                                step.shares.get(shareQuantityDto.getId()).repurchased = true;
                                step.shares.get(shareQuantityDto.getId()).amountBeforeRepurchase =
                                        step.shares.get(shareQuantityDto.getId()).amount;
                                step.shares.get(shareQuantityDto.getId()).amount = shareQuantityDto.getQuantity();
                            }
                        });
                    }
                }
                reportSteps.put(step.stepType, step);
            }
        });
        return reportSteps;
    }

    /*private Map<Long, ShareCompensation> buildCompensations(Set<CompensationDto> compensations) {
        Map<Long, ShareCompensation> shareCompensations = new HashMap<>();
        Optional.ofNullable(compensations)
                .ifPresent(compensationDtos -> compensationDtos.forEach(compensationDto -> {
            ShareCompensation shareCompensation = new ShareCompensation();
            shareCompensation.shareId = compensationDto.getId();
            shareCompensation.sum = compensationDto.getSum();
            shareCompensations.put(shareCompensation.shareId, shareCompensation);
        }));
        return shareCompensations;
    }*/

    private Map<Long, SharePrice> buildSharePrices(Set<SharePriceDto> sharePrices) {
        Map<Long, SharePrice> sharePrices1 = new HashMap<>();
        Optional.ofNullable(sharePrices)
                .ifPresent(sharePriceDtos -> sharePriceDtos.forEach(sharePriceDto -> {
            SharePrice sharePrice = new SharePrice();
            sharePrice.shareId = sharePriceDto.getId();
            sharePrice.price = sharePriceDto.getPrice();
            sharePrice.priceOperationId = sharePriceDto.getPriceOperationId();
            sharePrices1.put(sharePrice.shareId, sharePrice);
        }));
        return sharePrices1;
    }

    private Map<Long, ShareAmount> buildShares(Set<ShareQuantityDto> shareQuantities) {
        Map<Long, ShareAmount> shareAmounts = new HashMap<>();
        Optional.ofNullable(shareQuantities)
                .ifPresent(shareQuantityDtos -> shareQuantityDtos.forEach(shareQuantityDto -> {
            ShareAmount shareAmount = new ShareAmount();
            shareAmount.shareId = shareQuantityDto.getId();
            shareAmount.amount = shareQuantityDto.getQuantity();
            shareAmount.buySellAmount = shareQuantityDto.getBuySellQuantity();
            shareAmounts.put(shareAmount.shareId, shareAmount);
        }));
        return shareAmounts;
    }

    private Set<GamePlayer> buildGamePlayers(GameDto gameDto) {
        Set<GamePlayer> gamePlayers = new TreeSet<>();
        gameDto.getCompetitors().forEach(competitorDto -> {
            GamePlayer player = new GamePlayer();
            player.turnOrder = competitorDto.getMoveOrder();
            player.name = competitorDto.getUserName();
            player.playerCards = buildPlayerCards(competitorDto.getCompetitorCards());
            gamePlayers.add(player);
        });
        return gamePlayers;
    }

    private Set<PlayerCard> buildPlayerCards(Set<CompetitorCardDto> competitorCards) {
        Set<PlayerCard> playerCards = new HashSet<>();
        competitorCards.forEach(competitorCardDto -> {
            PlayerCard playerCard = new PlayerCard();
            playerCard.id = competitorCardDto.getId();
            playerCard.cardId = competitorCardDto.getCardId();
            playerCard.applied = competitorCardDto.getApplied();
            playerCards.add(playerCard);
        });
        return playerCards;
    }

    private Set<Game> buildGames(GameDto gameDto) {
        Set<Game> games = Sets.newTreeSet(Comparator.comparing(o -> o.letter));
        Game game = new Game();
        game.id = gameDto.getId();
        game.letter = GameLetter.valueOf(gameDto.getGameLetter());
        game.status = convertGameStatus(gameDto.getGameStatus());
        game.players = buildGamePlayers(gameDto);
        game.rounds = buildRounds(gameDto);
        if (gameDto.getGameStatus().equals(GameStatus.FINISHED.name())) {
            game.result = buildResult(gameDto);
        }
        games.add(game);
        gameDto.getRelatedGames().forEach(relatedGame -> {
            Game game1 = new Game();
            game1.id = relatedGame.getGameId();
            game1.letter = GameLetter.valueOf(relatedGame.getGameLetter());
            game1.status = convertGameStatus(relatedGame.getStatus());
            if (relatedGame.getStatus().equals(GameStatus.FINISHED.name())) {
                game1.result = buildRelatedGameResult(relatedGame);
            }
            games.add(game1);
        });
        return games;
    }

    private Set<GameResult> buildRelatedGameResult(RelatedGame relatedGame) {
        Set<GameResult> gameResultSet = Sets.newTreeSet(Comparator.comparingInt(o -> o.turnOrder));
        relatedGame.getCompetitorResults().forEach(competitorResultDto -> {
            PlayerResult playerResult = new PlayerResult();
            playerResult.name = competitorResultDto.getUserName();
            playerResult.bankrupt = competitorResultDto.getOut();
            playerResult.winner = competitorResultDto.getWinner();
            playerResult.totalPoints = competitorResultDto.getTotalPoints();
            playerResult.totalFunds = competitorResultDto.getTotalFunds();
            playerResult.fundsDifference = relatedGame.getCompetitorDiffs().stream()
                    .filter(competitorDiffDto -> competitorDiffDto.getFirstUserName().equals(competitorResultDto.getUserName()))
                    .map(CompetitorDiffDto::getFundsAbsoluteDiff).findFirst().orElse(0);
            GameResult gameResult = new GameResult();
            gameResult.turnOrder = competitorResultDto.getMoveOrder();
            gameResult.result = playerResult;
            gameResultSet.add(gameResult);
        });
        return gameResultSet;
    }

    private Set<GameResult> buildResult(GameDto gameDto) {
        Set<GameResult> gameResultSet = Sets.newTreeSet(Comparator.comparingInt(o -> o.turnOrder));
        gameDto.getCompetitorResults().forEach(competitorResultDto -> {
            PlayerResult playerResult = new PlayerResult();
            playerResult.name = competitorResultDto.getUserName();
            playerResult.bankrupt = competitorResultDto.getOut();
            playerResult.winner = competitorResultDto.getWinner();
            playerResult.totalPoints = competitorResultDto.getTotalPoints();
            playerResult.totalFunds = competitorResultDto.getTotalFunds();
            playerResult.fundsDifference = gameDto.getCompetitorDiffs().stream()
                    .filter(competitorDiffDto -> competitorDiffDto.getFirstUserName().equals(competitorResultDto.getUserName()))
                    .map(CompetitorDiffDto::getFundsAbsoluteDiff).findFirst().orElse(0);
            GameResult gameResult = new GameResult();
            gameResult.turnOrder = competitorResultDto.getMoveOrder();
            gameResult.result = playerResult;
            gameResultSet.add(gameResult);
        });
        return gameResultSet;
    }

    private Set<GameSetPlayer> buildPlayers(GameDto gameDto) {
        Set<GameSetPlayer> players = new HashSet<>();
        Set<CompetitorDto> competitorDtos = gameDto.getCompetitors();
        competitorDtos.forEach(competitorDto -> {
            GameSetPlayer gameSetPlayer = new GameSetPlayer();
            Player player = new Player();
            player.name = competitorDto.getUserName();
            player.bot = competitorDto.getBot();
            gameSetPlayer.player = player;
            gameSetPlayer.initiator = competitorDto.getInitiator();
            if (!gameSetPlayer.initiator) {
                PlayerInvitation playerInvitation = new PlayerInvitation();
                playerInvitation.statusSetAt =
                        competitorDto.getJoinedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                playerInvitation.invitationStatus = com.stockholdergame.server.web.dto.game.InvitationStatus.ACCEPTED;
                gameSetPlayer.invitation = playerInvitation;
            }
            players.add(gameSetPlayer);
        });
        return players;
    }

    private GameOptions buildGameOptions(Long gameVariantId) {
        GameOptions gameOptions = new GameOptions();
        gameOptions.cardOption = holder.getCardOption(gameVariantId);
        // todo - add other game options
        return gameOptions;
    }

    private GameStatus convertGameStatus(String gameStatus) {
        com.stockholdergame.server.model.game.GameStatus gameStatus1 =
                com.stockholdergame.server.model.game.GameStatus.valueOf(gameStatus);
        GameStatus gameStatus2 = null;
        switch (gameStatus1) {
            case OPEN:
                gameStatus2 = GameStatus.CREATED;
                break;
            case RUNNING:
                gameStatus2 =  GameStatus.RUNNING;
                break;
            case FINISHED:
                gameStatus2 =  GameStatus.FINISHED;
        }
        return gameStatus2;
    }


    private Long matchCardOptionToGameVariant(CardOption cardOption) {
        return holder.getGameVariantMap().getOrDefault(cardOption, 1L);
    }


    private static class GameVariantMapHolder {
        private GameFacade gameFacade;
        private Map<CardOption, Long> gameVariantMap = new HashMap<>();

        public GameVariantMapHolder(GameFacade gameFacade) {
            this.gameFacade = gameFacade;
        }

        public Map<CardOption, Long> getGameVariantMap() {
            if (gameVariantMap.isEmpty()) {
                fillGameVariantMap();
            }
            return gameVariantMap;
        }

        private void fillGameVariantMap() {
            List<GameVariantDto> gameVariantDtoList = gameFacade.getGameVariants();
            gameVariantDtoList.forEach(gameVariantDto -> {
                CardOption cardOption = new CardOption();
                gameVariantDto.getCardGroups().forEach(cardGroupDto -> {
                    String cardGroupName = cardGroupDto.getGroupName();
                    if (cardGroupName.contains("big")) {
                        cardOption.major = cardGroupDto.getGamerCardQuantity();
                    } else if (cardGroupName.contains("small")) {
                        cardOption.minor = cardGroupDto.getGamerCardQuantity();
                    }
                });
                gameVariantMap.putIfAbsent(cardOption, gameVariantDto.getId());
            });
        }

        public CardOption getCardOption(Long gameVariantId) {
            if (gameVariantMap.isEmpty()) {
                fillGameVariantMap();
            }
            for (Map.Entry<CardOption, Long> entry : gameVariantMap.entrySet()) {
                if (entry.getValue().equals(gameVariantId)) {
                    return entry.getKey();
                }
            }
            return null;
        }
    }
}
