package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.account.ChatMessage;
import com.stockholdergame.server.model.account.ChatMessageProjection;
import com.stockholdergame.server.model.account.ChatMessagesCount;
import com.stockholdergame.server.model.account.ChatProjection;

import java.util.Date;
import java.util.List;

public interface ChatMessageDao extends GenericDao<ChatMessage, Long> {

    List<ChatMessageProjection> findAllForLastDays(String chatId, int days, String userName);

    void removeUserChatByChatId(String chatId, String userName);

    void removeUserChatsByUserId(String userName);

    List<ChatMessage> findUnreadByUserName(String userName);

    List<ChatProjection> findChatsByUserName(String userName);

    List<ChatMessageProjection> findLastThreeMessages(String chatId, String userName);

    List<ChatMessagesCount> countUnNotifiedMessages(Date fromDateTime);

    void markNotified(String firstUserName, String secondUserName);
}
