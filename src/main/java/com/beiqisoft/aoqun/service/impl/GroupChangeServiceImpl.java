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
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.GroupChange;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.repository.GroupChangeRepository;
import com.beiqisoft.aoqun.repository.RankTestRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.GroupChangeService;
import com.beiqisoft.aoqun.service.PaddockChangeService;

@Service
public class GroupChangeServiceImpl extends BaseServiceIml<GroupChange,GroupChangeRepository> implements GroupChangeService{

	@Autowired
	public GroupChangeRepository groupChangeRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public RankTestRepository rankTestRepository;
	@Autowired
	public PaddockChangeService paddockChangeService;
	
	public Page<GroupChange> find(final GroupChange groupChange) {
		return groupChangeRepository.findAll(new Specification<GroupChange>() {
			public Predicate toPredicate(Root<GroupChange> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(groupChange,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(groupChange.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<GroupChange> find(GroupChange groupChange, int size) {
		return groupChangeRepository.findAll(new Specification<GroupChange>() {
			public Predicate toPredicate(Root<GroupChange> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public GroupChangeRepository getRepository() {
		return groupChangeRepository;
	}

	@Override
	public Message verify(String earTag,Long rankId) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		RankTest rank=rankTestRepository.findOne(rankId);
		List<GroupChange> groups = groupChangeRepository.findByBase_code(earTag);
		
		if (base==null){
			return GlobalConfig.setAbnormal("羊只不存在");
		}
		if(!groups.isEmpty()) {
			if(!groups.isEmpty()&&groups.get(0).getBase().getRank().getId()==rankId) {
			return GlobalConfig.setAbnormal("不可定为相同级别");
			}
		}
		
		if (rank.getSex()!=null && rank.getBreed()!=null){
			if (!base.getSex().equals(rank.getSex())){
				return GlobalConfig.setAbnormal("该羊性别有误,不可定级");
			}
			if (!base.getBreed().equals(rank.getBreed())){
				return GlobalConfig.setAbnormal("该羊品种有误,不可定级");
			}
		}
		if (base.getRank()==null){
			if (!rank.isRank(base.getGeneticLevel())){
				return GlobalConfig.setIsPass("该羊的基因或级别低于本次定级，是否执行本次定级");
			}
		}
		else if(!rank.isRank(base.getRank().getRank())){
			return GlobalConfig.setIsPass("该羊的基因或级别低于本次定级，是否执行本次定级");
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public GroupChange add(GroupChange groupChange, String earTag){
		BaseInfo base = baseInfoService.findByCodeOrRfid(earTag);
		groupChangeRepository.save(groupChange.setBaseReturnThis(base));
		baseInfoService.getRepository().save(base.setGeneticLevelReturnThis(groupChange.getToRank()));
		if(groupChange.getToPaddock()!=null && groupChange.getToPaddock().getId()!=null){
			paddockChangeService.add(groupChange.getToPaddock(), earTag, groupChange.getOrg(),groupChange.getRecorder());
		}
		return groupChange;
	}
}
