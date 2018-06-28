package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Pregnancy;

public interface PregnancyRepository extends BaseRepository<Pregnancy>{
	
	/**
	 * 查找已孕记录
	 * */
	Pregnancy findByResultAndParity_id(String result,Long parityId);
	
	/**
	 * 根据胎次及测孕次数查询
	 * */
	Pregnancy findByPregnancySeqAndParity_id(String seq,Long parityId);
	
	/**
	 * 根据胎次查询测孕记录并降序排序
	 * */
	
	List<Pregnancy> findByParity_idOrderByPregnancySeqDesc(Long parityId);
	
}
