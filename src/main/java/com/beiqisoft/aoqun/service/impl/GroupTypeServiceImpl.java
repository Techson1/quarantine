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
import com.beiqisoft.aoqun.entity.GroupType;
import com.beiqisoft.aoqun.repository.GroupTypeRepository;
import com.beiqisoft.aoqun.service.GroupTypeService;

/**
 * @deprecated
 * */
@Service
public class GroupTypeServiceImpl extends BaseServiceIml<GroupType,GroupTypeRepository> implements GroupTypeService{

	@Autowired
	public GroupTypeRepository groupTypeRepository;
	
	public Page<GroupType> find(final GroupType groupType) {
		return groupTypeRepository.findAll(new Specification<GroupType>() {
			public Predicate toPredicate(Root<GroupType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(groupType,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(groupType.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<GroupType> find(GroupType groupType, int size) {
		return groupTypeRepository.findAll(new Specification<GroupType>() {
			public Predicate toPredicate(Root<GroupType> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public GroupTypeRepository getRepository() {
		return groupTypeRepository;
	}

	@Override
	public boolean SignVerify(GroupType groupType) {
		if (groupTypeRepository.findByPurposeAndSexAndBreed_idAndSign(
				groupType.getPurpose(),groupType.getSex(),groupType.getBreed().getId(), groupType.getSign())!=null){
			return false;
		}
		return true;
	}

	@Override
	public Page<GroupType> findByGroup(String breedId, String sex) {
		if (!"".equals(breedId)){
			
		}
		return null;
	}

}
