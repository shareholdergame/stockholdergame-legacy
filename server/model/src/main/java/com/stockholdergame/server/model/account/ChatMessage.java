package com.stockholdergame.server.model.account;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 *
 */
@Entity
@Table(name = "m_chat_messages")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = -3267177173646934961L;

    @Id
    @GeneratedValue
    @Column(name = "chat_message_id")
    private Long id;

    @Column(name = "message", length = 4000)
    private String message;

    @Column(name = "sent", nullable = false)
    private Date sent;

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @OneToMany(mappedBy = "chatMessage", fetch = EAGER, cascade = ALL)
    private Set<UserChat> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Set<UserChat> getUsers() {
        return users;
    }

    public void setUsers(Set<UserChat> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("chatId", chatId)
                .append("sent", sent)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatMessage)) {
            return false;
        }
        ChatMessage m = (ChatMessage) o;
        return new EqualsBuilder()
                .append(id, m.id)
                .append(chatId, m.chatId)
                .append(sent, m.sent)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(chatId)
                .append(sent)
                .toHashCode();
    }
}
