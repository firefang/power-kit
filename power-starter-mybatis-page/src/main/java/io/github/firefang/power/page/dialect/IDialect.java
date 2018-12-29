package io.github.firefang.power.page.dialect;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据库方言，针对不同数据库进行实现
 * 
 * @author xinufo
 *
 */
public interface IDialect {
    // 分页的id后缀
    String SUFFIX_PAGE = "_PAGE";
    // 第一个分页参数
    String PAGEPARAMETER_FIRST = "FIRST" + SUFFIX_PAGE;
    // 第二个分页参数
    String PAGEPARAMETER_SECOND = "SECOND" + SUFFIX_PAGE;

    /**
     * 处理查询参数对象
     *
     * @param ms MappedStatement
     * @param parameterObject 参数
     * @param rowDounds 分页信息
     * @param boundSql sql
     * @param pageKey key
     * @return 处理后的参数
     */
    Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, RowBounds rowBounds,
            CacheKey pageKey);

    /**
     * 生成分页查询 sql
     * 
     * @param sql 原始SQL
     * @param rowBounds 分页
     * @param pageKey key
     * @return 带分页的SQL
     */
    String getPageSql(String sql, RowBounds rowBounds, CacheKey pageKey);

}
