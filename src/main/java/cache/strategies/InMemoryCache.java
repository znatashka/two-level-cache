package cache.strategies;

import cache.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class InMemoryCache<K, V> implements Cache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(InMemoryCache.class);

    private int maxLength;

    private Map<K, V> cache = new HashMap<>();
    private Map<K, Date> counter = new HashMap<>();

    InMemoryCache(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean put(K key, V value) {
        log.info("InMemoryCache | put | key={} | value={}", key, value);

        if (cache.size() < maxLength) {
            cache.put(key, value);
            counter.put(key, new Date());
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        log.info("InMemoryCache | get | key={}", key);

        if (cache.containsKey(key)) {
            counter.put(key, new Date());
            return cache.get(key);
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        log.info("InMemoryCache | remove | key={}", key);

        if (cache.containsKey(key)) {
            cache.remove(key);
            counter.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        log.info("InMemoryCache | clear");

        cache.clear();
        counter.clear();
    }

    @Override
    public SimpleEntry<K, V> findUnusableItem() {
        log.info("InMemoryCache | findUnusableItem");

        Iterator<Map.Entry<K, Date>> iterator = Tools.sortByValueAsc(counter).entrySet().iterator();
        if (iterator.hasNext()) {
            K unusableKey = new SimpleEntry<>(iterator.next()).getKey();
            for (Map.Entry<K, V> entry : cache.entrySet()) {
                if (unusableKey.equals(entry.getKey())) return new SimpleEntry<>(entry);
            }
        }
        return null;
    }
}