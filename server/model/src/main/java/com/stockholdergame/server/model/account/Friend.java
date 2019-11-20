package com.stockholdergame.server.model.account;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Alexander Savin
 *         Date: 28.5.11 21.30
 */
@Entity
@Table(name = "a_friends")
public class Friend implements Serializable {

    private static final long serialVersionUID = -7423621754106238444L;

    @Id
    private FriendPk id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gamer_id", insertable = false, updatable = false)
    private GamerAccount gamer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", insertable = false, updatable = false)
    private GamerAccount friend;

    public FriendPk getId() {
        return id;
    }

    public void setId(FriendPk id) {
        this.id = id;
    }

    public GamerAccount getGamer() {
        return gamer;
    }

    public void setGamer(GamerAccount gamer) {
        this.gamer = gamer;
    }

    public GamerAccount getFriend() {
        return friend;
    }

    public void setFriend(GamerAccount friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Friend)) {
            return false;
        }
        Friend g = (Friend) o;
        return new EqualsBuilder()
                .append(id, g.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }
}
