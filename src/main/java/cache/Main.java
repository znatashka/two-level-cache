package cache;

import cache.data.InitData;
import cache.strategies.TwoLevelCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        InitData initData = InitData.initialize();
        log.info("{}", initData);

        TwoLevelCache<Integer, String> twoLevelCache = new TwoLevelCache<>(
                initData.getMaxLengthInMemoryCache(),
                initData.getMaxLengthFileSystemCache(),
                initData.getFileSystemCachePath()
        );

        for (int i = 0; i < 10; i++) {
            twoLevelCache.put(i, "Object#" + i);
            log.info(twoLevelCache.get(i));
        }

        log.info(twoLevelCache.get(8)); // чтение из кэша в памяти
        log.info(twoLevelCache.get(2)); // чтение из кэша в файловой системе

        twoLevelCache.remove(7); // удаление из кэша в памяти
        twoLevelCache.remove(1); // удаление из кэша в файловой системе

        twoLevelCache.clear();
    }
}