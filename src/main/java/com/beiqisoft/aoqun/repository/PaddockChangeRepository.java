package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.PaddockChange;


public interface PaddockChangeRepository extends BaseRepository<PaddockChange>{
	
	@Query(value="SELECT new PaddockChange(p.base,max(p.date),p.toPaddock) FROM PaddockChange p GROUP BY p.base")
	List<PaddockChange> findBySql();

	List<PaddockChange> findByBase_Id(Long id);
}
