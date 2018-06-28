package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.ReviseWeight;
import com.beiqisoft.aoqun.repository.ReviseWeightRepository;

public interface ReviseWeightService extends BaseService<ReviseWeight, ReviseWeightRepository>{
	/**
	 * 分页获取用户对象
	 * @param reviseWeight 查询条件
	 * @return
	 */
	Page<ReviseWeight> find(ReviseWeight reviseWeight);
	
	Page<ReviseWeight> find(ReviseWeight reviseWeight, int pageNum);

	void anew(String earTag);
	
}
