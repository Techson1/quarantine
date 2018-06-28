package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.InventoryDetail;

public interface InventoryDetailRepository extends BaseRepository<InventoryDetail>{

	/**
	 * 根据羊只查找id
	 * */
	InventoryDetail findByBase_id(Long id);

	@Query(value="SELECT i.base.id FROM InventoryDetail i WHERE i.inventory.id=?1")
	List<Long> findByInventory(Long id);
	
	@Query(value="FROM InventoryDetail i WHERE i.inventory.id=?1")
	List<InventoryDetail> findByInventoryDetail(Long id);

	/**
	 * 查找圈舍明细
	 * */
	List<InventoryDetail> findByInventory_idAndFromPaddock_id(Long inventoryId, Long paddockId);
	
	/**
	 * 查找圈舍明细
	 * */
	List<InventoryDetail> findByInventory_idAndToPaddock_id(Long inventoryId, Long paddockId);

	/**
	 * 查找圈舍明细
	 */
	@Query(value="SELECT new InventoryDetail(i.id,i.base.code,i.base.sex,i.base.birthDay,i.toPaddock.name,i.fromPaddock.name) "
			+ "FROM InventoryDetail i  "
			+ "WHERE i.inventory.id=?1 AND i.toPaddock.id=?2")
	List<InventoryDetail> findByInventoryAndPaddock(Long inventoryId, Long paddockId);
	
	/**
	 * 
	 * */
	
	/**
	 * 差异查询
	 * */
	@Query(value="FROM InventoryDetail i WHERE i.inventory.id=?1 And i.fromPaddock != i.toPaddock")
	Page<InventoryDetail> findByInventory_id(Long inventoryId,Pageable pageable);
	
	/**
	 * 查询差异查询
	 * */
	@Query(value="FROM InventoryDetail i WHERE i.inventory.id=?1 And i.fromPaddock != i.toPaddock And i.fromPaddock.id =?2")
	Page<InventoryDetail> findByInventory_id(Long inventoryId,Long paddockId,Pageable pageable);
}
