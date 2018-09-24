package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.service.PregnancyService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 测孕访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "pregnancy")
public class PregnancyController extends BaseController<Pregnancy,PregnancyService> {
	@JSON(type=BaseInfo.class,include="code,paddock,physiologyStatus")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@JSON(type=Parity.class,include="id,parityMaxNumber,parityType")
	@RequestMapping(value ="list")
    public Page<Pregnancy> list(Pregnancy pregnancy) throws InterruptedException{
		//TODO 会产生效率问题,可以尝试采用ladbma并行流解决
		return page.pageAcquire(pregnancyService.find(pregnancy)).iteration(x -> {
			if (abortionService.getRepository().findByParity_id(x.getParity().getId())!=null){
				x.setIsAbortion(SystemM.PUBLIC_TRUE);
			}
//			LambingDam lambingDam=lambingDamService.getRepository().findByParity_id(x.getParity().getId());
//			if (lambingDam!=null){
//				x.setLambingCount(lambingDam.getBornTimes());
//			}
		});
    }
	
	/**
	 * 测孕批量校验
	 * 		app端可用
	 * */
	@RequestMapping(value ="appVerify")
	public Message appVerify(String damCodes,Date pregnancyDate){
		return pregnancyService.verify(damCodes,baseInfoService.findByCodeOrRfid(damCodes),pregnancyDate);
	}

	/**
	 * 测孕批量校验
	 * 		web端与app端公用
	 * */
	@RequestMapping(value ="verifys")
	public List<Message> verifys(String[] damCodes,Date pregnancyDate){
		List<Message> list=new ArrayList<Message>();
		for (String code:damCodes){
			list.add(pregnancyService.verify(code,baseInfoService.findByCodeOrRfid(code),pregnancyDate));
		}
		return list;
	}
	
	/**
	 * 测孕批量校验
	 * 		web端与app端公用
	 * */
	@RequestMapping(value ="verify")
	public Message verify(String damCode){
		return pregnancyService.verify(damCode,baseInfoService.findByCodeOrRfid(damCode));
	}
	
	/**
	 * 
	 * */
	@RequestMapping(value="appAdd")
	public Pregnancy appAdd(String damCodes,Pregnancy pregnancy){
		return pregnancyService.add(baseInfoService.findByCodeOrRfid(damCodes),new Pregnancy(pregnancy));
	}
	
	/**
	 * 测孕记录批量添加
	 * */
	@RequestMapping(value="adds")
	public Message adds(String [] damCodes,Pregnancy pregnancy){
		for (String code:damCodes){
			pregnancyService.add(baseInfoService.findByCodeOrRfid(code),new Pregnancy(pregnancy));
		}
		return SUCCESS;
	}
	
	/**
	 * 测孕页面修改校验
	 * */
	@RequestMapping(value="updateUiVerify/{id}")
	public Message updateUiVerify(@PathVariable Long id){
		return pregnancyService.editUiVerify(pregnancyService.getRepository().findOne(id));
	}
	
	/**
	 * 测孕修改校验
	 * @param parity.id
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(Long id, Date pregnancyDate){
		Joining joining=joiningService.getRepository().findByIsNewestJoiningAndParity_id(
				SystemM.PUBLIC_TRUE, pregnancyService.getRepository().findOne(id).getParity().getId());
		if (DateUtils.dateSubDate(joining.getJoiningDate(), pregnancyDate)>=0){
			return new Message(ABNORMAL,"测孕日期不能小于配种日期,该羊的配种日期为:"+DateUtils.DateToStr(joining.getJoiningDate()));
		}
		return SUCCESS;
	}
	
	/**
	 * 测孕修改
	 * */
	@RequestMapping(value="update")
	public Message update(Pregnancy pregnancy){
		Pregnancy editPregnancy=pregnancyService.getRepository().findOne(pregnancy.getId());
		editPregnancy.setPredict(
				breedParameterService.getRepository().findByName(SystemM.BREED_PRODUCTION).getParameter());
		//转圈
		if (pregnancy.getToPaddock()!=null && pregnancy.getToPaddock().getId()!=null){
			paddockChangeService.add(pregnancy.getToPaddock(), editPregnancy.getDam().getCode(),
					pregnancy.getOrg(),pregnancy.getRecorder());
		}
		else{
			pregnancy.setToPaddock(null);
		}
		pregnancy.setCtime(new Date());
		pregnancyService.getRepository().save(editPregnancy.editReturnThis(pregnancy));
		Parity parity=editPregnancy.getParity();
		Joining joining=joiningService.getRepository().findByParity_idAndJoiningSeq(parity.getId(), editPregnancy.getPregnancySeq());
		parity.setPostCross(DateUtils.dateSubDate(joining.getJoiningDate(), pregnancy.getPregnancyDate()));
		//修改羊只繁殖状态
		baseInfoService.getRepository().save(editPregnancy.getDam()
				.setBreedingStateReutrnThis(editPregnancy.returnBreedingState()));
		joiningService.getRepository().save(joining.setResultReturnThis(pregnancy.getResult()));
		return SUCCESS;
	}
	
	/**
	 * 测孕删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return pregnancyService.delVerify(id);
	}
}
