package com.stockholdergame.server.model.account;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alexander Savin
 *         Date: 14.1.13 21.12
 */
public class ChatMessageProjection implements Serializable {

    private static final long serialVersionUID = 799020854490132879L;

    private Long chatMessageId;

    private String senderName;

    private String message;

    private Date sent;

    private boolean isUnread;

    private String chatId;

    public ChatMessageProjection(Long chatMessageId,
                                 String senderName,
                                 String message,
                                 Date sent,
                                 boolean unread,
                                 String chatId) {
        this.chatMessageId = chatMessageId;
        this.senderName = senderName;
        this.message = message;
        this.sent = sent;
        isUnread = unread;
        this.chatId = chatId;
    }

    public Long getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(Long chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
