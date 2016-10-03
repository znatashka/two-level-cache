package cache.strategies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import testutils.KeyValue;
import testutils.ReflectionTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class TwoLevelCacheTest {

    private TwoLevelCache<KeyValue, KeyValue> twoLevelCache;
    private Cache<KeyValue, KeyValue> inMemoryCache = mock(InMemoryCache.class);
    private Cache<KeyValue, KeyValue> fileSystemCache = mock(FileSystemCache.class);

    @Before
    public void setUp() throws Exception {
        twoLevelCache = new TwoLevelCache<>(5, 10, "fake_path");
        ReflectionTestUtils.setField(twoLevelCache, "inMemoryCache", inMemoryCache);
        ReflectionTestUtils.setField(twoLevelCache, "fileSystemCache", fileSystemCache);
    }

    @After
    public void tearDown() {
        Mockito.reset(inMemoryCache, fileSystemCache);
    }

    @Test
    public void put() throws Exception {
        // PREPARE
        when(inMemoryCache.put(KeyValue.KEY1, KeyValue.VALUE1)).thenReturn(true);

        // ACT
        boolean result = twoLevelCache.put(KeyValue.KEY1, KeyValue.VALUE1);

        // ASSERT
        assertTrue(result);
        verify(inMemoryCache).put(KeyValue.KEY1, KeyValue.VALUE1);
    }

    @Test
    public void get() throws Exception {
        // PREPARE
        when(inMemoryCache.get(KeyValue.KEY1)).thenReturn(null);
        when(fileSystemCache.get(KeyValue.KEY1)).thenReturn(KeyValue.VALUE1);

        // ACT
        KeyValue result = twoLevelCache.get(KeyValue.KEY1);

        // ASSERT
        assertNotNull(result);
        assertEquals(KeyValue.VALUE1, result);
        verify(inMemoryCache).get(KeyValue.KEY1);
        verify(fileSystemCache).get(KeyValue.KEY1);
    }

    @Test
    public void remove() throws Exception {
        // PREPARE
        when(inMemoryCache.remove(KeyValue.KEY1)).thenReturn(false);
        when(fileSystemCache.remove(KeyValue.KEY1)).thenReturn(true);

        // ACT
        boolean result = twoLevelCache.remove(KeyValue.KEY1);

        // ASSERT
        assertTrue(result);
        verify(inMemoryCache).remove(KeyValue.KEY1);
        verify(fileSystemCache).remove(KeyValue.KEY1);
    }

    @Test
    public void clear() throws Exception {
        // ACT
        twoLevelCache.clear();

        // ASSERT
        verify(inMemoryCache).clear();
        verify(fileSystemCache).clear();
    }
}