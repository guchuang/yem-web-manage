package com.yem.web.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yem.dto.YemSubscribeDTO;
import com.yem.entity.YemSubscribe;
import com.yem.enums.McCodeTypeEnum;
import com.yem.web.manage.dao.YemSubscribeMapper;

@Service
public class YemSubscribeServiceImpl {

	@Autowired
	private YemSubscribeMapper yemSubscribeMapper;

	@Autowired
	private YemBaseServiceImpl baseServiceImpl;
	
	public YemSubscribe getSubscribeByCode(Long SubscribeCode) {
		return yemSubscribeMapper.selectBySubscribeCode(SubscribeCode);
	}
	
	public List<YemSubscribe> getSubscribeList(YemSubscribeDTO yemSubscribeDTO) {
		return yemSubscribeMapper.selectSubscribeList(yemSubscribeDTO);
	}
	
	public int getSubscribeListCount(YemSubscribeDTO yemSubscribeDTO) {
		return yemSubscribeMapper.selectSubscribeListCount(yemSubscribeDTO);
	} 
	
	public boolean addYemSubscribe(YemSubscribe Subscribe) {
		
		Subscribe.setSubscribeCode(baseServiceImpl.getMcCode(McCodeTypeEnum.SUBSCRIBE.toString()));
		
		return yemSubscribeMapper.insertSelective(Subscribe) > 0 ? true : false;
	}
	
	public boolean modifyYemSubscribe(YemSubscribe yemSubscribe) {
		return yemSubscribeMapper.updateBySubscribeCodeSelective(yemSubscribe) > 0 ? true : false;
	}
	
}
