package io.github.firefang.power.permission.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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
import io.github.firefang.power.permission.annotations.EntityId;
import io.github.firefang.power.permission.annotations.Permission;
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
            boolean access = false;
            Object entityId;
            entityId = getEntityIdFromExpression(pjp);
            try {
                access = checker.canAccess(permission, info.getUserId(), info.getRoleId(), entityId);
            } catch (Exception e) {
                LOG.error("Check permission failed", e);
                throw new NoPermissionException();
            }
            if (access) {
                return pjp.proceed();
            }
        }
        throw new NoPermissionException();
    }

    private Object getEntityIdFromExpression(ProceedingJoinPoint pjp) {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        Object[] args = pjp.getArgs();
        Parameter[] ps = method.getParameters();
        EntityId anno = null;
        Object entity = null;
        for (int i = 0; i < ps.length; ++i) {
            if ((anno = ps[i].getAnnotation(EntityId.class)) != null) {
                entity = args[i];
                String exp = anno.value();
                String name = ps[i].getName();
                if (exp.length() == 0) {
                    // 未写表达式，直接将被标记的对象当作实体类ID
                    return entity;
                }
                return evaluateExpression(exp, name, entity);
            }
        }
        return null;
    }

    private Object evaluateExpression(String expStr, String name, Object entity) {
        StandardEvaluationContext cxt = new StandardEvaluationContext();
        cxt.setVariable(name, entity);
        Expression exp = PARSER.parseExpression(expStr);
        return exp.getValue(cxt);
    }

}
