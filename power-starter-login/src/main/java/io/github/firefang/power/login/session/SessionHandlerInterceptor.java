package io.github.firefang.power.login.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.BaseAuthInterceptor;
import io.github.firefang.power.login.IAuthService;

/**
 * Session认证拦截器
 * 
 * @author xinufo
 *
 */
public class SessionHandlerInterceptor extends BaseAuthInterceptor {
    private String key;
    private IAuthService authSrv;

    public SessionHandlerInterceptor(String key, IAuthService authSrv) {
        this.key = key;
        this.authSrv = authSrv;
    }

    @Override
    protected void intercept(HttpServletRequest request) throws UnAuthorizedException {
        HttpSession session = request.getSession();
        authSrv.auth(session.getAttribute(key));
    }

}
