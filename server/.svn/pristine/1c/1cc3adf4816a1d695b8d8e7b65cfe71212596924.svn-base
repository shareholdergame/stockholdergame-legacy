package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import junit.framework.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
@Test
public class CashTest {

    @Test(dataProvider = "data4testCharge")
    public void testCharge(int initialCashValue, int sum, int expectedCashValue) throws NotEnoughFundsException {
        CashImpl cash = new CashImpl(initialCashValue);
        cash.charge(sum);
        Assert.assertEquals(expectedCashValue, cash.getValue());
    }

    @Test(dataProvider = "data4testChargeCompensation")
    public void testChargeCompensation(int initialCashValue, int compensationSum, int expectedCashValue,
                                       boolean isCurrentCompetitor) {
        CashImpl cash = new CashImpl(initialCashValue);
        cash.chargeCompensation(compensationSum, 1L, !isCurrentCompetitor);
        Assert.assertEquals(expectedCashValue, cash.getValue());
        if (!isCurrentCompetitor) {
            Assert.assertNotNull(cash.getCompensation(1L));
            Assert.assertEquals(cash.getCompensation(1L).getCompensationSum(), compensationSum);
            Assert.assertEquals(cash.getCompensation(1L).getCashValue(), expectedCashValue);
        } else {
            Assert.assertNull(cash.getCompensation(1L));
        }
    }

    @Test(expectedExceptions = NotEnoughFundsException.class)
    public void testChargeNotEnoughFunds() throws NotEnoughFundsException {
        CashImpl cash = new CashImpl(50);
        cash.charge(-100);
    }

    public void testRollback() throws NotEnoughFundsException {
        CashImpl cash = new CashImpl(50);
        cash.charge(-10);
        Assert.assertEquals(40, cash.getValue());
        cash.rollback();
        Assert.assertEquals(50, cash.getValue());
        cash.commit();
        Assert.assertEquals(50, cash.getValue());
    }

    public void testCommit() throws NotEnoughFundsException {
        CashImpl cash = new CashImpl(50);
        cash.charge(-10);
        Assert.assertEquals(40, cash.getValue());
        cash.commit();
        Assert.assertEquals(40, cash.getValue());
        cash.rollback();
        Assert.assertEquals(40, cash.getValue());
    }

    @DataProvider
    private Object[][] data4testCharge() {
        return new Object[][]{
                {0, 100, 100},
                {100, -20, 80}
        };
    }

    @DataProvider
    private Object[][] data4testChargeCompensation() {
        return new Object[][]{
                {0, 20, 20, true},
                {0, 20, 20, false},
                {0, -100, -100, true},
                {0, -100, -100, false}
        };
    }
}
