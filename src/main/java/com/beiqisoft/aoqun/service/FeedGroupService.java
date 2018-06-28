package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.FeedGroup;
import com.beiqisoft.aoqun.repository.FeedGroupRepository;

public interface FeedGroupService extends BaseService<FeedGroup, FeedGroupRepository>{
	/**
	 * 分页获取用户对象
	 * @param feedGroup 查询条件
	 * @return
	 */
	Page<FeedGroup> find(FeedGroup feedGroup);
	
	Page<FeedGroup> find(FeedGroup feedGroup, int pageNum);
	
}
