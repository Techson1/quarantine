package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.beiqisoft.aoqun.entity.Sales;
import com.beiqisoft.aoqun.entity.SalesDatail;
import com.beiqisoft.aoqun.repository.SalesDatailRepository;
import com.beiqisoft.aoqun.repository.SalesRepository;
import com.beiqisoft.aoqun.service.SalesService;

@Service
public class SalesServiceImpl extends BaseServiceIml<Sales,SalesRepository> implements SalesService{

	@Autowired
	public SalesRepository salesRepository;
	@Autowired
	public SalesDatailRepository salesDatailRepository;
	public Page<Sales> find(final Sales sales) {
		Page<Sales> page=salesRepository.findAll(new Specification<Sales>() {
			public Predicate toPredicate(Root<Sales> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(sales,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(sales.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
		List<Sales> list=page.getContent();
		if(null!=list &&list.size()>0) {
			for(int i=0;i<list.size();i++) {
				Sales sale=list.get(i);
				Integer checCount= salesDatailRepository.findQuerySalesId(sale.getId(),"1");//获取确认过的羊只数量
				sale.setCheckCount(checCount);
				//list.add(i, sale);
			}
		}
		return  page;
	}
	public Map<String,Object> findMapPage(final Sales sales) {
		Map<String,Object> map=new HashMap<String,Object>();
		Page<Sales> page=salesRepository.findAll(new Specification<Sales>() {
			public Predicate toPredicate(Root<Sales> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(sales,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(sales.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
		List<Sales> list=page.getContent();
		List<Sales> listRes=new ArrayList<Sales>();
		if(null!=list &&list.size()>0) {
			for(int i=0;i<list.size();i++) {
				Sales sale=list.get(i);
				Integer checCount= salesDatailRepository.findQuerySalesId(sale.getId(),"1");//获取确认过的羊只数量
				 
				map.put(String.valueOf(sale.getId()), checCount);
			}
		}
		
		map.put("page", page);
		
		return  map;
	}
	public Page<Sales> find(Sales sales, int size) {
		return salesRepository.findAll(new Specification<Sales>() {
			public Predicate toPredicate(Root<Sales> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public SalesRepository getRepository() {
		return salesRepository;
	}

}
