package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.FakeTypeSire;
import com.beiqisoft.aoqun.repository.FakeTypeSireRepository;

public interface FakeTypeSireService extends BaseService<FakeTypeSire, FakeTypeSireRepository>{
	/**
	 * 分页获取用户对象
	 * @param fakeTypeSire 查询条件
	 * @return
	 */
	Page<FakeTypeSire> find(FakeTypeSire fakeTypeSire);
	
	Page<FakeTypeSire> find(FakeTypeSire fakeTypeSire, int pageNum);
	
}
