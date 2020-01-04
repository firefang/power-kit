package io.github.firefang.power.permission.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限分组
 * 
 * @author xinufo
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionGroup {

    /**
     * @return 分组名
     */
    String value();

    /**
     * 额外参数，需使用SpringEL返回Map类型，key为参数名，value为参数值
     * 
     * @return
     */
    String extraParams() default "";
}
