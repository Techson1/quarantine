package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.FrozenStore;
import com.beiqisoft.aoqun.repository.FrozenStoreRepository;

public interface FrozenStoreService extends BaseService<FrozenStore, FrozenStoreRepository>{
	/**
	 * 分页获取用户对象
	 * @param frozenStore 查询条件
	 * @return
	 */
	Page<FrozenStore> find(FrozenStore frozenStore);
	
	Page<FrozenStore> find(FrozenStore frozenStore, int pageNum);
	
}
