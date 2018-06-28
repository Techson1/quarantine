package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.FakeTypeSire;
import com.beiqisoft.aoqun.service.FakeTypeSireService;

/**
 * 公羊缺陷访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "fakeTypeSire")
public class FakeTypeSireController extends BaseController<FakeTypeSire,FakeTypeSireService> {
	@RequestMapping(value ="list")
    public Page<FakeTypeSire> list(FakeTypeSire fakeTypeSire) throws InterruptedException{
		return fakeTypeSireService.find(fakeTypeSire);
    }
	
	/**
	 * 查询可用公羊缺陷
	 * @exception InterruptedException
	 * @return List<FakeTypeDam>
	 * */
	@RequestMapping(value ="flagList")
    public List<FakeTypeSire> flagList() throws InterruptedException{
		return fakeTypeSireService.getRepository().findByFlag(SystemM.PUBLIC_TRUE);
    }
	
	/**
	 * 修改公羊缺陷是否可用字段
	 * 
	 * @param id
	 * 			
	 * @param flag
	 * 			是否可用
	 * 				可用:1
	 * 				存档:2
	 * */
	@RequestMapping(value ="flagUp/{id}/{flag}")
	public Message flagUp(@PathVariable Long id,@PathVariable String flag){
		fakeTypeSireService.getRepository().save(fakeTypeSireService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	
	/**
	 * 公羊缺陷名称校验
	 * @param name
	 * 			公羊缺陷名称
	 * @return Message
	 * */
	@RequestMapping(value ="nameVerify")
	public boolean nameVerify(String name){
		return fakeTypeSireService.getRepository().findByName(name)==null;
	}
	
	/**
	 * 修改前置校验
	 * @param id
	 * 			母羊缺陷id
	 * @return boolean
	 * 			flag为1是返回true,否则返回false
	 * */
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(fakeTypeSireService.getRepository().findOne(id).getFlag());
	}
}
