package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.service.DeathDisposalReasonService;

/**
 * 疾病原因访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "deathDisposalReason")
public class DeathDisposalReasonController extends BaseController<DeathDisposalReason,DeathDisposalReasonService> {
	@RequestMapping(value ="list")
    public Page<DeathDisposalReason> list(DeathDisposalReason deathDisposalReason) throws InterruptedException{
		return deathDisposalReasonService.find(deathDisposalReason);
    }
	
	/**
	 * 根据parentId查询子列表
	 * 
	 * @deprecated
	 * 
	 * @param parentId
	 * 			父id
	 * @return List<DeathDisposalReason>
	 * */
	@RequestMapping(value ="parentIdList")
	public List<DeathDisposalReason> parentIdList(Long parentId){
		return deathDisposalReasonService.getRepository().findByParent_id(parentId);
	}
	
	/**
	 * 疾病名称校验
	 * @param name
	 * 			疾病名称
	 * @return boolean 
	 * */
	@RequestMapping(value ="nameVerify")
	public boolean nameVerify(String name){
		return deathDisposalReasonService.getRepository().findByName(name)==null;
	}
	
	/**
	 * 淘汰名称校验
	 * @param name
	 * 			淘汰名称
	 * @return boolean 
	 * */
	@RequestMapping(value ="weedOutNameVerify")
	public boolean weedOutNameVerify(String name){
		return deathDisposalReasonService.getRepository().findByNameAndType(name, SystemM.DEATH_WEED_OUT)==null;
	}
	
	/**
	 * 育种名称校验
	 * @param name
	 * 			育种名称
	 * @return boolean 
	 * */
	@RequestMapping(value ="breedingNameVerify")
	public boolean BreedingNameVerify(String name){
		return deathDisposalReasonService.getRepository().findByNameAndType(name, SystemM.DEATH_BREEDING)==null;
	}
	
	/**
	 * 死亡名称校验
	 * @param name
	 * 			死亡名称
	 * @return boolean 
	 * */
	@RequestMapping(value ="deathNameVerify")
	public boolean DeathNameVerify(String name){
		return deathDisposalReasonService.getRepository().findByNameAndType(name, SystemM.DEATH)==null;
	}
	

	/**
	 * 查找疾病类型父列表
	 * 
	 * * @param type
	 * 			疾病类型
	 * 				淘汰:2
	 * 				育种:3
	 * 				死亡:4V@PathVariable
	 * @return List<DeathDisposalReason>
	 * */
	@RequestMapping(value ="parentList/{type}")
	public List<DeathDisposalReason> parentList(@PathVariable String type){
		return deathDisposalReasonService.getRepository().findByParentIsNullAndType(type);
	}
	
	/**
	 * 查找疾病类型可用父列表
	 * @return List<DeathDisposalReason>
	 * */
	@RequestMapping(value ="parentFlagList/{type}")
	public List<DeathDisposalReason> parentFlagList(@PathVariable String type){
		return deathDisposalReasonService.getRepository().findByParentIsNullAndFlagAndType(SystemM.PUBLIC_TRUE,type);
	}
	
	/**
	 * 查找疾病类型可用父列表
	 * @return Page<DeathDisposalReason>
	 * */
	@RequestMapping(value ="parentFlagListPage/{type}/{page}")
	public Page<DeathDisposalReason> parentFlagListPage(@PathVariable String type,@PathVariable Integer page){
		return deathDisposalReasonService.getRepository().findByParentIsNullAndFlagAndType(SystemM.PUBLIC_TRUE,type,pageable(page, "ctime"));
	}
	
	/**
	 * 父列表查询
	 * */
	@RequestMapping(value= "parentListPage")
	public Page<DeathDisposalReason> parentListPage(String type,Integer page){
		return deathDisposalReasonService.getRepository().findByParentIsNullAndType(type, pageable(page, "flag"));
	}
	
	/**
	 * 修改疾病原因是否可用
	 * @param flag
	 * 			是否可用
	 * 				可用:1
	 * 				存档:0
	 * @return Message
	 * */
	@RequestMapping(value ="flagUp/{id}/{flag}")
	public Message flagUp(@PathVariable Long id,@PathVariable String flag){
		deathDisposalReasonService.getRepository().save(
				deathDisposalReasonService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
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
		return SystemM.PUBLIC_TRUE.equals(deathDisposalReasonService.getRepository().findOne(id).getFlag());
	}
	
	/**
	 * 修改数据
	 * 
	 * * @param type
	 * 			疾病类型
	 * 				淘汰:2
	 * 				育种:3
	 * 				死亡:4
	 * @return List<DeathDisposalReason>
	 * */
	@RequestMapping(value ="updateTable")
	public Message updateTable(){
		List<DeathDisposalReason> list=deathDisposalReasonService.getRepository().findByAndFlag("-1");
		for (DeathDisposalReason d:list){
			d.setFlag("0");
			deathDisposalReasonService.getRepository().save(d);
		}
		return SUCCESS;
	}
}
