package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.InventoryDifference;
import com.beiqisoft.aoqun.repository.InventoryDifferenceRepository;

public interface InventoryDifferenceService extends BaseService<InventoryDifference, InventoryDifferenceRepository>{
	/**
	 * 分页获取用户对象
	 * @param inventoryDifference 查询条件
	 * @return
	 */
	Page<InventoryDifference> find(InventoryDifference inventoryDifference);
	
	Page<InventoryDifference> find(InventoryDifference inventoryDifference, int pageNum);
	
}
