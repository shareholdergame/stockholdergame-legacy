package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.FriendRequestDao;
import com.stockholdergame.server.model.account.FriendRequest;
import com.stockholdergame.server.model.account.FriendRequestStatus;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 *         Date: 18.6.11 21.18
 */
@Repository
public class FriendRequestDaoImpl extends BaseDao<FriendRequest, Long> implements FriendRequestDao {

    public FriendRequest findByUniqueParameters(Long requestorId, Long requesteeId, FriendRequestStatus status) {
        return findSingleObject("FriendRequest.findByUniqueParameters", requestorId, requesteeId, status);
    }

    @SuppressWarnings("unchecked")
    public Integer countNewFriendRequests(Long gamerId) {
        List<Long> counts = (List<Long>) findByNamedQuery("FriendRequest.countFriendRequests", gamerId,
                FriendRequestStatus.CREATED);
        return counts != null && !counts.isEmpty() ? counts.get(0).intValue() : 0;
    }

    public List<FriendRequest> findByRequestorId(Long requestorId) {
        return findList("FriendRequest.findByRequestorId", requestorId);
    }
}
