package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.service.ParityService;

/**
 * 胎次访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "parity")
public class ParityController extends BaseController<Parity,ParityService> {
	@RequestMapping(value ="list")
    public Page<Parity> list(Parity parity) throws InterruptedException{
		return parityService.find(parity);
    }
}
