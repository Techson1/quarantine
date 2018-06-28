package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.GroupType;
import com.beiqisoft.aoqun.service.GroupTypeService;

/**
 * 定级定价访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@RestController
@RequestMapping(value = "groupType")
public class GroupTypeController extends BaseController<GroupType,GroupTypeService> {
	@RequestMapping(value ="list")
    public Page<GroupType> list(GroupType groupType) throws InterruptedException{
		return groupTypeService.find(groupType);
    }
	
	/**
	 * 定级定价多表连接查询
	 * @param breedId
	 * 			品种id
	 * @param sex
	 * 			性别
	 * @reutrn Page<GroupTYpe>
	 * */
	@RequestMapping(value ="groupTypelist")
	public Page<GroupType> groupTypelist(String breedId,String sex){
		
		
		return null;
	}
	
	/**
	 * 标志校验
	 * 		标志校验,如果用途,品种,性别,标志相同则返回101
	 * @param purpose
	 * 			用途
	 * @param sex
	 * 			性别
	 * @param breed.id
	 * 			品种
	 * @param sign
	 * 			标志
	 * @return Message
	 * */
	@RequestMapping(value ="signVerify")
	public boolean signVerify(GroupType groupType){
		return groupTypeService.SignVerify(groupType);
	}
	
	
}
