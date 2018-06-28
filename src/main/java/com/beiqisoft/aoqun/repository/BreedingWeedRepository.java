package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BreedingWeed;

public interface BreedingWeedRepository extends BaseRepository<BreedingWeed>{
	@Query("select count(*) from BreedingWeed d where d.org.id=?1 And d.base.sex=?2 And d.base.birthDay between ?3 And ?4")
	Integer findBreedNumByOrg_idAndBase_sexAndBase_birthDayBetween(Long id, String sex, Date startDate, Date monthOver);

	List<BreedingWeed> findByBase_id(Long id);
}
