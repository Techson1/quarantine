package com.beiqisoft.aoqun.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.GeneralVeternary;
import com.beiqisoft.aoqun.entity.Joining;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.domain.MessageEntity;
import com.beiqisoft.aoqun.repository.JoiningRepository;
import com.beiqisoft.aoqun.service.MessageService;
import com.beiqisoft.aoqun.util.DateUtils;

@Service
public class MessageServiceImpl extends BaseServiceIml<Joining,JoiningRepository> implements MessageService{

	@PersistenceContext  
	private EntityManager em;
	
	final Integer hintDay=3;
	
	final Integer JoiningHint=30;
	
	private String newEntity="new com.beiqisoft.aoqun.entity.domain.MessageEntity";
	
	@Override
	public JoiningRepository getRepository() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageEntity> findByJoingingList(Long orgId,String code,Long paddockId) {
		String hql="SELECT "+newEntity+"(p.dam.code,p.startDate,p.dam.breed.breedName,p.dam.paddock.name) "
				+ "FROM Parity p "
				+ "WHERE p.isNewestParity = '1' "
				+ "AND p.dam.org.id = "+orgId+" "
				+ "AND p.dam.physiologyStatus = 1 "
				+ "AND p.dam.flag = 0 "
				+ "AND SUBSTRING(p.dam.breedingState,1,2) = '10' ";
		if (code!=null && !"".equals(code)){
			hql+="AND p.dam.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND p.dam.paddock.id = "+ paddockId;
		}
		hql+=" order by p.dam.paddock.name asc";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageEntity> findByNotJoingingList(Long orgId, String code, Long paddockId) {
		String hql ="SELECT "+newEntity+"(p.dam.code,p.pregnancyDate,p.dam.breed.breedName,p.dam.paddock.name,MAX(p.pregnancySeq)) "
				+ "FROM Pregnancy p "
				+ "WHERE p.result = '3' "
				+ "AND p.parity.isNewestParity = '1' "
				+ "AND p.dam.org.id ="+orgId+" "
				+ "AND p.dam.physiologyStatus = 1"
				+ "AND p.dam.flag = 0 "
				+ "AND SUBSTRING(p.dam.breedingState,1,2) = '13' ";
				
		if (code!=null && !"".equals(code)){
			hql+="AND p.dam.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND p.dam.paddock.id = "+ paddockId;
		}
		
		hql+=" GROUP BY p.dam";
		hql+= " order by p.dam.paddock.name asc";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageEntity> findByPregnancyList(Long orgId, String code, Long paddockId) {
		String hql ="SELECT "+newEntity+"(j.dam.code,j.joiningDate,j.dam.breed.breedName,j.dam.paddock.name) "
				+ "FROM Joining j "
				+ "WHERE j.isNewestJoining = '1' "
				+ "AND j.parity.isNewestParity = '1'"
				+ "AND j.dam.org.id ="+orgId+" "
				+ "AND j.dam.physiologyStatus = 1"
				+ "AND j.dam.flag = 0 "
				+ "AND SUBSTRING(j.dam.breedingState,1,2) = '11' ";
		if (code!=null && !"".equals(code)){
			hql+="AND j.dam.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND j.dam.paddock.id = "+ paddockId;
		}
		hql += " order by j.dam.paddock.name asc";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageEntity> findByLambingDamList(Long orgId, String code, Long paddockId) {
		String hql ="SELECT "+newEntity+"(p.dam.code,p.joining.joiningDate,p.dam.breed.breedName,p.dam.paddock.name) "
				+ "FROM Pregnancy p "
				+ "WHERE p.result = '2' "
				+ "AND p.parity.isNewestParity = '1' "
				+ "AND p.dam.org.id ="+orgId+" "
				+ "AND p.dam.physiologyStatus = 1"
				+ "AND p.dam.flag = 0 "
				+ "AND SUBSTRING(p.dam.breedingState,1,2) = '14' ";
		if (code!=null && !"".equals(code)){
			hql+="AND p.dam.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND p.dam.paddock.id = "+ paddockId;
		}
		hql+=" order by p.dam.paddock.name asc";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageEntity> findByWeaningList(Long orgId, String code, Long paddockId) {
		String hql ="SELECT "+newEntity+"(L.dam.code,L.bornDate,L.dam.breed.breedName,L.dam.paddock.name) "
				+ "FROM LambingDam L "
				+ "WHERE L.parity.isNewestParity = '1' "
				+ "AND L.dam.org.id ="+orgId+" "
				+ "AND L.dam.physiologyStatus = 1"
				+ "AND L.dam.flag = 0 "
				+ "AND SUBSTRING(L.dam.breedingState,1,2) = '15' ";
		if (code!=null && !"".equals(code)){
			hql+="AND L.dam.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND L.dam.paddock.id = "+ paddockId;
		}
		hql+=" order by L.dam.paddock.name asc";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseInfo> findByinfertilityWarning(Long orgId, Integer pageNum,Integer predictDay,String code,Long paddockId) {
		String cross="";
		for (int i=predictDay;i<20;i++){
			cross+="11"+i+","+SystemM.BASE_INFO_BREEDING_STATE_UNPREGNANCY+i+",";
		}
		List<String> isCross= new ArrayList<>();
		for (String s:cross.substring(0,cross.length()-1).split(",")){
			isCross.add(s);
		}
		String hql="FROM BaseInfo b "
				+ "WHERE b.org.id ="+orgId+" "
				+ "AND b.physiologyStatus = 1 "
				+ "AND b.flag = '0' "
				+ "AND b.breedingState in (?1) ";
				
		if (code!=null && !"".equals(code)){
			hql+="AND b.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND b.paddock.id = "+ paddockId;
		}
		hql+=" order by b.paddock.name asc";
		return em.createQuery(hql).setParameter(1,isCross).getResultList();
	}

	@Override
	public List<BaseInfo> findByRankHint(Long orgId, Date day, String code, Long paddockId) {
		String formatDay = DateUtils.DateToStr(day);
		String hql="SELECT new BaseInfo(b.code,b.birthDay,b.breed.breedName,b.paddock.name,b.moonAge,b.breedingState) "
				+ "FROM BaseInfo b "
				+ "WHERE b.org.id ="+orgId+" "
				+ "AND b.physiologyStatus = 1 "
				+ "AND b.flag = '0' "
				+ "AND b.rank IS NULL "
				+ "AND b.birthDay<='"+formatDay+"'";
				
		if (code!=null && !"".equals(code)){
			hql+="AND b.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND b.paddock.id = "+ paddockId;
		}
		hql +=" order by b.paddock.name asc,b.birthDay asc";
		//List<?> list =  em.createQuery(hql).getResultList();
		return em.createQuery(hql).getResultList();
		//return list.stream().map(x-> setBaseInfo(x)).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GeneralVeternary> findByGeneralHint(Long orgId, String code, Long paddockId) {
		String hql = "FROM GeneralVeternary AS g where g.base.org.id="+orgId+" and g.base.physiologyStatus=1 "
				+ "GROUP BY g.base "
				+ "HAVING MAX(g.date)>= '2001-01-01' "
				+ "AND g.result='未治愈' "
				+ "order by g.date asc,g.base.paddock.name asc";
		return em.createQuery(hql).getResultList();
	}
	

	@Override
	public List<BaseInfo> findBySemenHint(Long orgId, String code, Long paddockId) {
		String hql="SELECT b.code,b.breed.breedName,b.birthDay,b.moonAge,b.paddock.name,b.breedingState FROM BaseInfo b "
				+ "WHERE b.org.id ="+orgId+" "
				+ "AND b.physiologyStatus = 1 "
				+ "AND b.flag = '0' "
				+ "AND b.breedingState in ('25','28','29') ";
				
		if (code!=null && !"".equals(code)){
			hql+="AND b.code LIKE '%"+code+"%' ";
		}
		if (paddockId!=null){
			hql+="AND b.paddock.id = "+ paddockId;
		}
		hql+=" order by b.paddock.name asc,b.birthDay asc";
		List<?> list= em.createQuery(hql).getResultList();
		return list.stream().map(x-> setBaseInfo(x)).collect(Collectors.toList());
	}
	
	public BaseInfo setBaseInfo(Object object){
		BaseInfo base = new BaseInfo();
		Object[] params = (Object[]) object;
		base.setCode(params[0].toString());
		Breed breed = new Breed();
		breed.setBreedName(params[1].toString());
		base.setBreed(breed);
		base.setBirthDay(DateUtils.StrToDate(params[2].toString()));
		if (params[3]!=null)
			base.setMoonAge(params[3].toString());
		else{
			base.setMoonAge("0");
		}
		Paddock paddock = new Paddock();
		paddock.setName(params[4].toString());
		base.setPaddock(paddock);
		base.setBreedingState(params[5].toString());
		return base;
	}
}