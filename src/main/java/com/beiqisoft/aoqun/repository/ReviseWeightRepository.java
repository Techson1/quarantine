package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.ReviseWeight;

public interface ReviseWeightRepository extends BaseRepository<ReviseWeight>{

	/**
	 * 根据羊只id查询校正体重数据
	 * */
	ReviseWeight findByBase_id(Long id);
}
