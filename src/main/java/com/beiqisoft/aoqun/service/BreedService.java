package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.repository.BreedRepository;

public interface BreedService extends BaseService<Breed, BreedRepository>{
	/**
	 * 分页获取用户对象
	 * @param breed 查询条件
	 * @return
	 */
	Page<Breed> find(Breed breed);
	
	Page<Breed> find(Breed breed, int pageNum);

	/**
	 * 根据连个品种对比系统并根据血统查询
	 * */
	Breed findByComparisonbreedIds(Breed damBreed,Breed sireBreed);
	
}
