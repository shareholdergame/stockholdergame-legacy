package com.stockholdergame.server.model.mail;

import com.stockholdergame.server.model.Identifiable;
import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "m_messages")
public class Message extends AbstractMessage implements Identifiable {

    private static final long serialVersionUID = -6171191550413816279L;

    @Id
    @Column(name = "message_id")
    private Long id;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "attempts_count", nullable = false)
    private Integer attemptsCount;

    @Column(name = "last_attempt_time")
    private Date lastAttemptTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(Integer attemptsCount) {
        this.attemptsCount = attemptsCount;
    }

    public Date getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(Date lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("recipient", recipient)
                .append("subject", subject)
                .append("created", created)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message)) {
            return false;
        }
        Message m = (Message) o;
        return new EqualsBuilder()
                .append(id, m.id)
                .append(recipient, m.recipient)
                .append(subject, m.subject)
                .append(created, m.created)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(recipient)
                .append(subject)
                .append(created)
                .toHashCode();
    }
}
