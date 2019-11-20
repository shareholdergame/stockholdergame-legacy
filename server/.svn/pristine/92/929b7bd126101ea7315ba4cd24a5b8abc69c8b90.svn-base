package com.stockholdergame.server.dto.game;

import com.stockholdergame.server.dto.PaginationDto;
import com.stockholdergame.server.dto.validation.constraints.EnumName;
import com.stockholdergame.server.model.game.GameStatus;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;

/**
 * @author Alexander Savin
 *         Date: 8.2.11 21.58
 */
public class GameFilterDto extends PaginationDto {

    @NotNull
    @EnumName(enumClass = GameStatus.class)
    private String gameStatus;

    private boolean isOffer;

    private boolean isInitiator;

    private boolean isNotInitiator;

    private Long gameVariantId;

    private String userName;

    private boolean smallAvatar;

    private boolean legacyRules;

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = StringUtils.isEmpty(gameStatus) ? null : gameStatus;
    }

    public boolean isOffer() {
        return isOffer;
    }

    public void setOffer(boolean offer) {
        isOffer = offer;
    }

    public boolean isInitiator() {
        return isInitiator;
    }

    public void setInitiator(boolean initiator) {
        isInitiator = initiator;
    }

    public boolean isNotInitiator() {
        return isNotInitiator;
    }

    public void setNotInitiator(boolean notInitiator) {
        isNotInitiator = notInitiator;
    }

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSmallAvatar() {
        return smallAvatar;
    }

    public void setSmallAvatar(boolean smallAvatar) {
        this.smallAvatar = smallAvatar;
    }

    public boolean isLegacyRules() {
        return legacyRules;
    }

    public void setLegacyRules(boolean legacyRules) {
        this.legacyRules = legacyRules;
    }
}
