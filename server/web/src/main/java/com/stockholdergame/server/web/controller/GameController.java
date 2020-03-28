package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.game.*;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.facade.GameFacade;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.web.dto.ErrorBody;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.game.*;
import com.stockholdergame.server.web.dto.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.*;

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

    @RequestMapping(value = "/{gameId}/report", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<GameSetReport> gameById(@PathVariable Long gameId) {
        GameDto gameDto = gameFacade.getGameById(gameId);
        return ResponseWrapper.ok(convertToGameSetReport(gameDto));
    }

    private GameSetReport convertToGameSetReport(GameDto gameDto) {
        GameSet gameSet = new GameSet();
        gameSet.id = gameDto.getGameSeriesId();
        gameSet.status = convertGameStatus(gameDto.getGameStatus());
        gameSet.options = buildGameOptions(gameDto);
        gameSet.label = gameDto.getLabel();
        gameSet.players = buildPlayers(gameDto);

        GameSetReport gameSetReport = new GameSetReport();
        gameSetReport.gameSet = gameSet;

        return gameSetReport;
    }

    private Set<GamePlayer> buildPlayers(GameDto gameDto) {
        Set<GamePlayer> players = new HashSet<>();
        Set<CompetitorDto> competitorDtos = gameDto.getCompetitors();
        competitorDtos.forEach(competitorDto -> {
            GamePlayer gamePlayer = new GamePlayer();
            Player player = new Player();
            player.name = competitorDto.getUserName();
            gamePlayer.player = player;
            gamePlayer.bot = competitorDto.getBot();
            gamePlayer.initiator = competitorDto.getInitiator();
            PlayerInvitation playerInvitation = new PlayerInvitation();
            playerInvitation.statusSetAt =
                    competitorDto.getJoinedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            playerInvitation.invitationStatus = com.stockholdergame.server.web.dto.game.InvitationStatus.ACCEPTED;
            gamePlayer.invitation = playerInvitation;
            players.add(gamePlayer);
        });
        return players;
    }

    private Set<GameOption> buildGameOptions(GameDto gameDto) {
        Long gameVariantId = gameDto.getGameVariantId();
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
