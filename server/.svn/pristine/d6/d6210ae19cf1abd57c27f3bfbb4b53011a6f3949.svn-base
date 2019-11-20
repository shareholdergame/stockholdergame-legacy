package com.stockholdergame.server.util.security;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 25.4.2010 14.45.27
 */
@Test
public class PasswordGeneratorTest {

    public void testGenerate() {
        String prev = null;
        for (int i = 0; i < 20; i++) {
            String p = RandomStringGenerator.generate();
            Assert.assertTrue(!p.equals(prev));
            prev = p;
        }
    }
}
