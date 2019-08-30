package com.yem.web.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yem.constant.Constants;
import com.yem.dto.YemShopDTO;
import com.yem.entity.YemShop;
import com.yem.exception.BaseException;
import com.yem.request.AddYemShopRequest;
import com.yem.request.ModifyYemShopRequest;
import com.yem.request.QueryYemShopListRequest;
import com.yem.request.QueryYemShopRequest;
import com.yem.response.AddYemShopResponse;
import com.yem.response.ModifyYemShopResponse;
import com.yem.response.QueryYemShopListResponse;
import com.yem.response.QueryYemShopResponse;
import com.yem.utils.DateUtil;
import com.yem.utils.StringUtil;
import com.yem.web.manage.filter.PreParamFilter;
import com.yem.web.manage.service.impl.YemShopServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 门店基础功能
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2019年7月10日 下午1:53:37 <br/>
 *
 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
 * @version 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/yem/shop")
@Slf4j
public class ShopController {

	@Autowired
	private YemShopServiceImpl shopService;

	@PostMapping("/query")
	public QueryYemShopResponse query(HttpServletRequest request) {
		log.info("开始门店详解查询接口");

		QueryYemShopResponse resp = new QueryYemShopResponse();

		try {
			
			String param = PreParamFilter.preHandle(request);

			QueryYemShopRequest reqModel = (QueryYemShopRequest) JSONObject.parseObject(param, 
					QueryYemShopRequest.class);

			resp.setYemShop(shopService.getShopByCode(reqModel.getShopCode()));

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resp;
	}

	@PostMapping("/list")
	public QueryYemShopListResponse list(HttpServletRequest request) {
		log.info("开始门店列表查询接口");

		QueryYemShopListResponse resp = new QueryYemShopListResponse();

		try {

			String param = PreParamFilter.preHandle(request);

			QueryYemShopListRequest reqModel = (QueryYemShopListRequest) JSONObject.parseObject(param, 
					QueryYemShopListRequest.class);

			YemShopDTO dto = new YemShopDTO();
			dto.setShopCode(reqModel.getShopCode());
			dto.setShopName(reqModel.getShopName());
			dto.setValid(reqModel.getValid());
			dto.setCreateBy(reqModel.getCreateBy());
			dto.setCreateTime(reqModel.getCreateTime());
			dto.setUpdateBy(reqModel.getUpdateBy());
			dto.setUpdateTime(reqModel.getUpdateTime());
			dto.setStart((reqModel.getPageNo() - 1) * reqModel.getPageSize());
			dto.setPageSize(reqModel.getPageSize());


			resp.setYemShops(shopService.getShopList(dto));
			resp.setTotalPage(shopService.getShopListCount(dto));
			resp.setPageNo(reqModel.getPageNo());
			resp.setPageSize(reqModel.getPageSize());
		} catch (BaseException e) {
			log.error("门店列表查询接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/add")
	public AddYemShopResponse add(HttpServletRequest request) {
		log.info("开始门店新增接口");

		AddYemShopResponse resp = new AddYemShopResponse();
		try {
			
			String param = PreParamFilter.preHandle(request);

			AddYemShopRequest reqModel = (AddYemShopRequest) JSONObject.parseObject(param, 
					AddYemShopRequest.class);

			YemShop shop = new YemShop();
			shop.setShopId(StringUtil.getUUIDWithOutRod());
			shop.setShopName(reqModel.getShopName());
			shop.setProvince(reqModel.getProvince());
			shop.setCity(reqModel.getCity());
			shop.setCounty(reqModel.getCounty());
			shop.setAddress(reqModel.getAddress());
			shop.setValid(reqModel.getValid());
			shop.setCreateTime(DateUtil.getDate());
			shop.setUpdateTime(DateUtil.getDate());

			if (shopService.addYemShop(shop)) {

				resp.setShopCode(shop.getShopCode());
			} else {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.ShopMsg.SHOP_ADD_ERROR);
			}
		} catch (BaseException e) {
			log.error("门店新增接口异常：", e);
		}
		return resp;
	}

	@PostMapping("/modify")
	public ModifyYemShopResponse modify(HttpServletRequest request) {
		log.info("开始门店修改接口");

		ModifyYemShopResponse resp = new ModifyYemShopResponse();

		try {
			String param = PreParamFilter.preHandle(request);

			ModifyYemShopRequest reqModel = (ModifyYemShopRequest) JSONObject.parseObject(param, 
					ModifyYemShopRequest.class);

			YemShop shop = new YemShop();
			shop.setShopCode(reqModel.getShopCode());
			shop.setShopName(reqModel.getShopName());
			shop.setProvince(reqModel.getProvince());
			shop.setCity(reqModel.getCity());
			shop.setCounty(reqModel.getCounty());
			shop.setAddress(reqModel.getAddress());
			shop.setValid(reqModel.getValid());
			shop.setUpdateTime(DateUtil.getDate());

			if (!shopService.modifyYemShop(shop)) {

				resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
				resp.setRespCode(Constants.ShopMsg.SHOP_MODIFY_ERROR);
			}

		} catch (BaseException e) {
			log.error("门店修改接口异常：", e);
		}
		return resp;
	}
}
