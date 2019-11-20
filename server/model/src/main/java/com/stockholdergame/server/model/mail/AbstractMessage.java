package com.stockholdergame.server.model.mail;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author Alexander Savin
 */
@MappedSuperclass
public abstract class AbstractMessage {

    @Column(name = "recipient", nullable = false)
    protected String recipient;

    @Column(name = "subject", nullable = false)
    protected String subject;

    @Column(name = "created", nullable = false)
    protected Date created;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
