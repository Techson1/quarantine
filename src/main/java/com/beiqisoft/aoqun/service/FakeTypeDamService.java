package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.FakeTypeDam;
import com.beiqisoft.aoqun.repository.FakeTypeDamRepository;

public interface FakeTypeDamService extends BaseService<FakeTypeDam, FakeTypeDamRepository>{
	/**
	 * 分页获取用户对象
	 * @param fakeTypeDam 查询条件
	 * @return
	 */
	Page<FakeTypeDam> find(FakeTypeDam fakeTypeDam);
	
	Page<FakeTypeDam> find(FakeTypeDam fakeTypeDam, int pageNum);
	
}
