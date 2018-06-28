package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.entity.Role;
import com.beiqisoft.aoqun.repository.RoleRepository;
import com.beiqisoft.aoqun.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceIml<Role,RoleRepository> implements RoleService{

	@Autowired
	public RoleRepository roleRepository;
	
	public Page<Role> find(final Role role) {
		return roleRepository.findAll(new Specification<Role>() {
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = 	getEntityPredicate(role,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(role.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Role> find(Role role, int size) {
		return roleRepository.findAll(new Specification<Role>() {
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public RoleRepository getRepository() {
		return roleRepository;
	}

	@Override
	public List<Role> findByList(Role role) {
		return roleRepository.findAll((root,query,criteriaBuilder)-> {
			List<Predicate> list = 	getEntityPredicate(role,root,criteriaBuilder);
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		});
	}

}
