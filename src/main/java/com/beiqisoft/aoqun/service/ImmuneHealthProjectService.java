package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.ImmuneHealthProject;
import com.beiqisoft.aoqun.repository.ImmuneHealthProjectRepository;

public interface ImmuneHealthProjectService extends BaseService<ImmuneHealthProject, ImmuneHealthProjectRepository>{
	/**
	 * 分页获取用户对象
	 * @param immuneHealthProject 查询条件
	 * @return
	 */
	Page<ImmuneHealthProject> find(ImmuneHealthProject immuneHealthProject);
	
	Page<ImmuneHealthProject> find(ImmuneHealthProject immuneHealthProject, int pageNum);
	
}
