package com.beiqisoft.aoqun.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.entity.Role;
import com.beiqisoft.aoqun.repository.RoleRepository;

public interface RoleService extends BaseService<Role, RoleRepository>{
	/**
	 * 分页获取用户对象
	 * @param role 查询条件
	 * @return
	 */
	Page<Role> find(Role role);
	
	Page<Role> find(Role role, int pageNum);

	List<Role> findByList(Role role);
	
}
