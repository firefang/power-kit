package io.github.firefang.power.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JSON工具类
 * 
 * @author xinufo
 *
 */
public abstract class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    /**
     * Object to JSON
     * 
     * @param object 要序列化成JSON的对象
     * @return JSON字符串
     */
    public static String stringify(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON to Object
     * 
     * @param json 要反序列化的JSON字符串
     * @param clazz 反序列化类型
     * @return 对象
     */
    public static <T> T parse(String json, Class<T> clazz) {
        if (!StringUtil.hasLength(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
