package io.github.firefang.power.permission;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionParam;

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
     * @param request 请求信息
     * @return 用户信息
     */
    UserInfo getUserInfoFromRequest(HttpServletRequest request);

    /**
     * 横向权限校验，需设置{@link Permission#horizontalCheck()}为true
     * 
     * @param permission 待访问权限的名称
     * @param info 用户信息
     * @param params 权限所在的方法上{@link PermissionParam}注解解析出来的参数
     * @param extra {@link Permission}上配置的额外参数
     * @return
     */
    boolean horizontalCheck(String permission, UserInfo info, Map<String, Object> params, Map<String, Object> extra);

    /**
     * 纵向权限校验，需设置{@link Permission#verticalCheck()}为true
     * 
     * @param permission 待访问权限的名称
     * @param info 用户信息
     * @param params 权限所在的方法上{@link PermissionParam}注解解析出来的参数
     * @param extra {@link Permission}上配置的额外参数
     * @return
     */
    boolean verticalCheck(String permission, UserInfo info, Map<String, Object> params, Map<String, Object> extra);
}
