package cache.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitData {
    private static final Logger log = LoggerFactory.getLogger(InitData.class);

    private int maxLengthInMemoryCache = 5;
    private int maxLengthFileSystemCache = 10;
    private String fileSystemCachePath = "C:/TwoLevelCache";

    public static InitData initialize(String[] args) {
        InitData initData = new InitData();
        if (args.length > 0) {
            try {
                initData.maxLengthInMemoryCache = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
            }
            if (args.length > 1) {
                try {
                    initData.maxLengthFileSystemCache = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (args.length > 2) {
                initData.fileSystemCachePath = args[2];
            }
        }
        return initData;
    }

    public int getMaxLengthInMemoryCache() {
        return maxLengthInMemoryCache;
    }

    public int getMaxLengthFileSystemCache() {
        return maxLengthFileSystemCache;
    }

    public String getFileSystemCachePath() {
        return fileSystemCachePath;
    }

    @Override
    public String toString() {
        return String.format("InitData | maxLengthInMemoryCache=%d | maxLengthFileSystemCache=%d | fileSystemCachePath=%s",
                maxLengthInMemoryCache, maxLengthFileSystemCache, fileSystemCachePath);
    }
}