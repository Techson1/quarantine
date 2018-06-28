package com.beiqisoft.aoqun.repository.guide;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.guide.JoiningLead;

public interface JoiningLeadRepository extends BaseRepository<JoiningLead>{
	
	@Query(value="SELECT MAX(j.lambing_dam_date) FROM JoiningLead j WHERE j.joiningDamId=?1")
	Date joiningMaxLambingDamDate(Long damId);
	
	@Query(value="FROM JoiningLead j WHERE j.joiningDamId=?1 AND j.joining_date<=?2")
	List<JoiningLead> joiningQuery(Long damId,Date lambingDamDate);
	
	@Query(value="FROM JoiningLead j WHERE j.joining_date>=?1")
	List<JoiningLead> joiningQueryDamDate(Date lambingDamDate);
	
	List<JoiningLead> findByType(String type);
	
	@Query(value="FROM JoiningLead j WHERE j.joiningDamId=?1 ORDER BY j.joining_date")
	List<JoiningLead> findByDamOrderByDate(Long damId);

	@Query(value="SELECT j.joiningDamId FROM JoiningLead j GROUP BY j.joiningDamId")
	List<Long> findByDam();

	@Query(value="FROM JoiningLead j WHERE j.joiningDamId = ?1 ORDER BY j.joining_date ASC")
	List<JoiningLead> findByJoiningDamId(Long damId);
}
