package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.InventoryDifference;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.InventoryDifferenceService;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "inventoryDifference")
public class InventoryDifferenceController extends BaseController<InventoryDifference,InventoryDifferenceService> {
	@JSON(type=BaseInfo.class,include="code,rfid,sex,birthDay,breed,moonAge,paddock")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=InventoryDifference.class,filter="org")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Breed.class,include="breedName")
	@RequestMapping(value ="list")
    public Page<InventoryDifference> list(InventoryDifference inventoryDifference) throws InterruptedException{
		return inventoryDifferenceService.find(inventoryDifference);
    }
}
