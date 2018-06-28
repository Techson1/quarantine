package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Weight;

public interface WeightRepository extends BaseRepository<Weight>{

	/**
	 * 根据
	 * */
	List<Weight> findByBase_idOrderByDayAgeDesc(Long id);

	/**
	 * 查询日期
	 * */
	@Query(value="FROM Weight w WHERE w.base.code= ?1 AND w.weighthDate >= ?2")
	List<Weight> findByWeight(String earTag,Date weighthDate);

	/**
	 * 查询
	 * */
	List<Weight> findByBase_codeOrderByWeighthDateDesc(String earTag);

	/**查找orgid为空的体重记录*/
	List<Weight> findByOrg_IdIsNull();

	/**
	 * 体重羊只状态查询
	 * */
	Weight findByBase_idAndType(Long id, String type);

	/**
	 * 羊只体重日期查询
	 * */
	Weight findByBase_idAndWeighthDate(Long id, Date weighthDate);
}
