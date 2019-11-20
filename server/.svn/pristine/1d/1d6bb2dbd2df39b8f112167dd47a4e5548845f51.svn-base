package com.stockholdergame.server.model.account;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 *         Date: 3.3.12 11.02
 */
@Embeddable
public class UserChatPk implements Serializable {

    @Column(name = "chat_message_id")
    private Long chatMessageId;

    @Column(name = "first_user_name", nullable = false)
    private String firstUserName;

    @Column(name = "second_user_name", nullable = false)
    private String secondUserName;

    public UserChatPk() {
    }

    public UserChatPk(Long chatMessageId, String firstUserName, String secondUserName) {
        this.chatMessageId = chatMessageId;
        this.firstUserName = firstUserName;
        this.secondUserName = secondUserName;
    }

    public Long getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(Long chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String userName) {
        this.firstUserName = userName;
    }

    public String getSecondUserName() {
        return secondUserName;
    }

    public void setSecondUserName(String collocutorName) {
        this.secondUserName = collocutorName;
    }
}
