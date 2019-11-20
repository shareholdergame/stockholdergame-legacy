package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 22.6.12 21.49
 */
@Test
public class SharePriceTest {

    public void testSetValue()
            throws SharePriceAlreadyChangedException {
        SharePriceImpl sharePrice = new SharePriceImpl(1L, 10);
        sharePrice.setValue(20);
        Assert.assertEquals(20, sharePrice.getValue());
    }

    @Test(expectedExceptions = SharePriceAlreadyChangedException.class)
    public void testSetValueSharePriceAlreadyChanged() throws SharePriceAlreadyChangedException {
        SharePriceImpl sharePrice = new SharePriceImpl(1L, 10);
        sharePrice.setValue(20);
        sharePrice.setValue(30);
    }

    public void testRollback() throws Exception {
        SharePriceImpl sharePrice = new SharePriceImpl(1L, 10);
        sharePrice.setValue(20);
        Assert.assertEquals(20, sharePrice.getValue());
        sharePrice.rollback();
        Assert.assertEquals(10, sharePrice.getValue());
        sharePrice.commit();
        Assert.assertEquals(10, sharePrice.getValue());
    }

    public void testCommit() throws Exception {
        SharePriceImpl sharePrice = new SharePriceImpl(1L, 10);
        sharePrice.setValue(20);
        Assert.assertEquals(20, sharePrice.getValue());
        sharePrice.commit();
        Assert.assertEquals(20, sharePrice.getValue());
        sharePrice.rollback();
        Assert.assertEquals(20, sharePrice.getValue());
    }
}
