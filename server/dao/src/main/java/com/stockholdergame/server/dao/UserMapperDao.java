package com.stockholdergame.server.dao;

import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import java.util.List;

/**
 *
 */
public interface UserMapperDao {

    List<UserDto> findUsers(Long currentUserId, UserFilterDto userFilter);

    int countUsers(Long currentUserId, UserFilterDto userFilter);
}
