package com.stockholdergame.server.gamecore;

import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public interface Cash {

    int getValue();

    Compensation getCompensation(Long shareId);

    void charge(int chargeSum) throws NotEnoughFundsException;

    void chargeCompensation(int compensationSum, Long shareId, boolean saveInHistory);
}
