package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.BaseGroup;
import com.beiqisoft.aoqun.repository.BaseGroupRepository;

public interface BaseGroupService extends BaseService<BaseGroup, BaseGroupRepository>{
	/**
	 * 分页获取用户对象
	 * @param baseGroup 查询条件
	 * @return
	 */
	Page<BaseGroup> find(BaseGroup baseGroup);
	
	Page<BaseGroup> find(BaseGroup baseGroup, int pageNum);

	/**
	 * 不分页查询
	 * */
	List<BaseGroup> findByList(BaseGroup baseGroup);
	
}
