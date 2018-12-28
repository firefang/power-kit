package io.github.firefang.power.login.token;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.IAuthService;

/**
 * Login service for token
 * 
 * @author xinufo
 *
 */
public interface ITokenAuthService extends IAuthService {

    /**
     * 验证当前token是否合法
     * 
     * @param token
     * @throws UnAuthorizedException 未登录抛出
     */
    void auth(String token) throws UnAuthorizedException;

    /**
     * Token登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return token
     * @throws UnAuthorizedException 认证不通过抛出
     */
    String login(String username, String password) throws UnAuthorizedException;

    /**
     * Token注销
     * 
     * @param token
     */
    void logout(String token);

}
