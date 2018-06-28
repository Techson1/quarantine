package com.beiqisoft.aoqun.controller.guide;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.guide.JoiningGuide;
import com.beiqisoft.aoqun.service.guide.JoiningGuideService;
@RestController
@RequestMapping(value = "joiningGuide")
public class JoiningGuideController extends BaseController<JoiningGuide,JoiningGuideService> {
	@RequestMapping(value ="list")
    public Page<JoiningGuide> list(JoiningGuide joiningGuide) throws InterruptedException{
		return joiningGuideService.find(joiningGuide);
    }
}
