package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Inventory;

public interface InventoryRepository extends BaseRepository<Inventory>{

	/**
	 * 盘点可用查询
	 * */
	List<Inventory> findByFlag(String publicTrue);
}
