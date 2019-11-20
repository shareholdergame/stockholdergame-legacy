package com.stockholdergame.server.model.account;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

/**
 * @author Alexander Savin
 *         Date: 3.3.12 10.54
 */
@Entity
@Table(name = "m_user_chats")
public class UserChat implements Serializable {

    private static final long serialVersionUID = -6262704783169056692L;

    @Id
    private UserChatPk id;

    @Column(name = "is_unread", nullable = false)
    @Type(type = "boolean")
    private Boolean isUnread;

    @Column(name = "is_notified", nullable = false)
    @Type(type = "boolean")
    private Boolean isNotified;

    @Enumerated(STRING)
    @Column(name = "message_type", nullable = false)
    private ChatMessageType messageType;

    @MapsId("chatMessageId")
    @ManyToOne
    @JoinColumn(name = "chat_message_id", insertable = false, updatable = false)
    private ChatMessage chatMessage;

    public UserChatPk getId() {
        return id;
    }

    public void setId(UserChatPk id) {
        this.id = id;
    }

    public Boolean getUnread() {
        return isUnread;
    }

    public void setUnread(Boolean unread) {
        isUnread = unread;
    }

    public Boolean getNotified() {
        return isNotified;
    }

    public void setNotified(Boolean notified) {
        isNotified = notified;
    }

    public ChatMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ChatMessageType messageType) {
        this.messageType = messageType;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
