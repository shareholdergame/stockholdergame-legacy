package com.stockholdergame.server.services.event.handler;

import com.stockholdergame.server.gamebot.BotPlayer;
import com.stockholdergame.server.services.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("playBotHandler")
public class PlayBotHandler implements EventHandler<Long[]> {

    @Autowired
    private BotPlayer botPlayer;

    @Override
    public void handle(Long userId, Long[] payload) {
        botPlayer.play(payload[0], payload[1]);
    }
}
