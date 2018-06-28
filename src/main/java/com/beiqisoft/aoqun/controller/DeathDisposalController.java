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
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.DeathDisposal;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.DeathdisposalService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;
/**
 * 死亡登记控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月23日上午12:23:29
 * */
@RestController
@RequestMapping(value = "deathdisposal")
public class DeathDisposalController extends BaseController<DeathDisposal,DeathdisposalService> {
	@JSON(type=BaseInfo.class,include="breed,code,rfid,birthDay,paddock,damCode,sireCode,sex,physiologyStatus,isAudit,breedingState")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name,contact")
	@JSON(type=DeathDisposalReason.class,include="name")
	@JSON(type=Contact.class,include="firstName")
	@RequestMapping(value ="list")
    public Page<DeathDisposal> list(DeathDisposal deathdisposal) throws InterruptedException{
		return page.pageAcquire(deathdisposalService.find(deathdisposal)).iteration(x ->{
			x.setMoonAge(DateUtils.dateToAge(x.getDate(),x.getBase().getBirthDay()));
			if (x.getBase()!=null){
				x.getBase().setDamCode(x.getBase().getDam()!=null?x.getBase().getDam().getCode():null);
				x.getBase().setSireCode(x.getBase().getSire()!=null?x.getBase().getSire().getCode():null);
			}
		});
    }
	
	/**
	 * 死亡登记导出
	 * */
	@RequestMapping(value="export")
	public void export(HttpServletRequest request, HttpServletResponse response,DeathDisposal deathdisposal,int number) throws IOException{
		List<DeathDisposal> deathDisposals= deathdisposalService.find(deathdisposal,PAGE_SIZE*number).getContent();
		for (DeathDisposal d:deathDisposals){
			d.setCode(d.getBase().getCode());
			d.setSireCode(d.getBase().getSire()!=null?d.getBase().getSire().getCode():"");
			d.setDamCode(d.getBase().getDam()!=null?d.getBase().getDam().getCode():"");
			d.setBirthDay(DateUtils.DateToStr(d.getBase().getBirthDay()));
			d.setBreedName(d.getBase().getBreed().getBreedName());
			d.setSex(SystemM.PUBLIC_SEX_SIRE.equals(d.getBase().getSex())?"公羊":"母羊");
			d.setMoonAge(DateUtils.dateToAge(d.getDate(),d.getBase().getBirthDay()));
			d.setReasonName(d.getReason()!=null?d.getReason().getName():"");
			d.setFatherReasonName(d.getFatherReason()!=null?d.getFatherReason().getName():"");
			if (d.getPaddock()!=null){
				d.setPaddockName(d.getPaddock().getName());
				d.setContactName(d.getPaddock().getContact()!=null?d.getPaddock().getContact().getFirstName():"");
			}
		}
		ExportDate.writeExcel("DeathDisposal",response,deathDisposals,
				new String[]{"code","sireCode","damCode","birthDay","breedName","sex","moonAge","reasonName",
						"fatherReasonName","date","treat","paddockName","recorder"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","公羊耳号","母羊耳号","出生日期","品种","性别","死亡时月龄","死亡原因",
						"死亡详因","死亡日期","处理措施","所在圈舍","操作人"});
	}
	
	/**
	 * 添加校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String earTag){
		return breedingWeedService.addVerify(earTag);
	}
	
	/**
	 * web添加
	 * */
	@RequestMapping(value="add")
	public Message add(DeathDisposal deathdisposal,String earTag){
		deathdisposalService.add(deathdisposal,earTag);
		baseInfoService.moonAgeEdit(earTag, deathdisposal.getDate());
		return SUCCESS;
	}
	
	/**
	 * app添加
	 * */
	@RequestMapping(value="appAdd")
	public DeathDisposal appAdd(DeathDisposal deathdisposal,String earTag){
		baseInfoService.moonAgeEdit(earTag, deathdisposal.getDate());
		return deathdisposalService.add(deathdisposal,earTag);
	}
	
	/**
	 * 死亡修改校验
	 * */
	@RequestMapping(value="updateAndDelVerify/{id}")
	public Message updateAndDelVerify(@PathVariable Long id){
		return deathdisposalService.updateAndDelVerify(id);
	}
	
	/**
	 * 删除
	 * */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return deathdisposalService.delete(id);
	}
	
	/**
	 * 死亡修改
	 * */
	@RequestMapping(value="update")
	public Message update(DeathDisposal deathdisposal){
		return deathdisposalService.update(deathdisposal);
	}
	
	/**
	 * 死亡统计
	 * */
	@JSON(type=DeathDisposal.class,include="reason,num,percent")
	@RequestMapping("deathForm")
	public List<DeathDisposal> deathForm(String type,Date startDate,Date endDate){
		return deathdisposalService.deathForm(type,startDate,endDate);
	}
}
