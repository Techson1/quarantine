package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.FakeTypeDam;

public interface FakeTypeDamRepository extends BaseRepository<FakeTypeDam>{
	
	List<FakeTypeDam> findByFlag(String flag);
	
	
	/**
	 * 根据母羊缺陷名称查找公羊
	 * 
	 * @param name
	 * 			母羊缺陷名称
	 * @return FakeTypeSire
	 */
	FakeTypeDam findByName(String name);
}
