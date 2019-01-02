package io.github.firefang.power.page.dialect.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.ibatis.cache.CacheKey;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.page.dialect.impl.MySqlDialect;

/**
 * @author xinufo
 *
 */
public class MySqlDialectTest {

    @Test
    public void getPageSql_ZeroOffset_Success() {
        MySqlDialect d = new MySqlDialect();
        String sql = d.getPageSql("select * from t;", Pagination.of(0, 10, Sort.unsorted()), mock(CacheKey.class));
        assertEquals("select * from t LIMIT ? ", sql);
    }

    @Test
    public void getPageSql_Success() {
        MySqlDialect d = new MySqlDialect();
        String sql = d.getPageSql("select * from t;", Pagination.of(10, 10, Sort.unsorted()), mock(CacheKey.class));
        assertEquals("select * from t LIMIT ?, ? ", sql);
    }

}
