package com.stockholdergame.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Savin
 *         Date: 11.11.2010 22.36.53
 */
@Test(enabled = false)
@ContextConfiguration(locations = "classpath:services-context.xml")
public class ServicesSpringContextIntegrationTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ApplicationContext ctx;

    public void testContext() {
        assertNotNull(ctx);
    }
}
