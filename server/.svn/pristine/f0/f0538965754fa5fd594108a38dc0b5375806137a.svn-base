package com.stockholdergame.server.dto.game;

import com.stockholdergame.server.dto.validation.constraints.EnumName;
import com.stockholdergame.server.model.game.InvitationStatus;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class ChangeInvitationStatusDto {

    @NotNull
    private Long gameId;

    private String[] inviteeNames;

    @NotNull
    @EnumName(enumClass = InvitationStatus.class)
    private String status;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String[] getInviteeNames() {
        return inviteeNames;
    }

    public void setInviteeNames(String[] inviteeNames) {
        this.inviteeNames = inviteeNames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status != null ? status.trim() : null;
    }
}
