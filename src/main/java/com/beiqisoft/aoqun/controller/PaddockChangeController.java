package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.PaddockChange;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.util.json.JSON;
import com.beiqisoft.aoqun.vo.PaddockChangeVo;
@RestController
@RequestMapping(value = "paddockChange")
public class PaddockChangeController extends BaseController<PaddockChange,PaddockChangeService> {


    
	@JSON(type=BaseInfo.class,include="breed,code,sex")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="list")
    public Page<PaddockChange> list(PaddockChange paddockChange) throws InterruptedException{
		return paddockChangeService.find(paddockChange);
    }
	/**
	 * 
	 * @param paddockChange
	 * @return
	 */
	@JSON(type=BaseInfo.class,include="breed,code,sex")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="findAllTurnList")
	@Cacheable(value="paddockChangeCache")
	public Page<PaddockChangeVo> findAllTurnList(PaddockChange paddockChange){
		
		return paddockChangeService.findAllTurnList(paddockChange);
	}
	@RequestMapping(value="addVerifys")
	public List<Message> addVerifys(String[] earTags,Long paddockId){
		//TODO 转栏校验预留接口
		List<Message> list=new ArrayList<Message>();
		for (String earTag:earTags){
			list.add(paddockChangeService.addVerify(earTag,paddockId));
		}
		return list;
	}
	@RequestMapping(value="addVerify")
	public Message addVerify(String earTag,Long paddockId){
		return paddockChangeService.addVerify(earTag,paddockId);
	}
	@RequestMapping(value="adds")
	public Message add(PaddockChange paddockChange,String[] earTags){
		for (String earTag:earTags){
			paddockChangeService.add(paddockChange.getToPaddock(),earTag,paddockChange.getOrg(),paddockChange.getRecorder());
		}
		return SUCCESS;
	}
	
	@JSON(type=PaddockChange.class,filter="org")
	@JSON(type=Paddock.class,include="id,name")
	@JSON(type=BaseInfo.class,include="id,code")
	@RequestMapping(value="appAdd")
	public PaddockChange appAdd(PaddockChange paddockChange,String earTag){
		return paddockChangeService.add(paddockChange.getToPaddock(),earTag,paddockChange.getOrg(),paddockChange.getRecorder());
	}
	
	@Transactional
	@RequestMapping(value="appAddAll")
	public Map<String,Object> appAddAll(Long fromPaddockId,Long toPaddockId,Long orgId,Date date,String recorder){
		Map<String,Object> map=new HashMap<>();
		Paddock fromPaddock = paddockService.getRepository().findOne(fromPaddockId);
		Paddock toPaddock = paddockService.getRepository().findOne(toPaddockId);
		map.put("fromPaddock", fromPaddock);
		map.put("toPaddock", toPaddock);
		paddockChangeService.addAll(fromPaddock,toPaddock,recorder,organizationService.getRepository().findOne(orgId));
		return map;
	}
}
