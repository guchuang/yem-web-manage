package com.yem.web.manage.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.yem.common.BaseMsg;
import com.yem.common.BaseMsgHeader;
import com.yem.constant.ApiConstant;
import com.yem.exception.BaseException;
import com.yem.exception.ParamException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ParamFilter implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		BaseMsg baseMsg = new BaseMsg();
		JSONObject json = parseHanlderRequest(preHandlerRequest(request));
		checkBaseMsg(json, baseMsg);
		request.setAttribute(ApiConstant.ROUTE_KEY, json);
		return true;
	}
	
	/**
	 * 预处理post请求数据流
	 * (这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
	 * @param request
	 * @return
	 * @throws BaseException
	 * @since JDK 1.8
	 */
	public String preHandlerRequest(HttpServletRequest request) throws BaseException{
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			br.close();
		} catch (IOException e) {
			log.error("参数解析失败");
			throw new ParamException("参数解析失败");
		}
		return sb.toString();
	}
	
	/**
	 * 转换成json并校验报文合法性
	 * (这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
	 * @param json
	 * @return
	 * @throws BaseException
	 * @since JDK 1.8
	 */
	private JSONObject parseHanlderRequest(String json) throws BaseException{
		JSONObject param;
		try {
			
			param = JSONObject.parseObject(json);
		} catch (Exception e) {
			log.error("参数转换异常");
			throw new ParamException(e.getMessage());
		}
		
		return param;
	}
	
	/**
	 * 校验请求信息
	 * (这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
	 * @param json
	 * @param msg
	 * @throws BaseException
	 * @since JDK 1.8
	 */
	private void checkBaseMsg(JSONObject json, BaseMsg msg) throws BaseException {
		if (json == null) {
			throw new ParamException("请求信息缺失");
		}
		if (!json.containsKey(ApiConstant.API_MSG_HEADER)) {
			throw new ParamException("请求头缺失");
		}
		if (!json.containsKey(ApiConstant.API_MSG_BODY)) {
			throw new ParamException("请求体缺失");
		}

		JSONObject body = json.getJSONObject(ApiConstant.API_MSG_BODY);
		msg.setRequestBody(body);
		
		JSONObject header = json.getJSONObject(ApiConstant.API_MSG_HEADER);
		if (!header.containsKey(ApiConstant.API_MSG_SOURCE)) {
			throw new ParamException("请求来源缺失");
		}
		if (!header.containsKey(ApiConstant.API_MSG_TIME)) {
			throw new ParamException("请求时间缺失");
		}
		if (!header.containsKey(ApiConstant.API_MSG_ACT)) {
			throw new ParamException("请求动作缺失");
		}
		msg.setRequestHeader(JSONObject.toJavaObject(header, BaseMsgHeader.class));

	}
}
