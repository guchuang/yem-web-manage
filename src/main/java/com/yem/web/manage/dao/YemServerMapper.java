package com.yem.web.manage.dao;

import java.util.List;

import com.yem.dto.YemServerDTO;
import com.yem.entity.YemServer;

public interface YemServerMapper {

    int insertSelective(YemServer record);

    YemServer selectByServerCode(Long serverCode);
    
    List<YemServer> selectServerList(YemServerDTO dto);
    
    int selectServerListCount(YemServerDTO dto);

    int updateByServerCodeSelective(YemServer record);

}