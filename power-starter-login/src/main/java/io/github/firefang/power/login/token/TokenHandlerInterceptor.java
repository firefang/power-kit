package io.github.firefang.power.login.token;

import javax.servlet.http.HttpServletRequest;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.BaseAuthInterceptor;

/**
 * Token认证拦截器
 * 
 * @author xinufo
 *
 */
public class TokenHandlerInterceptor extends BaseAuthInterceptor {
    private String key;
    private ITokenAuthService tokenSrv;

    public TokenHandlerInterceptor(String key, ITokenAuthService tokenSrv) {
        this.key = key;
        this.tokenSrv = tokenSrv;
    }

    @Override
    protected void intercept(HttpServletRequest request) throws UnAuthorizedException {
        String token = request.getHeader(key);
        tokenSrv.auth(token);
    }

}
