package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.ShareQuantity;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author Alexander Savin
 *         Date: 23.6.12 9.33
 */
@Test
public class CompetitorAccountTest {

    private static final long SHARE_ID = 1L;

    private CompetitorAccountImpl competitorAccount;

    @BeforeMethod
    public void setUp() throws Exception {
        ShareQuantityImpl shareQuantity = new ShareQuantityImpl(new SharePriceImpl(SHARE_ID, 100), 1);
        competitorAccount = new CompetitorAccountImpl(1, false, new CashImpl(100),
                Arrays.<ShareQuantity>asList(shareQuantity));
    }

    @Test(dataProvider = "data4testBuySellShare")
    public void testBuySellShare(int buySellQuantity, int expectedQuantity, int expectedCash) throws Exception {
        competitorAccount.buySellShare(SHARE_ID, buySellQuantity);
        Assert.assertEquals(expectedQuantity, competitorAccount.getShareQuantity(SHARE_ID));
        Assert.assertEquals(expectedCash, competitorAccount.getCash());
        Assert.assertEquals(200, competitorAccount.getTotal());
    }

    public void testOut() throws Exception {
        competitorAccount.chargeCompensation(-10000, SHARE_ID, false);
        competitorAccount.out();
        Assert.assertEquals(true, competitorAccount.isOut());
    }

    @Test(dataProvider = "data4testChargeCompensation")
    public void testChargeCompensation(int compensationSum, int expectedCash, boolean isCurrent) throws Exception {
        competitorAccount.chargeCompensation(compensationSum, SHARE_ID, isCurrent);
        Assert.assertEquals(expectedCash, competitorAccount.getCash());
        Assert.assertEquals(expectedCash + 100, competitorAccount.getTotal());
    }

    public void testCommit() throws Exception {
        competitorAccount.buySellShare(SHARE_ID, -1);
        competitorAccount.chargeCompensation(100, SHARE_ID, false);
        Assert.assertEquals(300, competitorAccount.getTotal());
        competitorAccount.commit();
        Assert.assertEquals(300, competitorAccount.getTotal());
        competitorAccount.rollback();
        Assert.assertEquals(300, competitorAccount.getTotal());
    }

    public void testRollback() throws Exception {
        competitorAccount.buySellShare(SHARE_ID, -1);
        competitorAccount.chargeCompensation(100, SHARE_ID, false);
        Assert.assertEquals(300, competitorAccount.getTotal());
        competitorAccount.rollback();
        Assert.assertEquals(200, competitorAccount.getTotal());
        competitorAccount.commit();
        Assert.assertEquals(200, competitorAccount.getTotal());
    }

    @DataProvider
    private Object[][] data4testBuySellShare() {
        return new Object[][]{
                {-1, 0, 200},
                {1, 2, 0},
                {0, 1, 100}
        };
    }

    @DataProvider
    private Object[][] data4testChargeCompensation() {
        return new Object[][]{
                {100, 200, true},
                {-100, 0, false},
                {-200, -100, false}
        };
    }
}
