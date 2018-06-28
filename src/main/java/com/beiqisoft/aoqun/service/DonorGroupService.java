package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.DonorGroup;
import com.beiqisoft.aoqun.repository.DonorGroupRepository;

public interface DonorGroupService extends BaseService<DonorGroup, DonorGroupRepository>{
	/**
	 * 分页获取用户对象
	 * @param donorGroup 查询条件
	 * @return
	 */
	Page<DonorGroup> find(DonorGroup donorGroup);
	
	Page<DonorGroup> find(DonorGroup donorGroup, int pageNum);

	/**
	 * 供体组群校验
	 * */
	Message addVerify(String code,Long projectId);

	/**
	 * 供体组群添加
	 * 一、添加供体组群
	 * 二、修改羊只繁殖状态为供体准备
	 * 三、修改该胎次,胎次类型改为供体,供体胎次数+1
	 * */
	DonorGroup add(String code, Long projectId,String recorder);

	/**
	 * 供体羊删除
	 * @param id
	 * 			供体组群id
	 * */
	Message delete(Long id);

	/**
	 * 供体删除校验
	 * @param id
	 * 			供体组群id
	 * */
	Message delVerify(Long id);

	/**
	 * 修改供体组群信息
	 * */
	void updateFlag(Long id, String flag);
	
}
