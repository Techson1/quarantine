package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.RamTraining;

public interface RamTrainingRepository extends BaseRepository<RamTraining>{

	/**
	 * 根据羊只查询
	 * */
	List<RamTraining> findByRam_id(Long id);

	/**
	 *查询最大时间
	 * */
	//@Query(value="SELECT new RamTraining(r.assess,MAX(r.date)) FROM RamTraining r WHERE r.ram.id = ?1 ")
	@Query(value="select new RamTraining(t.assess,t.date) from RamTraining t where t.date = (select MAX(r.date) from RamTraining r where r.ram.id = ?1) ")
	RamTraining findByMaxDate(Long id);

	RamTraining findByRam_idAndDate(Long id, Date date);
}
