package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Hint;
import com.beiqisoft.aoqun.service.HintService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "hint")
public class HintController extends BaseController<Hint,HintService> {
	@RequestMapping(value ="list")
    public Page<Hint> list(Hint hint) throws InterruptedException{
		return hintService.find(hint);
    }
	
	@JSON(type=Hint.class,include="name,parameter")
	@RequestMapping(value="findByList")
	public List<Hint> findByList(Hint hint){
		return hintService.findByList(hint);
	}
	
	/**
	 * 初始化
	 */
	@RequestMapping(value="init")
	public Message init(Hint hint){
		hintService.getRepository().findByOrg_id(hint.getOrg().getId()).forEach(hintService.getRepository()::delete);
		hintService.getRepository().save(new Hint(SystemM.NONPREGNANT_CROSS_HINT_DAY,10,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.UNPREGNANCY_CROSS_HINT_DAY,10,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.PREGNANCY_HINT_DAY,30,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.LAMBING_DAM_HINT_DAY,147,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.WEANING_HINT_DAY,30,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.RANK_HINT_DAY,130,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.WEIGHT_HINT_DAY,3,hint.getOrg()));
		hintService.getRepository().save(new Hint(SystemM.WARNING_NUMBER,5,hint.getOrg()));
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * */
	@RequestMapping(value="edit")
	public Message edit(Long id,Integer parameter,Integer hint){
		hintService.getRepository().save(hintService.getRepository().findOne(id)
				.setParameterReturnThis(parameter,hint));
		return SUCCESS;
	}
}
