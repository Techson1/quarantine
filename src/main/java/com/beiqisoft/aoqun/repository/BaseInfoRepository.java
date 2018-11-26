package com.beiqisoft.aoqun.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.MonthRankOnHand;

public interface BaseInfoRepository extends BaseRepository<BaseInfo>{
	
	/**
	 * 根据可视耳号查询羊只表
	 * @param code
	 * 			可视耳号
	 * @return BaseInfo
	 * */
	BaseInfo findByCode(String code);
	/**
	 * 根据可视耳号和场区区分
	 * @param code
	 * @param orgid
	 * @return
	 */
	BaseInfo findByCodeAndOrgId(String code,Long orgid);
	
	/**
	 * 查询耳标
	 * 		查询可视耳号的电子耳号
	 * @param code
	 * 			可视耳号
	 * @param rfid
	 * 			电子耳号
	 * @return BaseInfo
	 * */
	BaseInfo findByCodeOrRfid(String code,String rfid);
	/**
	 * json 
	 * 增加产区区分
	 * @param code
	 * @param rfid
	 * @param orgId
	 * @return
	 */
	BaseInfo findByCodeOrRfidAndOrgId(String code,String rfid,Long orgId);
	/**
	 * @author json
	 * @param code    可视耳号
	 * @param rfid     电子耳号
	 * @param orgId    组织机构代码
	 * @param physiologyStatus  库存状态
	 * @return BaseInfo
	 */
	BaseInfo findByCodeOrRfidAndOrgIdAndPhysiologyStatus(String code,String rfid,Long orgId,Long physiologyStatus);
	/**
	 * 根据可视耳号查询归档羊只
	 * @param code
	 * @param flag
	 * */
	BaseInfo findByCodeAndFlag(String code,String falg);
	
	/**
	 * 根据电子耳号查询
	 * */
	BaseInfo findByRfid(String rfid);
	
	/**
	 * 根据电子耳号查询归档羊只
	 * */
	BaseInfo findByRfidAndFlag(String rfid,String falg);
	
	/**
	 * 批量查询id
	 * */
	List<BaseInfo> findByIdIn(Long[] ids);
	
	/**
	 * 根据产羔母羊id查找baseInfo
	 * */
	List<BaseInfo> findByLambingDam_id(Long lambingDamId);

	/**
	 * 根据产羔母羊id查找baseInfo
	 * */
	Page<BaseInfo> findByLambingDam_id(Long lambingDamId,Pageable pageable);
	
	/**
	 * 根据母羊产羔记录id及出生日期查询羔羊
	 * */
	List<BaseInfo> findByLambingDam_idAndBirthDay(Long id, Date birthDay);

	/**
	 * 根据母羊产羔记录id及出生日期查询羔羊 并排序
	 * */
	List<BaseInfo> findByLambingDam_idAndBirthDayOrderByCtimeDesc(Long id,
			Date birthDay);

	/**
	 * 查询活羔数
	 * */
	@Query(value="SELECT COUNT(*) FROM BaseInfo b WHERE b.dam.id=?1 AND b.physiologyStatus=?2")
	Integer findByDamNumber(Long damId,Long physiologyStatus);

	/**根据栋栏查询全部羊只*/
	List<BaseInfo> findByPaddock_id(Long fromPaddockId);

	/**
	 * 查询在库羊只
	 * */
	List<BaseInfo> findByPhysiologyStatusAndFlag(Long normal,String flag);

	/**
	 * 批量查询可视耳号
	 * */
	List<BaseInfo> findByCodeIn(String[] codes);

	/**
	 * 批量查询电子耳号
	 * */
	List<BaseInfo> findByRfidIn(String[] rfids);

	@Query(value="SELECT new MonthRankOnHand(b.breed,b.sex,b.rank,b.moonAge,count(*),b.org.id) FROM BaseInfo b WHERE b.flag='0' AND b.physiologyStatus=1 GROUP BY b.org,b.breed,b.sex,b.rank.rank,b.moonAge")
	List<MonthRankOnHand> findOnHandByBreedAndMoonAge();

	/**核对查询*/
	@Query(value="FROM BaseInfo b WHERE b.physiologyStatus=1 AND b.flag='0' AND id NOT IN(?1)")
	List<BaseInfo> findByCheckTo(List<Long> array);
	
	@Query(value="FROM BaseInfo b WHERE b.physiologyStatus=1 AND b.flag='0' AND b.org.id=?1 AND b.id NOT IN("
			+ "SELECT i.base.id FROM InventoryDetail i WHERE i.inventory.id=?2)")
	List<BaseInfo> findByCheck(Long orgId,Long id);

	/**
	 * 查询圈舍下所有活羊
	 * */
	List<BaseInfo> findByPaddock_idAndPhysiologyStatusAndFlag(Long paddockId,
			Long physiologyStatus, String flag);
	
	/**
	 * app查询出库羊只
	 * */
	@Query(value="FROM BaseInfo b WHERE b.physiologyStatus=?2 AND b.flag=0 AND (b.code=?1 or b.rfid=?1 )")
	BaseInfo findByAppAudit(String code,Long type);

	/**
	 * 查询分厂羊只
	 * */
	BaseInfo findByCodeAndFlagAndOrg_id(String code, String publicFalse, Long orgId);

	@Query(value="SELECT COUNT(b.id) FROM BaseInfo b "
			+ "WHERE b.lambingDam.id=?1 "
			+ "AND (CAST(b.moonAge AS int)>=2 OR b.physiologyStatus = 9 OR b.physiologyStatus = 10 )")
	String findBySurvival(Long id);

	List<BaseInfo> findByIsOutsourcingAndBirthDayBetween(String string, Date startDate, Date endDate);
	@Query(value="select count(*) from t_base_info b where b.flag = '0' And b.physiology_status='1' And b.org_id = ?1 And b.sex=?2 And b.birth_day <= ?3",nativeQuery=true)
	Integer findFirstNumByFlayAndPhysiologyStatusAndOrg_idAndSexBirthDayEnd(Long id, String sex,Date date);

	List<BaseInfo> findByPhysiologyStatusIn(long l, long m);
}