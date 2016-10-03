package cache.data;

import static cache.utils.InitDataUtils.checkBooleanAnswer;
import static cache.utils.InitDataUtils.checkIntegerAnswer;
import static cache.utils.Tools.readAnswerFromConsole;

public class InitData {

    private int maxLengthInMemoryCache = 5;
    private int maxLengthFileSystemCache = 10;
    private String fileSystemCachePath = "TwoLevelCache";

    public static InitData initialize() {
        boolean isSet = checkBooleanAnswer(readAnswerFromConsole("Do you want to set your settings? [y/n]: "));

        InitData initData = new InitData();
        if (isSet) {
            initData.maxLengthInMemoryCache = checkIntegerAnswer(
                    readAnswerFromConsole("Set Max Length In Memory Cache: ")
            );
            initData.maxLengthFileSystemCache = checkIntegerAnswer(
                    readAnswerFromConsole("Set Max Length File System Cache: ")
            );
            initData.fileSystemCachePath = readAnswerFromConsole("Set File System Cache Path: ");
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