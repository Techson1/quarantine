package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BreedingWeed;
import com.beiqisoft.aoqun.repository.BreedingWeedRepository;

public interface BreedingWeedService extends BaseService<BreedingWeed, BreedingWeedRepository>{
	/**
	 * 分页获取用户对象
	 * @param breedingWeed 查询条件
	 * @return
	 */
	Page<BreedingWeed> find(BreedingWeed breedingWeed);
	
	Page<BreedingWeed> find(BreedingWeed breedingWeed, int pageNum);

	/**
	 * 育种淘汰添加校验
	 * */
	Message addVerify(String earTag);

	/**
	 * 育种淘汰添加
	 * */
	BreedingWeed add(BreedingWeed breedingWeed, String earTag);
	
	/**
	 * 育种淘汰修改校验
	 * */
	Message updateAndDelVerify(Long id);

	/**
	 * 育种淘汰删除
	 * */
	Message delete(Long id);

	/**
	 * 育种淘汰修改
	 * */
	Message update(BreedingWeed breedingWeed);

	/**
	 * 育种淘汰页面校验
	 */
	Message updateUiverify(Long id);
	
}
