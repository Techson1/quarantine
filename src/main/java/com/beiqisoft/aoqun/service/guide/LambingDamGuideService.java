package com.beiqisoft.aoqun.service.guide;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.guide.LambingDamGuide;
import com.beiqisoft.aoqun.repository.guide.LambingDamGuideRepository;

public interface LambingDamGuideService extends BaseService<LambingDamGuide, LambingDamGuideRepository>{
	/**
	 * 分页获取用户对象
	 * @param lambingDam 查询条件
	 * @return
	 */
	Page<LambingDamGuide> find(LambingDamGuide lambingDamGuide);
	
	Page<LambingDamGuide> find(LambingDamGuide lambingDamGuide, int pageNum);
	
}
