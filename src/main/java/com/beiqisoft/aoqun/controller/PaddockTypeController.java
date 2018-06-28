package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.PaddockType;
import com.beiqisoft.aoqun.service.PaddockTypeService;

/**
 * 栋栏访问控制类
 * @deprecated
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "paddockType")
public class PaddockTypeController extends BaseController<PaddockType,PaddockTypeService> {
	@RequestMapping(value ="list")
    public Page<PaddockType> list(PaddockType paddockType) throws InterruptedException{
		return paddockTypeService.find(paddockType);
    }
	
	/**
	 * 栋栏可用修改
	 * @param id
	 * 			栋栏id
	 * @param flag
	 * 			是否可用
	 * @return Message
	 * */
	@RequestMapping(value ="flagSave/{id}/{flag}")
	public Message flagSave(@PathVariable Long id,@PathVariable String flag)  {
		return paddockTypeService.saveReturnMessage(paddockTypeService.getRepository().findOne(id).setFlagReturnThis(flag));
	}
	
	/**
	 * 栋栏名称重复查询
	 * @param paddockTypeName
	 * 		 	栋栏名称
	 * @param orgId
	 * 			分厂id
	 * @return boolean
	 * 			栋栏名称存在返回false,不存在返回true
	 * */
	@RequestMapping(value ="nameVerify")
	public boolean nameVerify(String paddockTypeName,Long orgId){
		return paddockTypeService.getRepository().findByPaddockTypeNameAndOrg_id(paddockTypeName,orgId)==null;
	}
	
	/**
	 * 修改前置校验
	 * @param id
	 * 			疾病id
	 * @return boolean
	 * 			flag为1是返回true,否则返回false
	 * */
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(paddockTypeService.getRepository().findOne(id).getFlag());
	}
}
