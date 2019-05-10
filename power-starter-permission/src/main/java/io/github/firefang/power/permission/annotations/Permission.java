package io.github.firefang.power.permission.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限声明，用于权限校验及权限自动入库
 * 
 * @author xinufo
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * @return 权限名称（不可重复）
     */
    String value();

    /**
     * 是否进行横向权限校验
     * 
     * @return
     */
    boolean horizontalCheck() default true;

    /**
     * 是否进行纵向向权限校验
     * 
     * @return
     */
    boolean verticalCheck() default true;

    /**
     * 额外参数，需使用SpringEL返回Map类型，key为参数名，value为参数值
     * 
     * @return
     */
    String extraParams() default "";
}
