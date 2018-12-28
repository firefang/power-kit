package io.github.firefang.power.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * Base class for mappers
 * 
 * @author xinufo
 *
 */
public interface IBaseMapper<T, PK extends Serializable> {

    /**
     * 插入
     * 
     * @param entity
     * @return
     */
    Integer add(T entity);

    /**
     * 根据ID删除
     * 
     * @param id
     * @return
     */
    Integer deleteById(PK id);

    /**
     * 根据ID修改
     * 
     * @param entity
     * @return
     */
    Integer updateById(T entity);

    /**
     * 根据ID查询
     * 
     * @param id
     * @return
     */
    T findOneById(PK id);

    /**
     * 查询全部
     * 
     * @return
     */
    List<T> findAll();

    /**
     * 根据条件计数
     * 
     * @param condition
     * @return
     */
    Long count(T condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    List<T> find(T condition);

}
