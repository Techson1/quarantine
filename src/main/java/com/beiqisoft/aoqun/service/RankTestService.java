package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.repository.RankTestRepository;

public interface RankTestService extends BaseService<RankTest, RankTestRepository>{
	/**
	 * 分页获取用户对象
	 * @param rankTest 查询条件
	 * @return
	 */
	Page<RankTest> find(RankTest rankTest);
	
	Page<RankTest> find(RankTest rankTest, int pageNum);
	
}
