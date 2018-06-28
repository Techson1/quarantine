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
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Allot;
import com.beiqisoft.aoqun.entity.AllotDetail;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.repository.AllotDetailRepository;
import com.beiqisoft.aoqun.repository.AllotRepository;
import com.beiqisoft.aoqun.repository.OrganizationRepository;
import com.beiqisoft.aoqun.repository.PaddockRepository;
import com.beiqisoft.aoqun.service.AllotDetailService;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.PaddockService;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class AllotDetailServiceImpl extends BaseServiceIml<AllotDetail,AllotDetailRepository> implements AllotDetailService{

	@Autowired
	public AllotDetailRepository allotDetailRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public AllotRepository allotRepository;
	@Autowired
	public OrganizationRepository organizationRepository;
	@Autowired
	public PaddockRepository paddockRepository;
	
	public Page<AllotDetail> find(final AllotDetail allotDetail) {
		return allotDetailRepository.findAll(new Specification<AllotDetail>() {
			public Predicate toPredicate(Root<AllotDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(allotDetail,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(allotDetail.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<AllotDetail> find(AllotDetail allotDetail, int size) {
		return allotDetailRepository.findAll(new Specification<AllotDetail>() {
			public Predicate toPredicate(Root<AllotDetail> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public AllotDetailRepository getRepository() {
		return allotDetailRepository;
	}

	/**
	 * 羊只转出明细添加校验
	 * */
	@Override
	public Message addVerify(String code,Long orgId,Long allotId) {
		message = baseInfoService.flagVerify(code);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (baseInfoService.findByCodeOrRfid(code).getOrg().getId()!=orgId){
			return GlobalConfig.setAbnormal("该羊只不存在,不能添加");
		}
		Long sum=allotDetailRepository.findByAllot_id(allotId);
		if (sum!=null && sum>500){
			return GlobalConfig.setAbnormal("调拨羊只不能大于500");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public AllotDetail add(String code, Long orgId, Long id,String recorder) {
		BaseInfo base = baseInfoService.findByCodeOrRfid(code);
		Allot allot=allotRepository.findOne(id);
		AllotDetail allotDetail = new AllotDetail();
		allotDetail.add(base,allot);
		allotDetail.setRecorder(recorder);
		base.setPhysiologyStatus(MyUtils.strToLong(SystemM.ALLOT_DETAIL));
		allotDetail.setToPaddock(paddockRepository.findByName("虚拟圈"));
		allotDetailRepository.save(allotDetail);
		baseInfoService.getRepository().save(base);
		return allotDetail;
	}

	@Override
	public AllotDetail audit(String flag, Long orgId,Long paddockId,String operator,AllotDetail allotDateil) {
		Organization org = organizationRepository.findOne(orgId);
		allotDateil.setFlag(flag);
		allotDateil.setOperator(operator);
		if (SystemM.PUBLIC_TRUE.equals(flag)){//复核
			Paddock paddock = paddockRepository.findOne(paddockId);
			allotDateil.getBase().setOrg(org);
			allotDateil.getBase().setPaddock(paddock);
			allotDateil.setToPaddock(paddock);
			allotDateil.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
			allotDateil.getBase().setOrg(org);
			allotDateil.getBase().setPaddock(paddock);
		}
		else{//取消复核
			allotDateil.getBase().setOrg(allotDateil.getAllot().getFromOrg());
			allotDateil.getBase().setPaddock(allotDateil.getFromPaddock());
			allotDateil.setToPaddock(null);
			allotDateil.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.ALLOT_DETAIL));
			allotDateil.getBase().setOrg(allotDateil.getAllot().getFromOrg());
			allotDateil.getBase().setPaddock(allotDateil.getFromPaddock());
		}
		baseInfoService.getRepository().save(allotDateil.getBase());
		allotDetailRepository.save(allotDateil);
		return allotDateil;
	}

	@Override
	public Message delete(Long id) {
		AllotDetail allotDetail = allotDetailRepository.findOne(id);
		if (!SystemM.PUBLIC_FALSE.equals(allotDetail.getFlag())){
			return GlobalConfig.setAbnormal("该羊已复核不能删除");
		}
		allotDetail.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		baseInfoService.getRepository().save(allotDetail.getBase());
		allotDetailRepository.delete(allotDetail);
		return GlobalConfig.SUCCESS;
	}
}
