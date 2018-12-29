package io.github.firefang.power.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * 基础Mapper接口，包含了常用的增删改查方法
 * 
 * @author xinufo
 *
 */
public interface IBaseMapper<T, PK extends Serializable> {

    /**
     * 插入
     * 
     * @param entity 实体类
     * @return 影响行数
     */
    Integer add(T entity);

    /**
     * 根据ID删除
     * 
     * @param id 实体ID
     * @return 影响行数
     */
    Integer deleteById(PK id);

    /**
     * 根据ID修改
     * 
     * @param entity 实体ID
     * @return 影响行数
     */
    Integer updateById(T entity);

    /**
     * 根据ID查询
     * 
     * @param id 实体ID
     * @return 查询到的实体类
     */
    T findOneById(PK id);

    /**
     * 查询全部
     * 
     * @return 查询到的实体类列表
     */
    List<T> findAll();

    /**
     * 根据条件计数
     * 
     * @param condition 查询条件
     * @return 查询到的条数
     */
    Long count(T condition);

    /**
     * 根据条件查询
     * 
     * @param condition 查询条件
     * @return 查询到的实体类列表
     */
    List<T> find(T condition);

}
