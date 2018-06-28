package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Hint;

public interface HintRepository extends BaseRepository<Hint>{

	/**
	 * 根据分厂查询
	 * */
	List<Hint> findByOrg_id(Long id);
	
	/**
	 * 根据名称及分厂查询
	 * */
	Hint findByNameAndOrg_id(String name,Long orgId);
}
