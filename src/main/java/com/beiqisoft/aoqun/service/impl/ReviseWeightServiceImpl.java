package com.beiqisoft.aoqun.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.ReviseWeight;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.repository.ReviseWeightRepository;
import com.beiqisoft.aoqun.repository.WeightRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.ReviseWeightService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class ReviseWeightServiceImpl extends BaseServiceIml<ReviseWeight,ReviseWeightRepository> implements ReviseWeightService{

	@Autowired
	public ReviseWeightRepository reviseWeightRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public WeightRepository weightRepository;
	
	public Page<ReviseWeight> find(final ReviseWeight reviseWeight) {
		return reviseWeightRepository.findAll(new Specification<ReviseWeight>() {
			public Predicate toPredicate(Root<ReviseWeight> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(reviseWeight,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(reviseWeight.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<ReviseWeight> find(ReviseWeight reviseWeight, int size) {
		return reviseWeightRepository.findAll(new Specification<ReviseWeight>() {
			public Predicate toPredicate(Root<ReviseWeight> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public ReviseWeightRepository getRepository() {
		return reviseWeightRepository;
	}

	@Override
	public void anew(String earTag) {
		BaseInfo base =baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return ;
		}
		ReviseWeight revise = reviseWeightRepository.findByBase_id(base.getId());
		if(revise==null){
			revise = new ReviseWeight();
		}else {
			reviseWeightRepository.delete(revise);
			revise = new ReviseWeight();
		}
		revise.setBase(base);
		test(revise,base);
		reviseWeightRepository.save(revise);
	}
	
	public void test(ReviseWeight revise,BaseInfo base){
		List<Weight> list=weightRepository.findByBase_codeOrderByWeighthDateDesc(base.getCode());
		if (list.size()==0){
			return;
		}
		// 强制注释--->问题代码
		for (int i=0;i<list.size();i++){
			if (list.get(i).getDayAge()==null){
				list.remove(i);
			}
		}
		// 结束问题代码
		for (int i=3;i<=24;++i){
			int day=((int) (i*GlobalConfig.AVERAGE_MONTH)+1);
			List<Weight> minWeights= list.stream().filter(x -> day>x.getDayAge()).limit(1).collect(Collectors.toList());
			List<Weight> maxWeights= list.stream().filter(x -> day<=x.getDayAge()).collect(Collectors.toList());
			Weight minWeight=minWeights.isEmpty()?null:minWeights.get(0);
			Weight maxWeight=maxWeights.isEmpty()?null:maxWeights.get(maxWeights.size()-1);
			if (minWeight==null){
				minWeight=base.getWeight();
				if (minWeight==null){
					return ;
				}
			}
			if (maxWeight==null){
				return ;
			}
			//前重减后重
			double w=(MyUtils.strToDoubleSub(maxWeight.getWeights(), minWeight.getWeights()));
			//间隔天数
			int d = DateUtils.dateSubDate(maxWeight.getWeighthDate(), minWeight.getWeighthDate());
			//第一结果
			double sum=w/d;
			//月龄减前重
			int sumDay=(day-minWeight.getDayAge());
			//最终计算结果
			double k =sum * sumDay+MyUtils.strToDoble(minWeight.getWeights());
			revise.calculate(i, String.format("%.1f", k));
		}
	}

}
