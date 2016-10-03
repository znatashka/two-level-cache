package cache.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap.SimpleEntry;

/**
 * Основной класс для работы с двухуровневым кэшем
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class TwoLevelCache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(TwoLevelCache.class);

    private Cache<K, V> inMemoryCache;
    private Cache<K, V> fileSystemCache;

    public TwoLevelCache(int maxLengthInMemoryCache, int maxLengthFileSystemCache, String fileSystemCachePath) {
        inMemoryCache = new InMemoryCache<>(maxLengthInMemoryCache);
        fileSystemCache = new FileSystemCache<>(maxLengthFileSystemCache, fileSystemCachePath);
    }

    /**
     * Метод добавления записи в кэш
     *
     * @param key   ключ записи
     * @param value значение записи
     * @return true - если удалось добавть, false - если нет
     */
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

    /**
     * Метод получения записи по ключу
     *
     * @param key ключ записи
     * @return значение
     */
    public V get(K key) {
        log.info("TwoLevelCache | get | key={}", key);

        V value = inMemoryCache.get(key);
        if (value == null) value = fileSystemCache.get(key);
        return value;
    }

    /**
     * Метод удаления записи из кэша
     *
     * @param key ключ записи
     * @return true - если удалось удалить, false - если нет
     */
    public boolean remove(K key) {
        log.info("TwoLevelCache | remove | key={}", key);

        return inMemoryCache.remove(key) || fileSystemCache.remove(key);
    }

    /**
     * Метод очистки кэша
     */
    public void clear() {
        log.info("TwoLevelCache | clear");

        inMemoryCache.clear();
        fileSystemCache.clear();
    }
}