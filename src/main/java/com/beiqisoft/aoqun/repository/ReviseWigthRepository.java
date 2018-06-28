package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.ReviseWigth;

public interface ReviseWigthRepository extends BaseRepository<ReviseWigth>{

	/**
	 * 根据羊只信息查找矫正体重
	 * */
	ReviseWigth findByBase_id(Long id);
}
