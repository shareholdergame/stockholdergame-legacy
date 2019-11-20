package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import junit.framework.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 22.6.12 22.30
 */
@Test
public class ShareQuantityTest {

    @Test(dataProvider = "data4testBuySell")
    public void testBuySell(int initialQuantity, int buySellQuantity, int expectedQuantity, int expectedLocked)
            throws NotEnoughSharesException, SharesLockedException {
        ShareQuantityImpl shareQuantity = new ShareQuantityImpl(new SharePriceImpl(1L, 10), initialQuantity);
        shareQuantity.buySell(buySellQuantity);
        Assert.assertEquals(expectedQuantity, shareQuantity.getQuantity());
        Assert.assertEquals(expectedLocked, shareQuantity.getLocked());
    }

    @Test(expectedExceptions = NotEnoughSharesException.class)
    public void testBuySellNotEnoughShares() throws NotEnoughSharesException, SharesLockedException {
        ShareQuantityImpl shareQuantity = new ShareQuantityImpl(new SharePriceImpl(1L, 10), 1);
        shareQuantity.buySell(-2);
    }

    @Test(expectedExceptions = SharesLockedException.class)
    public void testBuySellSharesLocked() throws NotEnoughSharesException, SharesLockedException {
        ShareQuantityImpl shareQuantity = new ShareQuantityImpl(new SharePriceImpl(1L, 10), 1);
        shareQuantity.buySell(1);
        shareQuantity.buySell(1);
        shareQuantity.buySell(-3);
    }

    public void testRollback() throws Exception {
        ShareQuantityImpl shareQuantity = new ShareQuantityImpl(new SharePriceImpl(1L, 10), 1);
        shareQuantity.buySell(1);
        Assert.assertEquals(2, shareQuantity.getQuantity());
        Assert.assertEquals(1, shareQuantity.getLocked());
        shareQuantity.rollback();
        Assert.assertEquals(1, shareQuantity.getQuantity());
        Assert.assertEquals(0, shareQuantity.getLocked());
        shareQuantity.commit();
        Assert.assertEquals(1, shareQuantity.getQuantity());
        Assert.assertEquals(0, shareQuantity.getLocked());
    }

    public void testCommit() throws Exception {
        ShareQuantityImpl shareQuantity = new ShareQuantityImpl(new SharePriceImpl(1L, 10), 1);
        shareQuantity.buySell(1);
        Assert.assertEquals(2, shareQuantity.getQuantity());
        Assert.assertEquals(1, shareQuantity.getLocked());
        shareQuantity.commit();
        Assert.assertEquals(2, shareQuantity.getQuantity());
        Assert.assertEquals(0, shareQuantity.getLocked());
        shareQuantity.rollback();
        Assert.assertEquals(2, shareQuantity.getQuantity());
        Assert.assertEquals(0, shareQuantity.getLocked());
    }

    @DataProvider
    private Object[][] data4testBuySell() {
        return new Object[][]{
                {1, -1, 0, 0},
                {0, 1, 1, 1},
                {1, 1, 2, 1}
        };
    }
}
