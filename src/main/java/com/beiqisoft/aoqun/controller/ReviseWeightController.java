package com.beiqisoft.aoqun.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.ReviseWeight;
import com.beiqisoft.aoqun.service.ReviseWeightService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;

@RestController
@RequestMapping(value = "reviseWeight")
public class ReviseWeightController extends BaseController<ReviseWeight,ReviseWeightService> {
	
	@JSON(type=BaseInfo.class,include="breed,code,sex,birthDay,initialWeigh,weaningDate,weaningWeight")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<ReviseWeight> list(ReviseWeight reviseWeight) throws InterruptedException{
		return page.pageAcquire(reviseWeightService.find(reviseWeight)).iteration(x->{
			x.setMonthAge(DateUtils.dateToAge(x.getBase().getBirthDay()));
			x.setWeaningAge(DateUtils.dateSubDate(x.getBase().getWeaningDate(), x.getBase().getBirthDay())+"");
		});
    }
	
	/**
	 * 校正数据体重导出
	 * 
	 * @param wigth 查询条件
	 * @param number 页数
	 * @throws IOException 
	 * */
	@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,ReviseWeight reviseWeight,int number) throws IOException{
		List<ReviseWeight> reviseWeights = reviseWeightService.find(reviseWeight,PAGE_SIZE*number).getContent();
		for (ReviseWeight r:reviseWeights){
			r.setCode(r.getBase().getCode());
			r.setBreedName(r.getBase().getBreed().getBreedName());
			r.setSex(r.getBase().getSex().equals(SystemM.PUBLIC_SEX_SIRE)?"公羊":"母羊");
			r.setBirthDay(DateUtils.DateToStr(r.getBase().getBirthDay()));
			r.setMonthAge(String.valueOf(r.getBase().getMoonAge()));
			r.setInitialWeigh(r.getBase().getInitialWeigh()+"");
			r.setWeaningAge(r.getBase().getWeaningDay()+"");
			r.setWeaningWeight(r.getBase().getWeaningWeight()+"");
		}
		ExportDate.writeExcel("reviseWeight",response,reviseWeights,
				new String[]{"code","breedName","sex","birthDay","monthAge","initialWeigh","weaningDay","weaningWeight",
							 "threeWeight","fourWeight","fiveWeight","sixWeight","sevenWeight","eightWeight"
							 ,"nineWeight","tenWeight","elevenWeight","twelveWeight","eighteenWeight","twentyFourWeight",},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","品种","性别","出生日期","现在月龄","出生重","断奶日龄","断奶重",
							"三月体重","四月体重","五月体重","六月体重","七月体重","八月体重","九月体重","十月体重","十一月体重",
							"十二月体重","十八月体重","二十四月体重"});
	}
	
	@RequestMapping(value="anew")
	public Message anew(String earTag){
		reviseWeightService.anew(earTag);
		return SUCCESS;
	}
}
