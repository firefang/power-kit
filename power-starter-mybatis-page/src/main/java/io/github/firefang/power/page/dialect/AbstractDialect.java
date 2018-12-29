package io.github.firefang.power.page.dialect;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

import io.github.firefang.power.page.mybatis.util.MetaObjectUtil;

public abstract class AbstractDialect implements IDialect {

    @SuppressWarnings("unchecked")
    @Override
    public Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql,
            RowBounds rowBounds, CacheKey pageKey) {
        Map<String, Object> paramMap = null;
        if (parameterObject == null) {
            paramMap = new HashMap<String, Object>();
        } else if (parameterObject instanceof Map) {
            // 解决不可变Map的情况
            paramMap = new HashMap<String, Object>();
            paramMap.putAll((Map<String, Object>) parameterObject);
        } else {
            paramMap = new HashMap<String, Object>();
            // 动态sql时的判断条件不会出现在ParameterMapping中，但是必须有，所以这里需要收集所有的getter属性
            // TypeHandlerRegistry可以直接处理的会作为一个直接使用的对象进行处理
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry()
                    .hasTypeHandler(parameterObject.getClass());
            MetaObject metaObject = MetaObjectUtil.forObject(parameterObject);
            // 需要针对注解形式的MyProviderSqlSource保存原值
            if (!hasTypeHandler) {
                for (String name : metaObject.getGetterNames()) {
                    paramMap.put(name, metaObject.getValue(name));
                }
            }
            // 下面这段方法，主要解决一个常见类型的参数时的问题
            if (boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0) {
                for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                    String name = parameterMapping.getProperty();
                    if (!name.equals(PAGEPARAMETER_FIRST) && !name.equals(PAGEPARAMETER_SECOND)
                            && paramMap.get(name) == null) {
                        if (hasTypeHandler || parameterMapping.getJavaType().equals(parameterObject.getClass())) {
                            paramMap.put(name, parameterObject);
                            break;
                        }
                    }
                }
            }
        }
        return processPageParameter(ms, paramMap, boundSql, rowBounds, pageKey);
    }

    /*
     * 处理分页参数
     */
    public abstract Object processPageParameter(MappedStatement ms, Map<String, Object> paramMap, BoundSql boundSql,
            RowBounds rowBounds, CacheKey pageKey);

}
