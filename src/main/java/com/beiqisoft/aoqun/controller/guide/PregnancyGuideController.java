package com.beiqisoft.aoqun.controller.guide;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.guide.PregnancyGuide;
import com.beiqisoft.aoqun.service.guide.PregnancyGuideService;
@RestController
@RequestMapping(value = "pregnancyGuide")
public class PregnancyGuideController extends BaseController<PregnancyGuide,PregnancyGuideService> {
	@RequestMapping(value ="list")
    public Page<PregnancyGuide> list(PregnancyGuide pregnancyGuide) throws InterruptedException{
		return pregnancyGuideService.find(pregnancyGuide);
    }
}
