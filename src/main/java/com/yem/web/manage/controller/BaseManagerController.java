package com.yem.web.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yem.constant.Constants;
import com.yem.request.QueryYemMcCodeRequest;
import com.yem.response.QueryYemMcCodeResponse;
import com.yem.web.manage.service.impl.YemBaseServiceImpl;

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
@RequestMapping("/base")
public class BaseManagerController {
	
	@Autowired
	private YemBaseServiceImpl yemBaseService;

    @PostMapping("/mcCode")
    public QueryYemMcCodeResponse query(@RequestParam("req") QueryYemMcCodeRequest req) {
    	QueryYemMcCodeResponse resp = new QueryYemMcCodeResponse();
    	
    	resp.setRespCode(Constants.RESP_SUC_CODE);
    	resp.setRespMsg(Constants.RESP_SUC_MSG);
    	
    	Long mcCode = yemBaseService.getMcCode(req.getMcCodeType());
    	
    	if (mcCode == null) {
    		
        	resp.setRespCode(Constants.RESP_BIZ_ERR_CODE);
        	resp.setRespMsg(Constants.RESP_BIZ_ERR_MSG);
        	return resp;
    	}
    	
    	resp.setMcCode(mcCode);
    	
    	return resp;
    }

}
