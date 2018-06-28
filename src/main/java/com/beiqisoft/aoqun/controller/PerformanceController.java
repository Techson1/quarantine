package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.Performance;
import com.beiqisoft.aoqun.service.PerformanceService;

@RestController
@RequestMapping(value = "performance")
public class PerformanceController extends BaseController<Performance,PerformanceService>{
	
	@RequestMapping(value ="list")
    public Page<Performance> list(Performance performance) throws InterruptedException{
		return performanceService.find(performance);
    }
}
