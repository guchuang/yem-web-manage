package com.yem.web.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yem.common.BaseMsgResponse;
import com.yem.constant.Constants;
import com.yem.dto.YemRoleDTO;
import com.yem.entity.YemRole;
import com.yem.exception.BaseException;
import com.yem.exception.ParamException;
import com.yem.request.AddYemRoleRequest;
import com.yem.request.ModifyYemRoleRequest;
import com.yem.request.QueryYemRoleListRequest;
import com.yem.request.QueryYemRoleRequest;
import com.yem.response.AddYemRoleResponse;
import com.yem.response.ModifyYemRoleResponse;
import com.yem.response.QueryYemRoleListResponse;
import com.yem.response.QueryYemRolePermissionResponse;
import com.yem.response.QueryYemRoleResponse;
import com.yem.utils.BizValidate;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.web.manage.filter.PreParamFilter;
import com.yem.web.manage.service.impl.YemPermissionServiceImpl;
import com.yem.web.manage.service.impl.YemRoleServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 角色基础功能
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2019年7月10日 下午1:53:37 <br/>
 *
 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
 * @version 
 * @since JDK 1.8
 */
@CrossOrigin
@RestController
@RequestMapping("/yem/role")
@Slf4j
public class RoleController {

	@Autowired
	private YemRoleServiceImpl roleService;
	
	@Autowired
	private YemPermissionServiceImpl permissionService;

	@PostMapping("/query")
	public QueryYemRoleResponse query(HttpServletRequest request) {
		log.info("开始角色详解查询接口");

		QueryYemRoleResponse resp = new QueryYemRoleResponse();
		try {

			String param = PreParamFilter.preHandle(request);

			QueryYemRoleRequest reqModel = (QueryYemRoleRequest) JSONObject.parseObject(param, 
					QueryYemRoleRequest.class);

			resp.setYemRole(roleService.getRoleByCode(reqModel.getRoleCode()));

			resp.setPermissions(permissionService.getPermissionByRoleCode(reqModel.getRoleCode()));
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}

	@PostMapping("/permission")
	public BaseMsgResponse permission(HttpServletRequest request) {
		log.info("开始角色权限详解查询接口");

		QueryYemRolePermissionResponse resp = new QueryYemRolePermissionResponse();
		try {

			String param = PreParamFilter.preHandle(request);

			QueryYemRoleRequest reqModel = (QueryYemRoleRequest) JSONObject.parseObject(param, 
					QueryYemRoleRequest.class);

			resp.setYemPermissions(permissionService.getPermissionByRoleCode(reqModel.getRoleCode()));

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}
	
	@PostMapping("/list")
	public QueryYemRoleListResponse list(HttpServletRequest request) {
		log.info("开始角色列表查询接口");

		QueryYemRoleListResponse resp = new QueryYemRoleListResponse();
		try {

			String param = PreParamFilter.preHandle(request);

			QueryYemRoleListRequest reqModel = (QueryYemRoleListRequest) JSONObject.parseObject(param, 
					QueryYemRoleListRequest.class);

			YemRoleDTO dto = new YemRoleDTO();
			dto.setRoleCode(reqModel.getRoleCode());
			dto.setRoleName(reqModel.getRoleName());
			dto.setValid(reqModel.getValid());
			dto.setCreateBy(reqModel.getCreateBy());
			dto.setCreateTime(reqModel.getCreateTime());
			dto.setUpdateBy(reqModel.getUpdateBy());
			dto.setUpdateTime(reqModel.getUpdateTime());
			dto.setStart((reqModel.getPageNo() - 1) * reqModel.getPageSize());
			dto.setPageSize(reqModel.getPageSize());


			resp.setYemRoles(roleService.getRoleList(dto));
			resp.setTotalPage(roleService.getRoleListCount(dto));
			resp.setPageNo(reqModel.getPageNo());
			resp.setPageSize(reqModel.getPageSize());

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}

	@PostMapping("/add")
	public AddYemRoleResponse add(HttpServletRequest request) {
		log.info("开始角色新增接口");

		AddYemRoleResponse resp = new AddYemRoleResponse();

		try {

			String param = PreParamFilter.preHandle(request);

			AddYemRoleRequest reqModel = (AddYemRoleRequest) JSONObject.parseObject(param, 
					AddYemRoleRequest.class);

			String result = BizValidate.valid(reqModel);
			if (!StringUtil.isBlank(result)) {
				throw new ParamException(result);
			}
			
			YemRole role = new YemRole();
			role.setRoleId(StringUtil.getUUIDWithOutRod());
			role.setRoleName(reqModel.getRoleName());
			role.setValid(reqModel.getValid());
			role.setDescription(reqModel.getDescription());
			role.setCreateTime(DateUtil.getDate());
			role.setUpdateTime(DateUtil.getDate());

			if (roleService.addYemRole(role, reqModel.getPermissions())) {

				resp.setRoleCode(role.getRoleCode());
			} else {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespMsg(Constants.RoleMsg.ROLE_ADD_ERROR);
			}
		} catch (ParamException e1) {
			
			resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
			resp.setRespMsg(e1.getMessage());
		} catch (BaseException e2) {
			
			resp.setRespCode(Constants.RESP_PARAM_ERR_CODE);
			resp.setRespMsg(e2.getMessage());
		} catch (Exception e) {
			
			log.error(e.getMessage());
		}

		return resp;
	}

	@PostMapping("/modify")
	public ModifyYemRoleResponse modify(HttpServletRequest request) {
		log.info("开始角色修改接口");

		ModifyYemRoleResponse resp = new ModifyYemRoleResponse();

		try {

			String param = PreParamFilter.preHandle(request);

			ModifyYemRoleRequest reqModel = (ModifyYemRoleRequest) JSONObject.parseObject(param, 
					ModifyYemRoleRequest.class);

			YemRole role = new YemRole();
			role.setRoleCode(reqModel.getRoleCode());
			role.setRoleName(reqModel.getRoleName());
			role.setValid(reqModel.getValid());
			role.setDescription(reqModel.getDescription());
			role.setUpdateTime(DateUtil.getDate());

			if (!roleService.modifyYemRoleAndPermission(role, reqModel.getPermissions())) {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.RoleMsg.ROLE_MODIFY_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return resp;
	}


	@PostMapping("/disabled")
	public ModifyYemRoleResponse disabled(HttpServletRequest request) {
		log.info("开始角色修改接口");

		ModifyYemRoleResponse resp = new ModifyYemRoleResponse();

		try {

			String param = PreParamFilter.preHandle(request);

			ModifyYemRoleRequest reqModel = (ModifyYemRoleRequest) JSONObject.parseObject(param, 
					ModifyYemRoleRequest.class);

			YemRole role = new YemRole();
			role.setRoleCode(reqModel.getRoleCode());
			role.setValid(reqModel.getValid());
			role.setUpdateTime(DateUtil.getDate());

			if (!roleService.modifyYemRole(role)) {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.RoleMsg.ROLE_MODIFY_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return resp;
	}
}
