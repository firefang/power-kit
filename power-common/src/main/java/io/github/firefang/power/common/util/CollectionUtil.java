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

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null ? true : collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null ? true : map.isEmpty();
    }

}
