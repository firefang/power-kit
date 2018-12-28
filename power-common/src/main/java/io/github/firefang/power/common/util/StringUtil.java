package io.github.firefang.power.common.util;

/**
 * Utility class for String
 * 
 * @author xinufo
 *
 */
public abstract class StringUtil {

    public static boolean hasLength(String str) {
        return str == null ? false : str.length() > 0;
    }

}
