package com.yem.web.manage.dao;

import com.yem.entity.YemMember;

public interface YemMemberMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(YemMember record);

    int insertSelective(YemMember record);

    YemMember selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(YemMember record);

    int updateByPrimaryKey(YemMember record);
}