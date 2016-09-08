package cache.utils;

import java.util.*;
import java.util.Map.Entry;

public class Tools {

    public static <K> Map<K, Date> sortByValueAsc(Map<K, Date> unsortMap) {
        List<Entry<K, Date>> list = new LinkedList<>(unsortMap.entrySet());

        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        Map<K, Date> sortedMap = new LinkedHashMap<>();
        for (Entry<K, Date> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}