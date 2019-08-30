package com.yem.web.manage.dao;

import java.util.List;

import com.yem.entity.YemUserRole;

public interface YemUserRoleMapper {
	
    int deleteByUserCode(Long userCode);

    int insertSelective(YemUserRole record);

    int insertBatch(List<YemUserRole> records);

    YemUserRole selectByPrimaryKey(String userRoleId);

    int updateByPrimaryKeySelective(YemUserRole record);

    int updateByPrimaryKey(YemUserRole record);
}