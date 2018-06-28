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
import com.beiqisoft.aoqun.entity.Abortion;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.service.AbortionService;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 流产访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time  2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "abortion")
public class AbortionController extends BaseController<Abortion,AbortionService> {
	@JSON(type=BaseInfo.class,include="code,paddock,physiologyStatus")
	@JSON(type=Paddock.class,include="name")	
	@JSON(type=Organization.class,include="brief")
	@JSON(type=Parity.class,include="parityMaxNumber")
	@RequestMapping(value ="list")
    public Page<Abortion> list(Abortion abortion) throws InterruptedException{
		return abortionService.find(abortion);
    }
	
	/**
	 * 添加校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(String codes,Date abortionDate){
		return abortionService.addVerify(codes, abortionDate);
	}
	
	/**
	 * 添加校验
	 * */
	@RequestMapping(value="addVerifys")
	public List<Message> addVerify(String[] codes,Date abortionDate){
		List<Message> list=new ArrayList<Message>();
		for (String code:codes){
			list.add(abortionService.addVerify(code,abortionDate));
		}
		return list;
	}
	
	/**
	 * 
	 * */
	@RequestMapping(value="appAdd")
	public Abortion appAdd(String codes,Abortion abortion){
		return abortionService.add(codes, new Abortion(abortion,codes));
	}
	
	/**
	 * 添加
	 * 一、添加流产
	 * 二、修改胎次为已关闭
	 * 三、修改羊只状态为流产/空怀
	 * 四、添加一个新胎次
	 * */
	@RequestMapping(value="adds")
	public Message adds(String[] codes,Abortion abortion){
		for (String code:codes){
			abortionService.add(code, new Abortion(abortion,code));
		}
		return SUCCESS;
	}
	
	/**
	 * 流产删除
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return abortionService.delVerify(id);
	}
	
	/**
	 * 流产界面修改校验
	 * */
	@RequestMapping(value="updateUiVerify/{id}")
	public Message updateUiVerify(@PathVariable Long id){
		return abortionService.updateUiVerify(id);
	}
	
	/**
	 * 流产修改校验
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(Long id ,Date abortionDate){
		return abortionService.updateVerify(id,abortionDate);
	}
	
	/**
	 * 流产修改
	 * */
	@RequestMapping(value="update")
	public Message update(Abortion abortion){
		return abortionService.update(abortion);
	}
}