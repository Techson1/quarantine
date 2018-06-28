package com.beiqisoft.aoqun.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.InventoryDifference;

public interface InventoryDifferenceRepository extends BaseRepository<InventoryDifference>{
	@Modifying
	@Transactional
	@Query(value="DELETE FROM InventoryDifference i WHERE i.inventory.id=?1")
	void deletes(Long id);
}
