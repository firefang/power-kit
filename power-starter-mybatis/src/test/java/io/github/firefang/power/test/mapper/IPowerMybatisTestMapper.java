package io.github.firefang.power.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.github.firefang.power.test.PowerMybatisTestEntity;

/**
 * @author xinufo
 *
 */
@Mapper
public interface IPowerMybatisTestMapper {

    Integer add(PowerMybatisTestEntity entity);

    PowerMybatisTestEntity findById(Integer id);

}
