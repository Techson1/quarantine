package com.beiqisoft.aoqun.repository.guide;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.guide.JoiningGuide;

public interface JoiningGuideRepository extends BaseRepository<JoiningGuide>{

	@Query(value="SELECT j.damId FROM JoiningGuide j GROUP BY j.damId")
	List<Long> findByDam();
}
