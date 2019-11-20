package com.stockholdergame.server.util.collections;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Test
public class ComparerTest {

    @Test(dataProvider = "getDada4In")
    public void testIn(boolean expected, String str, String[] strs) throws Exception {
        Assert.assertEquals(expected, Comparer.in(str, strs));
    }

    @DataProvider
    private Object[][] getDada4In() {
        return new Object[][] {
                {true, "a", new String[] {"a", "b"}},
                {false, "a", new String[] {"b", "c"}},
                {true, null, new String[] {null, "c"}},
                {false, null, new String[] {"a", "c"}},
                {false, null, new String[] {}},
                {false, null, null},
        };
    }

    @Test
    public void testSortedMap() {
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        map.put(1L, 100);
        map.put(2L, 50);
        map.put(3L, 20);
        map.put(4L, 50);

        Map<Long, Integer> sortedMap = CollectionsUtil.sortMapByValues(map);

        List<Map.Entry<Long, Integer>> entries = new ArrayList<Map.Entry<Long, Integer>>(sortedMap.entrySet());

        Assert.assertEquals(20, (int) entries.get(0).getValue());
        Assert.assertEquals(3L, (long) entries.get(0).getKey());

        Assert.assertEquals(50, (int) entries.get(1).getValue());
        Assert.assertEquals(50, (int) entries.get(2).getValue());
        Assert.assertTrue(entries.get(1).getKey() == 2L || entries.get(1).getKey() == 4L);
        Assert.assertTrue(entries.get(2).getKey() == 2L || entries.get(2).getKey() == 4L);

        Assert.assertEquals(100, (int) entries.get(3).getValue());
        Assert.assertEquals(1L, (long) entries.get(3).getKey());
    }
}
