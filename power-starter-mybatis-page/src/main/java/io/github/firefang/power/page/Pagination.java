package io.github.firefang.power.page;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Sort;

/**
 * 分页条件，扩展RowBounds加入Sort，用于在Mybatis拦截器中实现自动分页
 * 
 * @author xinufo
 *
 */
public class Pagination extends RowBounds {
    private Sort sort;

    public static Pagination of(int offset, int limit, Sort sort) {
        return new Pagination(offset, limit, sort);
    }

    @Deprecated
    public Pagination(int offset, int limit, Sort sort) {
        super(offset, limit);
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

}
