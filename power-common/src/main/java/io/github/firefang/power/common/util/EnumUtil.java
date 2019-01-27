package io.github.firefang.power.common.util;

import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * 枚举工具类
 * 
 * @author xinufo
 *
 */
public abstract class EnumUtil {
    private static final BiPredicate<? extends Enum<?>, String> STRICT = (e, n) -> {
        return e.name().equals(n);
    };
    private static final BiPredicate<? extends Enum<?>, String> NOT_STRICT = (e, n) -> {
        return e.name().equalsIgnoreCase(n);
    };

    public static <T extends Enum<T>> T fromString(Class<T> enumType, String name) {
        return fromString(enumType, name, false);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T fromString(Class<T> enumType, String name, boolean strict) {
        BiPredicate<T, String> p;
        if (strict) {
            p = (BiPredicate<T, String>) STRICT;
        } else {
            p = (BiPredicate<T, String>) NOT_STRICT;
        }
        for (T e : enumType.getEnumConstants()) {
            if (p.test(e, name)) {
                return e;
            }
        }
        return null;
    }

    public static <T extends Enum<T>> Optional<T> fromStringOptional(Class<T> enumType, String name) {
        return Optional.ofNullable(fromString(enumType, name, false));
    }

    public static <T extends Enum<T>> Optional<T> fromStringOptional(Class<T> enumType, String name, boolean strict) {
        return Optional.ofNullable(fromString(enumType, name, strict));
    }

}
