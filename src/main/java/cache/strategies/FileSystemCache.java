package cache.strategies;

import cache.utils.Serializer;
import cache.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class FileSystemCache<K, V> implements ICache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(FileSystemCache.class);

    private int maxLength;
    private String cachePath;

    private Map<K, Date> counter = new HashMap<>();

    FileSystemCache(int maxLength, String cachePath) {
        this.maxLength = maxLength;
        this.cachePath = cachePath;
    }

    @Override
    public boolean put(K key, V value) {
        log.info("FileSystemCache | put | key={} | value={}", key, value);

        File dir = new File(cachePath);
        if (!dir.exists()) {
            boolean isDirCreated = dir.mkdirs();
            if (!isDirCreated) return false;
        }
        int length = dir.list().length;
        try {
            if (length < maxLength) {
                Files.write(Paths.get(cachePath + "/" + key.hashCode()), Serializer.toBytes(value));
                counter.put(key, new Date());
                return true;
            } else {
                SimpleEntry<K, V> unusableItem = findUnusableItem();
                if (unusableItem != null) remove(unusableItem.getKey());
                return put(key, value);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public V get(K key) {
        log.info("FileSystemCache | get | key={}", key);

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(cachePath + "/" + key.hashCode()));
            counter.put(key, new Date());
            return Serializer.toObject(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        log.info("FileSystemCache | remove | key={}", key);

        Path path = Paths.get(cachePath + "/" + key.hashCode());
        if (Files.exists(path)) {
            try {
                Files.delete(path);
                counter.remove(key);
                return true;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public void clear() {
        log.info("FileSystemCache | clear");

        try {
            Files.walkFileTree(Paths.get(cachePath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
            counter.clear();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public SimpleEntry<K, V> findUnusableItem() {
        log.info("FileSystemCache | findUnusableItem");

        Iterator<Map.Entry<K, Date>> iterator = Tools.sortByValueAsc(counter).entrySet().iterator();
        if (iterator.hasNext()) {
            K unusableKey = new SimpleEntry<>(iterator.next()).getKey();
            V unusableValue = get(unusableKey);
            if (unusableValue != null)
                return new SimpleEntry<>(unusableKey, unusableValue);
        }
        return null;
    }
}