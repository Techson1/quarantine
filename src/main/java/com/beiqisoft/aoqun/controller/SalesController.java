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
import com.beiqisoft.aoqun.entity.SalesDatail;
import com.beiqisoft.aoqun.entity.rep.SalesRep;
import com.beiqisoft.aoqun.service.SalesService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping(value = "sales")
@Slf4j
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
	/**json
	 *   羊只复核
	 * @param id 销售单ID
	 * @param sheepids  复核羊只id
	 * @param reviewing 复核人
	 * @return
	 */
	@RequestMapping(value="checkReviewSheep/{id}/{reviewing}")
	public Message checkReviewSheep(@PathVariable Long id,@PathVariable String reviewing) {
		log.info("start checkReviewSheep...");
		Sales sales=salesService.getRepository().findOne(id);
		log.info("checkReviewSheep totalcount："+sales.getTotalCount());
		List<SalesDatail> list=salesDatailService.getRepository().findBySalesIdAndCheckStatus(Long.valueOf(id), "1");
		if(null==sales.getTotalCount()) {
			return COUNT_NULL;
		}
		if(null!=list&&Integer.valueOf(sales.getTotalCount())==list.size()) {
			log.info("end checkReviewSheep.....");
			//设置为已经复核
			salesService.getRepository().save(sales.setAudit(new Date(), reviewing));
			return SUCCESS;
		}else {
			
			return FAIL;
		}
		/*String ids[]=sheepids.split(",");
		if(ids.length==Integer.valueOf(sales.getTotalCount())) {//如果全部复核，则更新销售单状态
			log.debug("start change status...");
			List<SalesDatail> datails=salesDatailService.getRepository().findBySales_id(id);//获得销售单下所有详情
			for(SalesDatail d:datails) {
				d.setCheckStatus("1");//设置为选中状态
				salesDatailService.getRepository().save(d);
			}
			//设置为已经复核
			salesService.getRepository().save(sales.setAudit(new Date(), reviewing));
		}else {//只是记录选中状态
			log.debug("no pass change status");
			List<SalesDatail> datails=salesDatailService.getRepository().findBySales_id(id);//获得销售单下所有详情
			for(SalesDatail d:datails) {
				d.setCheckStatus("0");//设置为不选中状态
				salesDatailService.getRepository().save(d);
			}
			for(String sheepid:ids) {//把当前选中的设置为选中状态
				SalesDatail datail=salesDatailService.getRepository().findOne(Long.valueOf(sheepid));
				datail.setCheckStatus("1");//设置选中
				salesDatailService.getRepository().save(datail);
			}
		}*/
		 
	}
	/**
	 * 复核羊只，只要选中就更改状态
	 * @param sheepid t_sales_datail 销售详情表ID
	 * @param ckStatus  选中未1 否则为0
	 * @return
	 */
	@RequestMapping(value="checkOneSheep/{sheepid}/{ckStatus}")
	public Message checkOneSheep(@PathVariable String sheepid,@PathVariable String ckStatus) {
		log.info("startcheckOneSheep.....");
		SalesDatail datail=salesDatailService.getRepository().findOne(Long.valueOf(sheepid));
		datail.setCheckStatus(ckStatus);//设置选中
		if("1".equals(datail.getSales().getCheckFlag())) {//表示已经复核，不能取消羊只选中状态
			if("1".equals(ckStatus)) {
				datail.getItem().setPhysiologyStatus(Long.valueOf("10"));
			}else {
				return new Message(10001,"该销售单已经复核，不能取消！");
			}
		}else {
			
			if("1".equals(ckStatus)) {
				datail.getItem().setPhysiologyStatus(Long.valueOf("10"));
			}else {
				datail.getItem().setPhysiologyStatus(Long.valueOf("9"));
			}
		}
		
		salesDatailService.getRepository().save(datail);
		log.info("end checkOneSheep.....");
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
	 * 根据场区区分销售单数据
	 * @param orgId
	 * @return
	 */
	@JSON(type=Sales.class,include="customer,id,code")
	@JSON(type=Customer.class,include="firstName")
	@RequestMapping(value="findListByOrgId")
	public List<Sales> findListByOrgId(Long orgId){
		return salesService.getRepository().findByOrg_idAndCheckFlag(orgId,SystemM.PUBLIC_FALSE);
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
