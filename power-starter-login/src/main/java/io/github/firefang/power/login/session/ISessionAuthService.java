package io.github.firefang.power.login.session;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.IAuthService;

/**
 * Session认证服务
 * 
 * @author xinufo
 *
 */
public interface ISessionAuthService extends IAuthService {

    /**
     * 验证当前Session是否已经登录
     * 
     * @param key Session中存放认证内容的key
     * @throws UnAuthorizedException 未登录抛出
     */
    void auth(String key) throws UnAuthorizedException;

    /**
     * Session登录（若要获取当前Session，请使用
     * {@link io.github.firefang.power.web.util.CurrentRequestUtil
     * CurrentRequestUtil}）
     * 
     * @param username 用户名
     * @param password 密码
     * @throws UnAuthorizedException 认证不通过抛出
     */
    void login(String username, String password) throws UnAuthorizedException;

    /**
     * Session注销
     * 
     * @param userInfo 当前用户信息
     */
    void logout(Object userInfo);

}
