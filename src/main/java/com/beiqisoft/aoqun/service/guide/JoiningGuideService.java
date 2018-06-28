package com.beiqisoft.aoqun.service.guide;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.guide.JoiningGuide;
import com.beiqisoft.aoqun.repository.guide.JoiningGuideRepository;

public interface JoiningGuideService extends BaseService<JoiningGuide, JoiningGuideRepository>{
	/**
	 * 分页获取用户对象
	 * @param joiningGuide 查询条件
	 * @return
	 */
	Page<JoiningGuide> find(JoiningGuide joiningGuide);
	
	Page<JoiningGuide> find(JoiningGuide joiningGuide, int pageNum);
	
}
