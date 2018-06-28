package com.beiqisoft.aoqun.repository;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.FormualDetail;

public interface FormualDetailRepository extends BaseRepository<FormualDetail>{

	/**
	 * 统计百分比
	 * */
	@Query(value="SELECT SUM(f.ratio) FROM FormualDetail f WHERE f.formula.id=?1")
	Double findByFormual(Long formualId);

	/**
	 * 查询配方及原料
	 * */
	FormualDetail findByFormula_idAndMaterial_id(Long formulaId,Long materialId);
}
