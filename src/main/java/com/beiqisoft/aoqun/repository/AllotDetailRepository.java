package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.AllotDetail;

public interface AllotDetailRepository extends BaseRepository<AllotDetail>{

	/**
	 * 
	 * */
	AllotDetail findByAllot_idAndBase_id(Long allotId, Long baseId);

	@Query(value="SELECT COUNT(*) FROM AllotDetail a WHERE a.allot.id=?1")
	Long findByAllot_id(Long allotId);

	/**
	 * 根据羊只查询
	 * */
	List<AllotDetail> findByAllot_idAndFlag(Long id, String publicFalse);

	List<AllotDetail> findByBase_birthDayBetween(Date startDate, Date endDate);

	AllotDetail findByBase_code(String code);
}
