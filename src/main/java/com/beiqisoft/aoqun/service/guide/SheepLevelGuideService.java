package com.beiqisoft.aoqun.service.guide;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.guide.SheepLevelGuide;
import com.beiqisoft.aoqun.repository.guide.SheepLevelGuideRepository;

public interface SheepLevelGuideService extends BaseService<SheepLevelGuide, SheepLevelGuideRepository>{
	/**
	 * 分页获取用户对象
	 * @param sheepLevelGuide 查询条件
	 * @return
	 */
	Page<SheepLevelGuide> find(SheepLevelGuide sheepLevelGuide);
	
	Page<SheepLevelGuide> find(SheepLevelGuide sheepLevelGuide, int pageNum);
	
}
