package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.SalesDatail;
import com.beiqisoft.aoqun.repository.SalesDatailRepository;
import com.beiqisoft.aoqun.repository.SalesRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.SalesDatailService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class SalesDatailServiceImpl extends BaseServiceIml<SalesDatail,SalesDatailRepository> implements SalesDatailService{

	@Autowired
	public SalesDatailRepository salesDatailRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public SalesRepository salesRepository;
	
	public Page<SalesDatail> find(final SalesDatail salesDatail) {
		return salesDatailRepository.findAll(new Specification<SalesDatail>() {
			public Predicate toPredicate(Root<SalesDatail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(salesDatail,root,criteriaBuilder);
				
				if (salesDatail.getSales()!=null){
					list.add(criteriaBuilder.equal(root.join("sales", JoinType.INNER).get("id"), salesDatail.getSales().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(salesDatail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<SalesDatail> find(SalesDatail salesDatail, int size) {
		return salesDatailRepository.findAll(new Specification<SalesDatail>() {
			public Predicate toPredicate(Root<SalesDatail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public SalesDatailRepository getRepository() {
		return salesDatailRepository;
	}

	@Override
	public void add(SalesDatail salesDatail) {
		salesDatail.setItem(baseInfoService.findByCodeOrRfid(salesDatail.getCode()));
		//修改羊只信息
		salesDatail.getItem().setPhysiologyStatus(MyUtils.strToLong(SystemM.STAY_MARKET));
		baseInfoService.getRepository().save(salesDatail.getItem());
		//保存
		salesDatailRepository.save(salesDatail);
		//改变销售数量及金额
		salesRepository.save(salesRepository.findOne(salesDatail.getSales().getId()).add(salesDatail));
	}

	@Override
	public List<SalesDatail> findList(SalesDatail salesDatail) {
		return salesDatailRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(salesDatail,root,criteriaBuilder);
			
			if (salesDatail.getSales()!=null){
				list.add(criteriaBuilder.equal(root.join("sales", JoinType.INNER).get("id"), salesDatail.getSales().getId()));
			}
			
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		});
	}

	@Override
	public Message addVerify(SalesDatail salesDatail, String earTag) {
		BaseInfo base= baseInfoService.findByCodeOrRfid(earTag);
		if (base==null) return GlobalConfig.setAbnormal("该羊不存在");
		
		
		//判断是否定级
		if (base.getRank()==null) return GlobalConfig.setAbnormal("该羊没有添加定级");
		if (base.getRank().getPrice()==null) return GlobalConfig.setAbnormal("该羊的定级不是销售级,不能销售");
		
		//判断是否销售
		if (salesDatailRepository.findByItem_id(base.getId())!=null) 
			return GlobalConfig.setAbnormal("该羊已在销售列表中"); 
		//判断羊只是否在库
		return baseInfoService.flagVerify(base);
	}

	@Override
	public Message delete(Long[] ids) {
		List<BaseInfo> bases= new ArrayList<BaseInfo>();
		for (Long id:ids){
			SalesDatail salesDatail=salesDatailRepository.findOne(id);
			salesDatail.getItem().setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
			bases.add(salesDatail.getItem());
			salesRepository.save(salesDatail.getSales().sub(salesDatail));
			salesDatailRepository.delete(id);
		}
		baseInfoService.getRepository().save(bases);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Page<SalesDatail> inventory(SalesDatail salesDatail) {
		return salesDatailRepository.findAll((root,query,criteriaBuilder)->{
			List<Predicate> list = getEntityPredicate(salesDatail,root,criteriaBuilder);
			//list.add(criteriaBuilder.equal(root.join("sales", JoinType.INNER).get("checkFlag"), SystemM.PUBLIC_TRUE));
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			return query.getRestriction();
		},new PageRequest(salesDatail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}

	/**
	 * 复合
	 * */
	@Override
	public void audit(Long id,String flag) {
		baseInfoService.getRepository().save(
				salesDatailRepository.findBySales_id(id).stream()
					.map(x -> {
						x.getItem().setPhysiologyStatus(MyUtils.strToLong(flag));
						return x.getItem();
					}).collect(Collectors.toList()));
		
	}
}
