package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.EmbryoProject;

public interface EmbryoProjectRepository extends BaseRepository<EmbryoProject>{
	
	EmbryoProject findByProjectName(String projectName);

	List<EmbryoProject> findByStatus(String status);
}
