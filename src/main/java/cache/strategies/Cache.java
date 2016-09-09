package cache.strategies;

import java.util.AbstractMap.SimpleEntry;

/**
 * Данный интерфейс должны реализовывать все стратегии кэширования
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public interface Cache<K, V> {

    /**
     * Метод добавления записи в кэш
     *
     * @param key   ключ записи
     * @param value значение записи
     * @return true - если удалось добавть, false - если нет
     */
    boolean put(K key, V value);

    /**
     * Метод получения записи по ключу
     *
     * @param key ключ записи
     * @return значение
     */
    V get(K key);

    /**
     * Метод удаления записи из кэша
     *
     * @param key ключ записи
     * @return true - если удалось удалить, false - если нет
     */
    boolean remove(K key);

    /**
     * Метод очистки кэша
     */
    void clear();

    /**
     * Метод получения неиспользуемой записи из кэша
     *
     * @return неиспользуемая запись
     */
    SimpleEntry<K, V> findUnusableItem();
}