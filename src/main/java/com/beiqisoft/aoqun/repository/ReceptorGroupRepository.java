package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.ReceptorGroup;

public interface ReceptorGroupRepository extends BaseRepository<ReceptorGroup>{

	ReceptorGroup findByBaseInfo_idAndProject_id(Long baseInfoId,Long projectId);
}
