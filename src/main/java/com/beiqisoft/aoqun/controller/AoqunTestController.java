package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.AoqunTest;
import com.beiqisoft.aoqun.service.AoqunTestService;
@RestController
@RequestMapping(value = "aoqunTest")
public class AoqunTestController extends BaseController<AoqunTest,AoqunTestService> {
	@RequestMapping(value="list")
	 public Page<AoqunTest> list(AoqunTest aoqunTest) throws InterruptedException{
		 return aoqunTestService.find(aoqunTest);
    }
	/**
	 * 迭代器迭代
	 * */
	@RequestMapping(value ="list3")
    public Page<AoqunTest> list3(AoqunTest aoqunTest) throws InterruptedException{
		page.pageAcquire(aoqunTestService.find(aoqunTest));
		while (page.iterator().hasNext()){
			page.next();
			page.getEntity().setTest("已迭代器方式实现");
		}
		return page.getPage();
    }
	@RequestMapping(value ="list2")
    public Page<AoqunTest> list2(AoqunTest aoqunTest) throws InterruptedException{
		 return aoqunTestService.find(aoqunTest);
    }
	
}
