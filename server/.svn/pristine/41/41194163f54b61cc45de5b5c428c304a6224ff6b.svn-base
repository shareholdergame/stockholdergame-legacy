package com.stockholdergame.server.services.game;

import com.stockholdergame.server.dto.game.ChangeInvitationStatusDto;
import com.stockholdergame.server.dto.game.CreateInvitationDto;
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
import com.stockholdergame.server.session.UserInfo;

import java.util.List;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 20.2.2010 13.33.28
 */
public interface GameService {

    List<GameVariantDto> getGameVariants();

    GamesList getGames(GameFilterDto gameFilterDto);

    GameStatusDto joinToGame(Long gameId);

    GameStatusDto initiateGame(GameInitiationDto gameInitiationDto);

    GameStatusDto initiateGame(GameInitiationDto gameInitiationDto, Long userId);

    GameDto getGameById(Long gameId);

    GameDto getGameById(Long gameId, UserInfo userInfo);

    void cancelGame(Long gameId);

    void inviteUser(CreateInvitationDto invitationDto);

    void changeInvitationStatus(ChangeInvitationStatusDto changeInvitationStatusDto);

    List<GameVariantSummary> getGameVariantsSummary();

    GamerActivitySummary getGamerActivitySummary();

    TotalScoreDto getScores(ScoreFilterDto scoreFilter);

    void dropExpiredGameOffers();

    void interruptGames();

    void tryToStartGames(Set<Long> gameIds);

    void tryToStartGame(Long gameId);

    void startGameWithPredefinedMoveOrder(Long templateGameId);
}
