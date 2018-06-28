package com.beiqisoft.aoqun.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Customer;
import com.beiqisoft.aoqun.entity.Sales;
import com.beiqisoft.aoqun.entity.rep.SalesRep;
import com.beiqisoft.aoqun.service.SalesService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "sales")
public class SalesController extends BaseController<Sales,SalesService> {
	@RequestMapping(value ="list")
    public Page<Sales> list(Sales sales) throws InterruptedException{
		return salesService.find(sales);
    }
	
	/**
	 * 销售单添加
	 * */
	@RequestMapping(value="add")
	public Sales WebAndAppSave(Sales sales){
		//sales.setDate(new Date());
		salesService.getRepository().save(sales);
		return sales;
	}
	
	/**
	 * 订单名称重复校验
	 * */
	@RequestMapping(value="codeVerify")
	public boolean codeVerify(String code,Long orgId){
		return salesService.getRepository().findByCodeAndOrg_id(code,orgId)==null;
	}
	
	/**
	 * 复合
	 * */
	@RequestMapping(value="accomplish/{id}/{reviewing}")
	public Message accomplish(@PathVariable Long id,@PathVariable String reviewing){
		salesService.getRepository().save(
				salesService.getRepository().findOne(id).setAudit(new Date(),reviewing));
		salesDatailService.audit(id,SystemM.AUDIT_MARKET);
		return SUCCESS;
	}
	
	/**
	 * 取消复合
	 * */
	@RequestMapping(value="notAudit/{id}")
	public Message notAudit(@PathVariable Long id){
		salesService.getRepository().save(
				salesService.getRepository().findOne(id).setNotAudit());
		salesDatailService.audit(id,SystemM.STAY_MARKET);
		return SUCCESS;
	}
	
	/**
	 * 查询未完成订单
	 * */
	@JSON(type=Sales.class,include="customer,id,code")
	@JSON(type=Customer.class,include="firstName")
	@RequestMapping(value="findBylist")
	public List<Sales> findBylist(){
		return salesService.getRepository().findByCheckFlag(SystemM.PUBLIC_FALSE);
	}
	
	/**
	 * 完成校验
	 * */
	@RequestMapping(value="accomplistVerify/{id}")
	public boolean accomplistVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(salesService.getRepository().findOne(id).getCheckFlag());
	}
	
	/**
	 * 销售报表
	 * */
	@RequestMapping(value="salesRep")
	public List<SalesRep> salesRep(String year,String month){
		Date startDate = DateUtils.StrToDate(year+"-"+month+"-1");
		Date endDate = DateUtils.StrToDate(year+"-"+month+"-"+DateUtils.moonDay(year+"-"+month));
		
		return salesService.getRepository().findByRep(startDate,endDate);
	}
	
	/**
	 * 销售名称
	 * */
	@RequestMapping(value="salesName")
	public String salesName(){
		String code = salesService.getRepository().findByDate("%"+DateUtils.newDate().substring(0,7)+"%",currentUser().getOrganization().getId());
		String maxCode="001";
		if (code!=null && !"".equals(code)){
			maxCode="000"+(Integer.parseInt(code.substring(code.length()-3))+1);
		}
		return "XS"+DateUtils.newDate().substring(0,7).replace("-", "")+maxCode.substring(maxCode.length()-3);
	}
}
