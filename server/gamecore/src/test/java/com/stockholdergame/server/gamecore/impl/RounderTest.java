package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.Rounder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Rounder test
 */
@Test
public class RounderTest {

    @Test(dataProvider = "data4testRound")
    public void testRound(int value, int scaleStep, boolean isRoundingDown, int expectedValue) {
        int result = Rounder.round(value, scaleStep, isRoundingDown);
        Assert.assertEquals(result, expectedValue);
    }

    @DataProvider
    private Object[][] data4testRound() {
        return new Object[][]{
                {25, 10, false, 30},
                {25, 10, true, 20},
                {30, 10, false, 30},
                {30, 10, true, 30},
                {5, 10, false, 5}
        };
    }
}
