package cache.strategies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testutils.KeyValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;

import static org.junit.Assert.*;

public class FileSystemCacheTest {

    private FileSystemCache<KeyValue, KeyValue> fileSystemCache;
    private String cachePath = "src\\test\\resources\\cache";

    @Before
    public void setUp() throws Exception {
        fileSystemCache = new FileSystemCache<>(10, cachePath);
    }

    @After
    public void tearDown() throws Exception {
        Files.list(Paths.get(cachePath)).forEach(path -> {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void put() throws Exception {
        // ACT
        boolean result = fileSystemCache.put(KeyValue.KEY1, KeyValue.VALUE1);

        // ASSERT
        assertTrue(result);
    }

    @Test
    public void get() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        KeyValue result = fileSystemCache.get(KeyValue.KEY1);

        // ASSERT
        assertEquals(KeyValue.VALUE1, result);
    }

    @Test
    public void remove() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        boolean result = fileSystemCache.remove(KeyValue.KEY2);

        // ASSERT
        assertTrue(result);
        assertNull(fileSystemCache.get(KeyValue.KEY2));
        assertNotNull(fileSystemCache.get(KeyValue.KEY1));
    }

    @Test
    public void clear() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        fileSystemCache.clear();

        // ASSERT
        assertNull(fileSystemCache.get(KeyValue.KEY1));
        assertNull(fileSystemCache.get(KeyValue.KEY2));
    }

    @Test
    public void findUnusableItem() throws Exception {
        // PREPARE
        fillCacheWTwoKeys();

        // ACT
        AbstractMap.SimpleEntry<KeyValue, KeyValue> unusableItem = fileSystemCache.findUnusableItem();

        // ASSERT
        assertNotNull(unusableItem);
    }

    private void fillCacheWTwoKeys() {
        fileSystemCache.put(KeyValue.KEY1, KeyValue.VALUE1);
        fileSystemCache.put(KeyValue.KEY2, KeyValue.VALUE2);
    }
}