package com.beiqisoft.aoqun.repository;

import java.util.List;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.ImmuneHealthProject;

public interface ImmuneHealthProjectRepository extends BaseRepository<ImmuneHealthProject>{

	ImmuneHealthProject findByImmName(String name);

	List<ImmuneHealthProject> findByFlag(String flag);
}
