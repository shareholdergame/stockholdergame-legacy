package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.account.FriendRequest;
import com.stockholdergame.server.model.account.FriendRequestStatus;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 18.6.11 21.17
 */
public interface FriendRequestDao extends GenericDao<FriendRequest, Long> {

    FriendRequest findByUniqueParameters(Long requestorId, Long requesteeId, FriendRequestStatus status);

    Integer countNewFriendRequests(Long gamerId);

    List<FriendRequest> findByRequestorId(Long requestorId);
}
