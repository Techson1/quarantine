package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Feeding;
import com.beiqisoft.aoqun.repository.FeedingRepository;

public interface FeedingService extends BaseService<Feeding, FeedingRepository>{
	/**
	 * 分页获取用户对象
	 * @param feeding 查询条件
	 * @return
	 */
	Page<Feeding> find(Feeding feeding);
	
	Page<Feeding> find(Feeding feeding, int pageNum);
	
}
