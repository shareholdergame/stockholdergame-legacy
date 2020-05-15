package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.StatisticsDao;
import com.stockholdergame.server.model.game.result.Statistics;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@Repository
public class StatisticsDaoImpl implements StatisticsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends Statistics> getUserStatistics(String userName, String statisticsVariant, int offset, int maxResults) {
        String queryStr = StringUtils.isNotBlank(userName)
                ? String.format("select s from %1$s s where s.userName like ? order by s.zyrianov desc, s.notActual, s.ratio desc, s.userName", statisticsVariant)
                : String.format("select s from %1$s s order by s.zyrianov desc, s.notActual, s.ratio desc, s.userName", statisticsVariant);

        Query query = em.createQuery(queryStr);
        if (StringUtils.isNotBlank(userName)) {
            query.setParameter(1, userName + "%");
        }
        query.setFirstResult(offset);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public long countUserStatistics(String userName, String statisticsVariant) {
        String queryStr = StringUtils.isNotBlank(userName) ? String.format("select count(s) from %1$s s where s.userName like ?", statisticsVariant)
                : String.format("select count(s) from %1$s s", statisticsVariant);

        Query query = em.createQuery(queryStr);
        if (StringUtils.isNotBlank(userName)) {
            query.setParameter(1, userName + "%");
        }
        List<Long> count = (List<Long>) query.getResultList();
        return count != null && !count.isEmpty() ? count.get(0) : Long.valueOf(0L);
    }
}
