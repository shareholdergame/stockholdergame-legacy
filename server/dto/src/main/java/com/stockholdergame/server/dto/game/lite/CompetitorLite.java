package com.stockholdergame.server.dto.game.lite;

import java.util.Date;

/**
 *
 */
public class CompetitorLite {

    private Long gameId;

    private String userName;

    private byte[] avatar;

    private boolean isBot;

    private boolean isRemoved;

    private Integer moveOrder;

    private boolean isInitiator;

    private boolean isOut;

    private boolean isWinner;

    private boolean isInvitation;

    private Date joined;

    private Integer totalFunds;

    private Date invitationCreated;

    private Date invitationExpired;

    private String invitationStatus;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public boolean isInitiator() {
        return isInitiator;
    }

    public void setInitiator(boolean initiator) {
        isInitiator = initiator;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean isInvitation() {
        return isInvitation;
    }

    public void setInvitation(boolean invitation) {
        isInvitation = invitation;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public Integer getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(Integer totalFunds) {
        this.totalFunds = totalFunds;
    }

    public Date getInvitationCreated() {
        return invitationCreated;
    }

    public void setInvitationCreated(Date invitationCreated) {
        this.invitationCreated = invitationCreated;
    }

    public Date getInvitationExpired() {
        return invitationExpired;
    }

    public void setInvitationExpired(Date invitationExpired) {
        this.invitationExpired = invitationExpired;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }
}
