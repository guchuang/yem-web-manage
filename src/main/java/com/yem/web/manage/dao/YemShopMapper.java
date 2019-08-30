package com.yem.web.manage.dao;

import java.util.List;

import com.yem.dto.YemShopDTO;
import com.yem.entity.YemShop;

public interface YemShopMapper {

    int insertSelective(YemShop record);

    YemShop selectByShopCode(Long shopCode);

    List<YemShop> selectShopList(YemShopDTO dto);

    int selectShopListCount(YemShopDTO dto);

    int updateByShopCodeSelective(YemShop record);

}