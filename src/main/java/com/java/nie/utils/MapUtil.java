package com.java.nie.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {


    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int limit) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).limit(limit)
                .forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
