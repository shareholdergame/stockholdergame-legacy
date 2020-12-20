package com.stockholdergame.server.services.messaging.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.services.messaging.MessageBuffer;
import com.stockholdergame.server.services.messaging.MessagingService;
import flex.messaging.messages.AsyncMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Savin
 *         Date: 16.3.11 8.42
 */
@Service
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private MessageChannel toFlex;

    @Autowired
    private MessageBuffer messageBuffer;

    public <T> void send(Long gamerId, T notificationBody) {
        String subTopic = getSubtopic(gamerId);
        Message<T> message = MessageBuilder.withPayload(notificationBody).setHeader(AsyncMessage.SUBTOPIC_HEADER_NAME, subTopic).build();
        messageBuffer.addMessage(gamerId, notificationBody);
        try {
            toFlex.send(message);
        } catch (Exception e) {
            // todo - log error
        }
    }

    private String getSubtopic(Long gamerId) {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
        if (gamerAccount == null) {
            throw new ApplicationException("user not found ID " + gamerId);
        }
        return gamerAccount.getSubtopicName();
    }
}
