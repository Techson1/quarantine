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
import com.beiqisoft.aoqun.entity.SizeView;
import com.beiqisoft.aoqun.service.SizeViewService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "sizeView")
public class SizeViewController extends BaseController<SizeView,SizeViewService> {
	@JSON(type=BaseInfo.class,include="breed,code,sex")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<SizeView> list(SizeView sizeView) throws InterruptedException{
		return page.pageAcquire(sizeViewService.find(sizeView)).iteration(x ->{
			x.setMoonAge(DateUtils.dateSubDate(x.getDate(),x.getBase().getBirthDay()) +"");
		});
    }
	
	/**
	 * 体尺数据体重导出
	 * 
	 * @param wigth 查询条件
	 * @param number 页数
	 * @throws IOException 
	 * */
	@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,SizeView sizeView,int number) throws IOException{
		List<SizeView> sizeViews = sizeViewService.find(sizeView,PAGE_SIZE*number).getContent();
		for (SizeView r:sizeViews){
			r.setCode(r.getBase().getCode());
			r.setBreedName(r.getBase().getBreed().getBreedName());
			r.setSex(SystemM.PUBLIC_SEX_SIRE.equals(r.getBase().getSex())?"公羊":"母羊");
		}
		ExportDate.writeExcel("sizeView",response,sizeViews,
				new String[]{"code","breedName","sex","date","tall","bodyLength","bust","circumFerence","testis",
						"depth","yjsd","yjkd"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","品种","性别","测定日期","身高(cm)","体长(cm)","胸围","管围","阴囊围(cm)",
						"背膘厚度(mm)	","眼肌深度(cm)","眼肌宽度(cm)"});
	}
	
	/**
	 * 校验
	 * */
	 @RequestMapping(value="verify")
	 public Message verify(String earTag,Long orgId){
		return sizeViewService.verify(earTag,orgId);
	 }
	
	/**
	 * 添加
	 * */
	@RequestMapping(value ="add")
    public Message add(SizeView sizeView,String earTag){
		sizeViewService.add(sizeView,earTag);
		return SUCCESS;
    }
	
	/**
	 * APP添加
	 * */
	@RequestMapping(value ="appAdd")
    public SizeView appAdd(SizeView sizeView,String earTag){
		return sizeViewService.add(sizeView,earTag);
    }
}
