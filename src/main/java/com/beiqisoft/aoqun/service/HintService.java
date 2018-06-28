package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Hint;
import com.beiqisoft.aoqun.repository.HintRepository;

public interface HintService extends BaseService<Hint, HintRepository>{
	/**
	 * 分页获取用户对象
	 * @param hint 查询条件
	 * @return
	 */
	Page<Hint> find(Hint hint);
	
	Page<Hint> find(Hint hint, int pageNum);

	List<Hint> findByList(Hint hint);
	
}
