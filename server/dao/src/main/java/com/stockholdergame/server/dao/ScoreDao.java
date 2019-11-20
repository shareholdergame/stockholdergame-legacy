package com.stockholdergame.server.dao;

import com.stockholdergame.server.dto.PaginationDto;
import com.stockholdergame.server.model.game.TotalScoreProjection;
import com.stockholdergame.server.model.game.archive.Score;
import com.stockholdergame.server.model.game.archive.ScorePk;
import java.util.List;

/**
 *
 * @author Aliaksandr Savin
 */
public interface ScoreDao extends GenericDao<Score, ScorePk> {

    List<Score> findByFirstGamerId(Long gamerId, String userName, String[] rulesVersions, PaginationDto pagination);

    int countScorers(Long gamerId, String userName, String[] rulesVersions);

    TotalScoreProjection countTotalScore(Long gamerId, String[] rulesVersions);
}
