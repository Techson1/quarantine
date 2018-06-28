package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.beiqisoft.aoqun.entity.OnHand;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.repository.OrganizationRepository;
import com.beiqisoft.aoqun.repository.PaddockRepository;
import com.beiqisoft.aoqun.service.PaddockService;

@Service
public class PaddockServiceImpl extends BaseServiceIml<Paddock,PaddockRepository> implements PaddockService{

	@Autowired
	public PaddockRepository paddockRepository;
	
	@PersistenceContext  
	private EntityManager em;
	public Page<Paddock> find(final Paddock paddock) {
		return paddockRepository.findAll(new Specification<Paddock>() {
			
			public Predicate toPredicate(Root<Paddock> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(paddock,root,criteriaBuilder);
				
				if (paddock.getOrg()!=null && paddock.getOrg().getId()!=null){
					list.add(criteriaBuilder.equal(root.join("org", JoinType.INNER).get("id"), paddock.getOrg().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(paddock.getPageNum(), GlobalConfig.PAGE_SIZE,Sort.Direction.DESC,"flag","name"));
	}
	
	public Page<Paddock> find(Paddock paddock, int size) {
		return paddockRepository.findAll(new Specification<Paddock>() {
			public Predicate toPredicate(Root<Paddock> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public PaddockRepository getRepository() {
		return paddockRepository;
	}

	@Override
	public List<Paddock> paddockNumber() {
		return null;
	}

	@Override
	public List<OnHand> livestock(String name, Long orgId) {
		return paddockRepository.findByGroupByAndOrg(orgId);
	}

	@Override
	public List<Paddock> livestockTest(String name, Long orgId) {
		return paddockRepository.findAll((root,query,criteriaBuilder) ->{
			List<Predicate> list =new ArrayList<Predicate>();
			if (name!=null && !"".equals(name)){
				list.add(criteriaBuilder.like(
						root.get("name").as(String.class),"%"+ name + "%"));
			}
			list.add(criteriaBuilder.equal(root.get("flag").as(String.class),"1"));
			Join<Organization,OrganizationRepository> join = root.join("org", JoinType.INNER);
			if(orgId!=null) list.add(criteriaBuilder.equal(join.get("id"), orgId));
			query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
			query.orderBy(orders("ASC","name").queryOrder(root,criteriaBuilder));
			return query.getRestriction();
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Paddock> findByNameAndOrgId(String name, Long orgId) {
		String hql="FROM Paddock b "
				+ "WHERE b.org.id ="+orgId
				+ " order by b.flag desc,b.name asc";
		if (name!=null && !"".equals(name)){
			hql+="AND b.name ="+name;
		}
		
		return em.createQuery(hql).getResultList();
	}
}
