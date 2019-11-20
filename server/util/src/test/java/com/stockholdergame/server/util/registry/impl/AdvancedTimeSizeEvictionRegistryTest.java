package com.stockholdergame.server.util.registry.impl;

import com.stockholdergame.server.util.registry.RegistryAlmostFullException;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 *
 */
public class AdvancedTimeSizeEvictionRegistryTest {

    @Test
    public void testContains() throws Exception {
        AdvancedTimeSizeEvictionRegistry<String, String> registry = new AdvancedTimeSizeEvictionRegistry<String, String>();
        registry.put("key", "value");
        Assert.assertTrue(registry.contains("key"));
    }

    @Test
    public void testGet() throws Exception {
        AdvancedTimeSizeEvictionRegistry<String, String> registry = new AdvancedTimeSizeEvictionRegistry<String, String>();
        registry.put("key", "value");
        Assert.assertEquals("value", registry.get("key"));
    }

    @Test
    public void testEvict() throws Exception {
        AdvancedTimeSizeEvictionRegistry<String, String> registry = new AdvancedTimeSizeEvictionRegistry<String, String>();
        registry.setMaxSize(5);
        registry.setFillFactor(1);
        registry.setEvictionTimeout(1500);

        registry.put("key1", "v1");
        Assert.assertEquals(1, registry.size());

        Thread.sleep(1000);

        registry.put("key2", "v2");
        Assert.assertEquals(2, registry.size());

        Thread.sleep(1000);

        registry.evict();
        Assert.assertEquals(1, registry.size());
        Assert.assertEquals("v2", registry.get("key2"));

        Thread.sleep(2000);
        registry.evict();
        Assert.assertEquals(0, registry.size());
    }

    @Test(expectedExceptions = RegistryAlmostFullException.class)
    public void testPutException() throws Exception {
        AdvancedTimeSizeEvictionRegistry<String, String> registry = new AdvancedTimeSizeEvictionRegistry<String, String>();
        registry.setMaxSize(5);
        registry.setFillFactor(0.5);
        registry.setEvictionTimeout(1500);

        registry.put("key1", "v1");
        registry.put("key2", "v2");
        registry.put("key3", "v3");
        registry.put("key4", "v4");
    }

    @Test
    public void testRemove() throws Exception {
        AdvancedTimeSizeEvictionRegistry<String, String> registry = new AdvancedTimeSizeEvictionRegistry<String, String>();
        registry.put("key", "value");
        Assert.assertTrue(registry.contains("key"));
        registry.remove("key");
        Assert.assertFalse(registry.contains("key"));
    }
}
