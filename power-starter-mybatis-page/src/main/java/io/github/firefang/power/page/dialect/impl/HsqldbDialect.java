package io.github.firefang.power.page.dialect.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

import io.github.firefang.power.page.dialect.AbstractDialect;
import io.github.firefang.power.page.mybatis.util.MetaObjectUtil;

public class HsqldbDialect extends AbstractDialect {

    @Override
    public Object processPageParameter(MappedStatement ms, Map<String, Object> paramMap, BoundSql boundSql,
            RowBounds rowBounds, CacheKey pageKey) {
        int limit = rowBounds.getLimit();
        int offset = rowBounds.getOffset();
        paramMap.put(PAGEPARAMETER_FIRST, limit);
        paramMap.put(PAGEPARAMETER_SECOND, offset);
        // 处理pageKey
        pageKey.update(limit);
        pageKey.update(offset);
        // 处理参数配置
        if (boundSql.getParameterMappings() != null) {
            List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>();
            if (boundSql != null && boundSql.getParameterMappings() != null) {
                newParameterMappings.addAll(boundSql.getParameterMappings());
            }
            if (limit > 0) {
                newParameterMappings
                        .add(new ParameterMapping.Builder(ms.getConfiguration(), PAGEPARAMETER_FIRST, Integer.class)
                                .build());
            }
            if (offset > 0) {
                newParameterMappings
                        .add(new ParameterMapping.Builder(ms.getConfiguration(), PAGEPARAMETER_SECOND, Integer.class)
                                .build());
            }
            MetaObject metaObject = MetaObjectUtil.forObject(boundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }
        return paramMap;
    }

    @Override
    public String getPageSql(String sql, RowBounds rowBounds, CacheKey pageKey) {
        String tmp = sql.trim();
        if (sql.endsWith(";")) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 20);
        sqlBuilder.append(tmp);
        if (rowBounds.getLimit() > 0) {
            sqlBuilder.append(" LIMIT ? ");
        }
        if (rowBounds.getOffset() > 0) {
            sqlBuilder.append(" OFFSET ? ");
        }
        return sqlBuilder.toString();
    }
}
