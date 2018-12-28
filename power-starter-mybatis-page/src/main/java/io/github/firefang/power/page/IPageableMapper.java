package io.github.firefang.power.page;

import java.io.Serializable;
import java.util.List;

import io.github.firefang.power.mapper.IBaseMapper;

/**
 * 分页 Mapper
 * 
 * @author xinufo
 *
 */
public interface IPageableMapper<T, PK extends Serializable> extends IBaseMapper<T, PK> {

    /**
     * 根据条件分页查询
     * 
     * @param condition
     * @param page
     * @return
     */
    List<T> find(T condition, Pagination page);

}
