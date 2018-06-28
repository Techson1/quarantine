package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Breed;

public interface BreedRepository extends BaseRepository<Breed>{
	
	/**
	 * 查询可以用的品种
	 * @param breedType
	 * 			品种类型
	 * @return List<Breed>
	 * */
	List<Breed> findByBreedType(String breedType);
	
	/**
	 * 查询品种名称
	 * @param breedName
	 * 			品种名称
	 * @return Breed
	 * */
	Breed findByBreedName(String breedName);
	
	/**
	 * 根据血统查询品种
	 * */
	Breed findByBreedIds(String blood);
	
	List<Breed> findByIdIn(Long[] ids);
}
