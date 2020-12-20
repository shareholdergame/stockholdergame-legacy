package com.stockholdergame.server.gamecore;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Aliaksandr Savin
 */
public class CompensationCalculatorTest {

    @Test(dataProvider = "data4testCalculateCompensation")
    public void testCalculateCompensation(int oldPrice, int finalPrice, int calculatedPrice, int shareQuantity,
                                          boolean isCurrentCompetitor, int expectedSum) throws Exception {
        int actualSum = CompensationCalculator.calculateCompensation(oldPrice, finalPrice, calculatedPrice,
            shareQuantity, isCurrentCompetitor);
        Assert.assertEquals(expectedSum, actualSum);
    }

    @Test(dataProvider = "data4testCalculateRedemptionSum")
    public void testCalculateRedemptionSum(int finalPrice, int calculatedPrice, int expectedSum) throws Exception {
        int actualSum = CompensationCalculator.calculateRepurchaseSum(finalPrice, calculatedPrice);
        Assert.assertEquals(expectedSum, actualSum);
    }

    @DataProvider
    private Object[][] data4testCalculateCompensation() {
        return new Object[][]{
            {100, 50, 50, 1, true, 50},
            {250, 250, 350, 1, true, 100},
            {50, 30, 30, 15, true, 300},
            {200, 250, 400, 10, true, 1500},
            {30, 10, -10, 1, true, 20},
            {100, 50, 50, 1, false, 0}
        };
    }

    @DataProvider
    private Object[][] data4testCalculateRedemptionSum() {
        return new Object[][]{
                {10, -30, 40},
        };
    }
}
