package io.github.firefang.power.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * Collection工具类
 * 
 * @author xinufo
 *
 */
public abstract class CollectionUtil {
    public static final float MAP_DEFAULT_LOAD_FACTOR = 0.75F;
    public static final int MAP_DEFAULT_SIZE = 16;

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null ? true : collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null ? true : map.isEmpty();
    }

    public static int mapSize(int numOfElements) {
        return mapSize(numOfElements, MAP_DEFAULT_LOAD_FACTOR);
    }

    public static int mapSize(int numOfElements, float factor) {
        return (int) (numOfElements / factor + 1);
    }

}
