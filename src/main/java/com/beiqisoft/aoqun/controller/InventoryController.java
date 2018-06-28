package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Inventory;
import com.beiqisoft.aoqun.entity.InventoryDifference;
import com.beiqisoft.aoqun.service.InventoryService;
@RestController
@RequestMapping(value = "inventory")
public class InventoryController extends BaseController<Inventory,InventoryService> {
	@RequestMapping(value ="list")
    public Page<Inventory> list(Inventory inventory) throws InterruptedException{
		return inventoryService.find(inventory);
    }
	
	/**
	 * 盘点核对
	 * */
	@RequestMapping(value="check")
	public Message check(Long id){
		List<InventoryDifference> inventoryDifferences = new ArrayList<>();
		Inventory inventory= inventoryService.getRepository().findOne(id);
		//删除历史记录
		inventoryDifferenceService.getRepository().deletes(id);
		//核对
		baseInfoService.getRepository().findByCheck(inventory.getOrg().getId(),id)
			.forEach(x->inventoryDifferences.add(new InventoryDifference(inventory,x,"")));
		//保存核对
		inventoryDifferenceService.getRepository().save(inventoryDifferences);
		return SUCCESS;
	}
	
	/**
	 * 可用盘点查询
	 * */
	@RequestMapping(value="flagList")
	public List<Inventory> flagList(){
		return inventoryService.getRepository().findByFlag(SystemM.PUBLIC_TRUE);
	}
	
	/**盘点校验*/
	@RequestMapping(value="falgVerify/{id}")
	public boolean falgVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(inventoryService.getRepository().findOne(id).getFlag()); 
	}
	
	/**盘点修改*/
	@RequestMapping(value="flagUpdate/{id}/{flag}")
	public Message flagUpdate(@PathVariable Long id,@PathVariable String flag){
		inventoryService.getRepository().save(inventoryService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
	}
	@RequestMapping("mydels")
	public Message delVerify(String ids) {
		String[] idss = ids.split(",");
		for(int i=0;i<idss.length;i++) {
			
		}
		return SUCCESS;
	}
}
