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
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.repository.RankTestRepository;
import com.beiqisoft.aoqun.service.RankTestService;

@Service
public class RankTestServiceImpl extends BaseServiceIml<RankTest,RankTestRepository> implements RankTestService{

	@Autowired
	public RankTestRepository rankTestRepository;
	
	public Page<RankTest> find(final RankTest rankTest) {
		return rankTestRepository.findAll(new Specification<RankTest>() {
			public Predicate toPredicate(Root<RankTest> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(rankTest,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(rankTest.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "breed","sex","price"));
	}
	
	public Page<RankTest> find(RankTest rankTest, int size) {
		return rankTestRepository.findAll(new Specification<RankTest>() {
			public Predicate toPredicate(Root<RankTest> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public RankTestRepository getRepository() {
		return rankTestRepository;
	}

}
