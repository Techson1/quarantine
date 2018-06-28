package com.beiqisoft.aoqun.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.beiqisoft.aoqun.entity.BreedParameter;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.RamTraining;
import com.beiqisoft.aoqun.service.RamTrainingService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;
import com.sun.swing.internal.plaf.synth.resources.synth_es;

/**
 * 采精调教访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年12月13日上午10:45:00
 */
@RestController
@RequestMapping(value = "ramTraining")
public class RamTrainingController extends BaseController<RamTraining,RamTrainingService> {
	@JSON(type=BaseInfo.class,include="breed,code,birthDay,paddock")
	@JSON(type=RamTraining.class,filter="paddock")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="list")
    public Page<RamTraining> list(RamTraining ramTraining) throws InterruptedException{
		return page.pageAcquire(ramTrainingService.find(ramTraining)).iteration(x ->{
			x.setMoonAge(DateUtils.dateToAge(x.getDate(),x.getRam().getBirthDay()));
		}) ;
    }
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete/{id}")
	public Message deletes(@PathVariable Long id){
		RamTraining ram= ramTrainingService.getRepository().findOne(id);
		ramTrainingService.getRepository().delete(id);
		baseInfoService.getRepository().save(ram.getRam().setBreedingStateReutrnThis(
				ramTrainingService
				.getRepository()
				.findByMaxDate(ram.getRam().getId()).getAssess()));
		return SUCCESS;
	}
	
	/**
	 * 采精导出
	 * @throws IOException 
	 * */
	@RequestMapping(value="export")
	public void export(HttpServletRequest request, HttpServletResponse response,RamTraining ramTraining,int number) throws IOException{
		List<RamTraining> ramTrainings=ramTrainingService.find(ramTraining,PAGE_SIZE*number).getContent();
		for (RamTraining r:ramTrainings){
			r.setCode(r.getRam().getCode());
			r.setBreedName(r.getRam().getBreed().getBreedName());
			r.setBirthDay(DateUtils.DateToStrMit(r.getRam().getBirthDay()));
			r.setPaddockName(r.getRam().getPaddock()!=null?r.getRam().getPaddock().getName():"");
			r.setOrgName(r.getOrg()!=null?r.getOrg().getOrgName():"");
			r.setMoonAge(DateUtils.dateToAge(r.getDate(), r.getRam().getBirthDay()));
		}
		
		ExportDate.writeExcel("ramTraining",response,ramTrainings,
				new String[]{"code","breedName","birthDay","date","moonAge","paddockName","assess","amount","density",
						"color","smell","activity","orgName","recorder"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","品种","出生日期","采精日期","采精月龄","现在圈舍","整体评价","精液量","精液密度",
						"颜色","气味","精子活力","分厂","操作人"});
	}
	
	/**
	 * 采精校验
	 * */
	@RequestMapping(value="trainingVerify")
	public Message trainingVerify(String earTag,Date date){
		BaseInfo base= baseInfoService.findByCodeOrRfid(earTag);
		BreedParameter parameter = breedParameterService.getRepository().findByName(SystemM.SEMEN_DAY_AGE);
		if (base==null){
			return GlobalConfig.setAbnormal(earTag+":该羊不存在");
		}
		if (!SystemM.PUBLIC_SEX_SIRE.equals(base.getSex())){
			return GlobalConfig.setAbnormal(earTag+":该羊是只母羊");
		}
		if (DateUtils.dateSubDate(date,base.getBirthDay())<parameter.getParameter()){
			return GlobalConfig.setAbnormal(earTag+":该羊的没有达到调教日龄");
		}
		if(ramTrainingService.getRepository().findByRam_idAndDate(base.getId(),date)!=null) {
			return GlobalConfig.setAbnormal(earTag+":该羊当日已采精");
		}
		return baseInfoService.flagVerify(base);
	}
	
	/**
	 * app添加
	 * */
	@RequestMapping(value ="appAdd")
	public RamTraining appAdd(RamTraining ramTraining,String earTag){
		ramTrainingService.getRepository().save(ramTraining.setRamReturnThis(baseInfoService.findByCodeOrRfid(earTag)));
		if (ramTraining.getPaddock()!=null&&ramTraining.getPaddock().getId()!=null){
			paddockChangeService.add(ramTraining.getPaddock(), 
					ramTraining.getRam().getCode(), 
					ramTraining.getOrg(),ramTraining.getRecorder());
		}
	System.err.println(ramTrainingService
	.getRepository()
	.findByMaxDate(ramTraining.getRam().getId()).getAssess());
		//修改羊只繁殖状态
		baseInfoService.getRepository().save(ramTraining.getRam().setBreedingStateReutrnThis(
				ramTrainingService
				.getRepository()
				.findByMaxDate(ramTraining.getRam().getId()).getAssess()));
		return ramTraining;
	}
	
	/**
	 * 采精调教添加及修改
	 * */
	@RequestMapping(value ="add")
	public Message add(RamTraining ramTraining,String earTag){
		ramTraining.setRamReturnThis(baseInfoService.findByCodeOrRfid(earTag));
		if (ramTraining.getPaddock()!=null&&ramTraining.getPaddock().getId()!=null){
			paddockChangeService.add(ramTraining.getPaddock(), 
					ramTraining.getRam().getCode(), ramTraining.getOrg(),
					ramTraining.getRecorder());
		}
		else{
			ramTraining.setPaddock(null);
		}
		ramTrainingService.getRepository().save(ramTraining);
		//修改羊只繁殖状态
		baseInfoService.getRepository().save(ramTraining.getRam().setBreedingStateReutrnThis(
				ramTrainingService
				.getRepository()
				.findByMaxDate(ramTraining.getRam().getId()).getAssess()));
		return SUCCESS;
	}
}
