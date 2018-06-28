package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Joining;

public interface JoiningRepository extends BaseRepository<Joining>{
	
	Joining findByIsNewestJoiningAndParity_id(String IsNewestJoining,Long id);
	
	Joining findByParity_idAndJoiningSeq(Long parityId,String joiningSeq);
	
	Joining findByDam_idAndProject_id(Long damId,Long projectId);

	List<Joining> findByDam_id(Long id);
	
	@Query(value="SELECT joining_seq,count(id),"
			+ "sum(CASE result WHEN '2' THEN 1 END) AS p,"
			+ "sum(CASE result WHEN '3' THEN 1 END) AS notP "
			+ "FROM t_joining "
			+ "WHERE joining_date>=?1 AND joining_date<=?2 AND org_id=?3 "
			+ "GROUP BY joining_seq",nativeQuery=true)
	List<?> joiningRep(Date startDate,Date endDate,Long orgId);
	
	@Query(value="SELECT j.joiningSeq FROM Joining j WHERE j.parity.id=?1 AND j.isNewestJoining = '1' ")
	String joiningMaxparity(Long parityId);
}
