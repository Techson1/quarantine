package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BreedParameter;

public interface BreedParameterRepository extends BaseRepository<BreedParameter>{

	/**
	 * 根据名称查找
	 * */
	BreedParameter findByName(String string);
}
