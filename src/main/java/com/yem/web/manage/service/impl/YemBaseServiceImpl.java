package com.yem.web.manage.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yem.entity.YemMcCode;
import com.yem.web.manage.dao.YemMcCodeMapper;

import lombok.Synchronized;

@Service
public class YemBaseServiceImpl {

	@Autowired
	private YemMcCodeMapper yemMcCodeMapper;
	
	@Synchronized
	public Long getMcCode(String mcCodeType) {
		Long code = null;
		YemMcCode mcCode = yemMcCodeMapper.selectByMcCodeType(mcCodeType);
		
		if (mcCode != null) {
			
			code = mcCode.getCurrentValue() + 1;
    	}
		mcCode.setCurrentValue(code);
		if (modifyMcCode(mcCode)) {
			return code;
		};
		return null;
	}
	
	private boolean modifyMcCode(YemMcCode yemMcCode) {
		return yemMcCodeMapper.updateByPrimaryKeySelective(yemMcCode) > 0 ? true : false;
	}
	
}
