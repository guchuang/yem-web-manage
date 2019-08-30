package com.yem.web.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yem.constant.Constants;
import com.yem.dto.YemUserDTO;
import com.yem.entity.YemUser;
import com.yem.request.AddYemUserRequest;
import com.yem.request.ModifyYemUserRequest;
import com.yem.request.QueryYemUserListRequest;
import com.yem.request.QueryYemUserRequest;
import com.yem.response.AddYemUserResponse;
import com.yem.response.ModifyYemUserResponse;
import com.yem.response.QueryYemUserListResponse;
import com.yem.response.QueryYemUserResponse;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.web.manage.filter.PreParamFilter;
import com.yem.web.manage.service.impl.YemRoleServiceImpl;
import com.yem.web.manage.service.impl.YemUserServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户基础功能
 * date: 2019年7月10日 下午1:53:37 <br/>
 *
 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
 * @version 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/yem/user")
@Slf4j
public class UserController {

	@Autowired
	private YemUserServiceImpl userService;
	
	@Autowired
	private YemRoleServiceImpl roleService;

	@PostMapping("/query")
	public QueryYemUserResponse query(HttpServletRequest request) {
		log.info("开始用户详解查询接口");

		QueryYemUserResponse resp = new QueryYemUserResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			QueryYemUserRequest reqModel = (QueryYemUserRequest) JSONObject.parseObject(param, 
					QueryYemUserRequest.class);

			resp.setYemUser(userService.getUserByCode(reqModel.getUserCode()));
			resp.setYemRoles(roleService.getRoleListByUserCode(reqModel.getUserCode()));
			
		} catch (Exception e) {
			log.error("用户详解查询接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/list")
	public QueryYemUserListResponse list(HttpServletRequest request) {
		log.info("开始用户列表查询接口");

		QueryYemUserListResponse resp = new QueryYemUserListResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			QueryYemUserListRequest reqModel = (QueryYemUserListRequest) JSONObject.parseObject(param, 
					QueryYemUserListRequest.class);

			YemUserDTO dto = new YemUserDTO();
			dto.setUserCode(reqModel.getUserCode());
			dto.setUserName(reqModel.getUserName());
			dto.setEmail(reqModel.getEmail());
			dto.setMobile(reqModel.getMobile());
			dto.setSex(reqModel.getSex());
			dto.setShopCode(reqModel.getShopCode());
			dto.setValid(reqModel.getValid());
			dto.setCreateBy(reqModel.getCreateBy());
			dto.setCreateTime(reqModel.getCreateTime());
			dto.setUpdateBy(reqModel.getUpdateBy());
			dto.setUpdateTime(reqModel.getUpdateTime());
			dto.setStart((reqModel.getPageNo() - 1) * reqModel.getPageSize());
			dto.setPageSize(reqModel.getPageSize());


			resp.setYemUsers(userService.getUserList(dto));
			resp.setTotalPage(userService.getUserListCount(dto));
			resp.setPageNo(reqModel.getPageNo());
			resp.setPageSize(reqModel.getPageSize());

		} catch (Exception e) {
			log.error("用户列表查询接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/add")
	public AddYemUserResponse add(HttpServletRequest request) {
		log.info("开始用户新增接口");

		AddYemUserResponse resp = new AddYemUserResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			AddYemUserRequest reqModel = (AddYemUserRequest) JSONObject.parseObject(param, 
					AddYemUserRequest.class);

			YemUser user = new YemUser();
			user.setUserId(StringUtil.getUUIDWithOutRod());
			user.setUserName(reqModel.getUserName());
			user.setMobile(reqModel.getMobile());
			user.setEmail(reqModel.getEmail());
			user.setSex(reqModel.getSex());
			user.setShopCode(reqModel.getShopCode());
			user.setValid(reqModel.getValid());
			user.setCreateTime(DateUtil.getDate());
			user.setUpdateTime(DateUtil.getDate());

			if (userService.addYemUser(user, reqModel.getRoles())) {

				resp.setUserCode(user.getUserCode());
			} else {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.UserMsg.USER_ADD_ERROR);
			}

		} catch (Exception e) {
			log.error("用户新增接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/modify")
	public ModifyYemUserResponse modify(HttpServletRequest request) {
		log.info("开始用户修改接口");

		ModifyYemUserResponse resp = new ModifyYemUserResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			ModifyYemUserRequest reqModel = (ModifyYemUserRequest) JSONObject.parseObject(param, 
					ModifyYemUserRequest.class);

			YemUser user = new YemUser();
			user.setUserCode(reqModel.getUserCode());
			user.setUserName(reqModel.getUserName());
			user.setMobile(reqModel.getMobile());
			user.setEmail(reqModel.getEmail());
			user.setSex(reqModel.getSex());
			user.setShopCode(reqModel.getShopCode());
			user.setValid(reqModel.getValid());
			user.setUpdateTime(DateUtil.getDate());

			if (!userService.modifyYemUser(user, reqModel.getRoles())) {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.UserMsg.USER_MODIFY_ERROR);
			}

		} catch (Exception e) {
			log.error("用户修改接口异常：", e);
		}
		return resp;
	}
}
