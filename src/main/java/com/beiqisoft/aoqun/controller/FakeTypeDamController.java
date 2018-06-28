package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.FakeTypeDam;
import com.beiqisoft.aoqun.service.FakeTypeDamService;

/**
 * 母羊缺陷访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "fakeTypeDam")
public class FakeTypeDamController extends BaseController<FakeTypeDam,FakeTypeDamService> {
	@RequestMapping(value ="list")
    public Page<FakeTypeDam> list(FakeTypeDam fakeTypeDam) throws InterruptedException{
		return fakeTypeDamService.find(fakeTypeDam);
    }
	
	/**
	 * 查询可用母羊缺陷
	 * @exception InterruptedException
	 * @return List<FakeTypeDam>
	 * */
	@RequestMapping(value ="flagList")
    public List<FakeTypeDam> flagList() throws InterruptedException{
		return fakeTypeDamService.getRepository().findByFlag(SystemM.PUBLIC_TRUE);
    }
	
	/**
	 * 修改母羊缺陷是否可用字段
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
		fakeTypeDamService.getRepository().save(fakeTypeDamService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
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
		return SystemM.PUBLIC_TRUE.equals(fakeTypeDamService.getRepository().findOne(id).getFlag());
	}
	
	/**
	 * 母羊缺陷名称校验
	 *		校验母羊缺陷名称是否存在,如果存在返回101否则返回100 
	 * @param name
	 * 			母羊名称
	 * @return Message
	 * */
	@RequestMapping(value ="nameVerify")
	public boolean nameVerify(String name){
		return fakeTypeDamService.getRepository().findByName(name)==null;
	}
	
	
	
	
}
