package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Parity;

public interface ParityRepository extends BaseRepository<Parity>{

	/**查询羊只最大胎次*/
	Parity findByIsNewestParityAndDam_id(String isNewestParity,Long damId);

	/**
	 * 胎次统计表
	 * */
	@Query(value="SELECT new Parity(p.id,p.dam.code,p.dam.breed.breedName,p.dam.birthDay,p.parityMaxNumber,"
			+ "p.startDate,p.closedDate) FROM Parity p WHERE p.closedDate>=?1 AND p.closedDate<=?2 AND p.dam.org.id=?3")
	List<Parity> findByParityStatistics(Date startDate, Date endDate,Long orgId);
}
