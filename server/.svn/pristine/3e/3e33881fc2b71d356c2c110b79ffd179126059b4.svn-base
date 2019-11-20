package com.stockholdergame.server.gamebot;

import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.GameInitiationDto;
import com.stockholdergame.server.dto.game.GameStatusDto;
import com.stockholdergame.server.dto.game.MoveDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.variant.GameVariantInfo;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface GameBotBusinessDelegate {

    List<Long> findVariantsWithoutOffers();

    GameStatusDto initiateGame(GameInitiationDto gameInitiationDto, Long botId);

    int countUserInitiatedGamesByMethod(Long userId, GameInitiationMethod initiationMethod);

    List<GamerAccount> findBots();

    GameState getGame(Long gameId, Long botId);

    GameVariantDto getGameVariant(Long gameVariantId);

    GameVariantInfo getGameVariantInfo(Long gameVariantId);

    void doMove(DoMoveDto doMoveDto, Long botId);

    Set<MoveDto> loadLastMoves(int currentMoveNumber, int currentMoveOrder, Long gameId, Long botId);

    List<Long> findPlayedGames(Long botId);
}
