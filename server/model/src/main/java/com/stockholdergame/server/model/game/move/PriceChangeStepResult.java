package com.stockholdergame.server.model.game.move;

import java.util.Map;

/**
 * @author Alexander Savin
 *         Date: 8.1.11 23.05
 */
public interface PriceChangeStepResult extends StepResult {

    Integer getSharePrice(Long shareId);

    Map<Long, Integer[]> getCompetitorCompensations(Long competitorId);

    Long getPriceOperationId(Long shareId);

    Map<Long, Integer> getCompetitorBankruptingProcedureMap(Long competitorId);

    int getBankruptCash(Long competitorId);
}
