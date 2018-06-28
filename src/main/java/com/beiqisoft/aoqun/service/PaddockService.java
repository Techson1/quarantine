package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.repository.PaddockRepository;

public interface PaddockService extends BaseService<Paddock, PaddockRepository>{
	/**
	 * 分页获取用户对象
	 * @param paddock 查询条件
	 * @return
	 */
	Page<Paddock> find(Paddock paddock);
	
	Page<Paddock> find(Paddock paddock, int pageNum);

	/**
	 * 存栏统计
	 * */
	List<Paddock> paddockNumber();

	/**
	 * 存栏统计查询
	 * */
	List<OnHand> livestock(String name, Long orgId);
	
	/**
	 * 
	 * */
	List<Paddock> livestockTest(String name, Long orgId);

	List<Paddock> findByNameAndOrgId(String name, Long orgId);
}
