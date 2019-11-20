package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.Descriptable;
import com.stockholdergame.server.model.i18n.ModelResourceBundleKeys;

/**
 * @author Alexander Savin
 *         Date: 12.1.13 15.22
 */
public enum EventType implements Descriptable {

    GAME_CREATED(ModelResourceBundleKeys.EVENT_GAME_CREATED),
    GAME_STARTED(ModelResourceBundleKeys.EVENT_GAME_STARTED),
    GAME_FINISHED(ModelResourceBundleKeys.EVENT_GAME_FINISHED);


    private String description;

    EventType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
