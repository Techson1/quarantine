package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.ContactType;
import com.beiqisoft.aoqun.service.ContactTypeService;

/**
 * 员工类型访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "contactType")
public class ContactTypeController extends BaseController<ContactType,ContactTypeService> {
	@RequestMapping(value ="list")
    public Page<ContactType> list(ContactType contactType) throws InterruptedException{
		return contactTypeService.find(contactType);
    }
	
	@RequestMapping(value="staffAll")
	public List<ContactType> staffAll(){
		List<ContactType> list= new ArrayList<ContactType>();
		for (ContactType c:contactTypeService.getRepository().findAll()){
			if (c.getType().equals("饲养员") || c.getType().equals("业务员")){
				list.add(c);
			}
		}
		return list;
	}
}
