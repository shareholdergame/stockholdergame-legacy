package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.ChatMessageDao;
import com.stockholdergame.server.model.account.ChatMessage;
import com.stockholdergame.server.model.account.ChatMessageProjection;
import com.stockholdergame.server.model.account.ChatMessagesCount;
import com.stockholdergame.server.model.account.ChatProjection;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Repository
public class ChatMessageDaoImpl extends BaseDao<ChatMessage, Long> implements ChatMessageDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<ChatMessageProjection> findAllForLastDays(String chatId, int days, String userName) {
        Date date = DateUtils.addDays(new Date(), -days);
        return (List<ChatMessageProjection>) findByNamedQuery("UserChat.findAllForLastDays", chatId, date, userName);
    }

    @Override
    public void removeUserChatByChatId(String chatId, String userName) {
        Query query = em.createNamedQuery("UserChat.removeUserChatByChatId");
        query.setParameter(1, chatId);
        query.setParameter(2, userName);
        query.executeUpdate();
    }

    @Override
    public void removeUserChatsByUserId(String userName) {
        Query query = em.createNamedQuery("UserChat.removeUserChatsByUserId");
        query.setParameter(1, userName);
        query.executeUpdate();
    }

    @Override
    public List<ChatMessage> findUnreadByUserName(String userName) {
        return findList("ChatMessage.findUnreadByUserName", userName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ChatProjection> findChatsByUserName(String userName) {
        Query query = em.createNamedQuery("ChatMessage.findChatsByUserName");
        query.setParameter(1, userName);
        query.setParameter(2, userName);
        query.setParameter(3, DateUtils.addMonths(new Date(), -1));
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ChatMessageProjection> findLastThreeMessages(String chatId, String userName) {
        Query query = em.createNamedQuery("UserChat.findLastThreeMessages");
        query.setParameter(1, chatId);
        query.setParameter(2, userName);
        query.setMaxResults(3);
        List result = query.getResultList();
        Collections.sort(result, new Comparator() {
            @Override
            public int compare(Object t, Object t1) {
                ChatMessageProjection cmp = (ChatMessageProjection) t;
                ChatMessageProjection cmp1 = (ChatMessageProjection) t1;
                return cmp.getSent().compareTo(cmp1.getSent());
            }
        });
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ChatMessagesCount> countUnNotifiedMessages(Date fromDateTime) {
        Query query = em.createNamedQuery("UserChat.countUnNotifiedMessages");
        query.setParameter(1, fromDateTime);
        return query.getResultList();
    }

    @Override
    public void markNotified(String firstUserName, String secondUserName) {
        Query query = em.createNamedQuery("UserChat.markNotified");
        query.setParameter(1, firstUserName);
        query.setParameter(2, secondUserName);
        query.executeUpdate();
    }
}
