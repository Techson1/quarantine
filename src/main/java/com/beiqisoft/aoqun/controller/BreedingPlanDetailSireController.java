package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.BreedingPlan;
import com.beiqisoft.aoqun.entity.BreedingPlanDetailSire;
import com.beiqisoft.aoqun.service.BreedingPlanDetailSireService;

/**
 * 公羊选配明细访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "breedingPlanDetailSire")
public class BreedingPlanDetailSireController extends BaseController<BreedingPlanDetailSire,BreedingPlanDetailSireService> {
	
	@RequestMapping(value ="list")
    public Page<BreedingPlanDetailSire> list(BreedingPlanDetailSire breedingPlanDetailSire) throws InterruptedException{
		return breedingPlanDetailSireService.find(breedingPlanDetailSire);
    }
	
	/**
	 * 添加公羊明细
	 * @param breedingPlan.id
	 * 			选配方案
	 * @param code
	 * 			耳标号
	 * @return Message
	 * 			返回
	 * */
	@RequestMapping(value ="saves")
	public Message saves(BreedingPlanDetailSire breedingPlanDetailSire,String code){
		breedingPlanDetailSireService.getRepository().findByBreedingPlan_idAndSireType(
				breedingPlanDetailSire.getBreedingPlan().getId(),breedingPlanDetailSire.getSireType());
		breedingPlanDetailSire.setSireReturnThis(baseInfoService.getRepository().findByCode(code));
		breedingPlanDetailSireService.getRepository().save(breedingPlanDetailSire);
		return SUCCESS;
	}
	
	/**
	 * 公羊校验
	 * */
	@RequestMapping(value="verify")
	public Message verify(String code,Long breedingPlanId){
		BaseInfo sire=baseInfoService.getRepository().findByCode(code);
		if(sire==null){
			return new Message(ABNORMAL,"没有耳号为:"+code+",这只羊");
		}
		if (!SystemM.PUBLIC_SEX_SIRE.equals(sire.getSex())){
			return new Message(ABNORMAL,"耳号为:"+code+",是只母羊");
		}
		if (breedingPlanDetailSireService.getRepository().findBySire_idAndBreedingPlan_id(sire.getId(),breedingPlanId)!=null){
			return GlobalConfig.setAbnormal("耳号为:"+code+",已存在不能添加");
		}
		BreedingPlan breedingPlan = breedingPlanService.getRepository().findOne(breedingPlanId);
		if (breedingPlan==null){
			return GlobalConfig.setAbnormal("前端数据传输错误");
		}
		if (!sire.getBreed().equals(breedingPlan.getSireBreed())){
			return GlobalConfig.setAbnormal("该羊品种与选配品种不匹配,不能添加");
		}
		if (!sire.getOrg().equals(breedingPlan.getOrg())){
			return  GlobalConfig.setAbnormal("不是本场羊只");
		}
		return baseInfoService.flagVerify(sire);
	}
	
	/**
	 * 删除公羊
	 **/
	@RequestMapping(value="deleteSire")
	public Message deleteSire(String sireCode,Long breedingPlanId){
		BaseInfo sire=baseInfoService.findByCodeOrRfid(sireCode);
		if (sire==null) return SUCCESS;
		breedingPlanDetailSireService.getRepository().delete(
				breedingPlanDetailSireService.getRepository()
						.findBySire_idAndBreedingPlan_id(sire.getId(), breedingPlanId));
		return SUCCESS;
	}
}
