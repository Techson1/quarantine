package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.service.ContactService;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 员工访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "contact")
public class ContactController extends BaseController<Contact,ContactService> {
	@RequestMapping(value ="list")
    public Page<Contact> list(Contact contact) throws InterruptedException{
		contact.setFlag(null);
		return contactService.find(contact);
    }
	
	@RequestMapping(value="contactList")
	public Page<Contact> contactList(Contact contact) throws InterruptedException{
		contact.setFlag(null);
		return contactService.contactList(contact);
	}
	
	@RequestMapping(value="breeder")
	public List<Contact> breeder(){
		return contactService.getRepository().findByFlagAndContactType_id(
				SystemM.PUBLIC_TRUE,contactTypeService.getRepository().findByType("饲养员").getId());
	}
	/**
	 * 增加场区区分
	 * @author json
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value="breederOrgid")
	public List<Contact> breederOrgid(Long orgId){
		return contactService.getRepository().findByFlagAndContactType_idAndOrgId(
				SystemM.PUBLIC_TRUE,contactTypeService.getRepository().findByType("饲养员").getId(),orgId);
	}
	/**
	 * 供应商查询
	 * */
	@RequestMapping(value ="supplierList")
    public Page<Contact> supplierList(Contact contact) throws InterruptedException{
		contact.setFlag(null);
		contact.setContactType(contactTypeService.getRepository().findByType("供应商"));
		return contactService.find(contact);
    }
	
	/**
	 * 
	 * */
	@RequestMapping(value="findBySalesman")
	public List<Contact> findBySalesman(Contact contact){
		return contactService.getRepository().findByFlagAndContactType_idAndOrg_id(
				SystemM.PUBLIC_TRUE,contactTypeService.getRepository()
				.findByType("业务员").getId(),contact.getOrg().getId());
	}
	
	/**
	 * 员工名称校验
	 * @param firstName
	 * 			员工名称
	 * @return boolean
	 * */
	 @RequestMapping(value ="firstNameVerify")
	 public boolean firstNameVerify(String firstName){
		 return contactService.getRepository().findByFirstName(firstName)==null;
	 }
	 
	 /**
	  * 供应商名称校验
	  * */
	 @RequestMapping(value="supplierVerify")
	 public boolean supplierVerify(String supplierName,Long id){
		 Long typeId= contactTypeService.getRepository().findByType("供应商").getId();
		 Contact contact = contactService.getRepository().findByFirstNameAndContactType_id(supplierName,typeId);
		 if (id == null && contact!=null) return false;
			if (contact!=null && !contact.getId().equals(id)) 
				return false;
			return true;
	 }
	 
	 /**
	  * 供应商查询
	  * */
	 @JSON(type=Contact.class,include="id,firstName,abbreviation,contacts")
	 @RequestMapping(value="findBySupplier")
	 public List<Contact> findBySupplier(){
		 return contactService.getRepository().findByFlagAndContactType_id(SystemM.PUBLIC_TRUE,contactTypeService.getRepository().findByType("供应商").getId());
	 }
	 
	 /**
	  * 供应商添加
	  * */
	 @RequestMapping(value="supplierSave")
	 public Message supplierSave(Contact contact,String supplierName){
		 contact.setFirstName(supplierName);
		 contact.setContactType(contactTypeService.getRepository().findByType("供应商"));
		 contactService.getRepository().save(contact);
		 return SUCCESS;
	 }
	 
	 /**
	  * 员工是否在职修改
	  * @param id
	  * 		员工id
	  * @param flag
	  * 		是否在职
	  * @return Message
	  * */
	 @RequestMapping(value ="falgSave/{id}/{flag}")
	 public Message falgSave(@PathVariable Long id,@PathVariable String flag){
		 contactService.getRepository().save(contactService.getRepository().findOne(id).setFlagReturnThis(flag));
		 return SUCCESS;
	 }
	 
	 /**
	  * 员工修改前置校验
	  * @param id
	  * 		员工id
	  * @return boolean
	  * 		flag为1是返回true，否则返回false
	  * */
	 @RequestMapping(value ="falgVerify/{id}")
	 public boolean falgVerify(@PathVariable Long id){
		return SystemM.PUBLIC_TRUE.equals(contactService.getRepository().findOne(id).getFlag());
	 }
	 
}
