package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.FrozenStore;
import com.beiqisoft.aoqun.service.FrozenStoreService;

/**
 * 存储罐号控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "frozenStore")
public class FrozenStoreController extends BaseController<FrozenStore,FrozenStoreService> {
	
	@RequestMapping(value ="list")
    public Page<FrozenStore> list(FrozenStore frozenStore) throws InterruptedException{
		return frozenStoreService.find(frozenStore);
    }
	
	/**
	 * 存储罐号是否可用
	 * @param id
	 * 			存储罐号 id
	 * @param flag
	 * 			是否可用
	 * */
	@RequestMapping(value="isFlag/{id}/{flag}")
	public Message isFlag(@PathVariable Long id,@PathVariable String flag){
		frozenStoreService.getRepository().save(
				frozenStoreService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	
	/**查询可用罐号*/
	@RequestMapping(value="findBy/{orgId}")
	public List<FrozenStore> findBy(@PathVariable Long orgId){
		return frozenStoreService.getRepository().findByFlagAndOrg_id(SystemM.PUBLIC_TRUE,orgId);
	}
	
	/**
	 * 修改前置校验
	 * @param id
	 * 			罐号id
	 * @return boolean
	 * 			flag为1是返回true,否则返回false
	 * */
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(frozenStoreService.getRepository().findOne(id).getFlag());
	}
	
	/**
	 * 罐号名称重复校验
	 * @param name
	 * 			罐号名称
	 * */
	@RequestMapping(value="nameVerify")
	public boolean nameVerify(String name){
		return frozenStoreService.getRepository().findByName(name)==null;
	}
}
