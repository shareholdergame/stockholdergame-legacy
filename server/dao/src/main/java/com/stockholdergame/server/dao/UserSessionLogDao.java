package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.account.UserSessionLog;
import java.util.List;

/**
 *
 */
public interface UserSessionLogDao extends GenericDao<UserSessionLog, Long> {

    UserSessionLog findLastSession(Long gamerId);

    List<UserSessionLog> findUnclosedLogRecords(Long userId);
}
