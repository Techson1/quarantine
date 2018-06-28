package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.service.EmbryoProjectService;

/**
 * 胚移项目访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "embryoProject")
public class EmbryoProjectController extends BaseController<EmbryoProject,EmbryoProjectService> {
	@RequestMapping(value ="list")
    public Page<EmbryoProject> list(EmbryoProject embryoProject) throws InterruptedException{
		embryoProject.setStatus(null);
		return embryoProjectService.find(embryoProject);
    }
	
	/**
	 * 方案名称重复校验
	 * */
	@RequestMapping(value="projectNameVerify")
	public boolean nameVerify(String projectName){
		return embryoProjectService.getRepository().findByProjectName(projectName)==null;
	}
	
	/**
	 * 修改项目状态
	 * */
	@RequestMapping(value="accomplish")
	public Message accomplish(Long id,String Status){
		 embryoProjectService.getRepository().save(
				 embryoProjectService.getRepository().findOne(id).setStatusReturnThis(Status));
		 return SUCCESS;
	}
	
	/**
	 * app查询
	 * */
	@RequestMapping(value="appList")
	public List<EmbryoProject> appList(){
		return embryoProjectService.getRepository().findByStatus(SystemM.PUBLIC_TRUE);
	}
}
