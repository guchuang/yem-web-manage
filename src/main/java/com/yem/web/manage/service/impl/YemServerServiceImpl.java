package com.yem.web.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yem.dto.YemServerDTO;
import com.yem.entity.YemServer;
import com.yem.enums.McCodeTypeEnum;
import com.yem.web.manage.dao.YemServerMapper;

@Service
public class YemServerServiceImpl {

	@Autowired
	private YemServerMapper yemServerMapper;

	@Autowired
	private YemBaseServiceImpl baseServiceImpl;
	
	public YemServer getServerByCode(Long serverCode) {
		return yemServerMapper.selectByServerCode(serverCode);
	}
	
	public List<YemServer> getServerList(YemServerDTO yemServerDTO) {
		return yemServerMapper.selectServerList(yemServerDTO);
	}
	
	public int getServerListCount(YemServerDTO yemServerDTO) {
		return yemServerMapper.selectServerListCount(yemServerDTO);
	} 
	
	public boolean addYemServer(YemServer server) {
		server.setServerCode(baseServiceImpl.getMcCode(McCodeTypeEnum.SERVER.toString()));
		return yemServerMapper.insertSelective(server) > 0 ? true : false;
	}
	
	public boolean modifyYemServer(YemServer yemServer) {
		return yemServerMapper.updateByServerCodeSelective(yemServer) > 0 ? true : false;
	}
	
}
