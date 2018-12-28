package io.github.firefang.power.page.mybatis;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.firefang.power.page.Pagination;
import io.github.firefang.power.page.test.IPowerPageTestMapper;
import io.github.firefang.power.page.test.PowerPageTestConfiguration;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerPageTestConfiguration.class)
public class PageInterceptorTest {
    @Autowired
    private IPowerPageTestMapper m;

    @Test
    public void noNeedPaginate_Success() {
        List<String> rs = m.findAll();
        assertEquals(5, rs.size());
        assertEquals("1", rs.get(0));
        assertEquals("2", rs.get(1));
        assertEquals("3", rs.get(2));
        assertEquals("4", rs.get(3));
        assertEquals("5", rs.get(4));
    }

    @Test
    public void noRowLimit_Success() {
        Pagination p = Pagination.of(3, RowBounds.NO_ROW_LIMIT, Sort.unsorted());
        List<String> rs = m.find(null, p);
        assertEquals(2, rs.size());
        assertEquals("4", rs.get(0));
        assertEquals("5", rs.get(1));
    }

    @Test
    public void needPaginate_Success() {
        Pagination p = Pagination.of(3, 1, Sort.unsorted());
        List<String> rs = m.find(null, p);
        assertEquals(1, rs.size());
        assertEquals("4", rs.get(0));
    }

    @Test
    public void needPaginateSort_Success() {
        Pagination p = Pagination.of(3, 1, Sort.by(Direction.DESC, "id"));
        List<String> rs = m.find(null, p);
        assertEquals(1, rs.size());
        assertEquals("2", rs.get(0));
    }

}
