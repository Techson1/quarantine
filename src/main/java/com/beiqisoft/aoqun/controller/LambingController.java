package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.Lambing;
import com.beiqisoft.aoqun.service.LambingService;

/**
 * 羔羊生产访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 * @deprecated
 */
@RestController
@RequestMapping(value = "lambing")
public class LambingController extends BaseController<Lambing,LambingService> {
	@RequestMapping(value ="list")
    public Page<Lambing> list(Lambing lambing) throws InterruptedException{
		return lambingService.find(lambing);
    }
}
