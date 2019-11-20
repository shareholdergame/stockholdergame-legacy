package com.stockholdergame.server.services.messaging.impl;

import com.stockholdergame.server.dao.ChatMessageDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dto.account.ChatDto;
import com.stockholdergame.server.dto.account.ChatHistoryDto;
import com.stockholdergame.server.dto.account.ChatMessageDto;
import com.stockholdergame.server.dto.account.IncomingChatMessageDto;
import com.stockholdergame.server.dto.account.SendMessageDto;
import com.stockholdergame.server.dto.game.event.UserNotification;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.helpers.MD5Helper;
import com.stockholdergame.server.model.account.ChatMessage;
import com.stockholdergame.server.model.account.ChatMessageProjection;
import com.stockholdergame.server.model.account.ChatMessageType;
import com.stockholdergame.server.model.account.ChatMessagesCount;
import com.stockholdergame.server.model.account.ChatProjection;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.UserChat;
import com.stockholdergame.server.model.account.UserChatPk;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.services.UserInfoAware;
import com.stockholdergame.server.services.event.BusinessEventBuilder;
import com.stockholdergame.server.services.messaging.ChatService;
import com.stockholdergame.server.services.messaging.MessagingService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Service("chatService")
public class ChatServiceImpl extends UserInfoAware implements ChatService {

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private ChatMessageDao chatMessageDao;

    @Autowired
    private MessagingService messagingService;

    public void sendMessage(SendMessageDto sendMessageDto) {
        String[] recipientNames = sendMessageDto.getRecipientNames();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(sendMessageDto.getMessage());
        chatMessage.setSent(new Date());
        chatMessage.setChatId(MD5Helper.generateMD5hash(generateStringForChatId(getCurrentUser().getUserName(), recipientNames)));
        chatMessage.setUsers(new HashSet<UserChat>());

        List<Long> gamerIds = new ArrayList<Long>(recipientNames.length);
        for(String recipientName : recipientNames) {
            GamerAccount gamerAccount = gamerAccountDao.findByUserName(recipientName);
            if (gamerAccount == null) {
                throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, recipientName);
            }
            gamerIds.add(gamerAccount.getId());

            UserChat outgoing = new UserChat();
            outgoing.setId(new UserChatPk(null, getCurrentUser().getUserName(), gamerAccount.getUserName()));
            outgoing.setMessageType(ChatMessageType.OUT);
            outgoing.setChatMessage(chatMessage);
            outgoing.setUnread(false);
            outgoing.setNotified(true);

            UserChat incoming = new UserChat();
            incoming.setId(new UserChatPk(null, gamerAccount.getUserName(), getCurrentUser().getUserName()));
            incoming.setMessageType(ChatMessageType.IN);
            incoming.setChatMessage(chatMessage);
            incoming.setUnread(true);
            incoming.setNotified(false);

            chatMessage.getUsers().add(incoming);
            chatMessage.getUsers().add(outgoing);
        }

        chatMessageDao.create(chatMessage);

        IncomingChatMessageDto chatMessageDto = DtoMapper.map(sendMessageDto, IncomingChatMessageDto.class);
        chatMessageDto.setChatMessageId(chatMessage.getId());
        chatMessageDto.setSenderName(getCurrentUser().getUserName());
        chatMessageDto.setSent(chatMessage.getSent());
        chatMessageDto.setUnread(true);
        for(Long gamerId : gamerIds) {
            UserNotification<IncomingChatMessageDto> event =
                    new UserNotification<IncomingChatMessageDto>(null, GameEventType.MESSAGE_RECEIVED, chatMessageDto);
            messagingService.send(gamerId, event);
        }
    }

    @Override
    public ChatHistoryDto getChatHistory(String[] userNames, int daysCount) {
        String chatId = MD5Helper.generateMD5hash(generateStringForChatId(getCurrentUser().getUserName(), userNames));
        List<ChatMessageProjection> chatMessages = chatMessageDao.findAllForLastDays(chatId, daysCount, getCurrentUser().getUserName());
        List<ChatMessageDto> chatMessageDtos = new ArrayList<ChatMessageDto>();
        for (ChatMessageProjection chatMessage : chatMessages) {
            ChatMessageDto chatMessageDto = DtoMapper.map(chatMessage, ChatMessageDto.class);
            chatMessageDtos.add(chatMessageDto);
        }
        ChatHistoryDto chatHistoryDto = new ChatHistoryDto();
        chatHistoryDto.setRecipientNames(userNames);
        chatHistoryDto.setChatMessages(chatMessageDtos);
        return chatHistoryDto;
    }

    @Override
    public List<ChatDto> getChats() {
        List<ChatDto> chatDtos = new ArrayList<ChatDto>();
        List<ChatProjection> chats = chatMessageDao.findChatsByUserName(getCurrentUser().getUserName());
        Map<String, Set<String>> chatsMap = new TreeMap<String, Set<String>>();
        for (ChatProjection chat : chats) {
            if (!chatsMap.containsKey(chat.getChatId())) {
                chatsMap.put(chat.getChatId(), new TreeSet<String>());
            }
            Set<String> userNames = chatsMap.get(chat.getChatId());
            if (!chat.getFirstUserName().equals(getCurrentUser().getUserName())) {
                userNames.add(chat.getFirstUserName());
            }
            if (!chat.getSecondUserName().equals(getCurrentUser().getUserName())) {
                userNames.add(chat.getSecondUserName());
            }
        }

        for (Map.Entry<String, Set<String>> entry : chatsMap.entrySet()) {
            ChatDto chatDto = new ChatDto();
            chatDto.setRecipientNames(entry.getValue().toArray(new String[entry.getValue().size()]));
            chatDtos.add(chatDto);
        }
        return chatDtos;
    }

    @Override
    public void clearHistory(String[] recipients) {
        String chatId = MD5Helper.generateMD5hash(generateStringForChatId(getCurrentUser().getUserName(), recipients));
        chatMessageDao.removeUserChatByChatId(chatId, getCurrentUser().getUserName());
    }

    @Override
    public List<IncomingChatMessageDto> getUnreadChatMessages() {
        List<ChatMessage> chatMessages = chatMessageDao.findUnreadByUserName(getCurrentUser().getUserName());
        List<IncomingChatMessageDto> incomingChatMessages = new ArrayList<IncomingChatMessageDto>();
        for (ChatMessage chatMessage : chatMessages) {
            IncomingChatMessageDto incomingChatMessage = new IncomingChatMessageDto();
            incomingChatMessage.setChatMessageId(chatMessage.getId());
            incomingChatMessage.setUnread(true);
            incomingChatMessage.setSent(chatMessage.getSent());
            incomingChatMessage.setMessage(chatMessage.getMessage());
            Set<String> recipientNames = new HashSet<String>();
            for (UserChat userChat : chatMessage.getUsers()) {
                if (userChat.getId().getFirstUserName().equals(getCurrentUser().getUserName()) &&
                        userChat.getMessageType().equals(ChatMessageType.IN)) {
                    incomingChatMessage.setSenderName(userChat.getId().getSecondUserName());
                } else if (!userChat.getId().getFirstUserName().equals(getCurrentUser().getUserName())) {
                    recipientNames.add(userChat.getId().getFirstUserName());
                }
            }
            incomingChatMessage.setRecipientNames(recipientNames.toArray(new String[recipientNames.size()]));
            incomingChatMessages.add(incomingChatMessage);
        }
        return incomingChatMessages;
    }

    @Override
    public void markChatMessagesAsRead(Long[] chatMessageIds) {
        for (Long chatMessageId : chatMessageIds) {
            ChatMessage chatMessage = chatMessageDao.findByPrimaryKey(chatMessageId);
            Set<UserChat> userChatSet = chatMessage.getUsers();
            for (UserChat userChat : userChatSet) {
                if (userChat.getId().getFirstUserName().equals(getCurrentUser().getUserName())
                    && userChat.getUnread()) {
                    userChat.setUnread(false);
                    userChat.setNotified(true);
                }
            }
            chatMessageDao.update(chatMessage);
        }
    }

    @Override
    @Transactional
    public void notifyByEmail() {
        Date currentDate = new Date();
        Date fromDateTime = DateUtils.addMinutes(currentDate, -15);
        List<ChatMessagesCount> chatMessagesCounts = chatMessageDao.countUnNotifiedMessages(fromDateTime);
        for (ChatMessagesCount chatMessagesCount : chatMessagesCounts) {
            publishEvent(BusinessEventBuilder.<ChatMessagesCount>initBuilder().setType(BusinessEventType.UNREAD_CHAT_NOTIFICATION)
                    .setPayload(chatMessagesCount).toEvent());
            chatMessageDao.markNotified(chatMessagesCount.getFirstUserName(), chatMessagesCount.getSecondUserName());
        }
    }

    private String generateStringForChatId(String senderName, String[] recipientNames) {
        TreeSet<String> sorted = new TreeSet<String>();
        sorted.add(senderName);
        Collections.addAll(sorted, recipientNames);
        StringBuilder sb = new StringBuilder();
        for (String s : sorted) {
            sb.append('@');
            sb.append(s);
        }
        return sb.toString();
    }
}
