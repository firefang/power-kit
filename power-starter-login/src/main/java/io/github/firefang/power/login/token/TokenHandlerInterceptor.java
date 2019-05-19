package io.github.firefang.power.login.token;

import javax.servlet.http.HttpServletRequest;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.BaseAuthInterceptor;
import io.github.firefang.power.login.IAuthService;

/**
 * Token认证拦截器
 * 
 * @author xinufo
 *
 */
public class TokenHandlerInterceptor extends BaseAuthInterceptor {
    private String key;
    private IAuthService authSrv;

    public TokenHandlerInterceptor(String key, IAuthService authSrv) {
        this.key = key;
        this.authSrv = authSrv;
    }

    @Override
    protected void intercept(HttpServletRequest request) throws UnAuthorizedException {
        String token = request.getHeader(key);
        authSrv.auth(token);
    }

}
