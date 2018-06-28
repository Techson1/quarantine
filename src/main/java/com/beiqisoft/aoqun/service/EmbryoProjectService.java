package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;

public interface EmbryoProjectService extends BaseService<EmbryoProject, EmbryoProjectRepository>{
	/**
	 * 分页获取用户对象
	 * @param embryoProject 查询条件
	 * @return
	 */
	Page<EmbryoProject> find(EmbryoProject embryoProject);
	
	Page<EmbryoProject> find(EmbryoProject embryoProject, int pageNum);
	
}
