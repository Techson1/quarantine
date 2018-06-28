package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.GroupChange;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.service.GroupChangeService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "groupChange")
public class GroupChangeController extends BaseController<GroupChange,GroupChangeService> {
	@JSON(type=BaseInfo.class,include="breed,code,sex")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="orgName,brief")
	@JSON(type=RankTest.class,include="name")
	@RequestMapping(value ="list")
    public Page<GroupChange> list(GroupChange groupChange) throws InterruptedException{
		return groupChangeService.find(groupChange);
    }
	
	/**
	 * 定级添加
	 * */
	@RequestMapping(value="getRank")
	public List<RankTest> getRank(String earTag){
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return null;
		}
		return rankTestService.getRepository().findByPriceIsNullOrSexAndBreed_id(base.getSex(), base.getBreed().getId());
	}
	
	/**
	 * 定级添加校验
	 * */
	@RequestMapping(value="getRankApp")
	public List<RankTest> getRankApp(String sex,Long breedId){
		return rankTestService.getRepository().findByPriceIsNullOrSexAndBreed_id(sex,breedId);
	}
	
	/**
	 * 校验
	 * */
	@RequestMapping(value="verify")
	public Message verify(String earTag,Long rankId){
		return groupChangeService.verify(earTag,rankId);
	}
	
	@RequestMapping(value="add")
	public Message add(GroupChange groupChange,String earTag,Long rankId){
		groupChangeService.add(groupChange.setToRankReturnThis(new RankTest(rankId)), earTag);
		return SUCCESS;
	}
	
	@RequestMapping(value="appAdd")
	public GroupChange appAdd(GroupChange groupChange,String earTag){
		return groupChangeService.add(groupChange,earTag);
	}
}
