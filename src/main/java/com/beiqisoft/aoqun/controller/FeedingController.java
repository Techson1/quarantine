package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.Feeding;
import com.beiqisoft.aoqun.service.FeedingService;
@RestController
@RequestMapping(value = "feeding")
public class FeedingController extends BaseController<Feeding,FeedingService> {
	@RequestMapping(value ="list")
    public Page<Feeding> list(Feeding feeding) throws InterruptedException{
		return feedingService.find(feeding);
    }
}
