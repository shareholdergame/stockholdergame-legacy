package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.*;

/**
 * @author Alexander Savin
 */
public enum GameStatus implements Descriptable {

    OPEN(GAME_STATUS_OPEN),

    RUNNING(GAME_STATUS_RUNNING),

    FINISHED(GAME_STATUS_FINISHED),

    CANCELLED(GAME_STATUS_CANCELLED),

    INTERRUPTED(GAME_STATUS_INTERRUPTED);

    private String description;

    GameStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
