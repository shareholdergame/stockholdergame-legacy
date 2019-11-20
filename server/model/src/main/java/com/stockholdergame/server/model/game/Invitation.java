package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.account.ChatMessage;
import com.stockholdergame.server.model.account.GamerAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Alexander Savin
 */
@Entity
/*@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)*/
@Table(name = "ag_invitations")
public class Invitation implements Serializable {

    private static final long serialVersionUID = 5680945941554452428L;

    @Id
    @GeneratedValue
    @Column(name = "invitation_id")
    private Long id;

    @Column(name = "invitee_id", nullable = false)
    private Long inviteeId;

    @Column(name = "inviter_id", nullable = false)
    private Long inviterId;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "status_id", nullable = false)
    @Enumerated
    private InvitationStatus status;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "completed_time")
    private Date completedTime;

    @Column(name = "expired_time")
    private Date expiredTime;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "inviter_id", insertable = false, updatable = false)
    private GamerAccount inviterAccount;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "invitee_id", insertable = false, updatable = false)
    private GamerAccount inviteeAccount;

    @OneToOne
    @JoinColumn(name = "user_message_id")
    private ChatMessage message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(Long inviteeId) {
        this.inviteeId = inviteeId;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GamerAccount getInviterAccount() {
        return inviterAccount;
    }

    public void setInviterAccount(GamerAccount inviterAccount) {
        this.inviterAccount = inviterAccount;
    }

    public GamerAccount getInviteeAccount() {
        return inviteeAccount;
    }

    public void setInviteeAccount(GamerAccount inviteeAccount) {
        this.inviteeAccount = inviteeAccount;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("gameId", gameId)
                .append("inviteeId", inviteeId)
                .append("inviterId", inviterId)
                .append("status", status)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Invitation)) {
            return false;
        }
        Invitation g = (Invitation) o;
        return new EqualsBuilder()
                .append(gameId, g.gameId)
                .append(inviteeId, g.inviteeId)
                .append(inviterId, g.inviterId)
                .append(status, g.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(gameId)
                .append(inviteeId)
                .append(inviterId)
                .append(status)
                .toHashCode();
    }
}
