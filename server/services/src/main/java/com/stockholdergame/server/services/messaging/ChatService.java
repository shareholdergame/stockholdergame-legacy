package com.stockholdergame.server.services.messaging;

import com.stockholdergame.server.dto.account.ChatDto;
import com.stockholdergame.server.dto.account.ChatHistoryDto;
import com.stockholdergame.server.dto.account.IncomingChatMessageDto;
import com.stockholdergame.server.dto.account.SendMessageDto;

import java.util.List;

public interface ChatService {

    void sendMessage(SendMessageDto sendMessageDto);

    ChatHistoryDto getChatHistory(String[] userNames, int daysCount);

    List<ChatDto> getChats();

    void clearHistory(String[] recipients);

    List<IncomingChatMessageDto> getUnreadChatMessages();

    void markChatMessagesAsRead(Long[] chatMessageIds);

    void notifyByEmail();
}
