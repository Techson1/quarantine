package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.MaterialDetail;

public interface MaterialDetailRepository extends BaseRepository<MaterialDetail>{

	/**
	 * 统计百分比
	 * */
	@Query(value="SELECT SUM(m.ratio) FROM MaterialDetail m WHERE m.material.id=?1")
	Double findByMateria(Long materiaId);

	/**
	 * 查找精料明细
	 * */
	List<MaterialDetail> findByMaterial_id(Long materialId);

	/**
	 * 查询名称是否存在
	 * */
	MaterialDetail findByMaterial_idAndBurden_id(Long materialId, Long burden);
}
