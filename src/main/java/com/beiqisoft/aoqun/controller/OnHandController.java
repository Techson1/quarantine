package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.service.OnHandService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "onHand")
public class OnHandController extends BaseController<OnHand,OnHandService> {
	
	@JSON(type=OnHand.class,include="paddock,num")
	@JSON(type=Paddock.class,include="id,name")
	@RequestMapping(value ="list")
    public Page<OnHand> list(OnHand onHand,String date) throws InterruptedException{
		if (date==null || "".equals(date)) date=DateUtils.newDate();
		onHand.setStartDate(DateUtils.dateAddInteger(DateUtils.StrToDate(date), 0));
		onHand.setEndDate(DateUtils.dateAddInteger(DateUtils.StrToDate(date), 1));
		return onHandService.find(onHand);
    }
	
 
}
