package com.beiqisoft.aoqun.repository.guide;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.guide.LambingDamGuide;

public interface LambingDamGuideRepository extends BaseRepository<LambingDamGuide>{

	/**查询正确羔羊记录*/
	List<LambingDamGuide> findByFlag(String flag);
}
