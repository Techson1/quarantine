package com.beiqisoft.aoqun.controller.guide;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.guide.JoiningLead;
import com.beiqisoft.aoqun.service.guide.JoiningLeadService;
@RestController
@RequestMapping(value = "joiningLead")
public class JoiningLeadController extends BaseController<JoiningLead,JoiningLeadService> {
	@RequestMapping(value ="list")
    public Page<JoiningLead> list(JoiningLead joiningLead) throws InterruptedException{
		return joiningLeadService.find(joiningLead);
    }
}
