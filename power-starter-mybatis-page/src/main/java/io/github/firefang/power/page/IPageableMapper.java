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
     * @param condition 查询条件
     * @param page 分页条件
     * @return 查询到的实体类列表
     */
    List<T> find(T condition, Pagination page);

}
