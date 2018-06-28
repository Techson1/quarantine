package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Allot;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.AllotService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "allot")
public class AllotController extends BaseController<Allot,AllotService> {
	@RequestMapping(value ="list")
    public Page<Allot> list(Allot allot) throws InterruptedException{
		return allotService.find(allot);
    }
	
	/**
	 * 修改校验
	 * */
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(allotService.getRepository().findOne(id).getFlag());
	}
	
	/**
	 * 修改
	 * */
	@RequestMapping(value="flagUpdate/{id}/{flag}")
	public Message flagUpdate(@PathVariable Long id,@PathVariable String flag){
		if (!allotDetailService.getRepository().findByAllot_idAndFlag(id,SystemM.PUBLIC_FALSE).isEmpty()){
			return GlobalConfig.setAbnormal("该调拨单还有未审核的羊只,不能关闭");
		}
		allotService.getRepository().save(allotService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	
	
	/**
	 * 可用列表查询
	 * */
	@JSON(type=Organization.class,include="id,brief")
	@JSON(type=Paddock.class,include="id,name")
	@RequestMapping(value="flagList/{orgId}/{type}")
	public List<Allot> flagList(@PathVariable Long orgId, @PathVariable String type){
		if (SystemM.PUBLIC_TRUE.equals(type)){
			return allotService.getRepository().findByToOrg_idAndFlag(orgId,SystemM.PUBLIC_TRUE);
		}
		else{
			return allotService.getRepository().findByFromOrg_idAndFlag(orgId,SystemM.PUBLIC_TRUE);
		}
	}
}
