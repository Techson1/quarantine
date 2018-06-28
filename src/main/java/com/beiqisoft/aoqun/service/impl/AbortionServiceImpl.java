package com.beiqisoft.aoqun.service.impl;

import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Abortion;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Parity;
import com.beiqisoft.aoqun.entity.Pregnancy;
import com.beiqisoft.aoqun.repository.AbortionRepository;
import com.beiqisoft.aoqun.repository.BaseInfoRepository;
import com.beiqisoft.aoqun.repository.ParityRepository;
import com.beiqisoft.aoqun.repository.PregnancyRepository;
import com.beiqisoft.aoqun.service.AbortionService;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class AbortionServiceImpl extends BaseServiceIml<Abortion,AbortionRepository> implements AbortionService{

	@Autowired
	public AbortionRepository abortionRepository;
	@Autowired
	public ParityRepository parityRepository;
	@Autowired
	public BaseInfoRepository baseInfoRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	@Autowired
	public PregnancyRepository pregnancyRepository;
	@Autowired
	public ParityService parityService;
	@Autowired
	public PaddockChangeService paddockChangeService;
	
	public Page<Abortion> find(final Abortion abortion) {
		return abortionRepository.findAll(new Specification<Abortion>() {
			public Predicate toPredicate(Root<Abortion> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(abortion,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(abortion.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "ctime"));
	}
	
	public Page<Abortion> find(Abortion abortion, int size) {
		return abortionRepository.findAll(new Specification<Abortion>() {
			public Predicate toPredicate(Root<Abortion> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public AbortionRepository getRepository() {
		return abortionRepository;
	}
	
	
	public Message addVerify(String code, Date abortionDate) {
		BaseInfo dam =baseInfoService.findByCodeOrRfid(code);
		if (dam==null){
			return new Message(GlobalConfig.ABNORMAL,code+"该羊不存在");
		}
		if (!SystemM.PUBLIC_SEX_DAM.equals(dam.getSex())){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊性别错误");
		}
		if (!SystemM.BASE_INFO_BREEDING_STATE_GESTATION.equals(dam.peelBreedingState())){
			return new Message(GlobalConfig.ABNORMAL,code+":只有繁殖状态为妊娠的羊只,才可以添加流产记录,该羊的繁殖状态为:"
					+SystemM.baseInfoBreedingRturnChinese(dam.peelBreedingState()));
		}
		Parity nowParity =parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,dam.getId());
		if (nowParity==null){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊的胎次不存在,请联系维护人员");
		}
		if (!SystemM.PUBLIC_TRUE.equals(nowParity.getIsNewestParity())){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊的胎次已关闭,不能添加");
		}
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, nowParity.getId());
		if (pregnancy==null){
			return new Message(GlobalConfig.ABNORMAL,code+":该羊的测孕测孕记录为已孕才能流产");
		}
		if (DateUtils.dateSubDate(pregnancy.getPregnancyDate(), abortionDate)>=0){
			return new Message(GlobalConfig.ABNORMAL,code
				+":该羊的流产日期不能大于已孕日期,该羊的已孕日期为:"
					+DateUtils.DateToStr(pregnancy.getPregnancyDate()));
		}
		return baseInfoService.flagVerify(code);
	}

	@Transactional
	public Abortion add(String code, Abortion abortion){
		BaseInfo dam=baseInfoService.findByCodeOrRfid(code);
		Parity nowParity =parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,dam.getId());
		Abortion nowAbortion=new Abortion(abortion);
		nowAbortion.setDam(dam);
		nowAbortion.setParity(nowParity);
		nowAbortion.setPaddock(dam.getPaddock());
		if (nowAbortion.getToPaddock()!=null && nowAbortion.getToPaddock().getId()!=null){
			paddockChangeService.add(nowAbortion.getToPaddock(), code, nowAbortion.getOrg(),nowAbortion.getRecorder());
		}else{
			nowAbortion.setToPaddock(null);
		}
		//保存是否结果为流产
		abortionRepository.save(nowAbortion);
		
		//修改胎次
		nowParity.setGestation(DateUtils.dateSubDate(abortion.getAbortionDate(),
				pregnancyRepository.findByResultAndParity_id(
						SystemM.RESULTS_PREGNANCY, nowParity.getId()).getPregnancyDate()));
		nowParity.setIsNewestParity(SystemM.PUBLIC_FALSE);
		nowParity.setIsClosed(SystemM.PUBLIC_TRUE);
		nowParity.setClosedDate(nowAbortion.getAbortionDate());
		parityRepository.save(nowParity);
		//TODO 流产后,羊只的繁殖状态是否设置为空怀,是否存在流产恢复期
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT);
		baseInfoRepository.save(dam);
		//添加胎次
		parityService.add(dam,nowParity);
		//转栏
		return nowAbortion;
	}

	@Override
	public Message delVerify(Long id) {
		Abortion abortion=abortionRepository.findOne(id);
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,abortion.getDam().getId());
		//判断繁殖状态
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(abortion.getDam().peelBreedingState())){
			//TODO 删除说明没有写
			return new Message(GlobalConfig.ABNORMAL,"不能删除");
		}
		//判断胎次
		if (nowParity.getParityMaxNumber()-abortion.getParity().getParityMaxNumber()!=1){
			return new Message(GlobalConfig.ABNORMAL,"不能删除2Parity");
		}
		
		return delete(abortion);
	}

	@Transactional
	@Override
	public Message delete(Abortion abortion) {
		BaseInfo dam=abortion.getDam();
		Parity nowParity=abortion.getParity();
		//修改母羊繁殖状态
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, nowParity.getId());
		dam.setBreedingState(SystemM.BASE_INFO_BREEDING_STATE_GESTATION+pregnancy.getPregnancySeq());
		//修改胎次
		nowParity.setIsNewestParity(SystemM.PUBLIC_TRUE);
		nowParity.setIsClosed(SystemM.PUBLIC_FALSE);
		nowParity.setClosedDate(null);
		nowParity.setGestation(0);
		
		Parity delParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,abortion.getDam().getId());
		//删除最新胎次,并保存胎次繁殖状态
		parityRepository.delete(delParity);
		parityRepository.save(nowParity);
		baseInfoRepository.save(dam);
		
		abortionRepository.delete(abortion);
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateUiVerify(Long id) {
		Abortion abortion=abortionRepository.findOne(id);
		Parity nowParity=parityRepository.findByIsNewestParityAndDam_id(SystemM.PUBLIC_TRUE,abortion.getDam().getId());
		//判断繁殖状态
		if (!SystemM.BASE_INFO_BREEDING_STATE_NONPREGNANT.equals(abortion.getDam().peelBreedingState())){
			//TODO 删除说明没有写
			return new Message(GlobalConfig.ABNORMAL,"不能删除");
		}
		//判断胎次
		if (nowParity.getParityMaxNumber()-abortion.getParity().getParityMaxNumber()!=1){
			return new Message(GlobalConfig.ABNORMAL,"不能删除2Parity");
		}
		return GlobalConfig.SUCCESS;
	}

	@Override
	public Message updateVerify(Long id, Date data) {
		Pregnancy pregnancy=pregnancyRepository.findByResultAndParity_id(SystemM.RESULTS_PREGNANCY, abortionRepository.findOne(id).getParity().getId());
		if (DateUtils.dateSubDate(pregnancy.getPregnancyDate(), data)>=0){
			return new Message(GlobalConfig.ABNORMAL,
				"该羊的流产日期不能大于已孕日期,该羊的已孕日期为:"
					+DateUtils.DateToStr(pregnancy.getPregnancyDate()));
		}
		return GlobalConfig.SUCCESS;
	}

	@Transactional
	@Override
	public Message update(Abortion abortion) {
		Abortion upAbortion=abortionRepository.findOne(abortion.getId());
		upAbortion.setAbortion(abortion);
		Parity parity=upAbortion.getParity();
		//计算妊娠时间
		parity.setGestation(DateUtils.dateSubDate(abortion.getAbortionDate(),
				pregnancyRepository.findByResultAndParity_id(
						SystemM.RESULTS_PREGNANCY, parity.getId()).getPregnancyDate()));
		//转栏
		if (upAbortion.getToPaddock()!=null && upAbortion.getToPaddock().getId()!=null){
			paddockChangeService.add(upAbortion.getToPaddock(), upAbortion.getDam().getCode(), 
						upAbortion.getOrg(),upAbortion.getRecorder());
		}
		else{
			upAbortion.setToPaddock(null);
		}
		abortionRepository.save(upAbortion);
		parityRepository.save(parity);
		
		return GlobalConfig.SUCCESS;
	}
}