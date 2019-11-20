package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GameSeriesResultDao;
import com.stockholdergame.server.model.game.result.GameSeriesResult;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 */
@Repository
public class GameSeriesResultDaoImpl extends BaseDao<GameSeriesResult, Long> implements GameSeriesResultDao {
}
