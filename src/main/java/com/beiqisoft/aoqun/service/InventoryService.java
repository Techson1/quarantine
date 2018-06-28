package com.beiqisoft.aoqun.service;

import org.springframework.data.domain.Page;
import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Inventory;
import com.beiqisoft.aoqun.repository.InventoryRepository;

public interface InventoryService extends BaseService<Inventory, InventoryRepository>{
	/**
	 * 分页获取用户对象
	 * @param inventory 查询条件
	 * @return
	 */
	Page<Inventory> find(Inventory inventory);
	
	Page<Inventory> find(Inventory inventory, int pageNum);
	
}
