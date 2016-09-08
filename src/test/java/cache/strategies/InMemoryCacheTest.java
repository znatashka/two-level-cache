package cache.strategies;

import org.junit.Before;
import org.junit.Test;
import testutils.KeyValue;

import java.util.AbstractMap;

import static org.junit.Assert.*;

public class InMemoryCacheTest {

    private ICache<KeyValue, KeyValue> inMemoryCache;

    @Before
    public void setUp() {
        inMemoryCache = new InMemoryCache<>(5);
    }

    @Test
    public void put() throws Exception {
        // ACT
        boolean result = inMemoryCache.put(KeyValue.KEY1, KeyValue.VALUE1);

        // ASSERT
        assertTrue(result);
    }

    @Test
    public void get() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        KeyValue result = inMemoryCache.get(KeyValue.KEY1);

        // ASSERT
        assertEquals(KeyValue.VALUE1, result);
    }

    @Test
    public void remove() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        boolean result = inMemoryCache.remove(KeyValue.KEY2);

        // ASSERT
        assertTrue(result);
        assertNull(inMemoryCache.get(KeyValue.KEY2));
        assertNotNull(inMemoryCache.get(KeyValue.KEY1));
    }

    @Test
    public void clear() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        inMemoryCache.clear();

        // ASSERT
        assertNull(inMemoryCache.get(KeyValue.KEY1));
        assertNull(inMemoryCache.get(KeyValue.KEY2));
    }

    @Test
    public void findUnusableItem() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        AbstractMap.SimpleEntry<KeyValue, KeyValue> unusableItem = inMemoryCache.findUnusableItem();

        // ASSERT
        assertNotNull(unusableItem);
    }

    private void fillCacheWTwoKeys() {
        inMemoryCache.put(KeyValue.KEY1, KeyValue.VALUE1);
        inMemoryCache.put(KeyValue.KEY2, KeyValue.VALUE2);
    }
}