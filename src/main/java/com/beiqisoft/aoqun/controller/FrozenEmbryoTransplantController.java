package com.beiqisoft.aoqun.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.FrozenEmbryoTransplant;
import com.beiqisoft.aoqun.service.FrozenEmbryoTransplantService;
@RestController
@RequestMapping(value = "frozenEmbryoTransplant")
public class FrozenEmbryoTransplantController extends BaseController<FrozenEmbryoTransplant,FrozenEmbryoTransplantService> {
	
	@RequestMapping(value ="list")
    public Page<FrozenEmbryoTransplant> list(FrozenEmbryoTransplant frozenEmbryoTransplant) throws InterruptedException{
		return frozenEmbryoTransplantService.find(frozenEmbryoTransplant);
    }
	
	/**
	 * 查询
	 * */
	@RequestMapping(value ="findByFrozenEmbryo")
    public List<FrozenEmbryoTransplant> findByFrozenEmbryo(Long frozenEmbryoId){
		if (frozenEmbryoId==null || "".equals(frozenEmbryoId)){
			return null;
		}
		return frozenEmbryoTransplantService.getRepository().findByFrozenEmbryo_id(frozenEmbryoId);
    }
	
	/**
	 * 冻胚移植添加
	 * */
	@RequestMapping(value="add")
	public Message add(String sheetCode,Integer transNum,Date date,Long frozenEmbryoId,Long projectId){
		return frozenEmbryoTransplantService.add(sheetCode,transNum,date,frozenEmbryoId,projectId);
	}
	
	/**
	 * 冻胚移植绑定校验
	 * */
	@RequestMapping(value="codeVerify")
	public Message codeVerify(Long id,String code){
		return frozenEmbryoTransplantService.codeVerify(id,code);
	}
	
	/**
	 * 冻胚绑定耳号
	 * */
	@RequestMapping(value="code")
	public Message code(Long id,String code){
		return frozenEmbryoTransplantService.code(id,code);
	}
	
	/**
	 * 冻胚页面修改校验
	 */
	@RequestMapping(value="updateUiVerify/{id}")
	public Message updateUiVerify(@PathVariable Long id){
		return frozenEmbryoTransplantService.updateUiVerify(id);
	}
	
	/**
	 * 冻胚修改校验
	 * */
	@RequestMapping(value="updateVerify")
	public Message updateVerify(Long id,String sheetCode,Integer transNum,Date date,String code){
		return frozenEmbryoTransplantService.updateVerify(id,sheetCode,transNum,date,code);
	}
	
	/**
	 * 冻胚修改
	 * */
	@RequestMapping(value="update")
	public Message update(Long id,String sheetCode,Integer transNum,Date date,String code){
		return frozenEmbryoTransplantService.update(id,sheetCode,transNum,date,code);
	}
	
	/**
	 * 冻胚删除校验
	 * */
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return frozenEmbryoTransplantService.delVerify(id);
	}
}