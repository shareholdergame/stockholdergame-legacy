package com.stockholdergame.server.model.game.move;

import com.stockholdergame.server.model.game.StepType;

import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 8.1.11 22.44
 */
public interface MoveResultAdapter {

    Long getGameId();

    Long getAppliedCardId();

    boolean isBankrupt();

    boolean isGameFinished();

    boolean isFinalMove();

    PriceChangeStepResult getPriceChangeStepResult();

    BuySellStepResult getBuySellStepResult(StepType stepType);

    GameResult getGameResult();

    List<Long> getBankruptCompetitorIds();
}
