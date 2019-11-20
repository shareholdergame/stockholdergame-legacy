package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.MessageDao;
import com.stockholdergame.server.model.mail.Message;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 */
@Repository
public class MessageDaoImpl extends BaseDao<Message, Long> implements MessageDao {

    public List<Message> findOutgoingMessages() {
        return findList("Message.findOutgoingMessages");
    }

    public List<Message> findFailedOutgoingMessages() {
        return findList("Message.findFailedOutgoingMessages");
    }
}
