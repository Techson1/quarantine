package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseGroup;
import com.beiqisoft.aoqun.entity.BaseGroupDetail;
import com.beiqisoft.aoqun.service.BaseGroupService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "baseGroup")
public class BaseGroupController extends BaseController<BaseGroup,BaseGroupService> {
	@RequestMapping(value ="list")
    public Page<BaseGroup> list(BaseGroup baseGroup) throws InterruptedException{
		return baseGroupService.find(baseGroup);
    }
	
	@JSON(type=BaseGroup.class,include="id,name")
	@RequestMapping(value="findByList")
	public List<BaseGroup> findByList(BaseGroup baseGroup) throws InterruptedException{
		return baseGroupService.findByList(baseGroup);
	}
	
	@RequestMapping(value="verify")
	public Message verify(String name){
		if (baseGroupService.getRepository().findByName(name)!=null){
			return new Message(101,"该群组名称已存在");
		}
		return SUCCESS;
	}
	
	@RequestMapping(value="copy")
	public Message copy(BaseGroup baseGroup,String ids){
		baseGroupService.getRepository().save(baseGroup);
		List<BaseGroupDetail> baseGroupDetails= new ArrayList<>(); 
		for (String id:ids.substring(0,ids.length()-1).split(",")){
			baseGroupDetails.add(new BaseGroupDetail(baseGroup,baseInfoService.getRepository().findOne(Long.parseLong(id))));
		}
		baseGroupDetailService.getRepository().save(baseGroupDetails);
		return SUCCESS;
	}
}