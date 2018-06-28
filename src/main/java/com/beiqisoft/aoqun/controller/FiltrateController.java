package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.domain.FiltrateCondition;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.FiltateService;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 查询器访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "filtrate")
public class FiltrateController extends BaseController<BaseInfo,BaseInfoService>{
	@Autowired
	FiltateService filtateService;
	
	@JSON(type=BaseInfo.class,include="id,code")
	@RequestMapping(value="query")
	public Page<BaseInfo> query(FiltrateCondition filtrateCondition,Integer pageNum){
		List<Long> queryIds=filtateService.mainFrame(filtrateCondition);
		return new PageImpl<BaseInfo>(baseInfoService.getRepository().findByIdIn(queryIds.stream()
				.skip(pageNum*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.toArray(Long[]::new)),pageable(pageNum),queryIds.size());
	}
}