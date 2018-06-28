package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.FeedGroup;
import com.beiqisoft.aoqun.service.FeedGroupService;
@RestController
@RequestMapping(value = "feedGroup")
public class FeedGroupController extends BaseController<FeedGroup,FeedGroupService> {
	@RequestMapping(value ="list")
    public Page<FeedGroup> list(FeedGroup feedGroup) throws InterruptedException{
		return feedGroupService.find(feedGroup);
    }
}
