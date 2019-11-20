package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.ProfileDao;
import com.stockholdergame.server.model.account.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 */
@Repository
public class ProfileDaoImpl extends BaseDao<Profile, Long> implements ProfileDao {

    public Profile findByUserName(String userName) {
        return findSingleObject("Profile.findByUserName", userName);
    }
}
