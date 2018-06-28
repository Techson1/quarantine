package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.MonthRankOnHand;

public interface MonthRankOnHandRepository extends BaseRepository<MonthRankOnHand>{
	
	@Query(value="SELECT breed_name as breedName,sex,rank_name as rankName ,age,moon_age as moonAge,IF (moon_age >= 72, SUM(num), num) AS num "
			+ "FROM r_month_rank_on_hand WHERE breed_id = ?1 AND ctime	LIKE ?2 And org_id=?3 GROUP BY breed_id,sex,rank_name,age,moon_age",nativeQuery=true)
	List<?> findByList(Long breedId,String ctime,Long orgId);
	
}
