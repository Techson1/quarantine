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
import com.beiqisoft.aoqun.entity.BaseGroupDetail;
import com.beiqisoft.aoqun.repository.BaseGroupDetailRepository;
import com.beiqisoft.aoqun.service.BaseGroupDetailService;

@Service
public class BaseGroupDetailServiceImpl extends BaseServiceIml<BaseGroupDetail,BaseGroupDetailRepository> implements BaseGroupDetailService{

	@Autowired
	public BaseGroupDetailRepository baseGroupDetailRepository;
	
	public Page<BaseGroupDetail> find(final BaseGroupDetail baseGroupDetail) {
		return baseGroupDetailRepository.findAll(new Specification<BaseGroupDetail>() {
			public Predicate toPredicate(Root<BaseGroupDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(baseGroupDetail,root,criteriaBuilder);
				//TODO query.from(BaseGroupDetail.class).fetch("base", JoinType.LEFT);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(baseGroupDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<BaseGroupDetail> find(BaseGroupDetail baseGroupDetail, int size) {
		return baseGroupDetailRepository.findAll(new Specification<BaseGroupDetail>() {
			public Predicate toPredicate(Root<BaseGroupDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public BaseGroupDetailRepository getRepository() {
		return baseGroupDetailRepository;
	}

	@Override
	public List<BaseGroupDetail> findByList(BaseGroupDetail baseGroupDetail) {
		return baseGroupDetailRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(baseGroupDetail,root,criteriaBuilder);
			//root.fetch("base",JoinType.LEFT);
			//query.from(BaseGroupDetail.class).fetch("base",JoinType.INNER);//.fetch("baseGroup",JoinType.INNER);
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		});
	}

}
