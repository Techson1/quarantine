package com.beiqisoft.aoqun.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.entity.GeneralVeternary;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.GeneralVeternaryService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 疾病诊疗控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月23日上午10:24:12
 * */
@RestController
@RequestMapping(value = "generalVeternary")
public class GeneralVeternaryController extends BaseController<GeneralVeternary,GeneralVeternaryService> {
	@JSON(type=BaseInfo.class,include="breed,code,birthDay,paddock,sex")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=DeathDisposalReason.class,include="name")
	@RequestMapping(value ="list")
    public Page<GeneralVeternary> list(GeneralVeternary generalVeternary) throws InterruptedException{
		return page.pageAcquire(generalVeternaryService.find(generalVeternary)).iteration(x->{
			x.setMoonAge(DateUtils.dateToAge(x.getDate(),x.getBase().getBirthDay()));
		}) ;
    }
	
	/**
	 * 疾病诊疗导出
	 * */
	@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,GeneralVeternary generalVeternary,int number) throws IOException{
		List<GeneralVeternary> generalVeternarys = generalVeternaryService
														.find(generalVeternary,PAGE_SIZE*number).getContent();
		for (GeneralVeternary g:generalVeternarys){
			g.setCode(g.getBase().getCode());
			g.setBreedName(g.getBase().getBreed().getBreedName());
			g.setSex(SystemM.PUBLIC_SEX_SIRE.equals(g.getBase().getSex())?"公羊":"母羊");
			g.setMoonAge(DateUtils.dateToAge(g.getDate(),g.getBase().getBirthDay()));
		}
		ExportDate.writeExcel("GeneralVeternary",response,generalVeternarys,
				new String[]{"code","breedName","sex","moonAge","date","fatherReasonName","resultName","remark","result","paddockName","recorder"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","品种","性别","月龄","诊疗日期","疾病原因","疾病详因","处理措施","治疗结果","所在饲舍","操作人"});
	}
	/**
	 * 疾病诊疗导出
	 * */
	@RequestMapping(value ="exportAll")
	public void exportAll(HttpServletRequest request, HttpServletResponse response,GeneralVeternary generalVeternary) throws IOException{
		List<GeneralVeternary> generalVeternarys = generalVeternaryService.findList(generalVeternary);
		for (GeneralVeternary g:generalVeternarys){
			g.setCode(g.getBase().getCode());
			g.setBreedName(g.getBase().getBreed().getBreedName());
			g.setSex(SystemM.PUBLIC_SEX_SIRE.equals(g.getBase().getSex())?"公羊":"母羊");
			g.setMoonAge(DateUtils.dateToAge(g.getDate(),g.getBase().getBirthDay()));
		}
		ExportDate.writeExcel("疾病诊疗查询结果导出",response,generalVeternarys,
				new String[]{"code","breedName","sex","moonAge","date","fatherReasonName","resultName","remark","result","paddockName","recorder"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","品种","性别","月龄","诊疗日期","疾病原因","疾病详因","处理措施","治疗结果","所在饲舍","操作人"});
	}
	/**
	 * 添加校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String earTag){
		return generalVeternaryService.addVerify(earTag);
	}
	
	/**
	 * 疾病诊疗添加
	 * */
	@RequestMapping(value="add")
	public Message add(GeneralVeternary generalVeternary,String earTag){
		 generalVeternaryService.add(generalVeternary,earTag);
		 return SUCCESS;
	}
	
	/**
	 * 疾病诊疗修改
	 * */
	@RequestMapping(value="update")
	public Message update(GeneralVeternary generalVeternary){
		generalVeternaryService.getRepository().save(
				generalVeternaryService.getRepository()
					.findOne(generalVeternary.getId()).update(generalVeternary));
		return SUCCESS;
	}
	
	/**
	 * 疾病诊疗修改
	 * */
	@RequestMapping(value="updateVerify/{id}")
	public Message updateVerify(@PathVariable Long id){
		return generalVeternaryService.updateVerify(id);
	}
	
	/**
	 * 疾病诊疗app添加
	 * */
	@RequestMapping(value="appAdd")
	public GeneralVeternary appAdd(GeneralVeternary generalVeternary,String earTag){
		return generalVeternaryService.add(generalVeternary,earTag);
	}
}
