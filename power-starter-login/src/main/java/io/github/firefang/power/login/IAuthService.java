package io.github.firefang.power.login;

import javax.servlet.http.HttpServletRequest;

import io.github.firefang.power.exception.UnAuthorizedException;

/**
 * 标记性接口，用于识别认证服务
 * 
 * @author xinufo
 *
 */
public interface IAuthService {

    /**
     * 登录
     * 
     * @param username
     * @param password
     * @param request
     * @return
     */
    Object login(String username, String password, HttpServletRequest request);

    /**
     * 注销
     * 
     * @param request
     */
    void logout(HttpServletRequest request);

    /**
     * 认证当前请求
     * 
     * @param info
     * @throws UnAuthorizedException
     */
    void auth(Object info) throws UnAuthorizedException;

}
