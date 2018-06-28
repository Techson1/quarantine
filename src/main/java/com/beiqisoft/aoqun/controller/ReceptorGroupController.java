package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.ReceptorGroup;
import com.beiqisoft.aoqun.service.ReceptorGroupService;

/**
 * 受体组群访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "receptorGroup")
public class ReceptorGroupController extends BaseController<ReceptorGroup,ReceptorGroupService> {
	@RequestMapping(value ="list")
    public Page<ReceptorGroup> list(ReceptorGroup receptorGroup) throws InterruptedException{
		return receptorGroupService.find(receptorGroup);
    }
	
	/**
	 * 受体组群校验及添加
	 * */
	@RequestMapping(value ="addVerifysTo")
	public List<Message> addVerifysTo(String[] codes,Long projectId,String recorder){
		List<Message> list=new ArrayList<Message>();
		for (String code:codes){
			Message m=receptorGroupService.addVerify(code,projectId);
			if (m.isCodeEqNormal()){
				receptorGroupService.add(code,projectId,recorder);
			}
			else{
				list.add(m);
			}
		}
		return list;
	}
	
	
	/**
	 * 受体组群校验
	 * */
	@RequestMapping(value ="appAddVerify")
	public Message appAddVerify(String codes,Long projectId){
		return receptorGroupService.addVerify(codes,projectId);
	}
	
	/**
	 * 受体组群添加
	 * */
	@RequestMapping(value="appAdd")
	public ReceptorGroup appAdd(String codes,Long projectId,String recorder){
	    return receptorGroupService.add(codes,projectId,recorder);
	}
	
	/**
	 * 受体组群校验
	 * */
	@RequestMapping(value ="addVerifys")
	public List<Message> addVerifys(String[] codes,Long projectId){
		List<Message> list=new ArrayList<Message>();
		for (String code:codes){
			list.add(receptorGroupService.addVerify(code,projectId));
		}
		return list;
	}
	
	/**
	 * 受体组群添加
	 * */
	@RequestMapping(value="adds")
	public Message adds(String[] codes,Long projectId,String recorder){
		for (String code:codes){
			receptorGroupService.add(code,projectId,recorder);
		}
		return SUCCESS;
	}
	
	/**
	 * 羊只复合修改
	 * */
	@RequestMapping(value="flagUpdate/{ids}/{flag}")
	public Message flagUpdate(@PathVariable String ids,@PathVariable String flag){
		for (String id:ids.split(",")){
			receptorGroupService.updateFlag(Long.parseLong(id),flag);
		}
		return SUCCESS;
	}
	
	/**
	 * 受体组群删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return receptorGroupService.delVerify(id);
	}
	
	/**
	 * 受体组群删除
	 * */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return receptorGroupService.delete(id);
	}
	
}
