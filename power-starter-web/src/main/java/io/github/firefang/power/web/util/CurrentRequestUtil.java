package io.github.firefang.power.web.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Utility class for getting current request
 * 
 * @author xinufo
 *
 */
public abstract class CurrentRequestUtil {

    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            throw new IllegalStateException("Can't get current request");
        }
        return ((ServletRequestAttributes) ra).getRequest();
    }

    public static String getHeader(String key) {
        HttpServletRequest request = getCurrentRequest();
        return request.getHeader(key);
    }

    public static Object getSessionAttribute(String key) {
        HttpServletRequest request = getCurrentRequest();
        return request.getSession().getAttribute(key);
    }

}
