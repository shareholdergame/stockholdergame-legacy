package com.stockholdergame.server.services.mail;

import com.stockholdergame.server.model.mail.Message;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 23.12.11 22.30
 */
public interface MailBoxService {

    void createMessage(String userEmail, String subject, String body);

    List<Message> findOutgoingMessages();

    List<Message> findFailedOutgoingMessages();

    void moveToSentBox(Long messageId);

    void markAsFailed(Long messageId);

    void removeMessage(Long messageId);
}
