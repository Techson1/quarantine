package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.service.RankTestService;

/**
 * 
 * */
@RestController
@RequestMapping(value = "rankTest")
public class RankTestController extends BaseController<RankTest,RankTestService> {
	
	@RequestMapping(value ="list")
    public Page<RankTest> list(RankTest rankTest) throws InterruptedException{
		rankTest.setRank(SystemM.RANKTEST_RANK_MARKET);
		return rankTestService.find(rankTest);
    }
	
	@RequestMapping(value="verify")
	public boolean verify(RankTest rankTest){
		return rankTestService.getRepository().findByNameAndSexAndBreed_id(
				rankTest.getName(), rankTest.getSex(),rankTest.getBreed().getId())==null;
	}
	
	@RequestMapping(value="init")
	public Message init(){
		rankTestService.getRepository().save(new RankTest(null,null,null,SystemM.RANKTEST_RANK_KERNEL,null));
		rankTestService.getRepository().save(new RankTest(null,null,null,SystemM.RANKTEST_RANK_MARKET_RESERVE_KERNEL,null));
		rankTestService.getRepository().save(new RankTest(null,null,null,SystemM.RANKTEST_RANK_PRODUCTION,null));
		return SUCCESS;
	}
	
	/**
	 * 根据品种性别查询
	 * */
	@RequestMapping(value="findByRank")
	public List<RankTest> findByRank(RankTest rankTest){
		return rankTestService.getRepository().findByBreed_idAndSex(rankTest.getBreed().getId(),rankTest.getSex());
	}
	
	@RequestMapping(value="add")
	public Message add(RankTest rankTest){
		rankTest.setRank(SystemM.RANKTEST_RANK_MARKET);
		rankTestService.getRepository().save(rankTest);
		return SUCCESS;
	}
}