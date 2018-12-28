package io.github.firefang.power.login.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
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
        sessionSrv.auth(key);
        return true;
    }

}
