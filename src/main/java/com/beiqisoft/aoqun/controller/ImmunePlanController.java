package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.ImmunePlan;
import com.beiqisoft.aoqun.service.ImmunePlanService;
@RestController
@RequestMapping(value = "immunePlan")
public class ImmunePlanController extends BaseController<ImmunePlan,ImmunePlanService> {
	@RequestMapping(value ="list")
    public Page<ImmunePlan> list(ImmunePlan immunePlan) throws InterruptedException{
		return immunePlanService.find(immunePlan);
    }
	
	@RequestMapping(value="flagUpdate/{id}/{flag}")
	public Message flagUpdate(@PathVariable Long id ,@PathVariable String flag){
		immunePlanService.getRepository().save(
				immunePlanService.getRepository().findOne(id)
				.setFlagReturnThis(flag)); 
		return SUCCESS;
	}
	
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(immunePlanService.getRepository().findOne(id).getFlag());
	}
}
