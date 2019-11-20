package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.account.Friend;
import com.stockholdergame.server.model.account.FriendPk;

/**
 * @author Alexander Savin
 *         Date: 4.7.11 23.37
 */
public interface FriendDao extends GenericDao<Friend, FriendPk> {

    boolean isFriendOfUser(Long userId, Long friendId);

    void removeByGamerId(Long gamerId);

    Long countFriends(Long userId);
}
