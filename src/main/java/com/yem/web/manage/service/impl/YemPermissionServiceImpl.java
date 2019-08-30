package com.yem.web.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yem.common.BaseService;
import com.yem.dto.YemPermissionDTO;
import com.yem.entity.YemPermission;
import com.yem.enums.McCodeTypeEnum;
import com.yem.web.manage.dao.YemPermissionMapper;

@Service
@Transactional
public class YemPermissionServiceImpl extends BaseService{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = -7090792830264932289L;
	
	@Autowired
	private YemPermissionMapper yemPermissionMapper;

	@Autowired
	private YemBaseServiceImpl baseServiceImpl;
	
	public YemPermission getPermissionByCode(Long permissionCode) {
		return yemPermissionMapper.selectByPermissionCode(permissionCode);
	}

	public List<YemPermission> getPermissionByRoleCode(Long roleCode) {
		return yemPermissionMapper.selectPermissionByRoleCode(roleCode);
	}
	
	public List<YemPermission> getPermissionList(YemPermissionDTO yemPermissionDTO) {
		return yemPermissionMapper.selectPermissionList(yemPermissionDTO);
	}
	
	public int getPermissionListCount(YemPermissionDTO YemPermissionDTO) {
		return yemPermissionMapper.selectPermissionListCount(YemPermissionDTO);
	} 
	
	public boolean addYemPermission(YemPermission permission) {
		
		permission.setPermissionCode(baseServiceImpl.getMcCode(McCodeTypeEnum.PERMISSION.toString()));
		
		return yemPermissionMapper.insertSelective(permission) > 0 ? true : false;
	}
	
	public boolean modifyYemPermission(YemPermission yemPermission) {
		return yemPermissionMapper.updateByPermissionCodeSelective(yemPermission) > 0 ? true : false;
	}
	
}
