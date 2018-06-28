package com.beiqisoft.aoqun.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.AllotDetail;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.OnHandMonth;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.service.OnHandMonthService;
@RestController
@RequestMapping(value = "onHandMonth")
public class OnHandMonthController extends BaseController<OnHandMonth,OnHandMonthService> {
	@RequestMapping(value ="list")
    public List<OnHandMonth> list(OnHandMonth onHandMonth) throws InterruptedException, ParseException{
		List<OnHandMonth> list = new ArrayList<OnHandMonth>();
		if(onHandMonth.getStartDate()!=null&&onHandMonth.getEndDate()!=null) {
			while(true) {
				//获取当月月底日期
				Date monthOver = monthFirst(onHandMonth.getStartDate());
				if(monthOver.getTime()>onHandMonth.getEndDate().getTime()) {
					list.add(operation(onHandMonth,onHandMonth.getEndDate()));
					break;
				}else {
					list.add(operation(onHandMonth,monthOver));
					onHandMonth.setStartDate(monthFirst(onHandMonth.getStartDate()));
				}
			}
		}
		Collections.reverse(list);
		return list;
    }
	/*private Date monthOver(Date repeatDate) throws ParseException {
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");  
		String date = dft.format(repeatDate);
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(dft.parse(date));  
         
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return calendar.getTime();  
	}*/
	private Date monthFirst(Date date) throws ParseException {
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");  
		String dd = dft.format(date);
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(dft.parse(dd));  
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
	}
	private String format(Date date) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月");
		return dft.format(date);
	}
	
	private OnHandMonth operation(OnHandMonth onHandMonth,Date monthOver) throws ParseException {
		
		
		Organization org = currentUser().getOrganization();
		
		OnHandMonth entity = new OnHandMonth();
		
		//出生日期
		entity.setBrithday(format(onHandMonth.getStartDate()));
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		//Date dada = dateFormat1.parse("2009-06-01");
		//期初数量
		BaseInfo base = new BaseInfo();
		base.setFlag("0");
		base.setPhysiologyStatus((long) 1);
		
		base.setOrg(org);
		base.setSex(onHandMonth.getSex());
		base.setBirthDayAssistEnd(onHandMonth.getStartDate());
		//entity.setStartNum(baseInfoService.find2(base).size());
		entity.setStartNum(baseInfoService.getRepository()
				.findFirstNumByFlayAndPhysiologyStatusAndOrg_idAndSexBirthDayEnd
				(org.getId(),onHandMonth.getSex(),onHandMonth.getStartDate()));
		// 出生数
		Integer lambNum = lambingDamService.getRepository().findByOrg_idAndBornDateBetween
			(org.getId(),onHandMonth.getStartDate(),onHandMonth.getEndDate());
		/*Integer lambNum = 0;
		for(LambingDam la:lambs) {
			 lambNum += la.getAliveCountF()+la.getAliveCountM();
		}*/

		entity.setBrithdayNum(lambNum);
		//调拨入库
		List<AllotDetail> allots = allotDetailService.getRepository().findByBase_birthDayBetween
			(onHandMonth.getStartDate(),onHandMonth.getEndDate());
		
		Integer allotinNum = allots.stream().filter(x->x.getToPaddock().getOrg().getId()==org.getId())
								.collect(Collectors.toList()).size();
		entity.setAllotInNum(allotinNum);
		
		//调拨出库
		Integer allotoutNum = allots.stream().filter(x->x.getFromPaddock().getOrg().getId()==org.getId())
				.collect(Collectors.toList()).size();
		entity.setAllotOutNum(allotoutNum);
		// 购入
		Integer purchaseNum = baseInfoService.getRepository().findByIsOutsourcingAndBirthDayBetween
				("1",onHandMonth.getStartDate(),onHandMonth.getEndDate()).size();
		entity.setPurchase(purchaseNum);
		//销售数量
		Integer salesNum = salesDatailService.getRepository().findSaleNumByItem_org_idAndItem_SexAndItem_birthDayBetween(org.getId(),onHandMonth.getSex(),onHandMonth.getStartDate(),monthOver);
		/*Integer salesNum = sales.stream().filter(x->onHandMonth.getStartDate().getTime()<x.getItem().getBirthDay().getTime()
				&&monthOver.getTime()>x.getItem().getBirthDay().getTime()
				&&x.getItem().getOrg().getId()==org.getId()).collect(Collectors.toList()).size();*/
		entity.setSale(salesNum);
		//死亡数量
		Integer deathNum = deathdisposalService.getRepository().findDeathNumByOrg_idAndBase_sexAndBase_birthDayBetween(org.getId(),onHandMonth.getSex(),onHandMonth.getStartDate(),monthOver);
		/*Integer deathNum = deaths.stream().filter(x->onHandMonth.getStartDate().getTime()<x.getBase().getBirthDay().getTime()
				&&monthOver.getTime()>x.getBase().getBirthDay().getTime()
				&&x.getOrg().getId()==org.getId()).collect(Collectors.toList()).size();*/
		entity.setDeath(deathNum);
		//疾病淘汰数量
		Integer illNum = illnessWeedService.getRepository().findIllNumByOrg_idAndBase_sexAndBase_birthDayBetween(org.getId(),onHandMonth.getSex(),onHandMonth.getStartDate(),monthOver);
		/*Integer illNum = ills.stream().filter(x->onHandMonth.getStartDate().getTime()<x.getBase().getBirthDay().getTime()
				&&monthOver.getTime()>x.getBase().getBirthDay().getTime()
				&&x.getOrg().getId()==org.getId()).collect(Collectors.toList()).size();*/
		entity.setDisease(illNum);
		//育种淘汰数量
		Integer breedNum = breedingWeedService.getRepository().findBreedNumByOrg_idAndBase_sexAndBase_birthDayBetween(org.getId(),onHandMonth.getSex(),onHandMonth.getStartDate(),monthOver);
		/*Integer breedNum = breeds.stream().filter(x->onHandMonth.getStartDate().getTime()<x.getBase().getBirthDay().getTime()
				&&monthOver.getTime()>x.getBase().getBirthDay().getTime()
				&&x.getOrg().getId()==org.getId()).collect(Collectors.toList()).size();*/
		entity.setBreeding(breedNum);
		//期末数量
	
		base.setBirthDayAssistEnd(onHandMonth.getEndDate());
		//entity.setEndNum(baseInfoService.find2(base).size());
		entity.setEndNum(baseInfoService.getRepository()
				.findFirstNumByFlayAndPhysiologyStatusAndOrg_idAndSexBirthDayEnd
				(org.getId(),onHandMonth.getSex(),onHandMonth.getEndDate()));
		   
		return entity;
	}
	
	/*@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,OnHandMonth onHandMonth,Organization org) throws IOException, ParseException{
		List<OnHandMonth> list = new ArrayList<OnHandMonth>();
		if(onHandMonth.getStartDate()!=null&&onHandMonth.getEndDate()!=null) {
			while(true) {
				//获取当月月底日期
				Date monthOver = monthFirst(onHandMonth.getStartDate());
				if(monthOver.getTime()>onHandMonth.getEndDate().getTime()) {
					list.add(operation(onHandMonth,onHandMonth.getEndDate(),org));
					break;
				}else {
					list.add(operation(onHandMonth,monthOver,org));
					onHandMonth.setStartDate(monthFirst(onHandMonth.getStartDate()));
				}
			}
		}
		Collections.reverse(list);
		
		ExportDate.writeExcel("onHandMonth",response,list,
				new String[]{"brithday","startNum","brithdayNum","allotInNum","purchase","allotOutNum","sale","death","disease","breeding","endNum"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"出生日期","期初数量","出生数量","调拨转入数量","购入","调拨转出数量","销售数量","死亡数量","疾病淘汰","育种淘汰","期末数量"});
		
	}*/
}
