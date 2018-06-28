package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.EmbryoTransplant;

public interface EmbryoTransplantRepository extends BaseRepository<EmbryoTransplant>{

	/**
	 *根据羊只及项目查找移植记录 
	 */
	EmbryoTransplant findByReceptor_idAndProject_id(Long receptorId, Long projectId);
	
	/**
	 * 根据冲胚id查询移植记录
	 * */
	@Query(value = "SELECT SUM(f.transNum) FROM EmbryoTransplant f WHERE f.embryoFlush.id=?1")
	Integer findByTransNum(Long id);

	List<EmbryoTransplant> findByEmbryoFlush_id(Long id);

	/**
	 * 根据移植序号和冲胚id查找
	 * */
	EmbryoTransplant findBySheetCodeAndEmbryoFlush_id(String sheetCode, Long embryoFlushId);

	/**
	 * 查找母羊移植次数
	 * */
	@Query(value="SELECT COUNT(*) FROM EmbryoTransplant f WHERE f.receptor.id=?1")
	Integer findByReceptorCount(Long receptorId);
	
	/**
	 * 查找母羊移植胚胎数
	 * */
	@Query(value="SELECT SUM(f.transNum) FROM EmbryoTransplant f WHERE f.receptor.id=?1")
	Integer findByReceptorTransNum(Long receptorId);
	
}
