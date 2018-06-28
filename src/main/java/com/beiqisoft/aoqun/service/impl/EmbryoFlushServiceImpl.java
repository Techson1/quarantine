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
import com.beiqisoft.aoqun.entity.BreedingState;
import com.beiqisoft.aoqun.entity.EmbryoFlush;
import com.beiqisoft.aoqun.entity.EmbryoProject;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.repository.BreedingStateRepository;
import com.beiqisoft.aoqun.repository.EmbryoFlushRepository;
import com.beiqisoft.aoqun.repository.EmbryoProjectRepository;
import com.beiqisoft.aoqun.repository.EmbryoTransplantRepository;
import com.beiqisoft.aoqun.repository.FrozenEmbryoRepository;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.EmbryoFlushService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class EmbryoFlushServiceImpl extends BaseServiceIml<EmbryoFlush,EmbryoFlushRepository> implements EmbryoFlushService{

	@Autowired
	public EmbryoFlushRepository embryoFlushRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public JoiningRepository joiningRepository;
	@Autowired
	public BreedingStateRepository breedingStateRepository;
	@Autowired
	public ParityService parityService;
	@Autowired
	public EmbryoTransplantRepository embryoTransplantRepository;
	@Autowired
	public FrozenEmbryoRepository frozenEmbryoRepository;
	
	public Page<EmbryoFlush> find(final EmbryoFlush embryoFlush) {
		return embryoFlushRepository.findAll(new Specification<EmbryoFlush>() {
			public Predicate toPredicate(Root<EmbryoFlush> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(embryoFlush,root,criteriaBuilder);
				
				if (embryoFlush.getProject()!=null){
					Join<EmbryoProject,EmbryoProjectRepository> join = root.join("project", JoinType.INNER);
					list.add(criteriaBuilder.equal(join.get("id"), embryoFlush.getProject().getId()));
				}
				
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(embryoFlush.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<EmbryoFlush> find(EmbryoFlush embryoFlush, int size) {
		return embryoFlushRepository.findAll(new Specification<EmbryoFlush>() {
			public Predicate toPredicate(Root<EmbryoFlush> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public EmbryoFlushRepository getRepository() {
		return embryoFlushRepository;
	}

	@Override
	public Message addVerify(String damCode, Date date, Long projectId) {
		//一、校验耳号是否存在
		BaseInfo dam =baseInfoService.findByCodeOrRfid(damCode);
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,damCode+":该羊不存在");
		}
		//二、校验该羊的繁殖状态是否为AI
		if (!SystemM.BASE_INFO_BREEDING_STATE_DONOR_AI.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,damCode
					+":只有繁殖状态为AI的羊才可以冲胚,该羊的防止状态为:"
					+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		//三、校验该羊只是否在当前项目中
		
		Joining joining =joiningRepository.findByDam_idAndProject_id(dam.getId(), projectId);
		if (joining==null){
			return new Message(GlobalConfig.ABNORMAL,"该羊不在当前胚移项目中,不能添加");
		}
	    //四、校验冲胚日期是否大于AI日期
		if (DateUtils.dateSubDate(joining.getJoiningDate(), date)>=0){
			return new Message(GlobalConfig.ABNORMAL,"冲胚日期不能小于AI日期,该羊的AI日期为"
						+DateUtils.DateToStr(joining.getJoiningDate()));
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	public Message add(String donorCode, EmbryoFlush embryoFlush) {
		BaseInfo donor=baseInfoService.findByCodeOrRfid(donorCode);
		embryoFlush.setDonor(donor);
		embryoFlush.setJoining(joiningRepository
					.findByDam_idAndProject_id(donor.getId(), embryoFlush.getProject().getId()));
		embryoFlushRepository.save(embryoFlush);
		donor.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_DONOR_GET_WELL);
		baseInfoService.getRepository().save(donor);
		breedingStateRepository.save(new BreedingState(donor,embryoFlush.getDate(),
				SystemM.BASE_INFO_BREEDING_STATE_DONOR_GET_WELL));
		//修改胎次
		Parity parity=embryoFlush.getJoining().getParity();
		parity.setIsNewestParity(SystemM.PUBLIC_FALSE);
		parity.setIsClosed(SystemM.PUBLIC_TRUE);
		parity.setClosedDate(embryoFlush.getDate());
		//添加新胎次
		parityService.add(donor, parity);
		
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateVerify(EmbryoFlush embryoFlush) {
		Integer transNum = embryoTransplantRepository.findByTransNum(embryoFlush.getId());
		Integer frozenNumber = frozenEmbryoRepository.findByFrozenNumber(embryoFlush.getId());
		message =embryoFlush.updateVerify(transNum, frozenNumber);
		if(!message.isCodeEqNormal()){
			return message;
		}
		//TODO 后续还有判断
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message delVerify(Long id) {
		EmbryoFlush embryoFlush= embryoFlushRepository.findOne(id);
		if (!embryoTransplantRepository.findByEmbryoFlush_id(embryoFlush.getId()).isEmpty()){
			return GlobalConfig.setAbnormal("该羊存在鲜胚移植记录,不能删除");
		}
		if (!frozenEmbryoRepository.findByEmbryoFlush_id(embryoFlush.getId()).isEmpty()){
			return GlobalConfig.setAbnormal("该羊存在冻胚制作记录,不能删除");
		}
		return delete(embryoFlush);
	}
	
	@Transactional
	@Override
	public Message delete(EmbryoFlush embryoFlush) {
		baseInfoService.getRepository().save(embryoFlush.getDonor()
				.setBreedingStateReutrnThis(SystemM.BASE_INFO_BREEDING_STATE_DONOR_AI));
		embryoFlushRepository.delete(embryoFlush); 
		return GlobalConfig.SUCCESS;
	}
	
	
}
