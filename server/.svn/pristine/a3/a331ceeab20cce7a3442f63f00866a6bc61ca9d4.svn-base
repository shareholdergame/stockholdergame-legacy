package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GameEventDao;
import com.stockholdergame.server.model.game.GameEvent;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 *         Date: 20.5.12 23.05
 */
@Repository
public class GameEventDaoImpl extends BaseDao<GameEvent, Long> implements GameEventDao {

    @Override
    public List<GameEvent> findByUserId(Long userId) {
        return findList("GameEvent.findByUserId", userId);
    }
}
