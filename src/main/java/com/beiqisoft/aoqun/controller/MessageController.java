package com.beiqisoft.aoqun.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.entity.GeneralVeternary;
import com.beiqisoft.aoqun.entity.Hint;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.entity.domain.MessageEntity;
import com.beiqisoft.aoqun.service.JoiningService;
import com.beiqisoft.aoqun.service.MessageService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 统计报表访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "message")
public class MessageController extends BaseController<Joining,JoiningService>{
	@Autowired
	MessageService messageService;
	
	//private final int day=0;
	
	/**
	 *空怀配种提示 
	 */
	@RequestMapping(value ="joiningMessage")
	public Page<MessageEntity> joiningMessage(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.NONPREGNANT_CROSS_HINT_DAY, orgId);
		int predictDay=h!=null?h.getParameter():10;
		return listToPage(messageService.findByJoingingList(orgId,code,paddockId),pageNum,predictDay,h.getHint()!=null?h.getHint():0);
	}
	
	/**
	 *未孕配种提示 
	 */
	@RequestMapping(value="notJoiningMessage")
	public Page<MessageEntity> notJoiningMessage(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.UNPREGNANCY_CROSS_HINT_DAY, orgId);
		int predictDay=h!=null?h.getParameter():10;
		return listToPage(messageService.findByNotJoingingList(orgId,code,paddockId),pageNum,predictDay,h.getHint()!=null?h.getHint():0);
	}
	
	/**
	 * 测孕提示 
	 */
	@RequestMapping(value = "pregnancyMessage")
	public Page<MessageEntity> pregnancyMessage(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.PREGNANCY_HINT_DAY, orgId);
		int predictDay=h!=null?h.getParameter():30;
		return listToPage(messageService.findByPregnancyList(orgId,code,paddockId),pageNum,predictDay,h.getHint()!=null?h.getHint():0);
	}
	
	/**
	 * 产羔提示
	 */
	@RequestMapping(value = "lambingDamMessage")
	public Page<MessageEntity> lambingDamMessage(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.LAMBING_DAM_HINT_DAY, orgId);
		int predictDay=h!=null?h.getParameter():147;
		messageService.findByLambingDamList(orgId,code,paddockId)
		.forEach(x->{System.out.println(x.getPredictDate()+":"+x.getDay());});
		return listToPage(messageService.findByLambingDamList(orgId,code,paddockId),pageNum,predictDay,h.getHint()!=null?h.getHint():0);
	}
	
	/**
	 * 断奶提示
	 */
	@RequestMapping(value = "weaningMessage")
	public Page<MessageEntity> weaningMessage(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.WEANING_HINT_DAY, orgId);
		int predictDay=h!=null?h.getParameter():30;
		return listToPage(messageService.findByWeaningList(orgId,code,paddockId),pageNum,predictDay,h.getHint()!=null?h.getHint():0);
	}
	
	/**
	 * 久配不孕警告
	 */
	@JSON(type=BaseInfo.class,include="code,breed,paddock,rank,birthDay,breedingState,moonAge")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=RankTest.class,include="name")
	@RequestMapping(value="infertilityWarning")
	public Page<BaseInfo> infertilityWarning(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.WARNING_NUMBER, orgId);
		int predictDay=h!=null?h.getParameter():5;
		return listToPage(messageService.findByinfertilityWarning(orgId,pageNum,predictDay,code,paddockId),pageNum);
	}
	
	/**
	 * 定级提示
	 */
	@JSON(type=BaseInfo.class,include="code,breed,paddock,birthDay,breedingState,moonAge,breedName,paddockName")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@RequestMapping(value="rankHint")
	public Page<BaseInfo> rankHint(Long orgId,Long paddockId,String code,Integer pageNum){
		Hint h = hintService.getRepository().findByNameAndOrg_id(SystemM.RANK_HINT_DAY, orgId);
		int predictDay=h!=null?h.getParameter():154;
		Date day = DateUtils.dateAddInteger(new Date(),-predictDay);
		return listToPageDj(messageService.findByRankHint(orgId,day,code,paddockId),pageNum);
	}
	
	/**
	 * 诊疗提示
	 * */
	@JSON(type=BaseInfo.class,include="code,breed,paddock,sex,birthDay,breedingState,moonAge,org")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=DeathDisposalReason.class,include="name")
	@RequestMapping(value="generalVeternaryHint")
	public Page<GeneralVeternary> generalVeternaryHint(Long orgId,Long paddockId,String code,Integer pageNum){
		return listToPageZL(messageService.findByGeneralHint(orgId,code,paddockId).stream()
				.filter(x -> 1L==x.getPhysiologyStatus())
				.collect(Collectors.toList()),pageNum);
	}
	
	/**
	 * 采精调教提示
	 * */
	@JSON(type=BaseInfo.class,include="code,breed,paddock,sex,birthDay,breedingState,moonAge")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=DeathDisposalReason.class,include="name")
	@RequestMapping(value="semenHint")
	public Page<BaseInfo> semenHint(Long orgId,Long paddockId,String code,Integer pageNum){
		return listToPage(messageService.findBySemenHint(orgId,code,paddockId),pageNum);
	}
	
	/**
	 * 一、将list进行筛选、排序、及分页
	 * 二、将list封装为page
	 * @param list 
	 * 			数据集合
	 * @param page
	 * 			第几页
	 * @return page分页数据
	 * */
	private Page<MessageEntity> listToPage(List<MessageEntity> list,Integer page,int predictDay,Integer day){
		list =list.parallelStream().map(x-> x.calculate(predictDay))
				.filter(y -> y.getDay()<=day)
				//.sorted((a,b) -> a.compareTo(b.getDay()))
				.collect(Collectors.toList());
		Long total = (long) list.size();
		return new PageImpl<MessageEntity>(list.stream()
				.skip(page*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.collect(Collectors.toList()),pageableASC(page,new String[] {"predictDate,paddockName"}),total);
	}
	
	/**
	 * 一、将list进行筛选、排序、及分页
	 * 二、将list封装为page
	 * @param list 
	 * 			数据集合
	 * @param page
	 * 			第几页
	 * @return page分页数据
	 * */
	private <T> Page<T> listToPage(List<T> list,Integer page){
		Long total = (long) list.size();
		return new PageImpl<T>(list.stream()
				.skip(page*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.collect(Collectors.toList()),pageableASC(page,"breedName"),total);
	}
	private Page<BaseInfo> listToPageDj(List<BaseInfo> list,Integer page){
		list = list.stream().filter(x->x.getRank()==null).collect(Collectors.toList());
		Long total = (long) list.size();
		return new PageImpl<BaseInfo>(list.stream()
				.skip(page*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.collect(Collectors.toList()),pageableASC(page,"moonAge"),total);
	}
	private Page<GeneralVeternary> listToPageZL(List<GeneralVeternary> list,Integer page){
		Long total = (long) list.size();
		return new PageImpl<GeneralVeternary>(list.stream()
				.skip(page*GlobalConfig.PAGE_SIZE)
				.limit(GlobalConfig.PAGE_SIZE)
				.collect(Collectors.toList()),pageableASC(page,"date"),total);
	}
}