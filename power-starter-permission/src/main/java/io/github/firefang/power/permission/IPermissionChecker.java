package io.github.firefang.power.permission;

import javax.servlet.http.HttpServletRequest;

/**
 * Checker for permission
 * 
 * @author xinufo
 *
 */
public interface IPermissionChecker {

    /**
     * 从请求中获取用户信息
     * 
     * @param request
     * @return
     */
    UserInfo getUserInfoFromRequest(HttpServletRequest request);

    /**
     * 检查是否有权操作
     * 
     * @param permission 权限
     * @param userId 用户ID
     * @param roleId 角色ID
     * @param entityId 正在访问的实体类ID
     * @return
     */
    public boolean canAccess(String permission, Object userId, Object roleId, Object entityId);

}
