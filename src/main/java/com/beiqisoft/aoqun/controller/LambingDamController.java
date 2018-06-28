package com.beiqisoft.aoqun.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.rep.LambingDamRep;
import com.beiqisoft.aoqun.service.LambingDamService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 母羊生产访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "lambingDam")
public class LambingDamController extends BaseController<LambingDam,LambingDamService> {
	@JSON(type=BaseInfo.class,include="code,breed")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<LambingDam> list(LambingDam lambingDam) throws InterruptedException{
		Page<LambingDam> page=lambingDamService.find(lambingDam);
		//采取并行流计算
		page.getContent().parallelStream().forEach(x->{
			if (DateUtils.dateSubDate(new Date(), x.getBornDate())>=62){
				x.setNumber(baseInfoService.getRepository().findBySurvival(x.getId()));
				if (x.getBornTimes()==null){
					x.setBornTimes(0);
				}
				x.setSurvival(MyUtils.percentage(MyUtils.strToLong(x.getNumber()), x.getAliveCountF()+x.getAliveCountM()));
			}
			else{
				x.setNumber("--");
				x.setSurvival("--");
			}
		});
		return page;
    }
	
	/**
	 * app查询
	 * */
	@JSON(type=LambingDam.class,filter="parity,org")
	@JSON(type=BaseInfo.class,include="code,breed")
	@RequestMapping(value="appList")
	public List<LambingDam> appList(String code,Date bornDate){
		return lambingDamService.appList(code,bornDate,currentUser().getOrganization().getId());
	}
	
	/**
	 * 添加校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String damCode,Date bornDate){
		return lambingDamService.addVerify(baseInfoService.findByCodeOrRfid(damCode),bornDate,currentUser().getOrganization().getId());
	}
	
	/**
	 * app添加
	 * */
	@RequestMapping(value="appAdd")
	public LambingDam appAdd(LambingDam lambingDam,String damCode){
		return lambingDamService.getRepository().findOne(
				Long.parseLong(lambingDamService.add(
						lambingDam, baseInfoService.findByCodeOrRfid(damCode)).getMsg()));
	}
	
	/**
	 * 添加
	 * */
	@RequestMapping(value="add")
	public Message add(LambingDam lambingDam,String damCode){
		return lambingDamService.add(lambingDam, baseInfoService.findByCodeOrRfid(damCode));
	}
	
	/**
	 * 修改页面校验
	 * */
	@RequestMapping(value="updateUiVerify/{id}")
	public Message editUiVerify(@PathVariable Long id){
		return lambingDamService.editUiVerify(lambingDamService.getRepository().findOne(id));
	}
	//TODO 修改校验前端处理,只需要校验日期是否存在
	
	/**
	 * 
	 * */
	@RequestMapping(value="updateVerify")
	public Message editVerify(Long id,Date bornDate){
		LambingDam lambingDam = lambingDamService.getRepository().findOne(id);
		if (!baseInfoService.getRepository().findByLambingDam_id(id).isEmpty()){
			if (!lambingDam.getBornDate().equals(bornDate)){
				return GlobalConfig.setAbnormal("已存在羔羊记录,不能修改母羊的产羔日期");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * app修改
	 * */
	@RequestMapping(value="appUpdate")
	public LambingDam appUpdate(LambingDam lambingDam){
		return lambingDamService.edit(lambingDam);
	}
	
	/**
	 * 修改
	 * */
	@RequestMapping(value="update")
	public Message edit(LambingDam lambingDam){
		lambingDamService.edit(lambingDam);
		return GlobalConfig.SUCCESS;
	}
	
	/**
	 * 查询羊只
	 * */
	@JSON(type=LambingDam.class,filter="org")
	@JSON(type=BaseInfo.class,filter="org,paddock,")
	@JSON(type=Parity.class,filter="org")
	@SuppressWarnings("deprecation")
	@RequestMapping(value="findOne/{id}")
	public LambingDam findOne(@PathVariable Long id){
		LambingDam lambingDam=lambingDamService.getRepository().findOne(id);
		Parity parity=lambingDam.getParity();
		//TODO 羔羊添加时是否要判断胎次是否关闭
		lambingDam.setJoining(joiningService.getRepository()
				.findByIsNewestJoiningAndParity_id(SystemM.PUBLIC_TRUE, parity.getId()));
		lambingDam.setTheSameFetus(lambingService.getRepository().findByLambingDam_id(lambingDam.getId()).size()+"");
		return lambingDam;
	}
	
	/**
	 * 产羔报表
	 * */
	@RequestMapping(value="findByRep")
	public List<LambingDamRep> findByRep(Date startDate,Date endDate,Long orgId){
		return lambingDamService.getRepository().findByRep(startDate,endDate,orgId);
	}
	
	/**
	 * 产羔删除
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return lambingDamService.delVerify(id);
	}
}