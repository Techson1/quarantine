package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Contact;
import com.beiqisoft.aoqun.repository.ContactRepository;

public interface ContactService extends BaseService<Contact, ContactRepository>{
	/**
	 * 分页获取用户对象
	 * @param contact 查询条件
	 * @return
	 */
	Page<Contact> find(Contact contact);
	
	Page<Contact> find(Contact contact, int pageNum);

	/**
	 * 员工列表
	 * */
	Page<Contact> contactList(Contact contact);
	
}
