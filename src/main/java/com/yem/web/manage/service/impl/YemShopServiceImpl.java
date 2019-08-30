package com.yem.web.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yem.dto.YemShopDTO;
import com.yem.entity.YemShop;
import com.yem.enums.McCodeTypeEnum;
import com.yem.web.manage.dao.YemShopMapper;

@Service
public class YemShopServiceImpl {

	@Autowired
	private YemShopMapper yemShopMapper;
	
	@Autowired
	private YemBaseServiceImpl baseServiceImpl;
	
	public YemShop getShopByCode(Long shopCode) {
		return yemShopMapper.selectByShopCode(shopCode);
	}
	
	public List<YemShop> getShopList(YemShopDTO yemShopDTO) {
		return yemShopMapper.selectShopList(yemShopDTO);
	}
	
	public int getShopListCount(YemShopDTO yemShopDTO) {
		return yemShopMapper.selectShopListCount(yemShopDTO);
	} 
	
	public boolean addYemShop(YemShop shop) {
		
    	shop.setShopCode(baseServiceImpl.getMcCode(McCodeTypeEnum.SHOP.toString()));
    	
		return yemShopMapper.insertSelective(shop) > 0 ? true : false;
	}
	
	public boolean modifyYemShop(YemShop yemShop) {
		return yemShopMapper.updateByShopCodeSelective(yemShop) > 0 ? true : false;
	}
	
}
