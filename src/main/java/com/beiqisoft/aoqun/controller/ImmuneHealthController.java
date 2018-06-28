package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.ImmuneHealth;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.ImmuneHealthService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "immuneHealth")
public class ImmuneHealthController extends BaseController<ImmuneHealth,ImmuneHealthService> {
	@JSON(type=BaseInfo.class,include="code,paddock")
	@JSON(type=Paddock.class,include="name")
	@RequestMapping(value ="list")
    public Page<ImmuneHealth> list(ImmuneHealth immuneHealth) throws InterruptedException{
		return immuneHealthService.find(immuneHealth);
    }
	
	@RequestMapping(value="addVerifys")
	public List<Message> addVerify(ImmuneHealth immuneHealth,String codes[]){
		List<Message> messages = new ArrayList<>();
		for(String code : codes){
			messages.add(baseInfoService.codeVerify(code));
		}
		return messages;
	}
	
	@RequestMapping(value="adds")
	public Message adds(ImmuneHealth immuneHealth,String codes[]){
		for (String code:codes){
			immuneHealthService.getRepository().save(
					new ImmuneHealth(immuneHealth,baseInfoService.findByCodeOrRfid(code)));
		}
		return SUCCESS;
	}
	@RequestMapping("update")
	public Message update(ImmuneHealth entity) throws Exception {
		ImmuneHealth emmu = (ImmuneHealth) reflectionUpdate(entity, getRepository());
		getRepository().save(emmu);
		return SUCCESS;
	}
}
