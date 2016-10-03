package cache.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToolsTest {

    @Test
    public void sortByValueAsc() throws Exception {
        // PREPARE
        String key1 = RandomStringUtils.random(10);
        String key2 = RandomStringUtils.random(10);
        String key3 = RandomStringUtils.random(10);
        Map<Object, Date> unsortedMap = new HashMap<>();
        unsortedMap.put(key3, parseDate("03.03.2000"));
        unsortedMap.put(key2, parseDate("02.02.1980"));
        unsortedMap.put(key1, parseDate("01.01.1970"));

        // ACT
        Map<Object, Date> sortedMap = Tools.sortByValueAsc(unsortedMap);

        // ASSERT
        List<Object> keys = new ArrayList<>(sortedMap.keySet());
        Assert.assertEquals(key1, keys.get(0));
        Assert.assertEquals(key2, keys.get(1));
        Assert.assertEquals(key3, keys.get(2));
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}