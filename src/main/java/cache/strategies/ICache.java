package cache.strategies;

import java.util.AbstractMap.SimpleEntry;

public interface ICache<K, V> {

    boolean put(K key, V value);

    V get(K key);

    boolean remove(K key);

    void clear();

    SimpleEntry<K, V> findUnusableItem();
}