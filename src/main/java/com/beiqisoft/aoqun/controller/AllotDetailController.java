package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.AllotDetail;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.AllotDetailService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "allotDetail")
public class AllotDetailController extends BaseController<AllotDetail,AllotDetailService> {
	
	@JSON(type=BaseInfo.class,include="code,rfid,paddock,breed,birthDay,sex")
	@JSON(type=Organization.class,include="orgName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<AllotDetail> list(AllotDetail allotDetail) throws InterruptedException{
		return page.pageAcquire(allotDetailService.find(allotDetail)).iteration(x->{
			x.setMoonAge(DateUtils.dateToAge(x.getCtime(), x.getBase().getBirthDay()));
		});
    }
	
	/**
	 * 调拨明细添加校验
	 * @param code
	 * 			耳号
	 * @param orgId
	 * 			分厂id
	 */
	@RequestMapping(value="addVerify")
	public Message addVerify(String code,Long orgId,Long allotId){
		return allotDetailService.addVerify(code,orgId,allotId);
	}
	
	/**
	 * 调拨明细添加
	 * @param code
	 * 			耳号
	 * @param orgId
	 * 			分厂id
	 * */
	@RequestMapping(value="add")
	public Message add(String code,Long orgId,Long allotId,String recorder){ 
		allotDetailService.add(code, orgId,allotId,recorder);
		return SUCCESS;
	}
	
	/**
	 * 调拨明细添加
	 * @param code
	 * 			耳号
	 * @param orgId
	 * 			分厂id
	 * */
	@JSON(type=BaseInfo.class,include="code,sex,breed")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief,orgName")
	@RequestMapping(value="appAdd")
	public AllotDetail appAdd(String code,Long orgId,Long allotId,String recorder){ 
		return allotDetailService.add(code, orgId,allotId,recorder);
	}
	
	/**
	 * 复合校验
	 * */
	@RequestMapping(value="appAuditVerify")
	public Message appAuditVerify(String code,Long allotId){
		BaseInfo base = baseInfoService.findByCodeOrRfid(code);
		if(base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (allotDetailService.getRepository().findByAllot_idAndBase_id(allotId,base.getId())==null){
			return GlobalConfig.setAbnormal("羊只不在该调拨明细下");
		}
		if (allotDetailService.getRepository().findByAllot_idAndBase_id(allotId,base.getId()).getFlag().equals("1")){
			return GlobalConfig.setAbnormal("羊只已复核");
		}
		return SUCCESS;
	}
	
	/**
	 * app调拨复合
	 * @param code
	 * @param flag
	 * @param orgId
	 * @param allotId
	 * @param paddockId
	 * @return
	 */
	@JSON(type=BaseInfo.class,include="code,sex,breed")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief,orgName")
	@RequestMapping(value="appAudit")
	public AllotDetail appAudit(String code,String flag,Long orgId,Long allotId,Long paddockId,String operator){
		BaseInfo base = baseInfoService.findByCodeOrRfid(code);
		return allotDetailService.audit(flag,orgId,paddockId,operator,
				allotDetailService.getRepository().findByAllot_idAndBase_id(allotId,base.getId()));
	}
	
	/**
	 * 调拨复合
	 * @param ids
	 * @param flag
	 * @param orgId
	 * @param paddockId
	 * @return
	 */
	@RequestMapping(value="audit")
	public Message audit(Long[] ids,String flag,Long orgId,Long paddockId,String operator){
		for (Long id:ids){
			allotDetailService.audit(flag,orgId,paddockId,operator,allotDetailService.getRepository().findOne(id));
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return allotDetailService.delete(id);
	}

}
