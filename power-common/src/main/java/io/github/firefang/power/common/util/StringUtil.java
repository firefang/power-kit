package io.github.firefang.power.common.util;

/**
 * 字符串工具类
 * 
 * @author xinufo
 *
 */
public abstract class StringUtil {

    public static boolean hasLength(String str) {
        return str == null ? false : str.length() > 0;
    }

}
