package io.github.firefang.power.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.github.firefang.power.exception.UnAuthorizedException;

/**
 * @author xinufo
 *
 */
public abstract class BaseAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            boolean errorHandling = ErrorController.class.isAssignableFrom(hm.getBeanType());
            if (!errorHandling && hm.getMethodAnnotation(PublicEndPoint.class) == null) {
                intercept(request);
            }
        }
        return true;
    }

    protected abstract void intercept(HttpServletRequest request) throws UnAuthorizedException;

}
