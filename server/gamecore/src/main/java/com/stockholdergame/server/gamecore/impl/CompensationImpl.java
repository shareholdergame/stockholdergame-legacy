package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.Compensation;

/**
 * @author Alexander Savin
 *         Date: 9.9.12 10.56
 */
public class CompensationImpl implements Compensation {

    private int compensationSum;

    private int cashValue;

    public CompensationImpl(int compensationSum, int cashValue) {
        this.compensationSum = compensationSum;
        this.cashValue = cashValue;
    }

    @Override
    public int getCompensationSum() {
        return compensationSum;
    }

    @Override
    public int getCashValue() {
        return cashValue;
    }
}
