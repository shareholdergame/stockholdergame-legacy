package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.Cash;
import com.stockholdergame.server.gamecore.Compensation;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.TransactionSupport;
import java.util.*;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public class CashImpl implements Cash, TransactionSupport {

    private int oldValue = 0;

    private int value = 0;

    private int chargeCounter = 0;

    private Map<Long, Compensation> compensationsAndFines = new TreeMap<>();

    public CashImpl(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Compensation getCompensation(Long shareId) {
        return compensationsAndFines.get(shareId);
    }

    public void charge(int chargeSum) throws NotEnoughFundsException {
        if (chargeSum < 0 && this.value + chargeSum < 0) {
            throw new NotEnoughFundsException("Cash current value: "  + this.value + ", cash expected value: " + chargeSum);
        }

        if (chargeCounter == 0) {
            this.oldValue = value;
        }
        this.value += chargeSum;
        this.chargeCounter++;
    }

    public void chargeCompensation(int compensationSum, Long shareId, boolean saveInHistory) {
        if (compensationSum == 0) {
            return;
        }
        if (chargeCounter == 0) {
            this.oldValue = this.value;
        }
        this.value += compensationSum;
        if (saveInHistory) {
            compensationsAndFines.put(shareId, new CompensationImpl(compensationSum, value));
        }
        this.chargeCounter++;
    }

    public void commit() {
        if (chargeCounter > 0) {
            compensationsAndFines.clear();
            chargeCounter = 0;
        }
    }

    public void rollback() {
        if (chargeCounter > 0) {
            this.value = this.oldValue;
            compensationsAndFines.clear();
            chargeCounter = 0;
        }
    }
}
