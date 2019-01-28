package io.github.firefang.power.login.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.BaseAuthInterceptor;

/**
 * Session认证拦截器
 * 
 * @author xinufo
 *
 */
public class SessionHandlerInterceptor extends BaseAuthInterceptor {
    private String key;
    private ISessionAuthService sessionSrv;

    public SessionHandlerInterceptor(String key, ISessionAuthService sessionSrv) {
        this.key = key;
        this.sessionSrv = sessionSrv;
    }

    @Override
    protected void intercept(HttpServletRequest request) throws UnAuthorizedException {
        HttpSession session = request.getSession();
        sessionSrv.auth(session.getAttribute(key));
    }

}
