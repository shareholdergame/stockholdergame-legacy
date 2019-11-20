package com.stockholdergame.server.model.account;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Alexander Savin
 *         Date: 28.5.11 21.32
 */
@Embeddable
public class FriendPk implements Serializable {

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    @Column(name = "friend_id", nullable = false)
    private Long friendId;

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("gamerId", gamerId)
                .append("friendId", friendId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FriendPk)) {
            return false;
        }
        FriendPk g = (FriendPk) o;
        return new EqualsBuilder()
                .append(gamerId, g.gamerId)
                .append(friendId, g.friendId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(gamerId)
                .append(friendId)
                .toHashCode();
    }
}
