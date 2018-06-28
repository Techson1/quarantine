package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.EmbryoRegistration;
import com.beiqisoft.aoqun.service.EmbryoRegistrationService;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 冻胚编码登记控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年12月5日下午5:10:00
 */
@RestController
@RequestMapping(value = "embryoRegistration")
public class EmbryoRegistrationController extends BaseController<EmbryoRegistration,EmbryoRegistrationService> {
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<EmbryoRegistration> list(EmbryoRegistration embryoRegistration) throws InterruptedException{
		return embryoRegistrationService.find(embryoRegistration);
    }
	
	/**
	 * 冻胚编码添加
	 * */
	@RequestMapping(value ="adds")
	public Message adds(EmbryoRegistration embryoRegistration){
		List<String> list=new ArrayList<String>();
		for (int i=0;i<embryoRegistration.getNumber();++i){
			list.add(embryoRegistrationService.add(embryoRegistration,i));
		}
		//return list.stream().filter(s -> s!=null).collect(Collectors.toList());
		return SUCCESS;
	}
	
	/**
	 * 冻胚编码校验
	 * */
	@JSON(type=EmbryoRegistration.class,include="code")
	@RequestMapping(value="addsVerify")
	public List<EmbryoRegistration> addsVerify(String prefix,String start,int number){
		String codes="";
		for (int i=0;i<number;++i){
			codes+=prefix+(Integer.parseInt(start)+i)+",";
		}
		return embryoRegistrationService.getRepository()
				.findByCodeIn(codes.substring(0,codes.length()-1).split(","));
	}
	
	/**
	 * 获取冻胚编码
	 * */
	@RequestMapping(value="getIsUse")
	public Message getIsUse(Long breedId){
		return embryoRegistrationService.findByOne(breedId);
	}
	
	/**
	 * 查找冻胚编码
	 * */
	@RequestMapping(value="findByCode")
	public EmbryoRegistration findByCode(String code){
		return embryoRegistrationService.getRepository().findByCode(code);
	}
}
