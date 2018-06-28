package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.FrozenEmbryo;

public interface FrozenEmbryoRepository extends BaseRepository<FrozenEmbryo>{

	/**
	 * 根据细管编号查询
	 * */
	FrozenEmbryo findByTubuleCode(String tubuleCode);
	
	/**
	 * 根据冲胚id计算冲胚数
	 * */
	@Query(value = "SELECT SUM(f.frozenNumber) FROM FrozenEmbryo f WHERE f.embryoFlush.id=?1")
	Integer findByFrozenNumber(Long embryoFlushId);
	
	/**
	 * 根据冲胚id查找冻胚记录
	 * */
	List<FrozenEmbryo> findByEmbryoFlush_id(Long id);
}
