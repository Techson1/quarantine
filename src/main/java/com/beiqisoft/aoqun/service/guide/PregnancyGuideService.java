package com.beiqisoft.aoqun.service.guide;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.guide.PregnancyGuide;
import com.beiqisoft.aoqun.repository.guide.PregnancyGuideRepository;

public interface PregnancyGuideService extends BaseService<PregnancyGuide, PregnancyGuideRepository>{
	/**
	 * 分页获取用户对象
	 * @param pregnancyGuide 查询条件
	 * @return
	 */
	Page<PregnancyGuide> find(PregnancyGuide pregnancyGuide);
	
	Page<PregnancyGuide> find(PregnancyGuide pregnancyGuide, int pageNum);
	
}
