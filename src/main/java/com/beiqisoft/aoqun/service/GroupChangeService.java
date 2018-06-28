package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.GroupChange;
import com.beiqisoft.aoqun.repository.GroupChangeRepository;

public interface GroupChangeService extends BaseService<GroupChange, GroupChangeRepository>{
	/**
	 * 分页获取用户对象
	 * @param groupChange 查询条件
	 * @return
	 */
	Page<GroupChange> find(GroupChange groupChange);
	
	Page<GroupChange> find(GroupChange groupChange, int pageNum);

	/**
	 * 定级校验
	 * */
	Message verify(String earTag,Long rankId);
	
	/**
	 * 定级添加
	 * */
	GroupChange add(GroupChange groupChange, String earTag);

}
