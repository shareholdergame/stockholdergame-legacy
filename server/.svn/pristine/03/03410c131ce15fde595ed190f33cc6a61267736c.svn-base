package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GameVariantDao;
import com.stockholdergame.server.model.game.variant.GameVariant;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Savin
 */
@Repository
public class GameVariantDaoImpl extends BaseDao<GameVariant, Long> implements GameVariantDao {

    public List<GameVariant> findAllActiveVariants() {
        return findList("GameVariant.findActiveAllVariants");
    }
}
