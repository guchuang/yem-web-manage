package com.yem.web.manage.dao;

import java.util.List;

import com.yem.dto.YemSubscribeDTO;
import com.yem.entity.YemSubscribe;

public interface YemSubscribeMapper {
	
    int insertSelective(YemSubscribe record);

    YemSubscribe selectBySubscribeCode(Long subscribeCode);

    List<YemSubscribe> selectSubscribeList(YemSubscribeDTO dto);

    int selectSubscribeListCount(YemSubscribeDTO dto);

    int updateBySubscribeCodeSelective(YemSubscribe record);

}