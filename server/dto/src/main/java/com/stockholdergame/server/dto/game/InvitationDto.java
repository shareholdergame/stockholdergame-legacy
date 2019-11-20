package com.stockholdergame.server.dto.game;

import java.util.Date;

/**
 * @author Alexander Savin
 */
public class InvitationDto {

    private Long id;

    private Long gameVariantId;

    private Long gameId;

    private Integer competitorsQuantity;

    private Date createdTime;

    private String inviterName;

    private String inviteeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }


    public Integer getCompetitorsQuantity() {
        return competitorsQuantity;
    }

    public void setCompetitorsQuantity(Integer competitorsQuantity) {
        this.competitorsQuantity = competitorsQuantity;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public String getInviteeName() {
        return inviteeName;
    }

    public void setInviteeName(String inviteeName) {
        this.inviteeName = inviteeName;
    }
}
