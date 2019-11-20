package com.stockholdergame.server.services.event.handler;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.model.account.ChatMessagesCount;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.services.event.EventHandler;
import com.stockholdergame.server.services.mail.MailPreparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("unreadChatNotificationHandler")
public class UnreadChatNotificationHandler implements EventHandler<ChatMessagesCount> {

    @Autowired
    private MailPreparationService mailPreparationService;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Override
    public void handle(Long userId, ChatMessagesCount payload) {
        GamerAccount gamerAccount = gamerAccountDao.findByUserName(payload.getFirstUserName());

        mailPreparationService.prepareUnreadChatMessagesNotification(gamerAccount.getEmail(), gamerAccount.getUserName(), gamerAccount.getLocale(),
                payload.getSecondUserName(), payload.getMessagesCount());
    }
}
