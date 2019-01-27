package io.github.firefang.power.login.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.github.firefang.power.login.PublicEndPoint;

/**
 * Session认证拦截器
 * 
 * @author xinufo
 *
 */
public class SessionHandlerInterceptor extends HandlerInterceptorAdapter {
    private String key;
    private ISessionAuthService sessionSrv;

    public SessionHandlerInterceptor(String key, ISessionAuthService sessionSrv) {
        this.key = key;
        this.sessionSrv = sessionSrv;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            if (hm.getMethodAnnotation(PublicEndPoint.class) == null) {
                HttpSession session = request.getSession();
                sessionSrv.auth(session.getAttribute(key));
            }
        }
        return true;
    }

}
