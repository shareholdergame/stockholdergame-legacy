package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.ROUNDING_DOUN;
import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.ROUNDING_UP;

/**
 * @author Alexander Savin
 */
public enum Rounding implements Descriptable {

    D (ROUNDING_DOUN),
    U (ROUNDING_UP);

    private String description;

    Rounding(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
