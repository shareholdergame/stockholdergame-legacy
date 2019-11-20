package com.stockholdergame.server.gamebot.impl;

import com.stockholdergame.server.gamebot.BotManager;
import com.stockholdergame.server.gamebot.GameBotBusinessDelegate;
import com.stockholdergame.server.gamebot.GameOffersChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component("gameOffersChecker")
public class GameOffersCheckerImpl implements GameOffersChecker {

    @Autowired
    private GameBotBusinessDelegate gameBotBusinessDelegate;

    @Autowired
    private BotManager botManager;

    @Override
    public void check() {
        List<Long> gameVariantsWithoutOffers = gameBotBusinessDelegate.findVariantsWithoutOffers();
        if (gameVariantsWithoutOffers.isEmpty()) {
            return;
        }
        botManager.offerGame(gameVariantsWithoutOffers.toArray(new Long[gameVariantsWithoutOffers.size()]));
    }
}
