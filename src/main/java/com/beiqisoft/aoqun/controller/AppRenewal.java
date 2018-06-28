package com.beiqisoft.aoqun.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "appRenewal")
public class AppRenewal {

	/**
	 * 添加校验
	 * */
	@RequestMapping(value="renewal")
	public Map<String,String> addVerify(String codes,Date abortionDate){
		Map<String,String> renewalMap=new HashMap<String, String>();
		renewalMap.put("versions","1.0");
		renewalMap.put("downloadSite", "http://aoqun.oss-cn-beijing.aliyuncs.com/app/aoqunAPP.apk");
		return renewalMap;
	}
}
