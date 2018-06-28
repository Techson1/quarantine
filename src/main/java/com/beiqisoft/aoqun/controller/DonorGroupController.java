package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.DonorGroup;
import com.beiqisoft.aoqun.service.DonorGroupService;

/**
 * 供体组群访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "donorGroup")
public class DonorGroupController extends BaseController<DonorGroup,DonorGroupService> {
	@RequestMapping(value ="list")
    public Page<DonorGroup> list(DonorGroup donorGroup) throws InterruptedException{
		return donorGroupService.find(donorGroup);
    }
	
	@RequestMapping(value ="addVerifysTo")
	public List<Message> addVerifysTo(String[] codes,Long projectId,String recorder){
		List<Message> list=new ArrayList<Message>();
		for (String code:codes){
			Message m=donorGroupService.addVerify(code,projectId);
			if (m.isCodeEqNormal()){
				donorGroupService.add(code,projectId,recorder);
			}
			else{
				list.add(m);
			}
		}
		return list;
	}
	
	/**
	 * app添加校验
	 * */
	@RequestMapping(value="appAddVerify")
	public Message appAddVerify(String codes,Long projectId){
		return donorGroupService.addVerify(codes,projectId);
	}
	
	/**
	 * app添加
	 * */
	@RequestMapping(value="appAdd")
	public DonorGroup appAdd(String codes,Long projectId,String recorder){
		return donorGroupService.add(codes,projectId,recorder);
	}
	
	/**
	 * 供体组群校验
	 * */
	@RequestMapping(value ="addVerifys")
	public List<Message> addVerifys(String[] codes,Long projectId){
		List<Message> list=new ArrayList<Message>();
		Arrays.asList(codes).stream().distinct().forEach(x -> list.add(donorGroupService.addVerify(x,projectId)));
		return list;
	}
	
	/**
	 * 供体组群添加
	 * */
	@RequestMapping(value="adds")
	public Message adds(String[] codes,Long projectId,String recorder){
		for (String code:codes){
			donorGroupService.add(code,projectId,recorder);
		}
		return SUCCESS;
	}
	
	/**
	 * 羊只复合修改
	 * */
	@RequestMapping(value="flagUpdate/{ids}/{flag}")
	public Message flagUpdate(@PathVariable String ids,@PathVariable String flag){
		for (String id:ids.split(",")){
			//donorGroupService.getRepository().save(donorGroupService.getRepository().findOne(Long.parseLong(id)).setFlagRetuanThis(flag));
			donorGroupService.updateFlag(Long.parseLong(id),flag);
		}
		return SUCCESS;
	}
	
	/**
	 * 供体组群删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return donorGroupService.delVerify(id);
	}
	
	/**
	 * 供体组群删除
	 * */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return donorGroupService.delete(id);
	}
}
