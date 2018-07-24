package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Weaning;
import com.beiqisoft.aoqun.service.WeaningService;
import com.beiqisoft.aoqun.util.json.JSON;
import com.beiqisoft.aoqun.vo.WeaningVo;

/**
 * 断奶访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "weaning")
public class WeaningController extends BaseController<Weaning,WeaningService> {
	@JSON(type=BaseInfo.class,include="code,breed")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<Weaning> list(Weaning weaning) throws InterruptedException{
		
		return weaningService.find(weaning);
    }
	@RequestMapping(value ="listAll")
    public Page<WeaningVo> listAll(Weaning weaning) throws InterruptedException{
		
		return weaningService.findPageWeaning(weaning);
    }
	/**
	 * 断奶校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String damCode,Date weaningDate){
		return weaningService.addVerify(damCode,weaningDate);
	}
	
	/**
	 * 断奶校验
	 * */
	@RequestMapping(value="addVerifys")
	public List<Message> addVerifys(String[] codes,Date weaningDate){
		List<Message> list=new ArrayList<Message>();
		for (String code:codes){
			list.add(weaningService.addVerify(code,weaningDate));
		}
		return list;
	}
	
	@RequestMapping(value="appAdd")
	public Weaning appAdd(String codes,Weaning weaning){
		return weaningService.add(codes,new Weaning(weaning));
	}
	
	/**
	 * 断奶添加
	 * 一、添加断奶
	 * 二、修改胎次
	 * 三、修改羊只
	 * 四、添加胎次
	 * */
	@RequestMapping(value="adds")
	public Message adds(String[] codes,Weaning weaning){
		for (String code:codes){
			weaningService.add(code, new Weaning(weaning));
		}
		return SUCCESS;
	}
	
	/**
	 * 断奶删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return weaningService.delVerify(id);
	}
	
	/**
	 *  断奶删除
	 * */
	@RequestMapping(value="delete/{id}")
	public Message delete(@PathVariable Long id){
		return weaningService.delete(id);
	}
	
	/**
	 * 断奶界面修改校验
	 * */
	@RequestMapping(value="updateUiVerify/{id}")
	public Message updateUiVerify(@PathVariable Long id){
		return weaningService.updateUiVerify(id);
	}
	
	/**
	 * 断奶修改校验
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(Long id ,Date weaningDate){
		return weaningService.updateVerify(id,weaningDate);
	}
	
	/**
	 * 断奶修改
	 * */
	@RequestMapping(value="update")
	public Message update(Weaning weaning){
		return weaningService.update(weaning);
	}
}
