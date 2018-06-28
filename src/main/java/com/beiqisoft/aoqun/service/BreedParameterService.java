package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BreedParameter;
import com.beiqisoft.aoqun.repository.BreedParameterRepository;

public interface BreedParameterService extends BaseService<BreedParameter, BreedParameterRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedParameter 查询条件
	 * @return
	 */
	Page<BreedParameter> find(BreedParameter breedParameter);
	
	Page<BreedParameter> find(BreedParameter breedParameter, int pageNum);
	
}
