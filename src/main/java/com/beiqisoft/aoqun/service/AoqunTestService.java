package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.AoqunTest;
import com.beiqisoft.aoqun.repository.AoqunTestRepository;

public interface AoqunTestService extends BaseService<AoqunTest, AoqunTestRepository>{
	/**
	 * 分页获取用户对象
	 * @param aoqunTest 查询条件
	 * @return
	 */
	Page<AoqunTest> find(AoqunTest aoqunTest);
	
	Page<AoqunTest> find(AoqunTest aoqunTest, int pageNum);
	
	Page<AoqunTest> findTest(AoqunTest aoqunTest);
	
}
