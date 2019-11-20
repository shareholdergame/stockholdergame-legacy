package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.UserSessionLogDao;
import com.stockholdergame.server.model.account.UserSessionLog;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class UserSessionLogDaoImpl extends BaseDao<UserSessionLog, Long> implements UserSessionLogDao {

    public UserSessionLog findLastSession(Long gamerId) {
        return findSingleObject("UserSessionLog.findLastSession", gamerId);
    }

    public List<UserSessionLog> findUnclosedLogRecords(Long userId) {
        return findList("UserSessionLog.findUnclosedLogRecords", userId);
    }
}
