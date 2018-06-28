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
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.entity.DeathDisposalReason;
import com.beiqisoft.aoqun.entity.IllnessWeed;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.IllnessWeedService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 疾病淘汰控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年12月23日上午10:51:00
 * */
@RestController
@RequestMapping(value = "illnessWeed")
public class IllnessWeedController extends BaseController<IllnessWeed,IllnessWeedService> {
	@JSON(type=BaseInfo.class,include="breed,code,rfid,birthDay,paddock,damCode,sireCode,sex,physiologyStatus,isAudit,breedingState")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name,contact")
	@JSON(type=Contact.class,include="firstName,abbreviation,contacts")
	@JSON(type=DeathDisposalReason.class,include="name")
	@RequestMapping(value ="list")
    public Page<IllnessWeed> list(IllnessWeed illnessWeed) throws InterruptedException{
		return page.pageAcquire(illnessWeedService.find(illnessWeed)).iteration(x->{
			x.setMoonAge(DateUtils.dateToAge(x.getDate(),x.getBase().getBirthDay()));
			x.getBase().setDamCode(x.getBase().getDam()==null?"":x.getBase().getDam().getCode());
			x.getBase().setSireCode(x.getBase().getSire()==null?"":x.getBase().getSire().getCode());
		});
    }
	
	/**
	 * 疾病淘汰导出
	 * */
	@RequestMapping(value ="export")
	public void export(HttpServletRequest request, HttpServletResponse response,IllnessWeed illnessWeed,int number) throws IOException{
		List<IllnessWeed> illnessWeeds= illnessWeedService.find(illnessWeed,PAGE_SIZE*number).getContent();
		for (IllnessWeed i:illnessWeeds){
			i.setCode(i.getBase().getCode());
			i.setSireCode(i.getBase().getSire()!=null?i.getBase().getSire().getCode():"");
			i.setDamCode(i.getBase().getDam()!=null?i.getBase().getDam().getCode():"");
			i.setBirthDay(DateUtils.DateToStr(i.getBase().getBirthDay()));
			i.setBreedName(i.getBase().getBreed().getBreedName());
			i.setSex(SystemM.PUBLIC_TRUE.equals(i.getBase().getSex())?"公羊":"母羊");
			i.setMoonAge(DateUtils.dateToAge(i.getDate(),i.getBase().getBirthDay()));
			i.setFatherReasonName(i.getFatherReason().getName());
			i.setReasonName(i.getReason().getName());
			i.setPaddockName(i.getPaddock()!=null?i.getPaddock().getName():"");
		}
		ExportDate.writeExcel("illnessWeed",response,illnessWeeds,
				new String[]{"code","sireCode","damCode","birthDay","breedName","sex","moonAge","date",
						"fatherReasonName","reasonName","treat","paddockName"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","公羊耳号","母羊耳号","出生日期","品种","性别","淘汰月龄","淘汰日期",
						"淘汰原因","淘汰详因","处理措施","所在圈舍"});
	}
	
	/**
	 * 疾病淘汰
	 * */
	@RequestMapping(value="add")
	public Message add(IllnessWeed illnessWeed,String earTag){
		 illnessWeedService.add(illnessWeed,earTag);
		 baseInfoService.moonAgeEdit(earTag, illnessWeed.getDate());
		 return SUCCESS;
	}
	
	/**
	 * 疾病淘汰
	 * */
	@RequestMapping(value="appAdd")
	public IllnessWeed appAdd(IllnessWeed illnessWeed,String earTag){
		baseInfoService.moonAgeEdit(earTag, illnessWeed.getDate());
		return illnessWeedService.add(illnessWeed,earTag);
	}
	
	/**
	 * 疾病淘汰校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String earTag){
		//校验方法一致
		return breedingWeedService.addVerify(earTag);
	}
	
	/**
	 * 修改删除校验
	 * */
	@RequestMapping(value="updateAndDelVerify/{id}")
	public Message updateAndDelVerify(@PathVariable Long id){
		return illnessWeedService.updateAndDelVerify(id);
	}
	
	/**
	 * 疾病删除
	 * */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return illnessWeedService.delete(id);
	}
	
	/**
	 * 疾病修改
	 * */
	@RequestMapping(value="update")
	public Message update(IllnessWeed illnessWeed){
		return illnessWeedService.update(illnessWeed);
	}
}
