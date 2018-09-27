package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Contact;

public interface ContactRepository extends BaseRepository<Contact>{
	
	/**
	 * 根据员工名称查找
	 * */
	Contact findByFirstName(String firstName);

	/**
	 * 查找供应商
	 * */
	Contact findByFirstNameAndContactType_id(String supplierName, Long typeId);

	
	List<Contact> findByFlagAndContactType_id(String publicTrue, Long id);

	/**
	 * 增加场区区分，下拉饲养员
	 * @param publicTrue
	 * @param id
	 * @param orgId
	 * @return List<Contact>
	 */
	List<Contact> findByFlagAndContactType_idAndOrgId(String publicTrue, Long id,Long orgId);
	
	/**
	 * 分厂查询
	 * */
	List<Contact> findByFlagAndContactType_idAndOrg_id(String publicTrue, Long contactTypeId, Long OrgId);
}
