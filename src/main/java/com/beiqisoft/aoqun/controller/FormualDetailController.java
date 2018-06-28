package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.FormualDetail;
import com.beiqisoft.aoqun.service.FormualDetailService;
@RestController
@RequestMapping(value = "formualDetail")
public class FormualDetailController extends BaseController<FormualDetail,FormualDetailService> {
	@RequestMapping(value ="list")
    public Page<FormualDetail> list(FormualDetail formualDetail) throws InterruptedException{
		return formualDetailService.find(formualDetail);
    }
	
	/**
	 * 添加校验
	 * @param formula.id
	 * 			配方
	 * @param ratio
	 * 			百分比
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(FormualDetail formualDetail){
		return formualDetailService.addVerify(formualDetail);
	}
	
	
}
