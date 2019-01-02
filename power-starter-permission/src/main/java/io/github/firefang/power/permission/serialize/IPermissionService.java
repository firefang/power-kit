package io.github.firefang.power.permission.serialize;

import java.util.List;

/**
 * Service to write entity into db
 * 
 * @author xinufo
 *
 */
public interface IPermissionService {

    /**
     * 批量更新（若已存在同名权限则进行更新，否则进行插入，若已存在的权限名称不在此列表中则删除）
     * 
     * @param entities 权限实体类列表
     */
    void upsertBatch(List<PermissionDO> entities);

}
