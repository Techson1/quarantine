package com.beiqisoft.aoqun.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.EmbryoTransplant;
import com.beiqisoft.aoqun.service.EmbryoTransplantService;

/**
 * 鲜胚移植控制访问类
 * */
@RestController
@RequestMapping(value = "embryoTransplant")
public class EmbryoTransplantController extends BaseController<EmbryoTransplant,EmbryoTransplantService> {
	@RequestMapping(value ="list")
    public Page<EmbryoTransplant> list(EmbryoTransplant embryoTransplant) throws InterruptedException{
		return embryoTransplantService.find(embryoTransplant);
    }
	
	/**
	 * 鲜胚移植添加
	 * */
	@RequestMapping(value="add")
	public Message add(String sheetCode,Integer transNum,Date date,String qualityGrade,Long embryoFlushId,String recorder){
		if (embryoTransplantService.deitJudge(sheetCode,embryoFlushId,qualityGrade,transNum,recorder)){
			
			embryoTransplantService.getRepository().save(new EmbryoTransplant(sheetCode,transNum,date,
					qualityGrade,embryoFlushService.getRepository().findOne(embryoFlushId),recorder));
		}
		return SUCCESS;
	}
	
	/**
	 * 鲜胚移植绑定耳号
	 * */
	@RequestMapping(value="bindingCode")
	public Message bindingCode(String code,Long id){
		return embryoTransplantService.bindingCode(code,id);
	}
	
	/**
	 * 鲜胚移植绑定修改校验
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(String code,Long id){
		return embryoTransplantService.updateVerify(code,id);
	}
	
	
	/**
	 * 鲜胚移植删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return embryoTransplantService.delVerify(id);
	}
}
