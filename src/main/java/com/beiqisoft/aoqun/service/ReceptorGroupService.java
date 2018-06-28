package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.ReceptorGroup;
import com.beiqisoft.aoqun.repository.ReceptorGroupRepository;

public interface ReceptorGroupService extends BaseService<ReceptorGroup, ReceptorGroupRepository>{
	/**
	 * 分页获取用户对象
	 * @param receptorGroup 查询条件
	 * @return
	 */
	Page<ReceptorGroup> find(ReceptorGroup receptorGroup);
	
	Page<ReceptorGroup> find(ReceptorGroup receptorGroup, int pageNum);

	ReceptorGroup add(String code, Long projectId,String recorder);

	Message addVerify(String code, Long projectId);

	/**
	 * 受体复合
	 * */
	void updateFlag(Long parseLong, String flag);

	/**
	 * 删除校验
	 * */
	Message delVerify(Long id);

	/**
	 * 删除
	 * */
	Message delete(Long id);
	
	
	
}
