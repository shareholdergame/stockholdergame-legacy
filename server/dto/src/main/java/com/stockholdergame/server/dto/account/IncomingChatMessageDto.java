package com.stockholdergame.server.dto.account;

/**
 * @author Aliaksandr Savin
 */
public class IncomingChatMessageDto extends ChatMessageDto {

    private String[] recipientNames;

    public String[] getRecipientNames() {
        return recipientNames;
    }

    public void setRecipientNames(String[] recipientNames) {
        this.recipientNames = recipientNames;
    }
}
