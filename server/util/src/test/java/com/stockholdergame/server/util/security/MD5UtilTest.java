package com.stockholdergame.server.util.security;

import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.assertEquals;

/**
 * @author Alexander Savin
 *         Date: 21.4.2010 23.19.16
 */
@Test
public class MD5UtilTest {

    public void testCreateMD5hash() throws NoSuchAlgorithmException {
        assertEquals(MD5Util.createStrongMD5Hash("test".getBytes()), "673836058fe81f1d206e11ce86bc5e60");
    }
}
