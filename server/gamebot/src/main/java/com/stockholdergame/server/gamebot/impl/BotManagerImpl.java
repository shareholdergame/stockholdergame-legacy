package com.stockholdergame.server.gamebot.impl;

import com.stockholdergame.server.dto.game.GameInitiationDto;
import com.stockholdergame.server.dto.game.GameStatusDto;
import com.stockholdergame.server.gamebot.BotApplicationException;
import com.stockholdergame.server.gamebot.BotManager;
import com.stockholdergame.server.gamebot.BotPlayer;
import com.stockholdergame.server.gamebot.GameBotBusinessDelegate;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.variant.Rounding;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 */
@Component("botManager")
public class BotManagerImpl implements BotManager {

    @Autowired
    private GameBotBusinessDelegate gameBotBusinessDelegate;

    private Map<Long, Long> botsMap;

    private boolean switchMoveOrderFlag = false;

    @Autowired
    private BotPlayer botPlayer;

    @Override
    public void offerGame(Long[] nonOfferedGameVariants) {
        if (botsMap == null) {
            fillBotsMap();
        }
        Map<Long, Long> nonPlayingBots = getNonPlayingBots();
        if (nonPlayingBots.isEmpty() || nonOfferedGameVariants.length <= 1) {
            return;
        }
        int expectedOffersNumber = nonOfferedGameVariants.length > nonPlayingBots.size()
            ? nonPlayingBots.size() : nonOfferedGameVariants.length - 1;
        List<Long> variantIds = shuffleVariants(nonOfferedGameVariants);
        List<Long> botIds = shuffleBots(botsMap.keySet());

        for (int i = 0; i < expectedOffersNumber; i++) {
            offerGame(variantIds.get(i), botIds.get(i));
        }
    }

    @Override
    public void wakeUp() {
        if (botsMap == null) {
            fillBotsMap();
        }
        for (Long botId : botsMap.keySet()) {
            List<Long> gameIds = gameBotBusinessDelegate.findPlayedGames(botId);
            for (Long gameId : gameIds) {
                botPlayer.play(gameId, botId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new BotApplicationException(e);
                }
            }
        }
    }

    private void offerGame(Long variantId, Long botId) {
        GameInitiationDto gameInitiationDto = new GameInitiationDto();
        gameInitiationDto.setGameVariantId(variantId);
        gameInitiationDto.setOffer(true);
        gameInitiationDto.setSwitchMoveOrder(switchMoveOrderFlag);
        gameInitiationDto.setRounding(Rounding.U.name());
        inverseSwitchMoveOrderFlag();
        GameStatusDto gameStatus = gameBotBusinessDelegate.initiateGame(gameInitiationDto, botId);
        botsMap.put(botId, gameStatus.getGameId());
    }

    private void inverseSwitchMoveOrderFlag() {
        switchMoveOrderFlag = !switchMoveOrderFlag;
    }

    private List<Long> shuffleBots(Set<Long> botIds) {
        return CollectionsUtil.shuffleObjects(botIds, 1);
    }

    private List<Long> shuffleVariants(Long[] nonOfferedGameVariants) {
        return CollectionsUtil.shuffleObjects(Arrays.asList(nonOfferedGameVariants), 1);
    }

    private Map<Long, Long> getNonPlayingBots() {
        Map<Long, Long> nonPlayingBots = new TreeMap<>();
        for (Map.Entry<Long, Long> entry : botsMap.entrySet()) {
            int userGamesNumber = gameBotBusinessDelegate.countUserInitiatedGamesByMethod(entry.getKey(), GameInitiationMethod.GAME_OFFER);
            if (userGamesNumber == 0) {
                entry.setValue(null);
            }
            if (null == entry.getValue()) {
                nonPlayingBots.put(entry.getKey(), null);
            }
        }
        return nonPlayingBots;
    }

    private void fillBotsMap() {
        List<GamerAccount> bots = gameBotBusinessDelegate.findBots();
        if (bots.isEmpty()) {
            throw new BotApplicationException("No bots are created on game server");
        }
        botsMap = new TreeMap<>();
        for (GamerAccount bot : bots) {
            botsMap.put(bot.getId(), null);
        }
    }
}

