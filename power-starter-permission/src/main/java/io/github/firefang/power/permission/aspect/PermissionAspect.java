package io.github.firefang.power.permission.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import io.github.firefang.power.exception.NoPermissionException;
import io.github.firefang.power.permission.IPermissionChecker;
import io.github.firefang.power.permission.UserInfo;
import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionParam;
import io.github.firefang.power.web.util.CurrentRequestUtil;

/**
 * Aspect to check permission
 * 
 * @author xinufo
 *
 */
@Aspect
public class PermissionAspect {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionAspect.class);
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    private IPermissionChecker checker;

    public PermissionAspect(IPermissionChecker checker) {
        this.checker = checker;
    }

    /*
     * (Controller || RestController) && PermissionGroup
     */
    @Pointcut("(@within(org.springframework.stereotype.Controller) || "
            + "@within(org.springframework.web.bind.annotation.RestController))"
            + " && @within(io.github.firefang.power.permission.annotations.PermissionGroup)")
    public void controllerPointcut() {
    }

    @Around("io.github.firefang.power.permission.aspect.PermissionAspect.controllerPointcut() && @annotation(anno)")
    public Object checkPermission(ProceedingJoinPoint pjp, Permission anno) throws Throwable {
        HttpServletRequest request = CurrentRequestUtil.getCurrentRequest();
        UserInfo info = checker.getUserInfoFromRequest(request);
        if (info != null) {
            String permission = anno.value();
            Map<String, Object> params = collectParams(pjp);
            Map<String, Object> extra = collectExtraParams(anno.extraParams());
            if (anno.verticalCheck()) {
                verticalCheck(permission, info, params, extra);
            }
            if (anno.horizontalCheck()) {
                horizontalCheck(permission, info, params, extra);
            }
            return pjp.proceed();
        }
        throw new NoPermissionException();
    }

    private Map<String, Object> collectParams(ProceedingJoinPoint pjp) {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        Object[] values = pjp.getArgs();
        Parameter[] ps = method.getParameters();
        PermissionParam anno = null;
        Map<String, Object> ret = new HashMap<>(8);
        for (int i = 0; i < ps.length; ++i) {
            if ((anno = ps[i].getAnnotation(PermissionParam.class)) != null) {
                String name = anno.name();
                String exp = anno.exp();
                Object value;
                if (exp.length() == 0) {
                    value = values[i];
                } else {
                    value = evaluateExpression(exp, values[i], Object.class);
                }
                ret.put(name, value);
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> collectExtraParams(String extraExp) {
        if (extraExp.length() == 0) {
            return Collections.emptyMap();
        }
        return evaluateExpression(extraExp, null, Map.class);
    }

    private <T> T evaluateExpression(String expStr, Object paramVal, Class<T> clazz) {
        StandardEvaluationContext cxt = new StandardEvaluationContext();
        if (paramVal != null) {
            // 使用#arg来引用参数
            cxt.setVariable("arg", paramVal);
        }
        Expression exp = PARSER.parseExpression(expStr);
        return exp.getValue(cxt, clazz);
    }

    private void verticalCheck(String permission, UserInfo info, Map<String, Object> params,
            Map<String, Object> extra) {
        boolean hasPermission = false;
        try {
            hasPermission = checker.verticalCheck(permission, info, params, extra);
        } catch (Exception e) {
            LOG.error("Check permission failed", e);
            throw new NoPermissionException("检查权限失败");
        }
        if (!hasPermission) {
            throw new NoPermissionException();
        }
    }

    private void horizontalCheck(String permission, UserInfo info, Map<String, Object> params,
            Map<String, Object> extra) {
        boolean hasPermission = false;
        try {
            hasPermission = checker.horizontalCheck(permission, info, params, extra);
        } catch (Exception e) {
            LOG.error("Check permission failed", e);
            throw new NoPermissionException("检查权限失败");
        }
        if (!hasPermission) {
            throw new NoPermissionException();
        }
    }

}
