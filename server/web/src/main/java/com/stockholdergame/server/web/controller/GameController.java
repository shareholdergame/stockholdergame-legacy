package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.game.ChangeInvitationStatusDto;
import com.stockholdergame.server.dto.game.GameInitiationDto;
import com.stockholdergame.server.dto.game.GameStatusDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.facade.GameFacade;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.services.game.PlayGameService;
import com.stockholdergame.server.web.dto.ErrorBody;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import com.stockholdergame.server.web.dto.game.CardOption;
import com.stockholdergame.server.web.dto.game.InvitationAction;
import com.stockholdergame.server.web.dto.game.NewGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/game")
public class GameController {

    private Map<CardOption, Long> gameVariantMap = new HashMap<>();

    @Autowired
    private GameFacade gameFacade;

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
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
            // todo
        } else {
            return ResponseWrapper.error(ErrorBody.of("Unknown action"));
        }
        return ResponseWrapper.ok();
    }

    private Long matchCardOptionToGameVariant(CardOption cardOption) {
        if (gameVariantMap.isEmpty()) {
            fillGameVariantMap();
        }
        return gameVariantMap.getOrDefault(cardOption, 1L);
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
}
