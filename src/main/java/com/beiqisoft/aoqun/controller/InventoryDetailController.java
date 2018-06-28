package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.InventoryDetail;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.PaddockChange;
import com.beiqisoft.aoqun.service.InventoryDetailService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "inventoryDetail")
public class InventoryDetailController extends BaseController<InventoryDetail,InventoryDetailService> {
	@JSON(type=BaseInfo.class,include="code,sex,birthDay")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=InventoryDetail.class,filter="org,inventory")
	@RequestMapping(value ="list")
    public Page<InventoryDetail> list(InventoryDetail inventoryDetail) throws InterruptedException{
		return inventoryDetailService.find(inventoryDetail);
    }
	
	/**
	 * 查找圈舍明细
	 * */
	@JSON(type=BaseInfo.class,include="code,sex,birthDay")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=InventoryDetail.class,include="id,base,toPaddock,fromPaddock")
	@RequestMapping(value="findByPaddock")
	public List<InventoryDetail> findByPaddock(Long inventoryId,Long paddockId){
		return inventoryDetailService.getRepository().findByInventoryAndPaddock(inventoryId,paddockId);
	}
	
	/**
	 * 查找差异
	 * */
	@JSON(type=BaseInfo.class,include="code,sex,birthDay")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=InventoryDetail.class,filter="org,inventory")
	@RequestMapping(value="findByPaddockChange")
	public Page<InventoryDetail> findByPaddockChange(Integer pageNum,Long inventoryId,Long paddockId){
		if (paddockId==null){
			return inventoryDetailService.getRepository()
					.findByInventory_id(inventoryId,pageable(pageNum, "toPaddock"));
		}else{
			return inventoryDetailService.getRepository()
					.findByInventory_id(inventoryId,paddockId, pageable(pageNum, "toPaddock"));
		}
		
	}
	
	/**
	 * 校验
	 * */
	@RequestMapping(value="verify")
	public Message verify(String code,Long paddockId,Long orgId){
		return inventoryDetailService.verify(code,paddockId,orgId);
	}
	
	/**
	 * 添加
	 * */
	@JSON(type=BaseInfo.class,include="code,sex,birthDay")
	@RequestMapping(value="add")
	public InventoryDetail add(String code,Long paddockId,Long inventoryId,String recorder){
	System.err.println(recorder);
		return inventoryDetailService.add(code,paddockId,inventoryId,recorder);
	}
	@RequestMapping("mydels")
	public Message mydels(String ids) {
		String[] idss = ids.split(",");
		for(int i=0;i<idss.length;i++) {
			InventoryDetail in = inventoryDetailService.getRepository().findOne(Long.parseLong(idss[i]));
			BaseInfo baseinfo = in.getBase();
			baseinfo.setPaddock(in.getFromPaddock());
			baseInfoService.getRepository().save(baseinfo);
			List<PaddockChange> paddockc = paddockChangeService.getRepository().findByBase_Id(in.getBase().getId());
			paddockChangeService.getRepository().delete(paddockc.get(0).getId());
			getRepository().delete(in.getId());
		}
		return SUCCESS;
	}
}
