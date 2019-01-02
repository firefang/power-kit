package io.github.firefang.power.permission.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记方法参数，用于获取实体类ID
 * 
 * @author xinufo
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityId {

    /**
     * @return 获取实体类ID的Spring EL表达式，若不写则直接将被标记的对象当作ID
     */
    String value() default "";

}
