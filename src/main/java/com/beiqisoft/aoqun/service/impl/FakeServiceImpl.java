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
import com.beiqisoft.aoqun.entity.Fake;
import com.beiqisoft.aoqun.repository.FakeRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.FakeService;

@Service
public class FakeServiceImpl extends BaseServiceIml<Fake,FakeRepository> implements FakeService{

	@Autowired
	public FakeRepository fakeRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<Fake> find(final Fake fake) {
		return fakeRepository.findAll(new Specification<Fake>() {
			public Predicate toPredicate(Root<Fake> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(fake,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(fake.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Fake> find(Fake fake, int size) {
		return fakeRepository.findAll(new Specification<Fake>() {
			public Predicate toPredicate(Root<Fake> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FakeRepository getRepository() {
		return fakeRepository;
	}

	@Override
	public Message addVerify(String earTag, String sex) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		if (base==null){
			return GlobalConfig.setAbnormal("该羊不存在");
		}
		if (!sex.equals(base.getSex())){
			return GlobalConfig.setAbnormal("性别错误");
		}
		if (fakeRepository.findByBase_id(base.getId())!=null){
			return GlobalConfig.setAbnormal("该羊已有缺陷记录,不能再添加缺陷");
		}
		return GlobalConfig.SUCCESS;
	}

}
