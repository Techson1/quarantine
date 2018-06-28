package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Material;
import com.beiqisoft.aoqun.repository.MaterialRepository;

public interface MaterialService extends BaseService<Material, MaterialRepository>{
	/**
	 * 分页获取用户对象
	 * @param material 查询条件
	 * @return
	 */
	Page<Material> find(Material material);
	
	Page<Material> find(Material material, int pageNum);
	
}
