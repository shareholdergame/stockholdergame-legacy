package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.game.variant.GameVariant;
import java.util.List;

/**
 * @author Alexander Savin
 */
public interface GameVariantDao extends GenericDao<GameVariant, Long> {

    List<GameVariant> findAllActiveVariants();
}
