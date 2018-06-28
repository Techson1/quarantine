package com.beiqisoft.aoqun.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.PaddockService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "paddock")
public class PaddockController extends BaseController<Paddock,PaddockService> {
	@RequestMapping(value ="list")
    public Page<Paddock> list(Paddock paddock) throws InterruptedException{
		return paddockService.find(paddock);
    }
	
	@RequestMapping("mylist")
	public Page<Paddock> mylist(Paddock paddock,Long orgId){
		List<Paddock> list = paddockService.findByNameAndOrgId(paddock.getName(),orgId);
		return new PageImpl<>(list.stream()
				.skip(paddock.getPageNum()*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.collect(Collectors.toList()), pageable(paddock.getPageNum()), list.size());
	}
	/**
	 * 厂区可用查询
	 * */
	@RequestMapping(value="findByList")
	public List<Paddock> findByList(){
		return paddockService.getRepository().findByFlag(SystemM.PUBLIC_TRUE);
	}
	
	/**
	 * 存栏统计
	 * */
	@JSON(type=OnHand.class,include="paddock,num")
	@JSON(type=Paddock.class,include="id,name")
	@RequestMapping(value="livestockStatistics")
	public Page<OnHand> livestockStatistics(String name,Long orgId){
		List<OnHand> list = paddockService.livestock(name,orgId);
		return new PageImpl<OnHand>(list,new PageRequest(0, GlobalConfig.PAGE_MAX_SIZE),list.size());
	}
	
	/**
	 * 查询在场统计
	 * */
	@JSON(type=OnHand.class,include="paddock,num")
	@JSON(type=Paddock.class,include="id,name")
	@RequestMapping(value="livestock")
	public List<Paddock> livestock(String name,Long orgId){
		return paddockService.livestockTest(name,orgId);
	}
	
	/**
	 * 名称唯一校验
	 * */
	@RequestMapping(value="nameVerify")
	public boolean nameVerify(Paddock paddock){
		return paddockService.getRepository().findByName(paddock.getName())==null;
	}
	
	/**
	 * 羊只存档修改
	 * */
	@RequestMapping(value="flagSave/{id}/{flag}")
	public Message flagUpdate(@PathVariable Long id,@PathVariable String flag){
		paddockService.getRepository().save(paddockService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	
	/**
	 * 羊只存档校验
	 * */
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(paddockService.getRepository().findOne(id).getFlag());
	}
	
	/** 
	 * 存栏统计
	 * */
	@RequestMapping(value="paddockNumber")
	public List<Paddock> paddockNumber(){
		//存栏统计时时计算还是夜间统计
		return paddockService.paddockNumber();
	}
}
