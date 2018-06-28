package com.beiqisoft.aoqun.repository.guide;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.guide.PregnancyGuide;

public interface PregnancyGuideRepository extends BaseRepository<PregnancyGuide>{

	PregnancyGuide findByJoiningId(Long joining);
	
}
