package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.model.account.GamerAccount;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 */
@Repository
public class GamerAccountDaoImpl extends BaseDao<GamerAccount, Long> implements GamerAccountDao {

    public GamerAccount findByUniqueParameters(Object... params) {
        return findSingleObject("GamerAccount.findByUniqueParams", params[0], params[0]);
    }

    public GamerAccount findByUserName(String userName) {
        return findSingleObject("GamerAccount.findByUserName", userName);
    }

    public GamerAccount findByEmail(String email) {
        return findSingleObject("GamerAccount.findByEmail", email);
    }

    @SuppressWarnings("unchecked")
    public List<Long> findRemovedGamerIdsWithExpiredTerm(Date removalDate) {
        return (List<Long>) findByNamedQuery("GamerAccount.findRemovedGamerIdsWithExpiredTerm", removalDate);
    }

    @Override
    public List<GamerAccount> findBots() {
        return findList("GamerAccount.findBots");
    }
}
