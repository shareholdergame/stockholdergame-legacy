package com.stockholdergame.server.web.dto;

import com.stockholdergame.server.web.dto.game.CardOption;

/**
 * Date: 10/11/2018
 *
 * @author Aliaksandr Savin
 */
public enum GameOptionFilter {

    all(null),

    game_3x5(CardOption.of(3, 5)),

    game_4x6(CardOption.of(4, 6)),

    custom(CardOption.of(5, 7));

    private CardOption cardOption;

    GameOptionFilter(CardOption cardOption) {
        this.cardOption = cardOption;
    }

    public CardOption getCardOption() {
        return cardOption;
    }
}
