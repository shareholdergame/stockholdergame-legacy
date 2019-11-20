package com.stockholdergame.server.facade.impl;

import com.stockholdergame.server.dto.ServerStatisticsDto;
import com.stockholdergame.server.dto.account.ChatDto;
import com.stockholdergame.server.dto.account.ChatHistoryDto;
import com.stockholdergame.server.dto.account.CreateFriendRequestDto;
import com.stockholdergame.server.dto.account.FriendRequestStatusDto;
import com.stockholdergame.server.dto.account.GetChatHistoryDto;
import com.stockholdergame.server.dto.account.IncomingChatMessageDto;
import com.stockholdergame.server.dto.account.SendMessageDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.dto.account.UsersList;
import com.stockholdergame.server.dto.game.GameEventDto;
import com.stockholdergame.server.dto.game.UserStatisticsFilterDto;
import com.stockholdergame.server.dto.game.UserStatisticsList;
import com.stockholdergame.server.facade.AbstractFacade;
import com.stockholdergame.server.facade.SocialFacade;
import com.stockholdergame.server.services.account.AccountService;
import com.stockholdergame.server.services.account.ProfileService;
import com.stockholdergame.server.services.common.StatisticsService;
import com.stockholdergame.server.services.messaging.ChatService;
import com.stockholdergame.server.services.messaging.GameEventContainerService;
import com.stockholdergame.server.validation.Validatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
@Component
@RemotingDestination(value = "socialFacade", channels = {"game-secure-amf", "game-amf"})
public class SocialFacadeImpl extends AbstractFacade implements SocialFacade {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private GameEventContainerService gameEventContainerService;

    @Autowired
    private StatisticsService statisticsService;

    public UsersList getUsers(@Validatable @NotNull UserFilterDto userFilter) {
        return accountService.getUsers(userFilter);
    }

    @Override
    public UserStatisticsList getUserStatistics(UserStatisticsFilterDto userFilter) {
        return statisticsService.getUserStatistics(userFilter);
    }

    @Transactional
    public void sendFriendRequest(@Validatable @NotNull CreateFriendRequestDto createFriendRequest) {
        profileService.sendFriendRequest(createFriendRequest);
    }

    @Transactional
    public void changeFriendRequestStatus(@Validatable @NotNull FriendRequestStatusDto friendRequestStatus) {
        profileService.changeFriendRequestStatus(friendRequestStatus);
    }

    @Transactional
    public void cancelFriendRequest(@Validatable @NotNull String requesteeUserName) {
        profileService.cancelFriendRequest(requesteeUserName);
    }

    @Transactional
    public void sendMessage(@Validatable @NotNull SendMessageDto sendMessageDto) {
        chatService.sendMessage(sendMessageDto);
    }

    @Override
    @Transactional
    public ChatHistoryDto getChatHistory(@Validatable @NotNull GetChatHistoryDto getChatHistoryDto) {
        return chatService.getChatHistory(getChatHistoryDto.getRecipientNames(), getChatHistoryDto.getDaysCount());
    }

    @Override
    @Transactional
    public List<ChatDto> getChats() {
        return chatService.getChats();
    }

    @Override
    @Transactional
    public void clearChatHistory(@NotNull String[] recipients) {
        chatService.clearHistory(recipients);
    }

    @Override
    @Transactional
    public List<GameEventDto> getLastEvents() {
        return gameEventContainerService.getLastEvents();
    }

    @Override
    @Transactional
    public List<IncomingChatMessageDto> getUnreadChatMessages() {
        return chatService.getUnreadChatMessages();
    }

    @Override
    public ServerStatisticsDto getServerStatistics() {
        return statisticsService.getServerStatistics();
    }

    @Override
    @Transactional
    public void markChatMessagesAsRead(Long[] chatMessageIds) {
        chatService.markChatMessagesAsRead(chatMessageIds);
    }
}
