package com.stockholdergame.server.facade.impl;

import com.stockholdergame.server.dto.game.ChangeInvitationStatusDto;
import com.stockholdergame.server.dto.game.CreateInvitationDto;
import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.GameDto;
import com.stockholdergame.server.dto.game.GameFilterDto;
import com.stockholdergame.server.dto.game.GameInitiationDto;
import com.stockholdergame.server.dto.game.GameStatusDto;
import com.stockholdergame.server.dto.game.GameVariantSummary;
import com.stockholdergame.server.dto.game.GamerActivitySummary;
import com.stockholdergame.server.dto.game.ScoreFilterDto;
import com.stockholdergame.server.dto.game.TotalScoreDto;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.facade.AbstractFacade;
import com.stockholdergame.server.facade.GameFacade;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.services.game.PlayGameService;
import com.stockholdergame.server.validation.Validatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
@Component
@RemotingDestination(value = "gameFacade", channels = {"game-secure-amf", "game-amf"})
public class GameFacadeImpl extends AbstractFacade implements GameFacade {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayGameService playGameService;

    @Transactional
    public List<GameVariantDto> getGameVariants() {
        return gameService.getGameVariants();
    }

    public GamesList getGames(@Validatable @NotNull GameFilterDto gameFilterDto) {
        return gameService.getGames(gameFilterDto);
    }

    @Transactional
    public GameStatusDto joinToGame(@Validatable @NotNull Long gameId) {
        return gameService.joinToGame(gameId);
    }

    @Override
    @Transactional
    public GameStatusDto joinToGameByGameSetId(Long gameSetId) {
        return gameService.joinToGameByGameSetId(gameSetId);
    }

    @Transactional
    public GameStatusDto initiateGame(@Validatable @NotNull GameInitiationDto gameInitiationDto) {
        return gameService.initiateGame(gameInitiationDto);
    }

    @Transactional
    public GameDto getGameById(@Validatable @NotNull Long gameId) {
        return gameService.getGameById(gameId);
    }

    @Transactional
    public GameDto doMove(@Validatable @NotNull DoMoveDto doMoveDto) {
        return playGameService.doMove(doMoveDto);
    }

    @Transactional
    public void cancelGame(@Validatable @NotNull Long gameId) {
        gameService.cancelGame(gameId);
    }

    @Transactional
    public void inviteUser(@Validatable @NotNull CreateInvitationDto invitationDto) {
        gameService.inviteUser(invitationDto);
    }

    @Transactional
    public void changeInvitationStatus(@Validatable @NotNull ChangeInvitationStatusDto changeInvitationStatusDto) {
        gameService.changeInvitationStatus(changeInvitationStatusDto);
    }

    public List<GameVariantSummary> getGameVariantsSummary() {
        return gameService.getGameVariantsSummary();
    }

    @Transactional
    public GamerActivitySummary getGamerActivitySummary() {
        return gameService.getGamerActivitySummary();
    }

    @Override
    @Transactional
    public TotalScoreDto getScores(@Validatable @NotNull ScoreFilterDto scoreFilter) {
        return gameService.getScores(scoreFilter);
    }
}
