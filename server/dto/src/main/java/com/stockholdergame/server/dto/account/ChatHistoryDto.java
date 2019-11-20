package com.stockholdergame.server.dto.account;

import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 15.1.13 21.17
 */
public class ChatHistoryDto extends ChatDto {

    private List<ChatMessageDto> chatMessages;

    public List<ChatMessageDto> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessageDto> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
