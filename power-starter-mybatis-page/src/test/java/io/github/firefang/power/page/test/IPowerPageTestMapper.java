package io.github.firefang.power.page.test;

import org.apache.ibatis.annotations.Mapper;

import io.github.firefang.power.page.IPageableMapper;

/**
 * @author xinufo
 *
 */
@Mapper
public interface IPowerPageTestMapper extends IPageableMapper<String, Integer> {

}
