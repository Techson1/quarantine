package com.beiqisoft.aoqun.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.MyUtils;
import com.beiqisoft.aoqun.vo.PaddockChangeVo;

import sun.rmi.runtime.Log;
@CacheConfig(cacheNames = "paddockChangeService")
@Service
public class PaddockChangeServiceImpl extends BaseServiceIml<PaddockChange,PaddockChangeRepository> implements PaddockChangeService{
	/**
     * 实体管理对象
     */
    @PersistenceContext
    EntityManager entityManager;
	@Autowired
	public PaddockChangeRepository paddockChangeRepository;
	@Autowired
	public BaseInfoService baseInfoService;
    
	private List<Object[]> listResult;
	private Integer total=0;
    @Autowired
    private RedisTemplate redisTemplate;
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

	@Override
	@Cacheable(value="findAllTurnList", key = "#p0")
	public Page<PaddockChangeVo> findAllTurnList(PaddockChange paddockChange) {
		//Session session = entityManager.unwrap(org.hibernate.Session.class);
		String keyPage = "turbar_page_"+paddockChange.getPageNum();
		String keyList = "turbar_list"+paddockChange.getOrg().getId();
		String  keyTotal="turbar_total";
		
		//ValueOperations<String,Page<PaddockChangeVo>> operations =redisTemplate.opsForValue();
		ValueOperations<String,Page<PaddockChangeVo>> operationsPage =redisTemplate.opsForValue();
		
		ValueOperations<String,List<Object[]>> listR=redisTemplate.opsForValue();
		ValueOperations<String,Integer> totalR= redisTemplate.opsForValue();
		
		int startRow=paddockChange.getPageNum()* GlobalConfig.PAGE_SIZE;
		int endRow=(paddockChange.getPageNum()+1)*GlobalConfig.PAGE_SIZE;
		
		 int search=0;//查询条件个数
		 search=searchReslutCount(paddockChange,search);
		 if(search==0) {//执行分页
			 if(redisTemplate.hasKey(keyPage)) {//如果缓存里存在，则直接返回结果
				 return operationsPage.get(keyPage);
			 }
		 }
		  //判断缓存中是否存在所有的数据
		 if(redisTemplate.hasKey(keyList)) {
			 listResult=listR.get(keyList);
			 total=totalR.get(keyTotal);
		 }else {
			 StringBuilder sqlString=new StringBuilder();
				sqlString.append("select v.code,v.breed_name,v.Sex,v.from_name,k.Name as to_name,v.org_name,v.recorder,v.ctime,v.from_paddock_Id,v.to_Paddock_id,v.cdate from t_bar_change_view v,t_paddock k where v.to_Paddock_id=k.id ");
				sqlString.append(" and v.org_id=").append("'").append(paddockChange.getOrg().getId()).append("'");
				
				StringBuilder countSql=new StringBuilder();
				
				sqlString.append("  order by v.ctime desc");
				
				Query reusltQuery = this.entityManager.createNativeQuery(sqlString.toString());
				
			    listResult=reusltQuery.getResultList();
			    int total= listResult.size();
			    listR.set(keyList, listResult);
		 }
		
		
		 List<PaddockChangeVo> newList=getSearchBean(listResult,paddockChange);//这里数据量太大，几十万的数据，待改进
		
		 int reuslttotal=newList.size();
		 if(startRow>reuslttotal) {
			 startRow=0;
		 }
		 if(endRow>reuslttotal) {
			 endRow=reuslttotal;
		 }
		 totalR.set(keyTotal, reuslttotal);
		 Page<PaddockChangeVo> incomeDailyPage = new PageImpl<PaddockChangeVo>(newList.subList(startRow, endRow),new PageRequest(paddockChange.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "weaningDate","ctime"),reuslttotal);
		 if(search==0) {
			 operationsPage.set(keyPage, incomeDailyPage);
		 }
		 return incomeDailyPage;
		
	}
	 
	public List<PaddockChangeVo> getSearchBean(List<Object[]> list,PaddockChange searchModel){
		
		List<PaddockChangeVo>  result=new ArrayList<PaddockChangeVo> ();
		  for(Object[] ob:list) {
			  PaddockChangeVo vo=new PaddockChangeVo();
			  vo.setCode(String.valueOf(ob[0]));
			  vo.setBreedName(String.valueOf(ob[1]));
			  vo.setSex(String.valueOf(ob[2]));
			  vo.setFromPaddock(String.valueOf(ob[3]));
			  vo.setToPaddock(String.valueOf(ob[4]));
			  vo.setBrief(String.valueOf(ob[5]));
			  vo.setRecorder(String.valueOf(ob[6]));
			  if(null==ob[7]) {
				  vo.setCtime(null);
			  }else { 
				 vo.setCtime(ob[7].toString().substring(0,16));
			  }
			  vo.setFromPaddockId(String.valueOf(ob[8]));
			  vo.setToPaddockId(String.valueOf(ob[9]));
			  // System.out.println(falg+"--"+falg2+"--"+falg3+"--"+falg4+"--"+falg5+"--"+falg6+"--");
			   if(checkSearch(vo,searchModel)) {
				   result.add(vo);
			   }
		  }
		 /* Collections.sort(result, new Comparator<PaddockChangeVo>() {
		       public int compare(PaddockChangeVo h1, PaddockChangeVo h2) {
		           return h1.getCtime().compareTo(h2.getCtime());
		       }
		   });*/
		  //这种排序只支持jdk1.8以上
		  //result.sort((PaddockChangeVo h1, PaddockChangeVo h2) -> h1.getCtime().compareTo(h2.getCtime()));
		  
		return result;
	}
	private int searchReslutCount(PaddockChange searchModel,int search) {
		
		if(null!=searchModel.getBase()) {
			if(StringUtils.isNotBlank(searchModel.getBase().getCode())) {
				search+=1;
			}
		   }
		   if(null!=searchModel.getFromPaddock()) {//转出
			   if(null!=searchModel.getFromPaddock().getId() ) {
				   search+=1;
			   }
		   }
		   if(null!=searchModel.getToPaddock()) {//转入圈
			   if(null!=searchModel.getToPaddock().getId()) {
				   search+=1;
			   }
		   }
		   if(null!=searchModel.getStartDate()&&null!=searchModel.getEndDate()) {
			   search+=1;
			   
		   }else {
			   if(null!=searchModel.getStartDate()) {
				   search+=1;
				    
			   }
			   if(null!=searchModel.getEndDate()) {
				   search+=1;
			   }
		   }
		   return search;
	}
	private boolean checkSearch(PaddockChangeVo vo,PaddockChange searchModel) {
		boolean falg=true;
		boolean falg2=true;
		boolean falg3=true;
		boolean falg4=true;
		boolean falg5=true;
		boolean falg6=true;
		 if(StringUtils.isNotBlank(searchModel.getBase().getCode())) {
			   if(!searchModel.getBase().getCode().trim().equals(vo.getCode().trim())) {
				   falg=false;  
			   }
		   }
		   if(null!=searchModel.getFromPaddock()) {//转出
			   if(null!=searchModel.getFromPaddock().getId() ) {
				   if(Long.valueOf(vo.getFromPaddockId())!=searchModel.getFromPaddock().getId()) {
					   falg2=false;  
				   }
			   }
		   }
		   if(null!=searchModel.getToPaddock()) {//转入圈
			   if(null!=searchModel.getToPaddock().getId()) {
				   if(Long.valueOf(vo.getToPaddockId())!=searchModel.getToPaddock().getId()) {
					   falg3=false; 
				   }
			   }
		   }
		   if(null!=searchModel.getStartDate()&&null!=searchModel.getEndDate()) {
			   if(null==vo.getCtime()) {
				   falg4=false; 
			   }else {
				   int com1=DateUtils.StrToDate(vo.getCtime()).compareTo(searchModel.getStartDate());
				   int com2=DateUtils.StrToDate(vo.getCtime()).compareTo(searchModel.getEndDate());
				   
				   if(!(com1>-1 && com2 <1)) {
				    	 falg4=false; 
				    }
			   }
			   
		   }else {
			   if(null!=searchModel.getStartDate()) {
				   if(null==vo.getCtime()) {
					   falg5=false; 
				   }else {
					  int rec= DateUtils.StrToDate(vo.getCtime()).compareTo(searchModel.getStartDate());
					   
					   if(!(rec >-1)) {
					    	falg5=false; 
					    }
				   }
				    
			   }
			   if(null!=searchModel.getEndDate()) {
				   if(null==vo.getCtime()) {
					   falg6=false; 
				   }else {
					   int rec=DateUtils.StrToDate(vo.getCtime()).compareTo(searchModel.getEndDate());
					   
					   if(!(rec<1)) {
						   falg6=false; 
					   }
				   }
				  
			   }
		   }
		   if(falg && falg2 && falg3 && falg4 && falg5 && falg6) {
			   return true;
		   }else {
			   return false;
		   }
	}
}
