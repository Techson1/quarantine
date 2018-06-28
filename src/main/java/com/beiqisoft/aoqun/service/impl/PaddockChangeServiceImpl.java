package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.PaddockChange;
import com.beiqisoft.aoqun.repository.PaddockChangeRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class PaddockChangeServiceImpl extends BaseServiceIml<PaddockChange,PaddockChangeRepository> implements PaddockChangeService{

	@Autowired
	public PaddockChangeRepository paddockChangeRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<PaddockChange> find(final PaddockChange paddockChange) {
		return paddockChangeRepository.findAll(new Specification<PaddockChange>() {
			public Predicate toPredicate(Root<PaddockChange> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(paddockChange,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				/*if ("0".equals(paddockChange.getType())){
					if (paddockChange.getFromPaddock()!=null && paddockChange.getFromPaddock().getId()!=null){
						Predicate p1=criteriaBuilder.equal(root.join("fromPaddock", JoinType.INNER).get("id"), paddockChange.getFromPaddock().getId());
						Predicate p2= criteriaBuilder.equal(root.join("toPaddock", JoinType.INNER).get("id"), paddockChange.getFromPaddock().getId());
						if (paddockChange.getBase()!=null && paddockChange.getBase().getCode()!=null && !"".equals(paddockChange.getBase().getCode())){
							Predicate p4= criteriaBuilder.like(root.join("base", JoinType.INNER).get("code"), paddockChange.getBase().getCode());
							Predicate or =criteriaBuilder.or(p1,p2);
							query.where(criteriaBuilder.and(or,p4));
						}
						else{
							query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
						}
					}
					else{
						if (paddockChange.getBase()!=null && paddockChange.getBase().getCode()!=null && !"".equals(paddockChange.getBase().getCode())){
							list.add(criteriaBuilder.like(root.join("base", JoinType.INNER).get("code"), paddockChange.getBase().getCode()));
						}
						query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
					}
				}*/
				/*else{
					if (paddockChange.getBase()!=null && paddockChange.getBase().getCode()!=null){
						list.add(criteriaBuilder.equal(root.join("base", JoinType.INNER).get("code"), paddockChange.getBase().getCode()));
					}
					if (paddockChange.getToPaddock()!=null && paddockChange.getToPaddock().getId()!=null){
						list.add(criteriaBuilder.equal(root.join("toPaddock", JoinType.INNER).get("id"), paddockChange.getToPaddock().getId()));
					}
					if (paddockChange.getFromPaddock()!=null && paddockChange.getFromPaddock().getId()!=null){
						list.add(criteriaBuilder.equal(root.join("fromPaddock", JoinType.INNER).get("id"), paddockChange.getFromPaddock().getId()));
					}
					query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				}*/
				return query.getRestriction();
			}
		},new PageRequest(paddockChange.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<PaddockChange> findByJpaRepository(PaddockChange paddockChange){
//		String hql = "FROM PaddockChange p where 1=1 ";
//		if (paddockChange.getBase()!=null && paddockChange.getBase().getCode()!=null){
//			hql+="AND p.base.code = "+paddockChange.getBase().getCode();
//		}
//		return PageImpl<PaddockChange>(new ArrayList<PaddockChange>);
		return null;
	}
	
	public Page<PaddockChange> find(PaddockChange paddockChange, int size) {
		return paddockChangeRepository.findAll(new Specification<PaddockChange>() {
			public Predicate toPredicate(Root<PaddockChange> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public PaddockChangeRepository getRepository() {
		return paddockChangeRepository;
	}

	@Transactional
	@Override
	public PaddockChange add(Paddock paddock, String earTag,Organization org,String recorder) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		if (paddock==null){
			return null;
		}
		if (paddock.equals(base.getPaddock())){
			return null;
		}
		PaddockChange paddockChange=new PaddockChange(base,paddock,recorder,org);
		paddockChange.setRecorder(recorder);
		paddockChangeRepository.save(paddockChange);
		base.setPaddock(paddock);
		baseInfoService.getRepository().save(base);
		return paddockChange;
		//return GlobalConfig.SUCCESS;
	}
	
	@Override
	public void addAll(Paddock fromPaddock, Paddock toPaddock,String recorder,Organization org) {
		List<PaddockChange> paddockList=new ArrayList<>();
		List<BaseInfo> baseList=baseInfoService.getRepository()
				.findByPaddock_idAndPhysiologyStatusAndFlag(
						fromPaddock.getId(),MyUtils.strToLong(SystemM.NORMAL),SystemM.PUBLIC_FALSE);
		for (BaseInfo base:baseList){
			paddockList.add(new PaddockChange(base,toPaddock,recorder,org));
			base.setPaddock(toPaddock);
		}
		paddockChangeRepository.save(paddockList);
		baseInfoService.getRepository().save(baseList);
	}

	@Override
	public Message addVerify(String earTag,Long paddockId) {
		BaseInfo base = baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return GlobalConfig.setAbnormal(earTag+":该羊不存在");
		}
		if (paddockId==null){
			return GlobalConfig.setAbnormal(earTag+":请选择圈舍");
		}
		if (base.getPaddock()!=null && base.getPaddock().getId().equals(paddockId)){
			return GlobalConfig.setAbnormal(earTag+":圈舍相同不能添加");
		}
		return baseInfoService.flagVerify(base);
	}

}
