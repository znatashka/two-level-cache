package cache.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InitDataTest {

    @Test
    public void initialize() throws Exception {
        // PREPARE
        int maxLengthInMemoryCache = 10;
        int maxLengthFileSystemCache = 15;
        String fileSystemCachePath = "fake_path";
        String[] args = {
                String.valueOf(maxLengthInMemoryCache),
                String.valueOf(maxLengthFileSystemCache),
                fileSystemCachePath
        };

        // ACT
        InitData initData = InitData.initialize(args);

        // ASSERT
        assertEquals(maxLengthInMemoryCache, initData.getMaxLengthInMemoryCache());
        assertEquals(maxLengthFileSystemCache, initData.getMaxLengthFileSystemCache());
        assertEquals(fileSystemCachePath, initData.getFileSystemCachePath());
    }
}