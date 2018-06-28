package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.ImmuneHealthProject;
import com.beiqisoft.aoqun.service.ImmuneHealthProjectService;
@RestController
@RequestMapping(value = "immuneHealthProject")
public class ImmuneHealthProjectController extends BaseController<ImmuneHealthProject,ImmuneHealthProjectService> {
	@RequestMapping(value ="list")
    public Page<ImmuneHealthProject> list(ImmuneHealthProject immuneHealthProject) throws InterruptedException{
		return immuneHealthProjectService.find(immuneHealthProject);
    }
	
	@RequestMapping(value="nameVerify")
	public boolean nameVerify(String immName){
		return immuneHealthProjectService.getRepository().findByImmName(immName)==null;
	}
	
	@RequestMapping(value="flagUpdate/{id}/{flag}")
	public Message flagUpdate(@PathVariable Long id ,@PathVariable String flag){
		immuneHealthProjectService.getRepository().save(
				immuneHealthProjectService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	
	/**
	 * 校验
	 * */
	@RequestMapping(value="flagVerify/{id}")
	public boolean flagVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(immuneHealthProjectService.getRepository().findOne(id).getFlag());
	}
	
	/**
	 * 列表可用查询
	 * */
	@RequestMapping(value="findByFlag")
	public List<ImmuneHealthProject> findByFlag(){
		return immuneHealthProjectService.getRepository().findByFlag(SystemM.PUBLIC_TRUE);
	}
}
