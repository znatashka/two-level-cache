package cache.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap.SimpleEntry;

public class TwoLevelCache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(TwoLevelCache.class);

    private ICache<K, V> inMemoryCache;
    private ICache<K, V> fileSystemCache;

    public TwoLevelCache(int maxLengthInMemoryCache, int maxLengthFileSystemCache, String fileSystemCachePath) {
        inMemoryCache = new InMemoryCache<>(maxLengthInMemoryCache);
        fileSystemCache = new FileSystemCache<>(maxLengthFileSystemCache, fileSystemCachePath);
    }

    public boolean put(K key, V value) {
        log.info("TwoLevelCache | put | key={} | value={}", key, value);

        boolean isPut = inMemoryCache.put(key, value);
        if (!isPut) {
            SimpleEntry<K, V> unusableItem = inMemoryCache.findUnusableItem();
            if (unusableItem != null)
                return inMemoryCache.remove(unusableItem.getKey()) && inMemoryCache.put(key, value) && fileSystemCache.put(unusableItem.getKey(), unusableItem.getValue());
        }
        return isPut;
    }

    public V get(K key) {
        log.info("TwoLevelCache | get | key={}", key);

        V value = inMemoryCache.get(key);
        if (value == null) value = fileSystemCache.get(key);
        return value;
    }

    public boolean remove(K key) {
        log.info("TwoLevelCache | remove | key={}", key);

        return inMemoryCache.remove(key) || fileSystemCache.remove(key);
    }

    public void clear() {
        log.info("TwoLevelCache | clear");

        inMemoryCache.clear();
        fileSystemCache.clear();
    }
}