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
import com.beiqisoft.aoqun.entity.Fake;
import com.beiqisoft.aoqun.service.FakeService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 羊只缺陷控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月13日上午11:50:42
 * */
@RestController
@RequestMapping(value = "fake")
public class FakeController extends BaseController<Fake,FakeService> {
	@JSON(type=BaseInfo.class,include="breed,code,sex,birthDay,moonAge,physiologyStatus")
	@JSON(type=Breed.class,include="breedName")	
	@RequestMapping(value ="list")
    public Page<Fake> list(Fake fake) throws InterruptedException{
		return fakeService.find(fake);
    }
	
	/**
	 * 羊只缺陷导入
	 * */
	@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,Fake fake,int number) throws IOException{
		List<Fake> fakes = fakeService.find(fake,PAGE_SIZE*number).getContent();
		for (Fake f:fakes){
			f.setCode(f.getBase().getCode());
			f.setBreedName(f.getBase().getBreed().getBreedName());
			f.setSex(f.getBase().getSex());
			f.setBirthDay(DateUtils.DateToStr(f.getBase().getBirthDay()));
			f.setMoonAge(DateUtils.dateToAge(f.getCreateTime(),f.getBase().getBirthDay()));
			f.setPhysiologyStatus(SystemM.NORMAL.equals(f.getBase().getPhysiologyStatus())?"在库":"出库");
		}
		ExportDate.writeExcel("fake",response,fakes,
				new String[]{"code","breedName","sex","brithDay","moonAge","fakeType","physiologyStatus","recorder"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","品种","性别","出生日期","月龄","缺陷类型","在场状态","操作人"});
		
	}
	
	/**
	 * app 添加
	 * */
	@RequestMapping(value="appAdd")
	public Fake appAdd(Fake fake,String earTag){
		fakeService.getRepository().save(fake.setBaseReturnThis(baseInfoService.findByCodeOrRfid(earTag)));
		return fake;
	}
	
	/**
	 * app添加校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String earTag,String sex){
		return fakeService.addVerify(earTag,sex);
	}
	
	/**
	 * 添加及修改
	 * */
	@RequestMapping(value="addAndEdit")
	public Message addAndEdit(Fake fake,String earTag){
		fakeService.getRepository().save(fake.setBaseReturnThis(baseInfoService.findByCodeOrRfid(earTag)));
		return SUCCESS;
	}
	
	/**
	 * 查询
	 * */
	@JSON(type=BaseInfo.class,include="code,breed,sex,birthDay")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Fake.class,filter="org")
	@RequestMapping(value="findOne/{id}")
	public Fake findOne(@PathVariable Long id){
		 Fake fake=fakeService.getRepository().findOne(id);
		 return fake.setFakeIdReturnThis(
					fakeTypeDamService.getRepository().findByName(fake.getFakeType()),
						 fakeTypeSireService.getRepository().findByName(fake.getFakeType()));
	}
}
