package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.FriendDao;
import com.stockholdergame.server.model.account.Friend;
import com.stockholdergame.server.model.account.FriendPk;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.Query;

/**
 * @author Alexander Savin
 *         Date: 4.7.11 23.37
 */
@Repository
public class FriendDaoImpl extends BaseDao<Friend, FriendPk> implements FriendDao {

    @SuppressWarnings("unchecked")
    public boolean isFriendOfUser(Long userId, Long friendId) {
        List<Object> results = (List<Object>) findByNamedQuery("Friend.isFriendOfUser", friendId, userId);
        return results != null && !results.isEmpty() ? (Boolean) results.get(0) : false;
    }

    @Override
    public void removeByGamerId(Long gamerId) {
        Query query = em.createNamedQuery("Friend.removeByGamerId");
        query.setParameter(1, gamerId);
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long countFriends(Long userId) {
        List<Long> count = (List<Long>) findByNamedQuery("Friend.countFriends", userId);
        return count != null && !count.isEmpty() ? count.get(0) : Long.valueOf(0L);
    }
}
