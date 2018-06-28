package com.beiqisoft.aoqun.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import com.beiqisoft.aoqun.entity.FrozenStore;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.repository.FrozenStoreRepository;
import com.beiqisoft.aoqun.repository.OrganizationRepository;
import com.beiqisoft.aoqun.service.FrozenStoreService;

@Service
public class FrozenStoreServiceImpl extends BaseServiceIml<FrozenStore,FrozenStoreRepository> implements FrozenStoreService{

	@Autowired
	public FrozenStoreRepository frozenStoreRepository;
	
	public Page<FrozenStore> find(final FrozenStore frozenStore) {
		return frozenStoreRepository.findAll(new Specification<FrozenStore>() {
			public Predicate toPredicate(Root<FrozenStore> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(frozenStore,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				//动态查询分厂
				if (frozenStore.getOrg()!=null){
					Join<Organization,OrganizationRepository> join = root.join("org", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), frozenStore.getOrg().getId()));
				}
				
				return query.getRestriction();
			}
		},new PageRequest(frozenStore.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<FrozenStore> find(FrozenStore frozenStore, int size) {
		return frozenStoreRepository.findAll(new Specification<FrozenStore>() {
			public Predicate toPredicate(Root<FrozenStore> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FrozenStoreRepository getRepository() {
		return frozenStoreRepository;
	}

}
