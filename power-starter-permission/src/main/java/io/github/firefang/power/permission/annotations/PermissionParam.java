package io.github.firefang.power.permission.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于权限校验的参数
 * 
 * @author xinufo
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionParam {

    /**
     * 参数名
     * 
     * @return
     */
    String name();

    /**
     * 用于计算参数值的SpringEL表达式，使用"#arg"引用被注解的参数，若为空则直接使用参数
     * 
     * @return
     */
    String exp() default "";

}
