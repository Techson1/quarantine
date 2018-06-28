package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.ReviseWigth;
import com.beiqisoft.aoqun.service.ReviseWigthService;

/**
 * @deprecated
 * */
@RestController
@RequestMapping(value = "reviseWigth")
public class ReviseWigthController extends BaseController<ReviseWigth,ReviseWigthService> {
	@RequestMapping(value ="list")
    public Page<ReviseWigth> list(ReviseWigth reviseWigth) throws InterruptedException{
		return reviseWigthService.find(reviseWigth);
    }
	
	/**
	 * 刷新校正记录
	 * */
	@RequestMapping(value="refresh")
	public Message refresh(Long[] ids){
		for (Long id:ids){
			reviseWigthService.refresh(reviseWigthService.getRepository().findOne(id));
		}
		return SUCCESS;
	}
	
}
