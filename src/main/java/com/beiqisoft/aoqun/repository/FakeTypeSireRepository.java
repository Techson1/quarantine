package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.FakeTypeSire;

public interface FakeTypeSireRepository extends BaseRepository<FakeTypeSire>{
	
	List<FakeTypeSire> findByFlag(String flag);
	
	/**
	 * 根据公羊缺陷名称查找公羊
	 * 
	 * @param name
	 * 			公羊缺陷名称
	 * @return FakeTypeSire
	 */
	FakeTypeSire findByName(String name);
}
