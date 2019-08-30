package com.yem.web.manage.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yem.dto.YemUserDTO;
import com.yem.entity.YemRole;
import com.yem.entity.YemShop;
import com.yem.entity.YemUser;
import com.yem.entity.YemUserRole;
import com.yem.enums.McCodeTypeEnum;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.vo.YemUserVO;
import com.yem.web.manage.dao.YemRoleMapper;
import com.yem.web.manage.dao.YemShopMapper;
import com.yem.web.manage.dao.YemUserMapper;
import com.yem.web.manage.dao.YemUserRoleMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class YemUserServiceImpl {

	@Autowired
	private YemUserMapper yemUserMapper;
	
	@Autowired
	private YemShopMapper yemShopMapper;
	
	@Autowired
	private YemRoleMapper yemRoleMapper;
	
	@Autowired
	private YemUserRoleMapper yemUserRoleMapper;
	
	@Autowired
	private YemBaseServiceImpl baseServiceImpl;
	
	public YemUserVO getUserByCode(Long userCode) {
		YemUserVO vo = new YemUserVO();
		YemUser user = new YemUserVO();
		
		user = yemUserMapper.selectByUserCode(userCode);
		if (user == null) {
			return null;
		}
		
		vo = po2vo(user);
		
		YemShop shop = yemShopMapper.selectByShopCode(user.getShopCode());
		if (shop != null) {
			vo.setShopName(shop.getShopName());
		}
		
		return vo;
	}
	
	public List<YemUser> getUserList(YemUserDTO yemUserDTO) {
		return yemUserMapper.selectUserList(yemUserDTO);
	}
	
	public int getUserListCount(YemUserDTO yemUserDTO) {
		return yemUserMapper.selectUserListCount(yemUserDTO);
	} 
	
	public boolean addYemUser(YemUser user, List<Long> roles) {
		if (roles != null && !roles.isEmpty()) {
			List<YemUserRole> userRoles = initPermissions(user, roles);
			
			yemUserRoleMapper.insertBatch(userRoles);
		}
		
    	user.setUserCode(baseServiceImpl.getMcCode(McCodeTypeEnum.USER.toString()));
    	
		return yemUserMapper.insertSelective(user) > 0 ? true : false;
	}
	
	public boolean modifyYemUser(YemUser yemUser, List<Long> roles) {
		if (roles != null && !roles.isEmpty()) {
			List<YemUserRole> userRoles = initPermissions(yemUser, roles);
			
			yemUserRoleMapper.deleteByUserCode(yemUser.getUserCode());
			yemUserRoleMapper.insertBatch(userRoles);
		}
		return yemUserMapper.updateByUserCodeSelective(yemUser) > 0 ? true : false;
	}
	
	private YemUserVO po2vo(YemUser user) {
		YemUserVO userVO = null;
		
		if (user != null) {
			
			JSON json = (JSON) JSONObject.toJSON(user);
			
			userVO = JSONObject.toJavaObject(json, YemUserVO.class);
		}
		return userVO;
	}
	
	private List<YemUserRole> initPermissions(YemUser user, List<Long> roles) {

		List<YemUserRole> userRoles = new ArrayList<YemUserRole>();
		
		for (Long roleCode : roles) {
			YemRole role = yemRoleMapper.selectByRoleCode(roleCode);
			if (role == null) {
				log.info("角色编码：【{}】不存在！", roleCode);
			}
			
			YemUserRole userRole = new YemUserRole();
			userRole.setUserRoleId(StringUtil.getUUIDWithOutRod());
			userRole.setUserCode(user.getUserCode());
			userRole.setRoleCode(roleCode);
			userRole.setCreateTime(DateUtil.getDate());
			userRole.setCreateBy(role.getCreateBy());
			
			userRoles.add(userRole);
		}
		
		return userRoles;
	}
}
