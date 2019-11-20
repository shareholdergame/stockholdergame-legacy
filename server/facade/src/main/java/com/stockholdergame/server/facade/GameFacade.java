package com.stockholdergame.server.facade;

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

import java.util.List;

/**
 *
 */
public interface GameFacade {

    List<GameVariantDto> getGameVariants();

    GamesList getGames(GameFilterDto gameFilterDto);

    GameStatusDto joinToGame(Long gameId);

    GameStatusDto initiateGame(GameInitiationDto gameInitiationDto);

    GameDto getGameById(Long gameId);

    GameDto doMove(DoMoveDto doMoveDto);

    void cancelGame(Long gameId);

    void inviteUser(CreateInvitationDto invitationDto);

    void changeInvitationStatus(ChangeInvitationStatusDto changeInvitationStatusDto);

    List<GameVariantSummary> getGameVariantsSummary();

    GamerActivitySummary getGamerActivitySummary();

    TotalScoreDto getScores(ScoreFilterDto scoreFilter);
}
