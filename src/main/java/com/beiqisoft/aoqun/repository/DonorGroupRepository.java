package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.DonorGroup;

public interface DonorGroupRepository extends BaseRepository<DonorGroup>{
	
	DonorGroup findByBaseInfo_idAndProject_id(Long damId,Long projectId);
}