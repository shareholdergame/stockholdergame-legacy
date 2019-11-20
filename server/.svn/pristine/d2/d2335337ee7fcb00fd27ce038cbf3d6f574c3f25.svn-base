package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.ScoreDao;
import com.stockholdergame.server.dto.PaginationDto;
import com.stockholdergame.server.model.game.TotalScoreProjection;
import com.stockholdergame.server.model.game.archive.Score;
import com.stockholdergame.server.model.game.archive.ScorePk;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Aliaksandr Savin
 */
@Repository
public class ScoreDaoImpl extends BaseDao<Score, ScorePk> implements ScoreDao {

    private static final TotalScoreProjection DEFAULT_TOTAL_SCORE = new TotalScoreProjection(0, 0, 0, 0);

    @Override
    @SuppressWarnings("unchecked")
    public List<Score> findByFirstGamerId(Long gamerId, String userName, String[] rulesVersions, PaginationDto pagination) {
        Query query = em.createNamedQuery("Score.findByFirstGamerId");
        query.setFirstResult(pagination.getOffset());
        query.setMaxResults(pagination.getMaxResults());
        query.setParameter(1, gamerId);
        query.setParameter(2, userName == null ? "%" : userName + "%");
        query.setParameter(3, Arrays.asList(rulesVersions));
        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            return new ArrayList<Score>();
        }

        Query query1 = em.createNamedQuery("Score.findBySecondGamerIds");
        query1.setParameter(1, gamerId);
        query1.setParameter(2, getScoreIds(results));
        query1.setParameter(3, Arrays.asList(rulesVersions));
        return query1.getResultList();
    }

    private List<Long> getScoreIds(List<Object[]> results) {
        ArrayList<Long> ids = new ArrayList<Long>();
        for (Object[] result : results) {
            ids.add((Long) result[0]);
        }
        return ids;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countScorers(Long gamerId, String userName, String[] rulesVersions) {
        List<Long> counts = (List<Long>) findByNamedQuery("Score.countScorers", gamerId, userName == null ? "%" : userName + "%",
                Arrays.asList(rulesVersions));
        return counts != null && !counts.isEmpty() ? counts.get(0).intValue() : 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TotalScoreProjection countTotalScore(Long gamerId, String[] rulesVersions) {
        List<TotalScoreProjection> scoreProjections = (List<TotalScoreProjection>) findByNamedQuery("Score.countTotalScore", gamerId,
                Arrays.asList(rulesVersions));
        return scoreProjections != null && !scoreProjections.isEmpty() ? scoreProjections.get(0) : DEFAULT_TOTAL_SCORE;
    }
}
