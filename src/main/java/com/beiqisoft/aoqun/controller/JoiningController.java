package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.service.JoiningService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 配种访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "joining")
public class JoiningController extends BaseController<Joining,JoiningService> {
	@JSON(type=BaseInfo.class,include="breed,code,paddock")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Parity.class,include="id,parityMaxNumber")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="list")
    public Page<Joining> list(Joining joining) throws InterruptedException{
		return joiningService.find(joining);
    }

	/**
	 * 配种羊只校验测试类
	 *  一、判断羊只是否存在
	 *  二、判断该羊性别
	 *  三、判断羊只当前状态
	 *  四、判断配种次数是不是10配
	 *  五、当前配种时间必须大于上一次配种时间,并且要小于等于当前时间
	 *  六、如果配种不在选配方案中、提示用户（todo）
	 *  七、判断羊只系谱关系(一代父母，兄妹)
	 *  八、判断是否在选配方案内
	 * */
	@RequestMapping(value ="verify")
	public Message verify(String damCode,Date joiningDate,String sireCode,Long orgId){
		//Long orgId = currentUser().getOrganization().getId();
		BaseInfo dam=baseInfoService.findByCodeOrRfid(damCode);
		//羊只校验
		Message damMessage= joiningService.joiningDamVerify(dam,damCode,orgId);
		if (damMessage.getCode()!=100){
			return damMessage;
		}
		damMessage = baseInfoService.flagVerify(damCode);
		if (damMessage.getCode()!=100){
			return damMessage;
		}
		//配种校验
		Message joiningMessage=joiningService.joiningVerify(dam,joiningDate);
		if (joiningMessage.getCode()!=100){
			return joiningMessage;
		}
		//血统判断
		Message bloodMessage=joiningService.joiningBloodVerify(dam, baseInfoService.findByCodeOrRfid(sireCode));
		if (bloodMessage.getCode()!=100){
			return bloodMessage;
		}
		//选配方案判断
		BaseInfo sire= baseInfoService.findByCodeOrRfid(sireCode);
		if (joiningService.isBreedingPlan(dam,sire)==null) return GlobalConfig.setIsPass(dam.getCode()+":该羊不在选配方案内,是否添加");
		return SUCCESS;
 	}
	
	/**公羊校验*/
	@RequestMapping(value ="sireVerify")
	public Message sireVerify(String sireCode){
		Long orgId = currentUser().getOrganization().getId();
		//公羊校验
		Message sireMessage = joiningService.joiningSireVerify(sireCode,orgId);
		if (sireMessage.getCode()!=100){
			return sireMessage;
		}
		sireMessage = baseInfoService.flagVerify(sireCode);
		if (sireMessage.getCode()!=100){
			return sireMessage;
		}
		return SUCCESS;
	}
	
	/**
	 * 配种批量校验
	 * 		web端与app端公用
	 * */
	@RequestMapping(value ="verifys")
	public List<Message> verifys(String[] damCodes,Date joiningDate,String sireCode,Long orgId){
		List<Message> messageList=new ArrayList<Message>();
		for (String damCode:Arrays.asList(damCodes).stream().distinct().collect(Collectors.toList())){
			messageList.add(verify(damCode,joiningDate,sireCode,orgId));
		}
		messageList.sort((h1, h2) -> h2.getCode().compareTo(h1.getCode()));
		return messageList;
	}
	
	/**
	 * app端配种添加
	 * */
	@JSON(type=Joining.class,filter="parity,org,project,breedingPlan,childBreed,project")
	@JSON(type=BaseInfo.class,include="code")
	@Transactional
	@RequestMapping(value="appAdd")
	public Joining appAdd(Joining joining,String damCodes,String sireCode){
		return joiningService.add(new Joining(joining) ,damCodes,sireCode);
	}
	
	/**
	 * 配种添加
	 * 	一、配种修改
	 * 	二、修改胎次
	 * 	三、修改羊只
	 * 	四、消息推送
	 * */
	@Transactional
	@RequestMapping(value="adds")
	public Message adds(Joining joining,String[] damCodes,String sireCode){
		//java8 stream API 集合去重   
		for (String damCode:Arrays.asList(damCodes).stream().distinct().collect(Collectors.toList())){
			joiningService.add(new Joining(joining),damCode,sireCode);
		}
		return SUCCESS;
	}
	
	/**
	 * 配种修改前校验
	 * @param id
	 * 			配种id
	 * @return Message
	 * */
	@RequestMapping(value="updateUiVerify/{id}")
	public Message updateUiVerify(@PathVariable Long id){
		return joiningService.editUiVerify(joiningService.getRepository().findOne(id));
	}
	
	/**
	 * 配种修改校验
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(Joining joining,String sireCode){
		return joiningService.editVerify(joining,baseInfoService.findByCodeOrRfid(sireCode));
	}
	
	/**
	 * 配种删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return joiningService.delVerify(id);
	}
	
	/**
	 * 修改
	 * */
	@RequestMapping(value="update")
	public Message edit(Long id,String sireCode,String sexStatus,String joiningType,Date joiningDate,String recorder,Long paddockId){
		Joining joining=joiningService.getRepository().findOne(id);
		joiningService.getRepository().save(joining.setUpdate(baseInfoService
				.findByCodeOrRfid(sireCode),sexStatus,joiningType,joiningDate,recorder));
		if ("1".equals(joining.getJoiningSeq())){
			Parity parity=joining.getParity();
			parity.setNonpregnancy(DateUtils.dateSubDate(joining.getJoiningDate(), parity.getStartDate()));
		}
		if (paddockId!=null){
			Paddock paddock = new Paddock();
			paddock.setId(paddockId);
			paddockChangeService.add(paddock, joining.getDam().getCode(), joining.getOrg(),joining.getRecorder());
		}
		return SUCCESS;
	}
	
	/**
	 * AI校验
	 * */
	@RequestMapping(value="addAiVerifys")
	public List<Message> addAiVerifys(String[] damCodes,String sireCode,Date joiningDate,Long projectId){
		List<Message> list =new ArrayList<Message>();
		//java8 stream API 集合去重
		for (String damCode:Arrays.asList(damCodes).stream().distinct().collect(Collectors.toList())){
			list.add(joiningService.addAiVerify(damCode,sireCode,joiningDate,projectId));
		}
		return list;
	}
	
	/**
	 * AI添加
	 * */
	@RequestMapping(value="addAis")
	public Message addAis (String[] damCodes,String sireCode,Date joiningDate,Long projectId){
		//java8 stream API 集合去重
		for (String damCode:Arrays.asList(damCodes).stream().distinct().collect(Collectors.toList())){
			joiningService.addAi(damCode,sireCode,joiningDate,projectId);
		}
		return SUCCESS;
	}
	
	/**
	 * AI界面修改校验
	 * */
	@RequestMapping(value="updateAiUiVerify/{id}")
	public Message updateAiUiVerify(@PathVariable Long id){
		return joiningService.updateUiAiVerify(id);
	}
	
	/**
	 * AI修改校验
	 * */
	@RequestMapping(value="updateAiVerify")
	public Message updateAiVerifys(Long id,String sireCode,Date joiningDate){
		return joiningService.updateAiVerifys(id,sireCode,joiningDate);
	}
	
	/**
	 * AI修改
	 * */
	@RequestMapping(value="updateAi")
	public Message updateAi(Long id,String sireCode,Date joiningDate){
		return joiningService.updateAi(id,sireCode,joiningDate);
	}
}