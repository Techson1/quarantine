package com.beiqisoft.aoqun.service.impl;

import java.util.Date;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.entity.FrozenEmbryo;
import com.beiqisoft.aoqun.entity.FrozenEmbryoTransplant;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;
import com.beiqisoft.aoqun.repository.FrozenEmbryoRepository;
import com.beiqisoft.aoqun.repository.FrozenEmbryoTransplantRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.PregnancyRepository;
import com.beiqisoft.aoqun.repository.ReceptorGroupRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.FrozenEmbryoTransplantService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class FrozenEmbryoTransplantServiceImpl extends BaseServiceIml<FrozenEmbryoTransplant,FrozenEmbryoTransplantRepository> implements FrozenEmbryoTransplantService{

	@Autowired
	public FrozenEmbryoTransplantRepository frozenEmbryoTransplantRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public ReceptorGroupRepository receptorGroupRepository;
	@Autowired
	public FrozenEmbryoRepository frozenEmbryoRepository;
	@Autowired
	public EmbryoProjectRepository embryoProjectRepository;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public PregnancyRepository pregnancyRepository;

	public Page<FrozenEmbryoTransplant> find(final FrozenEmbryoTransplant frozenEmbryoTransplant) {
		return frozenEmbryoTransplantRepository.findAll(new Specification<FrozenEmbryoTransplant>() {
			public Predicate toPredicate(Root<FrozenEmbryoTransplant> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(frozenEmbryoTransplant,root,criteriaBuilder);
				if (frozenEmbryoTransplant.getProject()!=null){
					Join<EmbryoProject,EmbryoProjectRepository> join = root.join("project", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), frozenEmbryoTransplant.getProject().getId()));
				}
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(frozenEmbryoTransplant.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<FrozenEmbryoTransplant> find(FrozenEmbryoTransplant frozenEmbryoTransplant, int size) {
		return frozenEmbryoTransplantRepository.findAll(new Specification<FrozenEmbryoTransplant>() {
			public Predicate toPredicate(Root<FrozenEmbryoTransplant> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public FrozenEmbryoTransplantRepository getRepository() {
		return frozenEmbryoTransplantRepository;
	}

	@Override
	public Message codeVerify(Long id, String code) {
		FrozenEmbryoTransplant f=frozenEmbryoTransplantRepository.findOne(id);
		f.setReceptor(baseInfoService.findByCodeOrRfid(code));
		if (f.getReceptor()==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊耳号不存在");
		}
		if (receptorGroupRepository.findByBaseInfo_idAndProject_id(
				f.getReceptor().getId(),f.getProject().getId())==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊不在受体组群中,不能添加");
		}
		if (frozenEmbryoTransplantRepository.findByReceptor_idAndProject_id(
				f.getReceptor().getId(),f.getProject().getId())!=null){
			return new Message(GlobalConfig.ABNORMAL,"该羊已做过鲜胚移植,不能绑定");
		}
		return GlobalConfig.SUCCESS;
	}
	
	@Transactional
	@Override
	public Message add(String sheetCode, Integer transNum, Date date,
			Long frozenEmbryoId, Long projectId) {
		if (save(sheetCode,transNum,date,frozenEmbryoId))
		
		//保存
		frozenEmbryoTransplantRepository.save(new FrozenEmbryoTransplant(
				sheetCode,transNum,date,new FrozenEmbryo(frozenEmbryoId),new EmbryoProject(projectId)));
		return GlobalConfig.SUCCESS;
	}
	
	public boolean save(String sheetCode,Integer transNum,Date date,Long frozenEmbryoId){
		FrozenEmbryoTransplant f=frozenEmbryoTransplantRepository
				.findBySheetCodeAndFrozenEmbryo_id(sheetCode,frozenEmbryoId);
		if (f!=null){
			f.setTransplantReturnThis(transNum,date,new FrozenEmbryo(frozenEmbryoId));
		}
		return true;
	}

	@Transactional
	@Override
	public Message code(Long id, String code) {
		//查找受体羊
		BaseInfo receptor= baseInfoService.findByCodeOrRfid(code);
		//保存冻胚记录
		frozenEmbryoTransplantRepository.save(
				frozenEmbryoTransplantRepository.findOne(id).setReceptorReturnThis(
						receptor,parityRepository.findByIsNewestParityAndDam_id(
								SystemM.PUBLIC_TRUE, receptor.getId())));
		//修改羊只繁殖状态为已移植
		baseInfoService.getRepository().save(receptor
				.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT));
		return GlobalConfig.SUCCESS;
	}
	
	@Override
	public Message delVerify(Long id) {
		FrozenEmbryoTransplant f=frozenEmbryoTransplantRepository.findOne(id);
		if (f.getParity()==null){
			return new Message(GlobalConfig.ABNORMAL,"数据错误,胎次为空,请联系维护人员");
		}
		if(!SystemM.PUBLIC_TRUE.equals(f.getParity().getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"该羊的胎次关闭,不能删除");
		}
		if(!pregnancyRepository.findByParity_idOrderByPregnancySeqDesc(f.getParity().getId()).isEmpty()){
			return new Message(GlobalConfig.ABNORMAL,"该羊已经测孕,删除测孕后才能删除该记录");
		}
		return delete(f);
	}

	@Transactional
	@Override
	public Message delete(FrozenEmbryoTransplant f) {
		//修改羊只繁殖状态
		baseInfoService.getRepository().save(
				f.getReceptor().setBreedingStateReutrnThis(
						SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_PREPARE));
		frozenEmbryoTransplantRepository.delete(f);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateUiVerify(Long id) {
		FrozenEmbryoTransplant f=frozenEmbryoTransplantRepository.findOne(id);
		if (f.getParity()==null){
			return new Message(GlobalConfig.ABNORMAL,"数据错误,胎次为空,请联系维护人员");
		}
		if(!SystemM.PUBLIC_TRUE.equals(f.getParity().getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,"该羊的胎次关闭,不能删除");
		}
		if(!pregnancyRepository.findByParity_idOrderByPregnancySeqDesc(f.getParity().getId()).isEmpty()){
			return new Message(GlobalConfig.ABNORMAL,"该羊已经测孕,删除测孕后才能删除该记录");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateVerify(Long id, String sheetCode, Integer transNum,
			Date date, String code) {
		//判断耳号是否可用修改
		FrozenEmbryoTransplant f=frozenEmbryoTransplantRepository.findOne(id);
		if (f.getReceptor()==null){
			return GlobalConfig.setAbnormal("数据关联确实,联系售后人员");
		}
		if (!f.getReceptor().isEarTag(code)){
			message=codeVerify(id,code);
			if (!message.isCodeEqNormal()){
				return message;
			}
		}
		//判断移植数量是否可以修改
		Integer hasTransNum=frozenEmbryoTransplantRepository.findByTransNum(f.getFrozenEmbryo().getId(), id);
		hasTransNum=hasTransNum==null?0:hasTransNum;
		if (f.getFrozenEmbryo().getUsableNumber()<(hasTransNum+transNum)){
			return GlobalConfig.setAbnormal("已用胚胎数不能大于细管胚胎数");
		}
		//判断日期
		if (DateUtils.dateSubDate(f.getFrozenEmbryo().getFreezeDate(),date)<=0){
			return GlobalConfig.setAbnormal("冻胚移植日期不能小于胚胎冷冻日期");
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public Message update(Long id, String sheetCode, Integer transNum,Date date, String code){
		FrozenEmbryoTransplant f=frozenEmbryoTransplantRepository.findOne(id);
		//f.setSheetCode(sheetCode);
		f.setTransNum(transNum);
		f.setDate(date);
		BaseInfo receptor= baseInfoService.findByCodeOrRfid(code);
		//保存冻胚记录
		frozenEmbryoTransplantRepository.save(
				frozenEmbryoTransplantRepository.findOne(id).setReceptorReturnThis(
						receptor,parityRepository.findByIsNewestParityAndDam_id(
								SystemM.PUBLIC_TRUE, receptor.getId())));
		//修改羊只繁殖状态为已移植
		baseInfoService.getRepository().save(receptor
				.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_RECEPTOR_TRANSPLANT));
		return GlobalConfig.SUCCESS;
	}
}
