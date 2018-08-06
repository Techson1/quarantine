package com.beiqisoft.aoqun.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.User;
import com.beiqisoft.aoqun.security.JwtUser;
import com.beiqisoft.aoqun.service.AbortionService;
import com.beiqisoft.aoqun.service.AllotDetailService;
import com.beiqisoft.aoqun.service.AllotService;
import com.beiqisoft.aoqun.service.AoqunTestService;
import com.beiqisoft.aoqun.service.BaseGroupDetailService;
import com.beiqisoft.aoqun.service.BaseGroupService;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.BreedParameterService;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.service.BreedingOnHandService;
import com.beiqisoft.aoqun.service.BreedingPlanDetailDamService;
import com.beiqisoft.aoqun.service.BreedingPlanDetailSireService;
import com.beiqisoft.aoqun.service.BreedingPlanService;
import com.beiqisoft.aoqun.service.BreedingStateService;
import com.beiqisoft.aoqun.service.BreedingWeedService;
import com.beiqisoft.aoqun.service.CodePurchaseOrderDetailService;
import com.beiqisoft.aoqun.service.CodePurchaseOrderService;
import com.beiqisoft.aoqun.service.CodeRegisterService;
import com.beiqisoft.aoqun.service.CompanyService;
import com.beiqisoft.aoqun.service.ContactService;
import com.beiqisoft.aoqun.service.ContactTypeService;
import com.beiqisoft.aoqun.service.CustomerService;
import com.beiqisoft.aoqun.service.DamBreedStateRepService;
import com.beiqisoft.aoqun.service.DeathDisposalReasonService;
import com.beiqisoft.aoqun.service.DeathdisposalService;
import com.beiqisoft.aoqun.service.DonorGroupService;
import com.beiqisoft.aoqun.service.EarChangeService;
import com.beiqisoft.aoqun.service.EmbryoFlushService;
import com.beiqisoft.aoqun.service.EmbryoGradingService;
import com.beiqisoft.aoqun.service.EmbryoProjectService;
import com.beiqisoft.aoqun.service.EmbryoRegistrationService;
import com.beiqisoft.aoqun.service.EmbryoTransplantService;
import com.beiqisoft.aoqun.service.FakeService;
import com.beiqisoft.aoqun.service.FakeTypeDamService;
import com.beiqisoft.aoqun.service.FakeTypeSireService;
import com.beiqisoft.aoqun.service.FeedGroupService;
import com.beiqisoft.aoqun.service.FeedingService;
import com.beiqisoft.aoqun.service.FormualDetailService;
import com.beiqisoft.aoqun.service.FormulaService;
import com.beiqisoft.aoqun.service.FrozenEmbryoService;
import com.beiqisoft.aoqun.service.FrozenEmbryoTransplantService;
import com.beiqisoft.aoqun.service.FrozenStoreService;
import com.beiqisoft.aoqun.service.GeneralVeternaryService;
import com.beiqisoft.aoqun.service.GroupChangeService;
import com.beiqisoft.aoqun.service.GroupTypeService;
import com.beiqisoft.aoqun.service.HintService;
import com.beiqisoft.aoqun.service.IllnessWeedService;
import com.beiqisoft.aoqun.service.ImmuneHealthProjectService;
import com.beiqisoft.aoqun.service.ImmuneHealthService;
import com.beiqisoft.aoqun.service.ImmunePlanService;
import com.beiqisoft.aoqun.service.InventoryDetailService;
import com.beiqisoft.aoqun.service.InventoryDifferenceService;
import com.beiqisoft.aoqun.service.InventoryService;
import com.beiqisoft.aoqun.service.JoiningService;
import com.beiqisoft.aoqun.service.LambingDamService;
import com.beiqisoft.aoqun.service.LambingService;
import com.beiqisoft.aoqun.service.LooksService;
import com.beiqisoft.aoqun.service.MaterialDetailService;
import com.beiqisoft.aoqun.service.MaterialService;
import com.beiqisoft.aoqun.service.MonthRankOnHandService;
import com.beiqisoft.aoqun.service.OnHandMonthService;
import com.beiqisoft.aoqun.service.OnHandService;
import com.beiqisoft.aoqun.service.OrganizationService;
import com.beiqisoft.aoqun.service.PaddockChangeService;
import com.beiqisoft.aoqun.service.PaddockService;
import com.beiqisoft.aoqun.service.PaddockTypeService;
import com.beiqisoft.aoqun.service.ParityService;
import com.beiqisoft.aoqun.service.PerformanceService;
import com.beiqisoft.aoqun.service.PregnancyService;
import com.beiqisoft.aoqun.service.PvgService;
import com.beiqisoft.aoqun.service.RamTrainingService;
import com.beiqisoft.aoqun.service.RankTestService;
import com.beiqisoft.aoqun.service.ReceptorGroupService;
import com.beiqisoft.aoqun.service.ReviseWeightService;
import com.beiqisoft.aoqun.service.ReviseWigthService;
import com.beiqisoft.aoqun.service.RoleService;
import com.beiqisoft.aoqun.service.SalesDatailService;
import com.beiqisoft.aoqun.service.SalesService;
import com.beiqisoft.aoqun.service.SizeViewService;
import com.beiqisoft.aoqun.service.SterilizeService;
import com.beiqisoft.aoqun.service.UserService;
import com.beiqisoft.aoqun.service.WeaningService;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.service.guide.JoiningGuideService;
import com.beiqisoft.aoqun.service.guide.JoiningLeadService;
import com.beiqisoft.aoqun.service.guide.PregnancyGuideService;
import com.beiqisoft.aoqun.service.guide.SheepLevelGuideService;
import com.beiqisoft.aoqun.util.ExecuteTime;
import com.beiqisoft.aoqun.util.StringUtils;
import com.beiqisoft.aoqun.util.page.PageIteration;
import com.beiqisoft.aoqun.util.page.PageIterationImpl;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class BaseController<T, S> extends GlobalConfig {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected Map<String, Object> result = new HashMap<String, Object>();
	
	protected int zero=0;
	
	//TODO 风险代码
	//page会存在堆上,该方法是成员变量,不会被垃圾回收器,回收
	protected PageIteration<T> page=new PageIterationImpl<T>();

	protected boolean debugFlag = false;
	//protected boolean debugFlag = true;
	@Autowired
	protected OnHandMonthService onHandMonthService;
	@Autowired
	protected MonthRankOnHandService monthRankOnHandService;
	@Autowired
	protected OnHandService onHandService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected ContactService contactService;
	@Autowired
	protected OrganizationService organizationService;
	@Autowired
	protected CompanyService companyService;
	@Autowired
	protected RoleService roleService;
	@Autowired
	protected PvgService pvgService;
	@Autowired
	protected BreedService breedService;
	@Autowired
	protected CustomerService customerService;
	@Autowired
	protected DeathDisposalReasonService deathDisposalReasonService;
	@Autowired
	protected FakeTypeDamService fakeTypeDamService;
	@Autowired
	protected FakeTypeSireService fakeTypeSireService;
	/**@deprecated*/
	@Autowired
	protected GroupTypeService groupTypeService;
	@Autowired
	protected EmbryoGradingService embryoGradingService;
	@Autowired
	protected CodePurchaseOrderService codePurchaseOrderService;
	/**@deprecated*/
	@Autowired
	protected CodePurchaseOrderDetailService codePurchaseOrderDetailService;
	@Autowired
	protected CodeRegisterService codeRegisterService;
	@Autowired
	protected BaseInfoService baseInfoService;
	@Autowired
	protected ContactTypeService contactTypeService;
	@Autowired
	protected PaddockTypeService paddockTypeService;
	@Autowired
	protected BreedingPlanService breedingPlanService;
	@Autowired
	protected BreedingPlanDetailDamService breedingPlanDetailDamService;
	@Autowired
	protected BreedingPlanDetailSireService breedingPlanDetailSireService;
	@Autowired
	protected BreedParameterService breedParameterService;
	@Autowired
	protected ParityService parityService;
	@Autowired
	protected JoiningService joiningService;
	@Autowired
	protected PregnancyService pregnancyService;
	@Autowired
	protected LambingService lambingService;
	@Autowired
	protected LambingDamService lambingDamService;
	@Autowired
	protected WeaningService weaningService;
	@Autowired
	protected AbortionService abortionService;
	@Autowired
	protected EmbryoProjectService embryoProjectService;
	@Autowired
	protected DonorGroupService donorGroupService;
	@Autowired
	protected ReceptorGroupService receptorGroupService;
	@Autowired
	protected EmbryoFlushService embryoFlushService;
	@Autowired
	protected EmbryoTransplantService embryoTransplantService;
	@Autowired
	protected FrozenEmbryoTransplantService frozenEmbryoTransplantService;
	@Autowired
	protected LooksService looksService;
	@Autowired
	protected RankTestService rankTestService;
	@Autowired
	protected BreedingStateService breedingStateService;
	@Autowired
	protected FrozenStoreService frozenStoreService;
	@Autowired
	protected FrozenEmbryoService frozenEmbryoService;
	@Autowired
	protected EmbryoRegistrationService embryoRegistrationService;
	@Autowired
	protected SizeViewService sizeViewService;
	@Autowired
	protected WeightService wigthService;
	@Autowired
	protected ReviseWigthService reviseWigthService;
	@Autowired
	protected BreedingWeedService breedingWeedService;
	@Autowired
	protected DeathdisposalService deathdisposalService;
	@Autowired
	protected IllnessWeedService illnessWeedService;
	@Autowired
	protected RamTrainingService ramTrainingService;
	@Autowired
	protected FakeService fakeService;
	@Autowired
	protected GroupChangeService groupChangeService;
	@Autowired
	protected PaddockService paddockService;
	@Autowired
	protected PaddockChangeService paddockChangeService;
	@Autowired
	protected EarChangeService earChangeService;
	@Autowired
	protected GeneralVeternaryService generalVeternaryService;
	@Autowired
	protected InventoryService inventoryService;
	@Autowired
	protected InventoryDetailService inventoryDetailService;
	@Autowired
	protected FormulaService formulaService;
	@Autowired
	protected MaterialService materialService;
	@Autowired
	protected MaterialDetailService materialDetailService;
	@Autowired
	protected FormualDetailService formualDetailService;
	@Autowired
	protected FeedGroupService feedGroupService;
	@Autowired
	protected FeedingService feedingService;
	@Autowired
	protected SalesService salesService;
	@Autowired
	protected SalesDatailService salesDatailService;
	@Autowired
	protected ImmuneHealthService immuneHealthService;
	@Autowired
	protected ImmuneHealthProjectService immuneHealthProjectService;
	@Autowired
	protected ImmunePlanService immunePlanService;
	@Autowired
	protected SterilizeService sterilizeService;
	@Autowired
	protected PerformanceService performanceService;
	@Autowired
	protected ReviseWeightService reviseWeightService;
	@Autowired
	protected AllotService allotService;
	@Autowired
	protected AllotDetailService allotDetailService;
	@Autowired
	protected BaseGroupDetailService baseGroupDetailService;
	@Autowired
	protected BaseGroupService baseGroupService;
	@Autowired
	protected BreedingOnHandService breedingOnHandService;
	@Autowired
	protected InventoryDifferenceService inventoryDifferenceService;
	//测试
	@Autowired
	protected AoqunTestService aoqunTestService;
	//导入
	@Autowired
	protected JoiningGuideService joiningGuideService;
	@Autowired
	protected PregnancyGuideService pregnancyGuideService;
	@Autowired
	protected JoiningLeadService joiningLeadService;
	@Autowired
	protected SheepLevelGuideService sheepLevelGuideService;
	@Autowired
	protected HintService hintService;
	@Autowired
	protected DamBreedStateRepService damBreedStateRepService;
	
	@RequestMapping(value = "one/{id}")
	public T findOne(@PathVariable Long id) {
		return getRepository().findOne(id);
	}

	@RequestMapping(value = "all")
	public Iterable<T> findAll() {
		return getRepository().findByOrderByIdDesc();
	}

	@RequestMapping(value = "dels")
	public Message deletes(@RequestParam("ids") String ids) {
		try{
			for (String id : ids.split(",")) {
				getRepository().delete(Long.parseLong(id));
			}
		}catch(Exception e){
			return new Message(ABNORMAL,"数据存在关联,请删除关联后再删除");
		}
		return SUCCESS;
	}

	@RequestMapping(value = "del/{id}")
	public Message delete(@PathVariable Long id) {
		try{
			getRepository().delete(id);
		}catch(Exception e){
			return new Message(ABNORMAL,"数据存在关联,请删除关联后再删除");
		}
		return SUCCESS;
	}

	@Transactional
	@RequestMapping(value = "save")
	public Message save(T entity) {
		getRepository().save(entity);
		return SUCCESS;
	}

	@RequestMapping(value = "session/user")
	public User getUser() {
		return (User) getSession().getAttribute(GlobalConfig.LOGIN_SESSION_KEY);
	}

	public User currentUser() {
		Object obj = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = null;
		String userName = "";
		try{
			Field f1=obj.getClass().getDeclaredField("username");
			f1.setAccessible(true);
			userName = (String) f1.get(obj);
		}catch(Exception e){e.printStackTrace();}
		if(!userName.equals("")){
			currentUser = userService.getRepository().findByUserName(userName);
		}
		return currentUser;
	}

	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected void sput(String key, T object) {
		getSession().putValue(key, object);
	}

	protected T sget(String key) {
		return (T) getSession().getAttribute(key);
	}

	protected Long getUserId() {
		Long id = 0l;
		User user = getUser();
		if (user != null) {
			id = user.getId();
		}
		return id;
	}

	private Class<S> persistentClass;

	public BaseController() {
		this.persistentClass = (Class<S>) getSuperClassGenricType(getClass(), 1);
	}

	protected BaseRepository<T> getRepository() {
		if (this.persistentClass.isInstance(onHandMonthService)) {
			return (BaseRepository<T>) onHandMonthService.getRepository();
		}
		if (this.persistentClass.isInstance(monthRankOnHandService)) {
			return (BaseRepository<T>) monthRankOnHandService.getRepository();
		}
		if (this.persistentClass.isInstance(onHandService)) {
			return (BaseRepository<T>) onHandService.getRepository();
		}
		if (this.persistentClass.isInstance(userService)) {
			return (BaseRepository<T>) userService.getRepository();
		}
		if (this.persistentClass.isInstance(contactService)) {
			return (BaseRepository<T>) contactService.getRepository();
		}
		if (this.persistentClass.isInstance(companyService)) {
			return (BaseRepository<T>) companyService.getRepository();
		}
		if (this.persistentClass.isInstance(organizationService)) {
			return (BaseRepository<T>) organizationService.getRepository();
		}
		if (this.persistentClass.isInstance(roleService)) {
			return (BaseRepository<T>) roleService.getRepository();
		}
		if (this.persistentClass.isInstance(breedService)) {
			return (BaseRepository<T>) breedService.getRepository();
		}
		if (this.persistentClass.isInstance(customerService)) {
			return (BaseRepository<T>) customerService.getRepository();
		}
		if (this.persistentClass.isInstance(deathDisposalReasonService)) {
			return (BaseRepository<T>) deathDisposalReasonService.getRepository();
		}
		if (this.persistentClass.isInstance(fakeTypeDamService)) {
			return (BaseRepository<T>) fakeTypeDamService.getRepository();
		}
		if (this.persistentClass.isInstance(fakeTypeSireService)) {
			return (BaseRepository<T>) fakeTypeSireService.getRepository();
		}
		if (this.persistentClass.isInstance(groupTypeService)) {
			return (BaseRepository<T>) groupTypeService.getRepository();
		}
		if (this.persistentClass.isInstance(embryoGradingService)) {
			return (BaseRepository<T>) embryoGradingService.getRepository();
		}
		if (this.persistentClass.isInstance(codePurchaseOrderService)) {
			return (BaseRepository<T>) codePurchaseOrderService.getRepository();
		}
		if (this.persistentClass.isInstance(codePurchaseOrderDetailService)) {
			return (BaseRepository<T>) codePurchaseOrderDetailService.getRepository();
		}
		if (this.persistentClass.isInstance(codeRegisterService)) {
			return (BaseRepository<T>) codeRegisterService.getRepository();
		}
		if (this.persistentClass.isInstance(baseInfoService)) {
			return (BaseRepository<T>) baseInfoService.getRepository();
		}
		if (this.persistentClass.isInstance(contactTypeService)) {
			return (BaseRepository<T>) contactTypeService.getRepository();
		}
		if (this.persistentClass.isInstance(paddockTypeService)) {
			return (BaseRepository<T>) paddockTypeService.getRepository();
		}
		if (this.persistentClass.isInstance(breedingPlanService)) {
			return (BaseRepository<T>) breedingPlanService.getRepository();
		}
		if (this.persistentClass.isInstance(breedingPlanDetailDamService)) {
			return (BaseRepository<T>) breedingPlanDetailDamService.getRepository();
		}
		if (this.persistentClass.isInstance(breedingPlanDetailSireService)) {
			return (BaseRepository<T>) breedingPlanDetailSireService.getRepository();
		}
		if (this.persistentClass.isInstance(breedParameterService)) {
			return (BaseRepository<T>) breedParameterService.getRepository();
		}
		if (this.persistentClass.isInstance(parityService)) {
			return (BaseRepository<T>) parityService.getRepository();
		}
		if (this.persistentClass.isInstance(joiningService)) {
			return (BaseRepository<T>) joiningService.getRepository();
		}
		if (this.persistentClass.isInstance(pregnancyService)) {
			return (BaseRepository<T>) pregnancyService.getRepository();
		}
		if (this.persistentClass.isInstance(lambingService)) {
			return (BaseRepository<T>) lambingService.getRepository();
		}
		if (this.persistentClass.isInstance(lambingDamService)) {
			return (BaseRepository<T>) lambingDamService.getRepository();
		}
		if (this.persistentClass.isInstance(weaningService)) {
			return (BaseRepository<T>) weaningService.getRepository();
		}
		if (this.persistentClass.isInstance(abortionService)) {
			return (BaseRepository<T>) abortionService.getRepository();
		}
		if (this.persistentClass.isInstance(embryoProjectService)) {
			return (BaseRepository<T>) embryoProjectService.getRepository();
		}
		if (this.persistentClass.isInstance(donorGroupService)) {
			return (BaseRepository<T>) donorGroupService.getRepository();
		}
		if (this.persistentClass.isInstance(receptorGroupService)) {
			return (BaseRepository<T>) receptorGroupService.getRepository();
		}
		if (this.persistentClass.isInstance(embryoFlushService)) {
			return (BaseRepository<T>) embryoFlushService.getRepository();
		}
		if (this.persistentClass.isInstance(embryoTransplantService)) {
			return (BaseRepository<T>) embryoTransplantService.getRepository();
		}
		if (this.persistentClass.isInstance(frozenEmbryoTransplantService)) {
			return (BaseRepository<T>) frozenEmbryoTransplantService.getRepository();
		}
		if (this.persistentClass.isInstance(looksService)) {
			return (BaseRepository<T>) looksService.getRepository();
		}
		if (this.persistentClass.isInstance(rankTestService)) {
			return (BaseRepository<T>) rankTestService.getRepository();
		}
		if (this.persistentClass.isInstance(breedingStateService)) {
			return (BaseRepository<T>) breedingStateService.getRepository();
		}
		if (this.persistentClass.isInstance(frozenStoreService)) {
			return (BaseRepository<T>) frozenStoreService.getRepository();
		}
		if (this.persistentClass.isInstance(frozenEmbryoService)) {
			return (BaseRepository<T>) frozenEmbryoService.getRepository();
		}
		if (this.persistentClass.isInstance(embryoRegistrationService)) {
			return (BaseRepository<T>) embryoRegistrationService.getRepository();
		}
		if (this.persistentClass.isInstance(sizeViewService)) {
			return (BaseRepository<T>) sizeViewService.getRepository();
		}
		if (this.persistentClass.isInstance(wigthService)) {
			return (BaseRepository<T>) wigthService.getRepository();
		}
		if (this.persistentClass.isInstance(reviseWigthService)) {
			return (BaseRepository<T>) reviseWigthService.getRepository();
		}
		if (this.persistentClass.isInstance(breedingWeedService)) {
			return (BaseRepository<T>) breedingWeedService.getRepository();
		}
		if (this.persistentClass.isInstance(deathdisposalService)) {
			return (BaseRepository<T>) deathdisposalService.getRepository();
		}
		if (this.persistentClass.isInstance(illnessWeedService)) {
			return (BaseRepository<T>) illnessWeedService.getRepository();
		}
		if (this.persistentClass.isInstance(ramTrainingService)) {
			return (BaseRepository<T>) ramTrainingService.getRepository();
		}
		if (this.persistentClass.isInstance(fakeService)) {
			return (BaseRepository<T>) fakeService.getRepository();
		}
		if (this.persistentClass.isInstance(groupChangeService)) {
			return (BaseRepository<T>) groupChangeService.getRepository();
		}
		if (this.persistentClass.isInstance(paddockService)) {
			return (BaseRepository<T>) paddockService.getRepository();
		}
		if (this.persistentClass.isInstance(paddockChangeService)) {
			return (BaseRepository<T>) paddockChangeService.getRepository();
		}
		if (this.persistentClass.isInstance(earChangeService)) {
			return (BaseRepository<T>) earChangeService.getRepository();
		}
		if (this.persistentClass.isInstance(generalVeternaryService)) {
			return (BaseRepository<T>) generalVeternaryService.getRepository();
		}
		if (this.persistentClass.isInstance(inventoryService)) {
			return (BaseRepository<T>) inventoryService.getRepository();
		}
		if (this.persistentClass.isInstance(inventoryDetailService)) {
			return (BaseRepository<T>) inventoryDetailService.getRepository();
		}
		if (this.persistentClass.isInstance(formulaService)) {
			return (BaseRepository<T>) formulaService.getRepository();
		}
		if (this.persistentClass.isInstance(materialService)) {
			return (BaseRepository<T>) materialService.getRepository();
		}
		if (this.persistentClass.isInstance(materialDetailService)) {
			return (BaseRepository<T>) materialDetailService.getRepository();
		}
		if (this.persistentClass.isInstance(formualDetailService)) {
			return (BaseRepository<T>) formualDetailService.getRepository();
		}
		if (this.persistentClass.isInstance(feedGroupService)) {
			return (BaseRepository<T>) feedGroupService.getRepository();
		}
		if (this.persistentClass.isInstance(feedingService)) {
			return (BaseRepository<T>) feedingService.getRepository();
		}
		if (this.persistentClass.isInstance(salesService)) {
			return (BaseRepository<T>) salesService.getRepository();
		}
		if (this.persistentClass.isInstance(salesDatailService)) {
			return (BaseRepository<T>) salesDatailService.getRepository();
		}
		if (this.persistentClass.isInstance(sterilizeService)) {
			return (BaseRepository<T>) sterilizeService.getRepository();
		}
		if (this.persistentClass.isInstance(performanceService)) {
			return (BaseRepository<T>) performanceService.getRepository();
		}
		if (this.persistentClass.isInstance(reviseWeightService)) {
			return (BaseRepository<T>) reviseWeightService.getRepository();
		}
		if (this.persistentClass.isInstance(allotService)) {
			return (BaseRepository<T>) allotService.getRepository();
		}
		if (this.persistentClass.isInstance(allotDetailService)) {
			return (BaseRepository<T>) allotDetailService.getRepository();
		}
		if (this.persistentClass.isInstance(baseGroupService)) {
			return (BaseRepository<T>) baseGroupService.getRepository();
		}
		if (this.persistentClass.isInstance(baseGroupDetailService)) {
			return (BaseRepository<T>) baseGroupDetailService.getRepository();
		}
		if (this.persistentClass.isInstance(breedingOnHandService)) {
			return (BaseRepository<T>) breedingOnHandService.getRepository();
		}
		if (this.persistentClass.isInstance(immuneHealthProjectService)) {
			return (BaseRepository<T>) immuneHealthProjectService.getRepository();
		}
		if (this.persistentClass.isInstance(immuneHealthService)) {
			return (BaseRepository<T>) immuneHealthService.getRepository();
		}
		if (this.persistentClass.isInstance(immunePlanService)) {
			return (BaseRepository<T>) immunePlanService.getRepository();
		}
		if (this.persistentClass.isInstance(hintService)) {
			return (BaseRepository<T>) hintService.getRepository();
		}
		if (this.persistentClass.isInstance(damBreedStateRepService)) {
			return (BaseRepository<T>) damBreedStateRepService.getRepository();
		}
		//测试
		if (this.persistentClass.isInstance(aoqunTestService)) {
			return (BaseRepository<T>) aoqunTestService.getRepository();
		}
		return null;
	}

	private static Class<Object> getSuperClassGenricType(final Class clazz,
			final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	/**
	 * 分页参数
	 * @param page
	 * 			第几页
	 * @param properties
	 * 			排序参数
	 * */
	protected PageRequest pageable(Integer page,String... properties ){
		return new PageRequest(page, GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, properties);
	}
	
	protected PageRequest pageableASC(Integer page,String... properties){
		return new PageRequest(page, GlobalConfig.PAGE_SIZE, Sort.Direction.ASC, properties);
	}
	/**
	 * 分页参数
	 * @param page
	 * 			第几页
	 * @param properties
	 * 			排序参数
	 * */
	protected PageRequest pageable(Integer page,Integer MaxNum ,String... properties ){
		return new PageRequest(page, MaxNum, Sort.Direction.DESC, properties);
	}
	
	/**
	 * 分页参数
	 * @param page
	 * 			第几页
	 * */
	protected PageRequest pageable(Integer page){
		return new PageRequest(page, GlobalConfig.PAGE_SIZE);
	}
	
	/**
	 * 开始计算时间
	 * */
	public void startTime(){
		ExecuteTime.getExecuteTime().inStartTime();
	}
	
	/**
	 * 结束计算时间
	 * */
	public void endTime(){
		ExecuteTime.getExecuteTime().inEndTime();
	}
	
	/**
	 * 计算模式
	 * @param i 0:秒 1:毫秒 2:毫微秒 
	 * */
	public void setMode(int i){
		ExecuteTime.getExecuteTime().setMode(i);
	}
	
	public String getFilePath() {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").toString().replace("WEB-INF/classes/", "")
				+ "file/";
		return path.replaceAll("file:", "");
	}
	/**
	 * 动态更新
	 * @param form
	 *            从表单的对象实体
	 * @param old
	 *            数据库的对象实体
	 * @throws Exception
	 * 
	 * @return old
	 */
	public static Object reflectionUpdate(Object form, BaseRepository repository)
			throws Exception {
		Class<? extends Object> clazz = form.getClass();
		Field[] field = clazz.getDeclaredFields();
		Object id = null;
		for (Field f : field) {
			id = clazz.getMethod("getId").invoke(form);
		}
		Object old = repository.findOne((Long) id);
		for (Field f : field) {
			Object obj = clazz.getMethod(
					"get" + StringUtils.upperCase(f.getName())).invoke(form);
			if (obj != null && (!"".equals(obj))) {
				f.setAccessible(true);
				f.set(old, obj);
			}
			if ("null".equals(obj)){
				f.setAccessible(true);
				f.set(old,null);
			}
		}
		return old;
	}
	
}
