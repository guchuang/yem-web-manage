package com.yem.web.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yem.constant.Constants;
import com.yem.dto.YemServerDTO;
import com.yem.entity.YemServer;
import com.yem.request.AddYemServerRequest;
import com.yem.request.ModifyYemServerRequest;
import com.yem.request.QueryYemServerListRequest;
import com.yem.request.QueryYemServerRequest;
import com.yem.response.AddYemServerResponse;
import com.yem.response.ModifyYemServerResponse;
import com.yem.response.QueryYemServerListResponse;
import com.yem.response.QueryYemServerResponse;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.web.manage.filter.PreParamFilter;
import com.yem.web.manage.service.impl.YemServerServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务基础功能
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2019年7月10日 下午1:53:37 <br/>
 *
 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
 * @version 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/yem/server")
@Slf4j
public class ServerController {

	@Autowired
	private YemServerServiceImpl serverService;

	@PostMapping("/yem/query")
	public QueryYemServerResponse query(HttpServletRequest request) {
		log.info("开始服务详解查询接口");

		QueryYemServerResponse resp = new QueryYemServerResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			QueryYemServerRequest reqModel = (QueryYemServerRequest) JSONObject.parseObject(param, 
					QueryYemServerRequest.class);

			resp.setYemServer(serverService.getServerByCode(reqModel.getServerCode()));

		} catch (Exception e) {
			log.error("预约详解查询接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/list")
	public QueryYemServerListResponse list(HttpServletRequest request) {
		log.info("开始服务列表查询接口");

		QueryYemServerListResponse resp = new QueryYemServerListResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			QueryYemServerListRequest reqModel = (QueryYemServerListRequest) JSONObject.parseObject(param, 
					QueryYemServerListRequest.class);

			YemServerDTO dto = new YemServerDTO();
			dto.setServerCode(reqModel.getServerCode());
			dto.setServerName(reqModel.getServerName());
			dto.setValid(reqModel.getValid());
			dto.setCreateBy(reqModel.getCreateBy());
			dto.setCreateTime(reqModel.getCreateTime());
			dto.setUpdateBy(reqModel.getUpdateBy());
			dto.setUpdateTime(reqModel.getUpdateTime());
			dto.setStart((reqModel.getPageNo() - 1) * reqModel.getPageSize());
			dto.setPageSize(reqModel.getPageSize());


			resp.setYemServers(serverService.getServerList(dto));
			resp.setTotalPage(serverService.getServerListCount(dto));
			resp.setPageNo(reqModel.getPageNo());
			resp.setPageSize(reqModel.getPageSize());

		} catch (Exception e) {
			log.error("预约详解查询接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/add")
	public AddYemServerResponse add(HttpServletRequest request) {
		log.info("开始服务新增接口");

		AddYemServerResponse resp = new AddYemServerResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			AddYemServerRequest reqModel = (AddYemServerRequest) JSONObject.parseObject(param, 
					AddYemServerRequest.class);

			YemServer server = new YemServer();
			server.setServerId(StringUtil.getUUIDWithOutRod());
			server.setServerName(reqModel.getServerName());
			server.setValid(reqModel.getValid());
			server.setCreateTime(DateUtil.getDate());
			server.setUpdateTime(DateUtil.getDate());

			if (serverService.addYemServer(server)) {

				resp.setServerCode(server.getServerCode());
			} else {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.ServerMsg.SERVER_ADD_ERROR);
			}

		} catch (Exception e) {
			log.error("预约详解查询接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/modify")
	public ModifyYemServerResponse modify(HttpServletRequest request) {
		log.info("开始服务修改接口");

		ModifyYemServerResponse resp = new ModifyYemServerResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			ModifyYemServerRequest reqModel = (ModifyYemServerRequest) JSONObject.parseObject(param, 
					ModifyYemServerRequest.class);

			YemServer server = new YemServer();
			server.setServerCode(reqModel.getServerCode());
			server.setServerName(reqModel.getServerName());
			server.setValid(reqModel.getValid());
			server.setUpdateTime(DateUtil.getDate());

			if (!serverService.modifyYemServer(server)) {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.ServerMsg.SERVER_MODIFY_ERROR);
			}

		} catch (Exception e) {
			log.error("预约详解查询接口异常：", e);
		}
		return resp;
	}
}
