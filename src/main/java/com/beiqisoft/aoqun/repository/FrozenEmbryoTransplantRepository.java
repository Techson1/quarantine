package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.FrozenEmbryoTransplant;

public interface FrozenEmbryoTransplantRepository extends BaseRepository<FrozenEmbryoTransplant>{

	FrozenEmbryoTransplant findByReceptor_idAndProject_id(Long receptorId, Long projectId);

	/**
	 * 根据耳号查询id
	 * */
	@Query(value = "SELECT SUM(f.transNum) FROM FrozenEmbryoTransplant f WHERE f.frozenEmbryo.id=?1 AND f.id!=?2")
	Integer findByTransNum(Long frozenEmbryoId,Long id);

	/**
	 * 根据耳号查询id
	 * */
	@Query(value = "SELECT SUM(f.transNum) FROM FrozenEmbryoTransplant f WHERE f.frozenEmbryo.id=?1")
	Integer findByTransNumNotId(Long frozenEmbryoId);
	
	/**
	 * 根据冻胚制作记录查找冻胚移植记录
	 * */
	List<FrozenEmbryoTransplant> findByFrozenEmbryo_id(Long id);

	/**
	 * 根据移植序号及冻胚库存id查找
	 * */
	FrozenEmbryoTransplant findBySheetCodeAndFrozenEmbryo_id(String sheetCode,
			Long frozenEmbryoId);
}
