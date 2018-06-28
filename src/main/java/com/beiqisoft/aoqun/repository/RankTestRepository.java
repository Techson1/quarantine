package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.RankTest;

public interface RankTestRepository extends BaseRepository<RankTest>{
	
	RankTest findByNameAndSexAndBreed_id(String name,String sex,Long breedId);
	
	/**
	 * 根据性别查找品种id
	 * */
	List<RankTest> findByPriceIsNullOrSexAndBreed_id(String sex,Long breedId);

	/**
	 * 根据品种性别查询
	 * */
	List<RankTest> findByBreed_idAndSex(Long id, String sex);

	/**
	 * 根据名称查询定级
	 * */
	RankTest findByNameAndRank(String name,String rank);
}
