package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.LambingDam;
import com.beiqisoft.aoqun.entity.rep.LambingDamRep;

public interface LambingDamRepository extends BaseRepository<LambingDam>{
	
	/**
	 * 根据胎次查询产羔记录
	 * */
	LambingDam findByParity_id(Long parityId);
	
	@Query(value="SELECT new LambingDam( "
			+ "L.id,"
			//+ "j.joiningSeq,"
			+ "L.bornTimes,"
			+ "L.aliveCountF,"
			+ "L.aliveCountM,"
			+ "L.deadCountF,"
			+ "L.deadCountM,"
			+ "L.badCountF,"
			+ "L.badCountM ) "
			+ " FROM LambingDam L WHERE L.parity.id = ?1")
	//@Query(value="FROM LambingDam l WHERE l.parity.id = ?1")
	LambingDam findByParityId(Long ParityId);

	/**
	 * 查询耳号及生产日期
	 * */
	@Query(value="FROM LambingDam l WHERE (l.dam.code=?1 OR l.dam.rfid=?2) AND l.bornDate=?3 AND l.org.id=?4 ORDER BY ctime desc")
	List<LambingDam> findByCodeAndBornDate(String code,String rfid,Date bornDate,Long orgId);
	
	/**
	 * 根据日期查询
	 * */
	List<LambingDam> findByBornDateAndOrg_idOrderByCtimeDesc(Date lambingDate,Long orgId);

	/**
	 * 根据日期统计报表
	 * */
	@Query(value="SELECT new com.beiqisoft.aoqun.entity.rep.LambingDamRep("
			+ "l.joining.childBreed,"
			+ "count(*),"
			+ "sum(l.bornTimes),"
			+ "sum(l.aliveCountF+l.aliveCountM),"
			+ "sum(l.deadCountF+l.deadCountM),"
			+ "sum(l.badCountF+l.badCountM)) "
			+ "FROM LambingDam l "
			+ "WHERE l.bornDate >=?1 AND l.bornDate <=?2 AND l.org.id=?3 "
			+ "GROUP BY l.joining.childBreed")
	List<LambingDamRep> findByRep(Date startDate, Date endDate,Long orgId);

	LambingDam findByDam_idAndBornDate(Long damId,Date bornDate);
	@Query("select sum(a.aliveCountF+a.aliveCountM) from LambingDam a where a.org.id=?1 and a.bornDate between ?2 And ?3")
	Integer findByOrg_idAndBornDateBetween(Long id, Date startDate, Date endDate);
}
