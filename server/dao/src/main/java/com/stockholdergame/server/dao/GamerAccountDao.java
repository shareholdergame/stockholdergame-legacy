package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.account.GamerAccount;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 */
public interface GamerAccountDao extends GenericDao<GamerAccount, Long>, UniqueFinder<GamerAccount> {

    GamerAccount findByUserName(String userName);

    GamerAccount findByEmail(String email);

    List<Long> findRemovedGamerIdsWithExpiredTerm(Date removalDate);

    List<GamerAccount> findBots();
}
