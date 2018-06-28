package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.rep.DamBreedStateRep;

public interface DamBreedStateRepRepository extends BaseRepository<DamBreedStateRep>{

	@Query(value="SELECT "+
			"org_id,"+
			"breed_id,"+
			"SUM(IF(LEFT (breeding_state, 2)='--',1,0)),"+
			"SUM(IF(LEFT (breeding_state, 2)='10',1,0)),"+
			"SUM(IF(LEFT (breeding_state, 2)='11',1,0)),"+
			"SUM(IF(LEFT (breeding_state, 2)='13',1,0)),"+
			"SUM(IF(LEFT (breeding_state, 2)='14',1,0)),"+
			"SUM(IF(LEFT (breeding_state, 2)='15',1,0)) "+
			"FROM t_base_info "+
			"WHERE physiology_status = 1 AND flag = 0 "+
			"GROUP BY org_id,breed_id ",nativeQuery=true)
	List<?> findByStatistics();

	/**
	 * 根据时间和分厂查询
	 * */
	@Query(value="FROM DamBreedStateRep d WHERE d.ctime >=?1 AND d.ctime < ?2 And org.id =?3")
	List<DamBreedStateRep> findByCtimeAndOrg_id(Date date,Date endDate, Long orgId);
	
	
}
