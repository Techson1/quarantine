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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.ReviseWigth;
import com.beiqisoft.aoqun.repository.ReviseWigthRepository;
import com.beiqisoft.aoqun.repository.WeightRepository;
import com.beiqisoft.aoqun.service.ReviseWigthService;

@Service
public class ReviseWigthServiceImpl extends BaseServiceIml<ReviseWigth,ReviseWigthRepository> implements ReviseWigthService{

	@Autowired
	public ReviseWigthRepository reviseWigthRepository;
	@Autowired
	public WeightRepository wightRepository;
	
	public Page<ReviseWigth> find(final ReviseWigth reviseWigth) {
		return reviseWigthRepository.findAll(new Specification<ReviseWigth>() {
			public Predicate toPredicate(Root<ReviseWigth> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(reviseWigth,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(reviseWigth.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ReviseWigth> find(ReviseWigth reviseWigth, int size) {
		return reviseWigthRepository.findAll(new Specification<ReviseWigth>() {
			public Predicate toPredicate(Root<ReviseWigth> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ReviseWigthRepository getRepository() {
		return reviseWigthRepository;
	}

	@Transactional
	@Override
	public void refresh(ReviseWigth reviseWigth) {
		//List<weight> wights=wightRepository.findByBase_idOrderByDayAgeDesc(reviseWigth.getBase().getId());
		//矫正数据刷新暂时不处理
	}

	@Override
	public void save(BaseInfo base) {
		ReviseWigth isReviseWigth = reviseWigthRepository.findByBase_id(base.getId());
		if(isReviseWigth==null){
			isReviseWigth = new ReviseWigth(base);
			reviseWigthRepository.save(isReviseWigth);
		}
		refresh(isReviseWigth);
	}
}
