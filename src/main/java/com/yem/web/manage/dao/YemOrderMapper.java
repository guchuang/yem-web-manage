package com.yem.web.manage.dao;

import com.yem.entity.YemOrder;

public interface YemOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(YemOrder record);

    int insertSelective(YemOrder record);

    YemOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(YemOrder record);

    int updateByPrimaryKey(YemOrder record);
}