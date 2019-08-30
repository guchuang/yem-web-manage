package com.yem.web.manage.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yem.dto.YemRoleDTO;
import com.yem.entity.YemPermission;
import com.yem.entity.YemRole;
import com.yem.entity.YemRolePermission;
import com.yem.enums.McCodeTypeEnum;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.web.manage.dao.YemPermissionMapper;
import com.yem.web.manage.dao.YemRoleMapper;
import com.yem.web.manage.dao.YemRolePermissionMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class YemRoleServiceImpl {

	@Autowired
	private YemRoleMapper yemRoleMapper;

	@Autowired
	private YemRolePermissionMapper yemRolePermissionMapper;

	@Autowired
	private YemPermissionMapper yemPermissionMapper;

	@Autowired
	private YemBaseServiceImpl baseServiceImpl;
	
	public YemRole getRoleByCode(Long roleCode) {
		return yemRoleMapper.selectByRoleCode(roleCode);
	}
	
	public List<YemRole> getRoleList(YemRoleDTO yemRoleDTO) {
		return yemRoleMapper.selectRoleList(yemRoleDTO);
	}
	
	public List<YemRole> getRoleListByUserCode(Long userCode) {
		return yemRoleMapper.selectRoleListByUserCode(userCode);
	}
	
	public int getRoleListCount(YemRoleDTO yemRoleDTO) {
		return yemRoleMapper.selectRoleListCount(yemRoleDTO);
	} 
	
	public boolean addYemRole(YemRole role, List<Long> permissions) {
		
		role.setRoleCode(baseServiceImpl.getMcCode(McCodeTypeEnum.ROLE.toString()));
		
		if (permissions != null && !permissions.isEmpty()) {
			List<YemRolePermission> rolePermissions = initPermissions(role, permissions);
			
			yemRolePermissionMapper.insertBatch(rolePermissions);
		}
		
		int rpAResult = yemRoleMapper.insertSelective(role);
		
		return rpAResult > 0  ? true : false;
	}
	
	public boolean modifyYemRole(YemRole yemRole) {
		return yemRoleMapper.updateByRoleCodeSelective(yemRole) > 0 ? true : false;
	}
	
	public boolean modifyYemRoleAndPermission(YemRole yemRole, List<Long> permissions) {
		if (permissions != null && !permissions.isEmpty()) {
			List<YemRolePermission> rolePermissions = initPermissions(yemRole, permissions);
			
			yemRolePermissionMapper.deleteByRoleCode(yemRole.getRoleCode());
			yemRolePermissionMapper.insertBatch(rolePermissions);
		}
		int rUResult = yemRoleMapper.updateByRoleCodeSelective(yemRole);
		
		return rUResult > 0 ? true : false;
	}
	
	public boolean disableYemRole(YemRole yemRole) {
		return yemRoleMapper.updateByRoleCodeSelective(yemRole) > 0 ? true : false;
	}
	
	/**
	 * 根据权限编码校验并初始化集合
	 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
	 * @param permissions
	 * @since JDK 1.8
	 */
	private List<YemRolePermission> initPermissions(YemRole role, List<Long> permissions) {

		List<YemRolePermission> rolePermissions = new ArrayList<YemRolePermission>();
		
		for (Long permissionCode : permissions) {
			YemPermission permission = yemPermissionMapper.selectByPermissionCode(permissionCode);
			if (permission == null) {
				log.info("权限编码：【{}】不存在！", permissionCode);
			}
			
			YemRolePermission rolePermission = new YemRolePermission();
			rolePermission.setRolePermissionId(StringUtil.getUUIDWithOutRod());
			rolePermission.setRoleCode(role.getRoleCode());
			rolePermission.setPermissionCode(permissionCode);
			rolePermission.setCreateTime(DateUtil.getDate());
			rolePermission.setCreateBy(role.getCreateBy());
			
			rolePermissions.add(rolePermission);
		}
		
		return rolePermissions;
	}
}
