package com.stockholdergame.server.facade;

import com.stockholdergame.server.dto.ServerStatisticsDto;
import com.stockholdergame.server.dto.account.*;
import com.stockholdergame.server.dto.game.GameEventDto;
import com.stockholdergame.server.dto.game.UserStatisticsFilterDto;
import com.stockholdergame.server.dto.game.UserStatisticsList;

import java.util.List;

/**
 *
 */
public interface SocialFacade {

    UsersList getUsers(UserFilterDto userFilter);

    UserStatisticsList getUserStatistics(UserStatisticsFilterDto userFilter);

    void sendFriendRequest(CreateFriendRequestDto createFriendRequest);

    void changeFriendRequestStatus(FriendRequestStatusDto friendRequestStatus);

    void cancelFriendRequest(String requesteeUserName);

    void sendMessage(SendMessageDto sendMessageDto);

    ChatHistoryDto getChatHistory(GetChatHistoryDto getChatHistoryDto);

    List<ChatDto> getChats();

    void clearChatHistory(String[] recipients);

    List<GameEventDto> getLastEvents();

    List<IncomingChatMessageDto> getUnreadChatMessages();

    ServerStatisticsDto getServerStatistics();

    void markChatMessagesAsRead(Long[] chatMessageIds);
}
