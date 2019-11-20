package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.mail.Message;

import java.util.List;

/**
 * @author Alexander Savin
 */
public interface MessageDao extends GenericDao<Message, Long> {

    List<Message> findOutgoingMessages();

    List<Message> findFailedOutgoingMessages();
}
