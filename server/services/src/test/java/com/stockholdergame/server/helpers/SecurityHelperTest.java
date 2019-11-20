package com.stockholdergame.server.helpers;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Alexander Savin
 */
@Test
public class SecurityHelperTest {

    public void testCheckMD5Hash() {
        String s = "test";
        String hash = MD5Helper.generateMD5hash("test");
        assertTrue(MD5Helper.checkMD5hash(s, hash));
    }

    public void testCheckMD5HashWithSalt() {
        String s = "test";
        String hash = MD5Helper.generateMD5hashWithSalt("test");
        assertTrue(MD5Helper.checkMD5hash(s, hash));
    }
}
