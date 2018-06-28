package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.FrozenEmbryo;
import com.beiqisoft.aoqun.service.FrozenEmbryoService;
import com.beiqisoft.aoqun.util.json.JSON;
/**
 * 冻胚库存控制访问类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "frozenEmbryo")
public class FrozenEmbryoController extends BaseController<FrozenEmbryo,FrozenEmbryoService> {
	@RequestMapping(value ="list")
    public Page<FrozenEmbryo> list(FrozenEmbryo frozenEmbryo) throws InterruptedException{
		return frozenEmbryoService.find(frozenEmbryo);
    }
	
	
	/**
	 * 库存
	 * */
	@RequestMapping(value ="inventory")
    public Page<FrozenEmbryo> inventory(FrozenEmbryo frozenEmbryo) throws InterruptedException{
		frozenEmbryo.setIsOutsourcing(null);
		return frozenEmbryoService.find(frozenEmbryo);
    }
	
	/**
	 * 冻胚制作添加
	 * */
	@RequestMapping(value="add")
	public Message add(FrozenEmbryo frozenEmbryo){
		return frozenEmbryoService.add(frozenEmbryo);
	}
	
	/**
	 * 细管号查询
	 * */
	@JSON(type=FrozenEmbryo.class,include="id,frozenNumber,usableNumber")
	@RequestMapping(value="findByTubuleCode/{tubuleCode}")
	public FrozenEmbryo findByTubuleCode(@PathVariable String tubuleCode){
		return frozenEmbryoService.getRepository().findByTubuleCode(tubuleCode);
	}
	
	/**
	 * 胚胎数修改
	 * */
	@JSON(type=FrozenEmbryo.class,include="id,frozenNumber,usableNumber,realityNumber")
	@RequestMapping(value="updateNumber")
	public FrozenEmbryo updateNumber(Long frozenEmbryoId,Integer reality){
		FrozenEmbryo f=frozenEmbryoService.getRepository().findOne(frozenEmbryoId);
		f.setUsableNumberReturnThis(reality);
		frozenEmbryoService.getRepository().save(f);
		return f;
	}
	
	/**
	 * 外购登记添加
	 * */
	
	@RequestMapping(value="registerSave")
	public Message registerSave(FrozenEmbryo frozenEmbryo){
		frozenEmbryo.setEmbryoRegistration(embryoRegistrationService.getRepository()
				.findByCode(frozenEmbryo.getTubuleCode()));
		
		embryoRegistrationService.getRepository().save(frozenEmbryo.getEmbryoRegistration()
				.setIsUseReturnThis(SystemM.PUBLIC_TRUE,frozenEmbryo.getFreezeDate()));
		frozenEmbryoService.getRepository().save(frozenEmbryo.formatting());
		return SUCCESS;
	}
	
	/**
	 * 冻胚制作删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return frozenEmbryoService.delVerify(id);
	}
	
	/**
	 * 冻胚制作修改校验
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(FrozenEmbryo frozenEmbryo){
		return frozenEmbryoService.updateVerify(frozenEmbryo);
	}
	
	/**
	 * 冻胚制作修改
	 * */
	@RequestMapping(value="update")
	public Message update(FrozenEmbryo frozenEmbryo){
		return frozenEmbryoService.update(frozenEmbryo);
	}
}
