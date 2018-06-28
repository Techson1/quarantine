package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Formula;
import com.beiqisoft.aoqun.service.FormulaService;
@RestController
@RequestMapping(value = "formula")
public class FormulaController extends BaseController<Formula,FormulaService> {
	@RequestMapping(value ="list")
    public Page<Formula> list(Formula formula) throws InterruptedException{
		formula.setRecorder(null);
		return formulaService.find(formula);
    }
	
	/**
	 * 日粮配方名称校验类
	 * */
	@RequestMapping(value="nameVerify")
	public boolean nameVerify(String formulaName){
		return formulaService.getRepository().findByFormulaName(formulaName)==null;
	}
	
	/**
	 * 日粮配方存档修改
	 * */
	@RequestMapping(value="isUsedUpdate/{id}/{isUsed}")
	public Message isUsedUpdate(@PathVariable Long id, @PathVariable String isUsed){
		formulaService.getRepository().save(
				formulaService.getRepository().findOne(id).setIsUsedReturnThis(isUsed));
		return SUCCESS;
	}
	
	/**
	 * 日料配方校验
	 * */
	@RequestMapping(value="isUsedVerify/{id}")
	public boolean isUsedVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(formulaService.getRepository().findOne(id).getIsUsed());
	}
}
