package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Performance;
import com.beiqisoft.aoqun.repository.PerformanceRepository;

public interface PerformanceService extends BaseService<Performance, PerformanceRepository>{
	/**
	 * 分页获取用户对象
	 * @param performance 查询条件
	 * @return
	 */
	Page<Performance> find(Performance performance);
	
	Page<Performance> find(Performance performance, int pageNum);
	
}
