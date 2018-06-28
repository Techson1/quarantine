package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.EarChange;
import com.beiqisoft.aoqun.service.EarChangeService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "earChange")
public class EarChangeController extends BaseController<EarChange,EarChangeService> {
	@JSON(type=EarChange.class,filter="base")
	@RequestMapping(value ="list")
    public Page<EarChange> list(EarChange earChange) throws InterruptedException{
		return earChangeService.find(earChange);
    }
	
	/**
	 * 新戴表校验
	 * 一、检查baseInfo表中电子耳号是否存在
	 * 二、检查电子耳号是否在耳标登记中存在
	 * */
	@RequestMapping(value="newVerify")
	public Message newVerify(String code,String rfid){
		return earChangeService.newVerify(baseInfoService.getRepository().findByCode(code),rfid);
	}
	
	/**
	 * 新戴标添加
	 * 一、校验
	 * 二、修改羊只电子耳号
	 * 三、修改电子耳号为已用
	 * */
	@RequestMapping(value="newAdd")
	public BaseInfo newAdd(String code,String rfid){
		return earChangeService.newAdd(baseInfoService.getRepository().findByCode(code),rfid);
	}
	
	/**
	 * 新戴标删除
	 * 二、修改羊只电子耳号为空
	 * 三、修改电子耳号为未用
	 * */
	@RequestMapping(value="newDel")
	public Message newDel(String code,String rfid){
		return earChangeService.newDel(baseInfoService.getRepository().findByCode(code),rfid);
	}
	
	/**
	 * 补戴标校验
	 * */
	@RequestMapping(value="changeVerify")
	public Message changeVerify(String code,String rfid){
		return earChangeService.changeVerify(baseInfoService.getRepository().findByCode(code),rfid);
	}
	
	/**
	 * 补戴标添加
	 * */
	@JSON(type=BaseInfo.class,include="id")
	@RequestMapping(value="changeAdd")
	public EarChange changeAdd(String code,String rfid,String cause,String recorder){
		return earChangeService.changeAdd(baseInfoService.getRepository().findByCode(code),rfid,cause,recorder);
	}
}
