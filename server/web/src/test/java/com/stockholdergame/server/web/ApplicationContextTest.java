package com.stockholdergame.server.web;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

/**
 *
 */
//@Test
@ContextConfiguration(locations = "classpath:web-context.xml")
public class ApplicationContextTest extends AbstractTestNGSpringContextTests {

    public void testApplicationContext() {
        Assert.assertNotNull(applicationContext);
    }
}
