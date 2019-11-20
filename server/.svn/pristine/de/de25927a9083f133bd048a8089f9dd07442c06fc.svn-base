package com.stockholdergame.server.model.mail;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "m_sent_messages")
public class SentMessage extends AbstractMessage implements Serializable {

    private static final long serialVersionUID = -5519431758535901693L;

    @Id
    @Column(name = "message_id")
    private Long id;

    @Column(name = "sent", nullable = false)
    private Date sent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("recipient", recipient)
                .append("subject", subject)
                .append("created", created)
                .append("sent", sent)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SentMessage)) {
            return false;
        }
        SentMessage m = (SentMessage) o;
        return new EqualsBuilder()
                .append(id, m.id)
                .append(recipient, m.recipient)
                .append(subject, m.subject)
                .append(created, m.created)
                .append(sent, m.sent)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(recipient)
                .append(subject)
                .append(created)
                .append(sent)
                .toHashCode();
    }
}
