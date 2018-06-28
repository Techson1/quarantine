package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Material;
import com.beiqisoft.aoqun.service.MaterialService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "material")
public class MaterialController extends BaseController<Material,MaterialService> {
	@RequestMapping(value ="list")
    public Page<Material> list(Material material) throws InterruptedException{
		return materialService.find(material);
    }
	
	/**
	 * 原料名称校验
	 * @param materialName
	 * 			原料名称
	 * @param org.id
	 * 			分厂id
	 * @return 名称不存在返回true,否则返回false
	 * */
	@RequestMapping(value="nameVerify")
	public boolean addVerify(Material material,Long id){
		Material materialt= materialService.getRepository()
				.findByMaterialNameAndOrg_id(material.getMaterialName(),material.getOrg().getId());
		 if (id == null && materialt!=null) return false;
			if (materialt!=null && !materialt.getId().equals(id)) 
				return false;
			return true;
	}
	
	/**
	 * 是否存档校验
	 * @param id
	 * @return 可用返回true 存档返回false
	 * */
	@RequestMapping(value="isUsedVerify/{id}")
	public boolean isUsedVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(materialService.getRepository().findOne(id).getIsUsed());
	}
	
	/**
	 * 是否存档修改
	 * */
	@RequestMapping(value="isUsedUpdate/{id}/{isUsed}")
	public Message isUsedUpdate(@PathVariable Long id,@PathVariable String isUsed){
		materialService.getRepository().save(
				materialService.getRepository().findOne(id).setIsUsedReturnThis(isUsed));
		return SUCCESS;
	}
	
	/**
	 * 纯种原料查询
	 * */
	@JSON(type=Material.class,filter="org")
	@RequestMapping(value="findByPurebred")
	public List<Material> findByPurebred(){
		return materialService.getRepository().findByIsUsedAndType(SystemM.PUBLIC_TRUE,SystemM.MATERIAL_BLOOD);
	}
	
	/**
	 * 原料查询
	 * */
	@JSON(type=Material.class,filter="org")
	@RequestMapping(value="findByAll")
	public List<Material> findByAll(){
		return  materialService.getRepository().findByIsUsed(SystemM.PUBLIC_TRUE);
	}
}
