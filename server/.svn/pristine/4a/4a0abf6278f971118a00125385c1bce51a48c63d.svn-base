package com.stockholdergame.server.model.account;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 */
@Entity
@Table(name = "a_user_session_log")
public class UserSessionLog implements Serializable {

    private static final long serialVersionUID = -783497928360122607L;

    @Id
    @GeneratedValue
    @Column(name = "session_id")
    private Long id;

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("gamerId", gamerId)
                .append("ipAddress", ipAddress)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserSessionLog)) {
            return false;
        }
        UserSessionLog g = (UserSessionLog) o;
        return new EqualsBuilder()
                .append(gamerId, g.gamerId)
                .append(ipAddress, g.ipAddress)
                .append(startTime, g.startTime)
                .append(endTime, g.endTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(gamerId)
                .append(ipAddress)
                .append(startTime)
                .append(endTime)
                .toHashCode();
    }
}
