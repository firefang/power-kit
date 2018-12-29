package io.github.firefang.power.page;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import io.github.firefang.power.exception.BadRequestException;

/**
 * 分页 Service
 * 
 * @author xinufo
 *
 * @param <C> 条件类型
 * @param <R> 查询结果类型
 */
public interface IPageableService<C, R> {

    /**
     * 分页查询
     * 
     * @param condition 查询条件
     * @param page 页码
     * @param size 每页条数
     * @param sort 排序信息
     * @return 分页结果
     */
    default Page<R> findByPage(C condition, int page, int size, Sort sort) {
        if (page <= 0) {
            throw new BadRequestException("page 必须大于0");
        }
        if (size <= 0) {
            throw new BadRequestException("size 必须大于0");
        }

        List<R> list;
        Long totalCount = count(condition);
        int offset = (page - 1) * size;
        Pagination pagination = Pagination.of(offset, size, sort);

        if (totalCount == null || totalCount <= 0 || offset >= totalCount) {
            list = Collections.emptyList();
        } else {
            list = find(condition, pagination);
        }
        return new PowerPageImpl<>(list, pagination, totalCount);
    }

    /**
     * 根据条件统计数量
     * 
     * @param condition 查询条件
     * @return 查询到的条数
     */
    Long count(C condition);

    /**
     * 根据条件分页查询
     * 
     * @param condition 查询条件
     * @param pagination 分页条件
     * @return 查询到的实体类列表
     */
    List<R> find(C condition, Pagination pagination);

}
