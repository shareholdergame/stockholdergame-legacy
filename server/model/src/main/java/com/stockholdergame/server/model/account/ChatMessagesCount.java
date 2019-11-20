package com.stockholdergame.server.model.account;

import java.io.Serializable;

public class ChatMessagesCount implements Serializable {

    private static final long serialVersionUID = -3645770173585447190L;

    private String firstUserName;

    private String secondUserName;

    private Long messagesCount;

    public ChatMessagesCount(String firstUserName, String secondUserName, Long messagesCount) {
        this.firstUserName = firstUserName;
        this.secondUserName = secondUserName;
        this.messagesCount = messagesCount;
    }

    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        this.firstUserName = firstUserName;
    }

    public String getSecondUserName() {
        return secondUserName;
    }

    public void setSecondUserName(String secondUserName) {
        this.secondUserName = secondUserName;
    }

    public Long getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(Long messagesCount) {
        this.messagesCount = messagesCount;
    }
}

