package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Material;
import com.beiqisoft.aoqun.entity.MaterialDetail;
import com.beiqisoft.aoqun.service.MaterialDetailService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "materialDetail")
public class MaterialDetailController extends BaseController<MaterialDetail,MaterialDetailService> {
	@RequestMapping(value ="list")
    public Page<MaterialDetail> list(MaterialDetail materialDetail) throws InterruptedException{
		return materialDetailService.find(materialDetail);
    }
	
	/**
	 * 添加校验
	 * */
	@RequestMapping(value="addVerify")
	public Message addVerify(MaterialDetail materialDetail){
		return materialDetailService.addVerify(materialDetail);
	}
	
	/**
	 * 查询id
	 * */
	@JSON(type=MaterialDetail.class,filter="org,name")
	@JSON(type=Material.class,filter="org")
	@RequestMapping(value="findByDetail")
	public List<MaterialDetail> findByDetail(Long materialId){
		return materialDetailService.getRepository().findByMaterial_id(materialId);
	}
	
}
