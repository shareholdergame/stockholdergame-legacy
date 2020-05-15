package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.game.*;
import com.stockholdergame.server.dto.game.lite.CompetitorLite;
import com.stockholdergame.server.dto.game.lite.GameLite;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.facade.GameFacade;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.web.dto.*;
import com.stockholdergame.server.web.dto.game.*;
import com.stockholdergame.server.web.dto.game.InvitationAction;
import com.stockholdergame.server.web.dto.player.Player;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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

@Api(value = "/", authorizations = { @Authorization("Bearer") })
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

    @ApiOperation("Creates new game")
    @RequestMapping(value = "/new", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> startGame(@RequestBody NewGame newGame) {
        GameInitiationDto gameInitiationDto = new GameInitiationDto();
        gameInitiationDto.setGameVariantId(matchCardOptionToGameVariant(newGame.cardOption));
        gameInitiationDto.setOffer(false);
        gameInitiationDto.setSwitchMoveOrder(true);
        gameInitiationDto.setInvitedUsers(new ArrayList<>(newGame.invitedPlayers));
        GameStatusDto gameStatusDto = gameFacade.initiateGame(gameInitiationDto);
        return ResponseWrapper.ok(gameStatusDto.getGameId());
    }

    @ApiOperation("Accepts/rejects invitation")
    @RequestMapping(value = "/{gameId}/invitation", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> performInvitationAction(@PathVariable("gameId") Long gameId,
                                                      @RequestParam("action") InvitationAction invitationAction) {
        if (invitationAction.equals(InvitationAction.accept)) {
            gameFacade.joinToGame(gameId);
        } else if (invitationAction.equals(InvitationAction.reject)) {
            ChangeInvitationStatusDto changeInvitationStatusDto = new ChangeInvitationStatusDto();
            changeInvitationStatusDto.setGameId(gameId);
            changeInvitationStatusDto.setStatus(InvitationStatus.REJECTED.name());
            gameFacade.changeInvitationStatus(changeInvitationStatusDto);
        } else if (invitationAction.equals(InvitationAction.cancel)) {
            ChangeInvitationStatusDto changeInvitationStatusDto = new ChangeInvitationStatusDto();
            changeInvitationStatusDto.setGameId(gameId);
            changeInvitationStatusDto.setStatus(InvitationStatus.CANCELLED.name());
            gameFacade.changeInvitationStatus(changeInvitationStatusDto);
        } else {
            return ResponseWrapper.error(ErrorBody.of("Unknown action"));
        }
        return ResponseWrapper.ok();
    }

    @ApiOperation("Do turn")
    @RequestMapping(value = "/{gameId}/doturn", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> doTurn(@PathVariable Long gameId, @RequestBody Turn turn) {
        gameFacade.doMove(buildDoMoveDto(gameId, turn));
        return ResponseWrapper.ok();
    }

    @ApiOperation("Gets game by identifier")
    @RequestMapping(value = "/{gameId}/report", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<GameSetReport> gameById(@PathVariable Long gameId) {
        GameDto gameDto = gameFacade.getGameById(gameId);
        return ResponseWrapper.ok(convertToGameSetReport(gameDto));
    }

    @ApiOperation("Gets games list")
    @RequestMapping(value = "/{gameOptionFilter}/{gameStatus}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<GameListResponse> getGames(@PathVariable GameOptionFilter gameOptionFilter,
                                                      @PathVariable GameStatus gameStatus,
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
        List<GameSet> gameList = new ArrayList<>();
        games.getGames().forEach(gameLite -> {
            Game game = new Game();
            game.id = gameLite.getId();
            game.letter = GameLetter.valueOf(gameLite.getGameLetter());
            game.status = convertGameStatus(gameLite.getGameStatus());
            if (gameLite.getGameStatus().equals(com.stockholdergame.server.model.game.GameStatus.RUNNING.name())) {
                game.position = buildPosition(gameLite);
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

    private PlayPosition buildPosition(GameLite gameLite) {
        PlayPosition playPosition = new PlayPosition();
        playPosition.round = gameLite.getLastMoveNumber();
        playPosition.turn = gameLite.getLastMoveOrder();
        return playPosition;
    }

    private Set<GamePlayer> buildPlayers(Set<CompetitorLite> competitors) {
        Set<GamePlayer> players = new HashSet<>();
        competitors.forEach(competitorDto -> {
            GamePlayer gamePlayer = new GamePlayer();
            Player player = new Player();
            player.name = competitorDto.getUserName();
            player.removed = competitorDto.isRemoved();
            player.bot = competitorDto.isBot();
            gamePlayer.player = player;
            gamePlayer.initiator = competitorDto.isInitiator();
            if (competitorDto.isInvitation()) {
                PlayerInvitation invitation = new PlayerInvitation();
                invitation.statusSetAt = getStatusChangedTime(competitorDto);
                invitation.invitationStatus = convertInvitationStatus(competitorDto.getInvitationStatus());
                gamePlayer.invitation = invitation;
            }
            players.add(gamePlayer);
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
        gameFilterDto.setOffset(offset);
        gameFilterDto.setMaxResults(itemsPerPage);
        if (gameStatus.equals(GameStatus.CREATED)) {
            gameFilterDto.setInitiator(true);
            gameFilterDto.setNotInitiator(true);
        }
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
        doMoveDto.setFirstBuySellActions(buildBuySellActions(turn.firstStep));
        doMoveDto.setPriceOperations(buildPriceOperations(turn.cardStep));
        doMoveDto.setLastBuySellActions(buildBuySellActions(turn.lastStep));
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

    private Set<BuySellDto> buildBuySellActions(BuySellStep firstStep) {
        Set<BuySellDto> buySellDtos = new HashSet<>();
        firstStep.buySellOperations.forEach(buySellOperation -> {
            BuySellDto buySellDto = new BuySellDto();
            buySellDto.setShareId(buySellOperation.shareId);
            buySellDto.setBuySellQuantity(buySellOperation.number);
            buySellDtos.add(buySellDto);
        });
        return buySellDtos;
    }

    private GameSetReport convertToGameSetReport(GameDto gameDto) {
        GameSet gameSet = new GameSet();
        gameSet.id = gameDto.getGameSeriesId();
        gameSet.status = convertGameStatus(gameDto.getGameStatus());
        gameSet.options = buildGameOptions(gameDto.getGameVariantId());
        gameSet.label = gameDto.getLabel();
        gameSet.players = buildPlayers(gameDto);
        gameSet.games = buildGames(gameDto);

        GameSetReport gameSetReport = new GameSetReport();
        gameSetReport.gameSet = gameSet;

        gameSetReport.report = buildReports(gameDto);

        return gameSetReport;
    }

    private Set<GameReport> buildReports(GameDto gameDto) {
        Set<GameReport> reports = new HashSet<>();
        GameReport gameReport = new GameReport();
        gameReport.gameId = gameDto.getId();
        gameReport.players = buildReportPlayers(gameDto);
        gameReport.rounds = buildRounds(gameDto);
        reports.add(gameReport);
        return reports;
    }

    private Set<ReportRound> buildRounds(GameDto gameDto) {
        Set<ReportRound> rounds = new HashSet<>();
        gameDto.getMoves().forEach(moveDto -> {
            ReportRound round = new ReportRound();
            round.round = moveDto.getMoveNumber();
            round.turns = buildTurns(moveDto.getCompetitorMoves());
            rounds.add(round);
        });
        return rounds;
    }

    private Set<ReportTurn> buildTurns(Set<CompetitorMoveDto> competitorMoves) {
        Set<ReportTurn> reportTurns = new HashSet<>();
        competitorMoves.forEach(competitorMoveDto -> {
            ReportTurn turn = new ReportTurn();
            turn.round = competitorMoveDto.getMoveNumber();
            turn.turn = competitorMoveDto.getMoveOrder();
            turn.finishedTime = competitorMoveDto.getFinishedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            turn.steps = buildTurnSteps(competitorMoveDto.getSteps());
            reportTurns.add(turn);
        });
        return reportTurns;
    }

    private Set<ReportStep> buildTurnSteps(Set<MoveStepDto> steps) {
        Set<ReportStep> reportSteps = new HashSet<>();
        steps.forEach(moveStepDto -> {
            ReportStep step = new ReportStep();
            step.stepType = StepType.valueOf(moveStepDto.getStepType());
            step.cashValue = moveStepDto.getCashValue();
            step.originalStepId = moveStepDto.getOriginalStepId();
            step.shares = buildShares(moveStepDto.getShareQuantities());
            step.sharePrices = buildSharePrices(moveStepDto.getSharePrices());
            step.compensations = buildCompensations(moveStepDto.getCompensations());
            reportSteps.add(step);
        });
        return reportSteps;
    }

    private Set<ShareCompensation> buildCompensations(Set<CompensationDto> compensations) {
        Set<ShareCompensation> shareCompensations = new HashSet<>();
        Optional.ofNullable(compensations)
                .ifPresent(compensationDtos -> compensationDtos.forEach(compensationDto -> {
            ShareCompensation shareCompensation = new ShareCompensation();
            shareCompensation.shareId = compensationDto.getId();
            shareCompensation.sum = compensationDto.getSum();
            shareCompensations.add(shareCompensation);
        }));
        return shareCompensations;
    }

    private Set<SharePrice> buildSharePrices(Set<SharePriceDto> sharePrices) {
        Set<SharePrice> sharePrices1 = new HashSet<>();
        Optional.ofNullable(sharePrices)
                .ifPresent(sharePriceDtos -> sharePriceDtos.forEach(sharePriceDto -> {
            SharePrice sharePrice = new SharePrice();
            sharePrice.shareId = sharePriceDto.getId();
            sharePrice.price = sharePriceDto.getPrice();
            sharePrice.priceOperationId = sharePriceDto.getPriceOperationId();
            sharePrices1.add(sharePrice);
        }));
        return sharePrices1;
    }

    private Set<ShareAmount> buildShares(Set<ShareQuantityDto> shareQuantities) {
        Set<ShareAmount> shareAmounts = new HashSet<>();
        Optional.ofNullable(shareQuantities)
                .ifPresent(shareQuantityDtos -> shareQuantityDtos.forEach(shareQuantityDto -> {
            ShareAmount shareAmount = new ShareAmount();
            shareAmount.shareId = shareQuantityDto.getId();
            shareAmount.amount = shareQuantityDto.getQuantity();
            shareAmount.buySellAmount = shareQuantityDto.getBuySellQuantity();
            shareAmounts.add(shareAmount);
        }));
        return shareAmounts;
    }

    private Set<ReportPlayer> buildReportPlayers(GameDto gameDto) {
        Set<ReportPlayer> reportPlayers = new HashSet<>();
        gameDto.getCompetitors().forEach(competitorDto -> {
            ReportPlayer player = new ReportPlayer();
            player.turnOrder = competitorDto.getMoveOrder();
            player.playerId = competitorDto.getId();
            player.playerCards = buildPlayerCards(competitorDto.getCompetitorCards());
            reportPlayers.add(player);
        });
        return reportPlayers;
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
        Set<Game> games = new HashSet<>();
        Game game = new Game();
        game.id = gameDto.getId();
        game.letter = GameLetter.valueOf(gameDto.getGameLetter());
        game.status = convertGameStatus(gameDto.getGameStatus());
        games.add(game);
        gameDto.getRelatedGames().forEach(relatedGame -> {
            Game game1 = new Game();
            game1.id = relatedGame.getGameId();
            game1.letter = GameLetter.valueOf(relatedGame.getGameLetter());
            game1.status = convertGameStatus(relatedGame.getStatus());
            games.add(game1);
        });
        return games;
    }

    private Set<GamePlayer> buildPlayers(GameDto gameDto) {
        Set<GamePlayer> players = new HashSet<>();
        Set<CompetitorDto> competitorDtos = gameDto.getCompetitors();
        competitorDtos.forEach(competitorDto -> {
            GamePlayer gamePlayer = new GamePlayer();
            Player player = new Player();
            player.name = competitorDto.getUserName();
            player.bot = competitorDto.getBot();
            gamePlayer.id = competitorDto.getId();
            gamePlayer.player = player;
            gamePlayer.initiator = competitorDto.getInitiator();
            if (!gamePlayer.initiator) {
                PlayerInvitation playerInvitation = new PlayerInvitation();
                playerInvitation.statusSetAt =
                        competitorDto.getJoinedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                playerInvitation.invitationStatus = com.stockholdergame.server.web.dto.game.InvitationStatus.ACCEPTED;
                gamePlayer.invitation = playerInvitation;
            }
            players.add(gamePlayer);
        });
        return players;
    }

    private Set<GameOption> buildGameOptions(Long gameVariantId) {
        Set<GameOption> gameOptions = new HashSet<>();
        gameOptions.add(holder.getCardOption(gameVariantId));
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
            for (Map.Entry<CardOption, Long> entry : gameVariantMap.entrySet()) {
                if (entry.getValue().equals(gameVariantId)) {
                    return entry.getKey();
                }
            }
            return null;
        }
    }
}
