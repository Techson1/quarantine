package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseGroupDetail;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.service.BaseGroupDetailService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "baseGroupDetail")
public class BaseGroupDetailController extends BaseController<BaseGroupDetail,BaseGroupDetailService> {
	
	@JSON(type=BaseInfo.class,include="code,sex,breed,birthDay,moonAge,flag,org,physiologyStatus")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=BaseGroupDetail.class,include="id,base,recorder,ctime")
	@RequestMapping(value ="list")
    public Page<BaseGroupDetail> list(BaseGroupDetail baseGroupDetail) throws InterruptedException{
		return page.pageAcquire(baseGroupDetailService.find(baseGroupDetail)).iteration(x->{
			x.getBase().setMoonAge(DateUtils.dateToAge(x.getBase().getBirthDay()));
		});
    }
	
	@JSON(type=BaseInfo.class,include="code,sex,breed,birthDay,org")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=BaseGroupDetail.class,include="id,base,recorder,ctime")
	@RequestMapping(value ="findByList")
    public List<BaseGroupDetail> findByList(BaseGroupDetail baseGroupDetail) throws InterruptedException{
		return baseGroupDetailService.findByList(baseGroupDetail);
    }
	
	@RequestMapping(value="addVerify")
	public Message addVerify(String code,Long orgId){
		Message message = baseInfoService.flagVerify(code);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (baseInfoService.findByCodeOrRfid(code).getOrg().getId()!=orgId){
			return GlobalConfig.setAbnormal("该羊只不存在,不能添加");
		}
		return SUCCESS;
	}
	
	@RequestMapping(value="add")
	public Message add(BaseGroupDetail baseGroupDetail,String code,Long orgId){
		Message message = baseInfoService.flagVerify(code);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if(baseInfoService.findByCodeOrRfid(code)==null) {
			return new Message(101,"该羊不存在");
		}
		
		if (baseInfoService.findByCodeOrRfid(code).getOrg().getId()!=orgId){
			return GlobalConfig.setAbnormal("该羊不在本场,不能添加");
		}
		if (baseInfoService.findByCodeOrRfid(code).getPhysiologyStatus()!=1L){
			return GlobalConfig.setAbnormal("该羊不在库,不能添加");
		}
		if (baseGroupDetailService.getRepository()
				.findByBase_codeAndBaseGroup_id(code,baseGroupDetail.getBaseGroup().getId())!=null){
			return GlobalConfig.setAbnormal("该羊只已在群组中不能,不能添加");
		}
		baseGroupDetail.setBase(baseInfoService.findByCodeOrRfid(code));
		baseGroupDetailService.getRepository().save(baseGroupDetail);
		return SUCCESS;
	}
	
	@RequestMapping(value="appAddVerify")
	public Message appAddVerify(BaseGroupDetail baseGroupDetail,String code,Long orgId){
		Message message = baseInfoService.flagVerify(code);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (baseInfoService.findByCodeOrRfid(code).getOrg().getId()!=orgId){
			return GlobalConfig.setAbnormal("该羊只不存在,不能添加");
		}
		if (baseGroupDetailService.getRepository()
				.findByBase_codeAndBaseGroup_id(code,baseGroupDetail.getBaseGroup().getId())!=null){
			return GlobalConfig.setAbnormal("该羊只已在群组中,不能添加");
		}
		if (baseGroupDetailService.getRepository()
				.findByBase_rfidAndBaseGroup_id(code,baseGroupDetail.getBaseGroup().getId())!=null){
			return GlobalConfig.setAbnormal("该羊只已在群组中,不能添加");
		}
		return SUCCESS;
	}
	
	@RequestMapping(value="appAdd")
	public BaseGroupDetail appAdd(BaseGroupDetail baseGroupDetail,String code,Long orgId){
		baseGroupDetail.setBase(baseInfoService.findByCodeOrRfid(code));
		baseGroupDetailService.getRepository().save(baseGroupDetail);
		return baseGroupDetail;
	}
}
