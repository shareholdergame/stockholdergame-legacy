package com.stockholdergame.server.dto.game;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Alexander Savin
 *         Date: 11.6.11 22.43
 */
public class CreateInvitationDto {

    @NotNull
    private Long gameId;

    @NotEmpty
    private String[] inviteeNames;

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
}
