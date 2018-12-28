package io.github.firefang.power.page.mybatis;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.page.dialect.DialectUtil;
import io.github.firefang.power.page.dialect.IDialect;
import io.github.firefang.power.page.parser.OrderByParser;

/**
 * Interceptor of Mybatis
 * 
 * @author xinufo
 *
 */
@Intercepts({
    // @formatter:off
    @Signature(type = Executor.class, method = "query",
        args = {
            MappedStatement.class,
            Object.class,
            RowBounds.class,
            ResultHandler.class
        }),
    @Signature(type = Executor.class, method = "query",
        args = {
            MappedStatement.class,
            Object.class,
            RowBounds.class,
            ResultHandler.class,
            CacheKey.class,
            BoundSql.class
        })
    // @formatter:on
})
public class PageInterceptor implements Interceptor {
    private final Field additionalParametersField;

    public PageInterceptor() {
        try {
            // 反射获取 BoundSql 中的 additionalParameters 属性
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
        Executor executor = (Executor) invocation.getTarget();

        if (!needPageable(rowBounds)) {
            // 无需分页直接返回
            return invocation.proceed();
        }

        CacheKey cacheKey;
        BoundSql boundSql;

        // 由于逻辑关系，只会进入一次
        if (args.length == 4) {
            // 4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            // 6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        Configuration configuration = ms.getConfiguration();
        IDialect dialect = DialectUtil.getDialect(configuration.getEnvironment().getDataSource());
        if (dialect == null) {
            throw new RuntimeException("没有找到数据库方言!");
        }

        String sql = boundSql.getSql();
        if (rowBounds instanceof Pagination) {
            // 支持排序
            Pagination pagination = (Pagination) rowBounds;
            String orderBy = getOrderBySql(pagination);
            if (orderBy != null) {
                // 添加 OrderBy 语句
                cacheKey.update(orderBy);
                sql = OrderByParser.converToOrderBySql(sql, orderBy);
            }
        }

        // 生成分页SQL
        parameter = dialect.processParameterObject(ms, parameter, boundSql, rowBounds, cacheKey);
        sql = dialect.getPageSql(sql, rowBounds, cacheKey);
        BoundSql pageBoundSql = new BoundSql(configuration, sql, boundSql.getParameterMappings(), parameter);
        @SuppressWarnings("unchecked")
        Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
        // 设置动态参数
        for (String key : additionalParameters.keySet()) {
            pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
        }

        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 判断是否需要分页
     * 
     * @param rowBounds
     * @return
     */
    private boolean needPageable(RowBounds rowBounds) {
        if (rowBounds == null) {
            return false;
        }
        return rowBounds.getLimit() > 0 && rowBounds.getLimit() != RowBounds.NO_ROW_LIMIT && rowBounds.getOffset() >= 0;
    }

    private String getOrderBySql(Pagination pagination) {
        Sort sort = pagination.getSort();
        if (sort == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String property;
        Direction direction;
        int index = 0;

        for (Order o : sort) {
            property = o.getProperty();
            if (property != null && !property.trim().isEmpty()) {
                if (index > 0) {
                    sb.append(',');
                }
                sb.append(property);
                ++index;
                direction = o.getDirection();
                if (direction != null) {
                    sb.append(' ').append(direction.name());
                }
            }
        }
        return sb.length() > 0 ? sb.toString() : null;
    }

}
