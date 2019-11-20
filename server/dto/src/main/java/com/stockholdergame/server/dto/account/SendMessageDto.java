package com.stockholdergame.server.dto.account;

public class SendMessageDto extends ChatMessageTextDto {

    private String[] recipientNames;

    public String[] getRecipientNames() {
        return recipientNames;
    }

    public void setRecipientNames(String[] recipientNames) {
        this.recipientNames = recipientNames;
    }
}
