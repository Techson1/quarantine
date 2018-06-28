package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BreedingState;
import com.beiqisoft.aoqun.repository.BreedingStateRepository;

public interface BreedingStateService extends BaseService<BreedingState, BreedingStateRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedingState 查询条件
	 * @return
	 */
	Page<BreedingState> find(BreedingState breedingState);
	
	Page<BreedingState> find(BreedingState breedingState, int pageNum);
	
}
