package com.stockholdergame.server.dto.game;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alexander Savin
 */
public class GameInitiationDto {

    @NotNull
    private Long gameVariantId;

    private boolean isOffer;

    private boolean switchMoveOrder;

    //@NotNull
    //@EnumName(enumClass = Rounding.class)
    @Deprecated
    private String rounding;

    private List<String> invitedUsers;

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    public boolean isOffer() {
        return isOffer;
    }

    public void setOffer(boolean offer) {
        isOffer = offer;
    }

    public boolean isSwitchMoveOrder() {
        return switchMoveOrder;
    }

    public void setSwitchMoveOrder(boolean switchMoveOrder) {
        this.switchMoveOrder = switchMoveOrder;
    }

    @Deprecated
    public String getRounding() {
        return rounding;
    }

    @Deprecated
    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    public List<String> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<String> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }
}
