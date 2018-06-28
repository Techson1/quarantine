package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BreedingOnHand;

public interface BreedingOnHandRepository extends BaseRepository<BreedingOnHand>{
	@Query(value="SELECT new BreedingOnHand(b,COUNT(*)) "
			+ "FROM BaseInfo b "
			+ "WHERE b.physiologyStatus = 1 AND b.flag = '0' "
			+ "GROUP BY b.org,b.breed,b.sex,b.breedingState")
	List<BreedingOnHand> findByStatistics();
}
