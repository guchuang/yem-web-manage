package com.yem.web.manage.dao;

import java.util.List;

import com.yem.entity.YemRolePermission;

public interface YemRolePermissionMapper {
    int deleteByRoleCode(Long roleCode);

    int insertBatch(List<YemRolePermission> record);

    int insertSelective(YemRolePermission record);

    YemRolePermission selectByPrimaryKey(String rolePermissionId);

    int updateByPrimaryKeySelective(YemRolePermission record);

    int updateByPrimaryKey(YemRolePermission record);
}