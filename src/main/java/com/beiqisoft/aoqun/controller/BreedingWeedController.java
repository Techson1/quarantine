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
import com.beiqisoft.aoqun.entity.BreedingWeed;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.BreedingWeedService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;
/**
 * 育种淘汰控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年12月23日上午10:51:00
 * */
@RestController
@RequestMapping(value = "breedingWeed")
public class BreedingWeedController extends BaseController<BreedingWeed,BreedingWeedService> {
	@JSON(type=BaseInfo.class,include="breed,code,rfid,sex,birthDay,damCode,sireCode,paddock,isAudit,breedingState")
	@JSON(type=Paddock.class,include="name,contact")
	@JSON(type=DeathDisposalReason.class,include="name")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Contact.class,include="firstName,abbreviation,contacts")
	@RequestMapping(value ="list")
    public Page<BreedingWeed> list(BreedingWeed breedingWeed) throws InterruptedException{
		return page.pageAcquire(breedingWeedService.find(breedingWeed)).iteration(x->{
			x.setMoonAge(DateUtils.dateToAge(x.getDate(),x.getBase().getBirthDay()));
			if(x.getBase()!=null&&x.getBase().getDam()!=null&&x.getBase().getDam().getId()!=null){
				x.getBase().setDamCode(baseInfoService.getRepository().findOne(x.getBase().getDam().getId()).getCode());
			}
			if(x.getBase()!=null&&x.getBase().getSire()!=null&&x.getBase().getSire().getId()!=null){
				x.getBase().setSireCode(baseInfoService.getRepository().findOne(x.getBase().getSire().getId()).getCode());
			}
		});
    }	
	
	/**
	 * 育种淘汰导出
	 * */
	@RequestMapping(value="export")
	public void export(HttpServletRequest request, HttpServletResponse response,BreedingWeed breedingWeed,int number) throws IOException{
		List<BreedingWeed> breedingWeeds = breedingWeedService.find(breedingWeed,PAGE_SIZE*number).getContent();
		for (BreedingWeed b:breedingWeeds){
			b.setCode(b.getBase().getCode());
			b.setSireCode(b.getBase().getSire()!=null?b.getBase().getSire().getCode():"");
			b.setDamCode(b.getBase().getDam()!=null?b.getBase().getDam().getCode():"");
			b.setBirthDay(DateUtils.DateToStr(b.getBase().getBirthDay()));
			b.setBreedName(b.getBase().getBreed().getBreedName());
			b.setSex(SystemM.PUBLIC_SEX_SIRE.equals(b.getBase().getSex())?"公羊":"母羊");
			b.setMoonAge(DateUtils.dateToAge(b.getDate(),b.getBase().getBirthDay()));
			b.setReasonName(b.getReason()!=null?b.getReason().getName():"");
			b.setFatherReasonName(b.getFatherReason()!=null?b.getFatherReason().getName():"");
			b.setIsAudit(SystemM.PUBLIC_TRUE.equals(b.getBase().getIsAudit())?"是":"否");
			b.setPaddockName(b.getPaddock().getName());
		}
		ExportDate.writeExcel("breedingWeed",response,breedingWeeds,
				new String[]{"code","sireCode","damCode","birthDay","breedName","sex","moonAge",
						"date","fatherReasonName","reasonName","treat","paddockName","operator"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","公羊耳号","母羊耳号","出生日期","品种","性别","月龄","淘汰日期","淘汰原因",
						"淘汰详因","处理措施","所在圈舍","饲养员"});
	}
	
	/**
	 * 育种淘汰
	 * */
	@RequestMapping(value="add")
	public Message add(BreedingWeed breedingWeed,String earTag){
		breedingWeedService.add(breedingWeed,earTag);
		baseInfoService.moonAgeEdit(earTag, breedingWeed.getDate());
		return SUCCESS;
	}
	
	/**
	 * app育种淘汰
	 * */
	@RequestMapping(value="appAdd")
	public BreedingWeed appAdd(BreedingWeed breedingWeed,String earTag){
		baseInfoService.moonAgeEdit(earTag, breedingWeed.getDate());
		return breedingWeedService.add(breedingWeed,earTag);
	}
	
	/**
	 * 育种淘汰校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String earTag){
		return breedingWeedService.addVerify(earTag);
	}
	
	/**
	 * 育种淘汰修改删除校验
	 * */
	@RequestMapping(value="updateAndDelVerify/{id}")
	public Message updateAndDelVerify(@PathVariable Long id){
		return breedingWeedService.updateAndDelVerify(id);
	}
	
	/**
	 * 育种删除
	 * */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return breedingWeedService.delete(id);
	}
	
	/**
	 * 育种修改
	 * */
	@RequestMapping(value="update")
	public Message update(BreedingWeed breedingWeed){
		return breedingWeedService.update(breedingWeed);
	}
}
