package com.stockholdergame.server.services.mail.impl;

import com.stockholdergame.server.dao.MessageDao;
import com.stockholdergame.server.dao.SentMessageDao;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.model.mail.Message;
import com.stockholdergame.server.model.mail.SentMessage;
import com.stockholdergame.server.services.mail.MailBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.stockholdergame.server.dao.util.IdentifierHelper.generateLongId;

/**
 * @author Alexander Savin
 *         Date: 23.12.11 22.31
 */
@Service
public class MailBoxServiceImpl implements MailBoxService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SentMessageDao sentMessageDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createMessage(String userEmail, String subject, String body) {
        Message message = new Message();
        message.setId(generateLongId());
        message.setRecipient(userEmail);
        message.setSubject(subject);
        message.setBody(body);
        message.setCreated(new Date());
        message.setAttemptsCount(0);
        messageDao.create(message);
    }

    @Transactional
    public List<Message> findOutgoingMessages() {
        return messageDao.findOutgoingMessages();
    }

    @Transactional
    public List<Message> findFailedOutgoingMessages() {
        return messageDao.findFailedOutgoingMessages();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void moveToSentBox(Long messageId) {
        Message message = messageDao.findByPrimaryKey(messageId);
        SentMessage sentMessage = createSentMessage(message);
        messageDao.remove(message);
        sentMessageDao.create(sentMessage);
    }

    @Transactional
    public void markAsFailed(Long messageId) {
        Message message = messageDao.findByPrimaryKey(messageId);
        message.setAttemptsCount(message.getAttemptsCount() + 1);
        message.setLastAttemptTime(new Date());
    }

    @Transactional
    public void removeMessage(Long messageId) {
        Message message = messageDao.findByPrimaryKey(messageId);
        messageDao.remove(message);
    }

    private SentMessage createSentMessage(Message message) {
        SentMessage sentMessage = DtoMapper.map(message, SentMessage.class);
        sentMessage.setSent(new Date());
        return sentMessage;
    }
}
