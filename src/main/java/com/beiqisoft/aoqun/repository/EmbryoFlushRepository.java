package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.EmbryoFlush;

public interface EmbryoFlushRepository extends BaseRepository<EmbryoFlush>{
	
	/**
	 * 根据羊只id项目id查询冲胚
	 * */
	EmbryoFlush findByDonor_idAndProject_id(Long donorId,Long projectId);
}
