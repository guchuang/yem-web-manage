package com.yem.web.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yem.common.BaseMsgResponse;
import com.yem.constant.Constants;
import com.yem.exception.BaseException;
import com.yem.utils.StringUtil;

/**
 * 基础逻辑处理
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2019年7月10日 下午1:53:37 <br/>
 *
 * @author <a href="mailto:hbszguchuang@163.com">guchuang</a>
 * @version 
 * @since JDK 1.8
 */
@RestController
public class WebExceptionResolverController {
	
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public BaseMsgResponse jsonErrorHandler(HttpServletRequest req, BaseException e) throws Exception {
		BaseMsgResponse msg = new BaseMsgResponse();
		msg.setRespMsg(e.getMessage());
		if (StringUtil.isEmpty(e.getCode())) {
			msg.setRespCode(Constants.RESP_ERR_CODE);
		} else {
			msg.setRespCode(e.getCode());
		}
	    return msg;
	}
}
