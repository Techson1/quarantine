package com.beiqisoft.aoqun.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.DeathDisposal;
import com.beiqisoft.aoqun.entity.rep.DelRep;
import com.beiqisoft.aoqun.repository.DeathDisposalRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.DeathdisposalService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;

@Service
public class DeathdisposalServiceImpl extends BaseServiceIml<DeathDisposal,DeathDisposalRepository> implements DeathdisposalService{

	@Autowired
	public DeathDisposalRepository deathdisposalRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@PersistenceContext  
	private EntityManager em;
	
	public Page<DeathDisposal> find(final DeathDisposal deathdisposal) {
		return deathdisposalRepository.findAll(new Specification<DeathDisposal>() {
			public Predicate toPredicate(Root<DeathDisposal> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(deathdisposal,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(deathdisposal.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "date"));
	}
	
	public Page<DeathDisposal> find(DeathDisposal deathdisposal, int size) {
		return deathdisposalRepository.findAll(new Specification<DeathDisposal>() {
			public Predicate toPredicate(Root<DeathDisposal> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "date"));
	}

	public DeathDisposalRepository getRepository() {
		return deathdisposalRepository;
	}

	@Override
	public DeathDisposal add(DeathDisposal deathdisposal, String earTag) {
		deathdisposal.setBase(baseInfoService.findByCodeOrRfid(earTag));
		deathdisposal.setPaddock(deathdisposal.getBase().getPaddock());
		deathdisposalRepository.save(deathdisposal);
		
		deathdisposal.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.STAY_DEATH));
		deathdisposal.getBase().setDeliveryDate(deathdisposal.getDate());
		deathdisposal.getBase().setIsAudit(SystemM.PUBLIC_FALSE);
		baseInfoService.getRepository().save(deathdisposal.getBase());
		return deathdisposal;
	}

	@Override
	public Message delete(Long id) {
		message = updateAndDelVerify(id);
		if (!message.isCodeEqNormal()){
			return message;
		}
		DeathDisposal deathdisposal=deathdisposalRepository.findOne(id);
		deathdisposal.getBase().setPhysiologyStatus(MyUtils.strToLong(SystemM.NORMAL));
		baseInfoService.getRepository().save(deathdisposal.getBase());
		deathdisposalRepository.delete(id);
		baseInfoService.moonAgeEdit(deathdisposal.getBase().getCode(), new Date());
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message update(DeathDisposal deathdisposal) {
		DeathDisposal nowDeathDisposal=deathdisposalRepository.findOne(deathdisposal.getId()).update(deathdisposal);
		deathdisposalRepository.save(nowDeathDisposal);
		
		nowDeathDisposal.getBase().setDeliveryDate(deathdisposal.getDate());
		baseInfoService.getRepository().save(nowDeathDisposal.getBase());
		baseInfoService.moonAgeEdit(nowDeathDisposal.getBase().getCode(), deathdisposal.getDate());
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateAndDelVerify(Long id) {
		DeathDisposal deathdisposal=deathdisposalRepository.findOne(id);
		if (deathdisposal.getBase()==null){
			return new Message(GlobalConfig.ABNORMAL,"数据错误,淘汰羊为空,请联系维护人员");
		}
		if (!SystemM.STAY_DEATH.equals(deathdisposal.getBase().getPhysiologyStatus()+"")){
			return new Message(GlobalConfig.ABNORMAL,"该记录只有为死亡待审核才能修改或删除");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public List<DeathDisposal> deathForm(String type, Date startDate,Date endDate) {
		
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DelRep> delRepList(String type, Date startDate, Date endDate,Long orgId) {
		  String hql ="SELECT new com.beiqisoft.aoqun.entity.rep.DelRep(t.fatherReason,COUNT(*)) FROM "+type+" t WHERE 1=1 ";
		  if (startDate!=null){
			  hql +=" AND date>='"+DateUtils.DateToStr(startDate)+"'";
		  }
		  if (endDate!=null){
			  hql +=" AND date<='"+DateUtils.DateToStr(endDate)+"'";
		  }
		  hql+=" AND t.org.id= "+orgId;
		  hql+=" GROUP BY t.fatherReason ";
		  List<DelRep> delReps= new PageImpl<DelRep>(em.createQuery(hql).getResultList()).getContent();
		  Long num=0L;
		  for (DelRep d:delReps){
			  num+=d.getCount();
		  }
		  for (DelRep d:delReps){
			  if (d.getCount()!=null || d.getCount()!=0){
				  double s=(d.getCount()*0.1)/(num*0.1)*100;
				  d.setProportion(String.format("%.2f", s)+"%");
			  }
			  else{
				  d.setProportion("0.00%");
			  }
		  }
		  List<DelRep> rep= MyUtils.listCope(delReps);
		  rep.add(new DelRep("全部",num,""));
		  return rep;
	}
}
