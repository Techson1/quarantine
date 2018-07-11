package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailDam;
import com.beiqisoft.aoqun.service.BreedingPlanDetailDamService;

/**
 * 母羊选配明细访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "breedingPlanDetailDam")
public class BreedingPlanDetailDamController extends BaseController<BreedingPlanDetailDam,BreedingPlanDetailDamService> {
	@RequestMapping(value ="list")
    public Page<BreedingPlanDetailDam> list(BreedingPlanDetailDam breedingPlanDetailDam) throws InterruptedException{
		return breedingPlanDetailDamService.find(breedingPlanDetailDam);
    }
	
	/**
	 * 根据选配方案查找选配明细
	 * */
	@RequestMapping(value ="findByBreedingPlan")
	public List<BreedingPlanDetailDam> list(Long breedingPlanId){
		return breedingPlanDetailDamService.getRepository().findByBreedingPlan_id(breedingPlanId);
	}
	
	/**
	 * 批量添加母羊明细
	 * @param breedingPlan.id
	 * 			选配方案
	 * @param ids
	 * 			羊只id集
	 * @return Message
	 * 			返回 
	 * */
	@RequestMapping(value ="saves")
	public Message saves(BreedingPlanDetailDam breedingPlanDetailDam,String[] codes){
		return breedingPlanDetailDamService.saves(breedingPlanDetailDam, codes);
	}
	
	/**
	 * 母羊耳号添加校验
	 * @param codes
	 * 			母羊
	 * */
	@RequestMapping(value ="verify")
	public List<Message> verify(String[] codes,BreedingPlanDetailDam breedingPlanDetailDam){
		List<Message> messages=new ArrayList<Message>();
		for (String s:codes){
			//BaseInfo baseInfo=baseInfoService.getRepository().findByCode(s);
			//修改为可视耳号和电子耳号都可以使用
			BaseInfo baseInfo=baseInfoService.getRepository().findByCodeOrRfid(s,s);
			if (baseInfo==null){
				messages.add(new Message(ABNORMAL,s+":耳标不存在"));
				continue;
			}
			if (!SystemM.PUBLIC_SEX_DAM.equals(baseInfo.getSex())){
				messages.add(new Message(ABNORMAL,s+":性别错误"));
				continue;
			}
			if (!breedingPlanDetailDamService.getRepository()
					.findByDam_idAndBreedingPlan_flag(baseInfo.getId(),SystemM.PUBLIC_TRUE)
					.isEmpty()){
				messages.add(GlobalConfig.setAbnormal(s+":该羊只已在其他选配方案中出现,不能添加"));
			}
			BreedingPlan breedingPlan=breedingPlanService.getRepository()
					.findOne(breedingPlanDetailDam.getBreedingPlan().getId());
			if(!breedingPlan.getDamBreed().equals(baseInfo.getBreed())){
				messages.add(GlobalConfig.setAbnormal(s+":该羊品种错误"));
			}
			if (!SystemM.NORMAL.equals(baseInfo.getPhysiologyStatus()+"")){
				messages.add(GlobalConfig.setAbnormal(s+":该羊已出库"));
			}
			if (!baseInfo.getOrg().equals(breedingPlan.getOrg())){
				messages.add(GlobalConfig.setAbnormal(s+":不是本场羊只"));
			}
			messages.add(SUCCESS);
		}
		return messages;
	}
}
