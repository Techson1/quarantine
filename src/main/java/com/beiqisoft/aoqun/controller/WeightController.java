package com.beiqisoft.aoqun.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;
import com.beiqisoft.aoqun.util.page.PageRewrite;
@RestController
@RequestMapping(value = "wigth")
public class WeightController extends BaseController<Weight,WeightService> {
	
	@JSON(type=BaseInfo.class,include="code,breed,sex,birthDay")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="list")
    public PageRewrite<Weight> list(Weight wigth) throws InterruptedException{
		PageRewrite<Weight> weights = new PageRewrite<Weight>(wigthService.find(wigth));
		return weights;
    }
	
	public List<Weight> verifyExcel(String fileName) throws Exception{
		// return 
		return null;
	}
	
	/**
	 * 原始体重导出
	 * 
	 * @param wigth 查询条件
	 * @param number 页数
	 * @throws IOException 
	 * */
	@RequestMapping(value ="export")
	public void  export(HttpServletRequest request, HttpServletResponse response,Weight wigth,int number) throws IOException{
		List<Weight> weights = wigthService.find(wigth,PAGE_SIZE*number).getContent();
		for (Weight w:weights){
			w.setCode(w.getBase().getCode());
			w.setPaddockName(w.getPaddock()!=null?w.getPaddock().getName():"");
			w.setOrgName(w.getOrg()!=null?w.getOrg().getOrgName():"");
		}
		ExportDate.writeExcel("weight",response,weights,
				new String[]{"code","weights","weighthDate","dayAge","monthAge","operator","lastDay","daily","paddockName","orgName"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","体重","称重日期","称重日龄","称重月龄","操作员","距上次天数","日增重","称重时圈舍","分厂名称"});
	}

	/**
	 * 
	 * 添加校验
     * @param type
     * 			1:断奶重
     *          2:月龄重
     * */
    @RequestMapping(value="addVerify")
    public Message addVerify(Weight wigth,String earTag,String type){
    	return wigthService.addVerify(wigth,earTag,type);
    }
	
    /**
     * @param type
     * 			1:断奶重
     *          2:月龄重
     * */
	@RequestMapping(value="add")
	public Message add(Weight wigth,String earTag,String type){
		BaseInfo base =baseInfoService.findByCodeOrRfid(earTag);
		wigth.setBase(base);
		wigthService.getRepository().save(wigth);
		if (SystemM.WEIGHT_TYPE_WEANING.equals(type)){
			base.setWeaningDate(wigth.getWeighthDate());
			base.setWeaningWeight(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(base);
		}
		//刷新上一次体重数据
		 wigthService.refresh(base);
		//刷新矫正数据
	    reviseWeightService.anew(base.getCode());
		return SUCCESS;
	}
	
	/**
	 * 修改称重接口
	 */
	@RequestMapping(value="update")
	public Message update(Weight wigth){
		if (SystemM.WEIGHT_TYPE_INITIAL.equals(wigth.getType())){
			wigth.setInitial(wigthService.getRepository().findOne(wigth.getId()));
			wigth.getBase().setInitialWeigh(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(wigth.getBase());
		}
		if (SystemM.WEIGHT_TYPE_WEANING.equals(wigth.getType())){
			wigth.getBase().setInitialWeigh(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(wigth.getBase());
		}
		wigthService.getRepository().save(wigth);
		//刷新上一次体重数据
		 wigthService.refresh(wigth.getBase());
		//刷新矫正数据
	    reviseWeightService.anew(wigth.getBase().getCode());
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * */
	@RequestMapping(value="deleteRefresh/{id}")
	public Message deleteRefresh(@PathVariable Long id){
		Weight weight= wigthService.getRepository().findOne(id);
		BaseInfo base=weight.getBase();
		wigthService.getRepository().delete(id);
		//刷新上一次体重数据
		 wigthService.refresh(base);
		//刷新矫正数据
	    reviseWeightService.anew(base.getCode());
		return SUCCESS;
	}
	
	 /**
     * @param type
     * 			1:断奶重
     *          2:月龄重
     * */
	@RequestMapping(value="appAdd")
	public Weight appAdd(Weight wigth,String earTag,String type){
		BaseInfo base =baseInfoService.findByCodeOrRfid(earTag);
		wigth.setBase(base);
		wigthService.getRepository().save(wigth);
		if (SystemM.WEIGHT_TYPE_WEANING.equals(type)){
			base.setWeaningDate(wigth.getWeighthDate());
			base.setWeaningWeight(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(base);
		}
		//刷新上一次体重数据
		 wigthService.refresh(base);
		//刷新矫正数据
	    reviseWeightService.anew(base.getCode());
		return wigth;
	}
}
