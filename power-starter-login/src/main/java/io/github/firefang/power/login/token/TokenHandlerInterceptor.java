package io.github.firefang.power.login.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Token认证拦截器
 * 
 * @author xinufo
 *
 */
public class TokenHandlerInterceptor extends HandlerInterceptorAdapter {
    private String key;
    private ITokenAuthService tokenSrv;

    public TokenHandlerInterceptor(String key, ITokenAuthService tokenSrv) {
        this.key = key;
        this.tokenSrv = tokenSrv;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader(key);
        tokenSrv.auth(token);
        return true;
    }

}
