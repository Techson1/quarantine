package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.ContactType;
import com.beiqisoft.aoqun.repository.ContactTypeRepository;

public interface ContactTypeService extends BaseService<ContactType, ContactTypeRepository>{
	/**
	 * 分页获取用户对象
	 * @param contactType 查询条件
	 * @return
	 */
	Page<ContactType> find(ContactType contactType);
	
	Page<ContactType> find(ContactType contactType, int pageNum);
	
}
