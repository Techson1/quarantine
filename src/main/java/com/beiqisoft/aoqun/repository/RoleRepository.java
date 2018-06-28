package com.beiqisoft.aoqun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Role;

public interface RoleRepository extends BaseRepository<Role>{
	
	List<Role> findByName(String name);
	
	@Query(value="FROM Role r WHERE r.name=?1")
	Role findByNameV(String name);
	
	
	List<Role> findByType(Integer type);
}
