package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.IllnessWeed;

public interface IllnessWeedRepository extends BaseRepository<IllnessWeed>{
	@Query("select count(*) from IllnessWeed d where d.org.id=?1 And d.base.sex=?2 And d.base.birthDay between ?3 And ?4")
	Integer findIllNumByOrg_idAndBase_sexAndBase_birthDayBetween(Long id, String sex, Date startDate, Date monthOver);

	List<IllnessWeed> findByBase_id(Long id);
}
