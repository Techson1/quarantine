package com.beiqisoft.aoqun.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Looks;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.service.LooksService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "looks")
public class LooksController extends BaseController<Looks,LooksService> {
	@JSON(type=Looks.class,filter="org")
	@JSON(type=BaseInfo.class,include="code,rfid,breed,sex,rank,birthDay")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=RankTest.class,include="name")
	@RequestMapping(value ="list")
    public Page<Looks> list(Looks looks) throws InterruptedException{
		return page.pageAcquire(looksService.find(looks)).iteration(x -> {
			x.setAge(DateUtils.dateSubDate(x.getDate(), x.getBase().getBirthDay()));
			x.setCode(x.getBase().getCode());
			x.setBirthDay(x.getBase().getBirthDay());
			x.setSex(x.getBase().getSex());
			x.setBreed(x.getBase().getBreed().getBreedName());
			if(x.getBase().getRank()!=null) x.setRank(x.getBase().getRank().getName());
			x.setBaseOrgId(x.getBase().getOrg().getId());
			getRepository().save(x);
		});
    }
	
	@RequestMapping(value ="verify")
	public Message verify(Looks look){
		return looksService.verify(look.getCode(),currentUser().getOrganization().getId());
	}
	
	@RequestMapping(value ="add")
	public Message add(Looks look){
		BaseInfo base= baseInfoService.findByCodeOrRfid(look.getCode());
		Looks looked=looksService.getRepository().findByBase_id(base.getId());
		if (looked!=null){
			looked.edit(look);
			looked.setCtime(new Date());
			looked.setCode(base.getCode());
			looked.setBirthDay(base.getBirthDay());
			looked.setSex(base.getSex());
			looked.setBreed(base.getBreed().getBreedName());
			looked.setRank(base.getRank().getName());
			looked.setBaseOrgId(base.getOrg().getId());
			looksService.getRepository().save(looked);
		}else{
			look.setBase(base);
			look.setBase(base);
			look.setCode(base.getCode());
			look.setBirthDay(base.getBirthDay());
			look.setSex(base.getSex());
			look.setBreed(base.getBreed().getBreedName());
			look.setRank(base.getRank().getName());
			look.setBaseOrgId(base.getOrg().getId());
			looksService.getRepository().save(look);
		}
		return SUCCESS;
	}
	
	@RequestMapping(value ="appAdd")
	public Looks appAdd(Looks look){
		BaseInfo base= baseInfoService.findByCodeOrRfid(look.getCode());
		Looks looked=looksService.getRepository().findByBase_id(base.getId());
		if (looked!=null){
			looked.edit(look);
			looked.setCode(base.getCode());
			looked.setBirthDay(base.getBirthDay());
			looked.setSex(base.getSex());
			looked.setBreed(base.getBreed().getBreedName());
			looked.setRank(base.getRank().getName());
			looked.setBaseOrgId(base.getOrg().getId());
			looksService.getRepository().save(looked);
			return looked;
		}else{
			look.setBase(base);
			look.setCode(base.getCode());
			look.setBirthDay(base.getBirthDay());
			look.setSex(base.getSex());
			look.setBreed(base.getBreed().getBreedName());
			look.setRank(base.getRank().getName());
			look.setBaseOrgId(base.getOrg().getId());
			looksService.getRepository().save(look);
			
			return look;
		}
	}
}