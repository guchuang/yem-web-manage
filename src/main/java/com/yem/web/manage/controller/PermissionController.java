package com.yem.web.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yem.constant.Constants;
import com.yem.dto.YemPermissionDTO;
import com.yem.entity.YemPermission;
import com.yem.request.AddYemPermissionRequest;
import com.yem.request.ModifyYemPermissionRequest;
import com.yem.request.QueryYemPermissionListRequest;
import com.yem.request.QueryYemPermissionRequest;
import com.yem.response.AddYemPermissionResponse;
import com.yem.response.ModifyYemPermissionResponse;
import com.yem.response.QueryYemPermissionListResponse;
import com.yem.response.QueryYemPermissionResponse;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.web.manage.filter.PreParamFilter;
import com.yem.web.manage.service.impl.YemPermissionServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 权限基础功能
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2019年7月10日 下午1:53:37 <br/>
 *
 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
 * @version 
 * @since JDK 1.8
 */
@RestController
@CrossOrigin
@RequestMapping("/yem/permission")
@Slf4j
public class PermissionController {

	@Autowired
	private YemPermissionServiceImpl permissionService;

	@PostMapping("/query")
	public QueryYemPermissionResponse query(HttpServletRequest request) {
		log.info("开始权限详解查询接口");
		QueryYemPermissionResponse resp = new QueryYemPermissionResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			QueryYemPermissionRequest reqModel = (QueryYemPermissionRequest) JSONObject.parseObject(param, 
					QueryYemPermissionRequest.class);

			resp.setYemPermission(permissionService.getPermissionByCode(reqModel.getPermissionCode()));

		} catch (Exception e) {

			log.error(e.getMessage());
		}
		return resp;
	}

	@PostMapping("/list")
	public QueryYemPermissionListResponse list(HttpServletRequest request) {
		log.info("开始权限列表查询接口");

		QueryYemPermissionListResponse resp = new QueryYemPermissionListResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			QueryYemPermissionListRequest reqModel = (QueryYemPermissionListRequest) JSONObject.parseObject(param, 
					QueryYemPermissionListRequest.class);

			YemPermissionDTO dto = new YemPermissionDTO();
			dto.setPermissionCode(reqModel.getPermissionCode());
			dto.setPermissionName(reqModel.getPermissionName());
			dto.setValid(reqModel.getValid());
			dto.setCreateBy(reqModel.getCreateBy());
			dto.setCreateTime(reqModel.getCreateTime());
			dto.setUpdateBy(reqModel.getUpdateBy());
			dto.setUpdateTime(reqModel.getUpdateTime());
			dto.setStart((reqModel.getPageNo() - 1) * reqModel.getPageSize());
			dto.setPageSize(reqModel.getPageSize());


			resp.setYemPermissions(permissionService.getPermissionList(dto));
			resp.setTotalPage(permissionService.getPermissionListCount(dto));
			resp.setPageNo(reqModel.getPageNo());
			resp.setPageSize(reqModel.getPageSize());

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}

	@PostMapping("/add")
	public AddYemPermissionResponse add(HttpServletRequest request) {
		log.info("开始权限新增接口");

		AddYemPermissionResponse resp = new AddYemPermissionResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			AddYemPermissionRequest reqModel = (AddYemPermissionRequest) JSONObject.parseObject(param, 
					AddYemPermissionRequest.class);

			YemPermission permission = new YemPermission();
			permission.setPermissionId(StringUtil.getUUIDWithOutRod());
			permission.setPermissionName(reqModel.getPermissionName());
			permission.setMethod(reqModel.getMethod());
			permission.setZuulPrefix(reqModel.getZuulPrefix());
			permission.setServerPrefix(reqModel.getServerPrefix());
			permission.setUri(reqModel.getUri());
			permission.setValid(reqModel.getValid());
			permission.setCreateTime(DateUtil.getDate());
			permission.setUpdateTime(DateUtil.getDate());

			if (permissionService.addYemPermission(permission)) {

				resp.setPermissionCode(permission.getPermissionCode());
			} else {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.PermissionMsg.PERMISSION_ADD_ERROR);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}

	@PostMapping("/modify")
	public ModifyYemPermissionResponse modify(HttpServletRequest request) {
		log.info("开始权限修改接口");

		ModifyYemPermissionResponse resp = new ModifyYemPermissionResponse();

		try {

			String param = PreParamFilter.preHandle(request);

			ModifyYemPermissionRequest reqModel = (ModifyYemPermissionRequest) JSONObject.parseObject(param, 
					ModifyYemPermissionRequest.class);

			YemPermission permission = new YemPermission();
			permission.setPermissionCode(reqModel.getPermissionCode());
			permission.setPermissionName(reqModel.getPermissionName());
			permission.setMethod(reqModel.getMethod());
			permission.setZuulPrefix(reqModel.getZuulPrefix());
			permission.setServerPrefix(reqModel.getServerPrefix());
			permission.setUri(reqModel.getUri());
			permission.setValid(reqModel.getValid());

			if (!permissionService.modifyYemPermission(permission)) {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.PermissionMsg.PERMISSION_MODIFY_ERROR);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}
}
