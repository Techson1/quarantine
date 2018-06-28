package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.ContactType;

public interface ContactTypeRepository extends BaseRepository<ContactType>{

	/**
	 * 供应商查询
	 * */
	ContactType findByType(String string);
}
