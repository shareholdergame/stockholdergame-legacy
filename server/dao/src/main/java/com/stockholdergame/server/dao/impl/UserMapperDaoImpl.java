package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.UserMapperDao;
import com.stockholdergame.server.dto.account.FriendFilterType;
import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.util.collections.MapBuilder;
import org.apache.commons.lang.Validate;
import org.apache.ibatis.session.RowBounds;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class UserMapperDaoImpl extends BaseMapperDao implements UserMapperDao {

    @SuppressWarnings("unchecked")
    public List<UserDto> findUsers(Long currentUserId, UserFilterDto userFilter) {
        Map<String, Object> params = createParametersMap(currentUserId, userFilter);

        return getSqlSession().selectList("User.findUsers", params,
            new RowBounds(userFilter.getOffset(), userFilter.getMaxResults()));
    }

    private Map<String, Object> createParametersMap(Long currentUserId, UserFilterDto userFilter) {
        Validate.notNull(currentUserId);
        Validate.notNull(userFilter);

        Map<String, Object> params = new MapBuilder<String, Object>()
                .append("currentUserId", currentUserId)
                .append("userNames", userFilter.getUserNames())
                .append("locale", userFilter.getLocale())
                .append("sex", userFilter.getSex())
                .append("country", userFilter.getCountry())
                .append("city", userFilter.getCity())
                .append("isFriend", false)
                .append("isExcludeFriends", false)
                .append("isExcludeFriendRequests", false)
                .append("hasFriendRequest", false)
                .append("showRemoved", userFilter.isShowRemoved())
                .append("excludedUserNames", userFilter.getExcludedUserNames())
                .append("gameId", userFilter.getGameId())
                .append("userName", userFilter.getUserName() != null ? userFilter.getUserName().trim().toLowerCase() + '%' : null)
                .toHashMap();

        if(userFilter.getFriendFilters() != null) {
            int fflen = userFilter.getFriendFilters().length;
            if(fflen > 0 && fflen < FriendFilterType.values().length) {
                if(Arrays.binarySearch(userFilter.getFriendFilters(), FriendFilterType.WITH_FRIEND_REQUESTS) >= 0) {
                    params.put("hasFriendRequest", true);
                }
                if(Arrays.binarySearch(userFilter.getFriendFilters(), FriendFilterType.FRIENDS_ONLY) >= 0) {
                    params.put("isFriend", true);
                }
                if(Arrays.binarySearch(userFilter.getFriendFilters(), FriendFilterType.OTHER) >= 0) {
                    params.put("isExcludeFriends", true);
                    params.put("isExcludeFriendRequests", true);
                }
            }
        }
        return params;
    }

    @Override
    public int countUsers(Long currentUserId, UserFilterDto userFilter) {
        Map<String, Object> params = createParametersMap(currentUserId, userFilter);

        return (Integer) getSqlSession().selectOne("User.countUsers", params);
    }
}
