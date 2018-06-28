package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.EmbryoRegistration;

public interface EmbryoRegistrationRepository extends BaseRepository<EmbryoRegistration>{

	EmbryoRegistration findByCode(String code);

	List<EmbryoRegistration> findByCodeIn(String[] codes);
	
	List<EmbryoRegistration> findByisUse(String isUse);

	/**
	 * 根据品种及未用查找
	 * */
	List<EmbryoRegistration> findByisUseAndBreed_id(String publicFalse,
			Long breedId);
}
