package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.entity.Sales;
import com.beiqisoft.aoqun.entity.SalesDatail;
import com.beiqisoft.aoqun.entity.domain.PrintBase;
import com.beiqisoft.aoqun.service.SalesDatailService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping(value = "salesDatail")
@Slf4j
public class SalesDatailController extends BaseController<SalesDatail,SalesDatailService> {
	@RequestMapping(value ="list")
    public Page<SalesDatail> list(SalesDatail salesDatail) throws InterruptedException{
		return salesDatailService.find(salesDatail);
    }
	
	@JSON(type=RankTest.class,include="name,price")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=BaseInfo.class,include="breed,rank,code,sex,birthDay,moonAge,bornStatus")
	@JSON(type=Contact.class,include="firstName")
	@JSON(type=Sales.class,filter="org,customer")
	@RequestMapping(value="inventory")
	public Page<SalesDatail> inventory(SalesDatail salesDatail){
		return page.pageAcquire(salesDatailService.inventory(salesDatail))
				.iteration(x -> x.getItem().setMoonAge(DateUtils.dateToAge(x.getItem().getBirthDay())));
	}
	
	/**
	 * 添加
	 * */
	@RequestMapping(value="add")
	public Message add(SalesDatail salesDatail,String earTag){
		salesDatail.setCode(earTag);
		salesDatailService.add(salesDatail);
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * */
	@RequestMapping(value="delete/{ids}")
	public Message delete(@PathVariable Long[] ids){
		return salesDatailService.delete(ids);
	}
	
	
	/**
	 * 校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(SalesDatail salesDatail,String earTag){
		return salesDatailService.addVerify(salesDatail,earTag);
	}
	
	/**
	 * 销售明细
	 * */
	@JSON(type=SalesDatail.class,filter="sales")
	@JSON(type=BaseInfo.class,include="breed,sex,code,bornStatus,rank,breedingState")
	@JSON(type=RankTest.class,include="name")
	@RequestMapping(value="findByList")
	public List<SalesDatail> findByList(SalesDatail salesDatail){
		return salesDatailService.findList(salesDatail)
				.stream()
					.map(x -> {
						if (x.getItem().getRank()!=null){
							x.discountFigure(x.getItem().getRank().priceShift());
						}
						return x;
					}).collect(Collectors.toList());
	}
	
	/**
	 * @deprecated
	 * */
	@RequestMapping(value="printBase/{salesId}")
	 public List<PrintBase> printBase(@PathVariable Long salesId){
		List<PrintBase> list =new ArrayList<>();
		for (SalesDatail s:salesDatailService.getRepository().findBySales_id(salesId)){
			if (s.getItem().getRank()!=null){
				s.discountFigure(s.getItem().getRank().priceShift());
			}
			list.add(new PrintBase(s));
		}
		return list;
	 }
}
