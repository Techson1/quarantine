package com.beiqisoft.aoqun.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.repository.WeightRepository;
import com.beiqisoft.aoqun.service.BaseInfoService;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.vo.WeightVo;

@Service
public class WeightServiceImpl extends BaseServiceIml<Weight,WeightRepository> implements WeightService{
	private Logger logger = LoggerFactory.getLogger(WeightServiceImpl.class);
	@Autowired
	public WeightRepository weigthRepository;
	@Autowired
	public BaseInfoService baseInfoService;
	
	public Page<Weight> find(final Weight wight) {
		return weigthRepository.findAll(new Specification<Weight>() {
			public Predicate toPredicate(Root<Weight> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = getEntityPredicate(wight,root,criteriaBuilder);
				query.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()])));
				return query.getRestriction();
			}
		},new PageRequest(wight.getPageNum(), GlobalConfig.PAGE_SIZE, Sort.Direction.DESC, "weighthDate"));
	}
	
	public Page<Weight> find(Weight wigth, int size) {
		return weigthRepository.findAll(new Specification<Weight>() {
			public Predicate toPredicate(Root<Weight> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				return query.getRestriction();
			}
		},new PageRequest(0, size, Sort.Direction.DESC, "ctime"));
	}

	public WeightRepository getRepository() {
		return weigthRepository;
	}

	@Override
	public Message addVerify(Weight weigth, String earTag,String type) {
		BaseInfo base=baseInfoService.findByCodeOrRfid(earTag);
		message = baseInfoService.flagVerify(base);
		if (!message.isCodeEqNormal()){
			return message;
		}
		if (!base.getOrg().getId().equals(weigth.getOrg().getId())){
			return GlobalConfig.setAbnormal("不是本分厂的羊,不能添加");
		}
		if (weigth.getId()==null){
			if(weigthRepository.findByBase_idAndWeighthDate(base.getId(),weigth.getWeighthDate())!=null){
				return GlobalConfig.setAbnormal("当天已经称过体重,不能再次称重");
			}
		}
		if (SystemM.WEIGHT_TYPE_WEANING.equals(type)){
			if(DateUtils.dateSubDate(weigth.getWeighthDate(), base.getBirthDay())>100){
				return GlobalConfig.setAbnormal("断奶日期不能大于出生日期100天");
			}
		}
		if (weigth.getId()==null){
			if(SystemM.WEIGHT_TYPE_WEANING.equals(type)){
				if(weigthRepository.findByBase_idAndType(
						base.getId(), SystemM.WEIGHT_TYPE_WEANING)!=null){
					return GlobalConfig.setAbnormal("该羊已添加过断奶重,不能重复添加");
				}
			}
		}
		return GlobalConfig.SUCCESS;
	}

	/**
	 * @author json
	 * 校验羊只信息
	 * @param base
	 * @param dateWeight
	 * @param orgId
	 * @return
	 */
	private Message checkWeight(BaseInfo base,Date dateWeight,Long orgId,String type) {
		//DateUtils.StrToDate(dateWeight);
		if (!base.getOrg().getId().equals(orgId)){
			return GlobalConfig.setAbnormal("不是本分厂的羊,不能添加");
		}
		if(weigthRepository.findByBase_idAndWeighthDate(base.getId(),dateWeight)!=null){
			return GlobalConfig.setAbnormal("当天已经称过体重,不能再次称重");
		}
		if(type.equals("4")) {//断奶重
			if(DateUtils.dateSubDate(dateWeight, base.getBirthDay())>100){
				return GlobalConfig.setAbnormal("断奶日期不能大于出生日期100天");
			}
			if(weigthRepository.findByBase_idAndType(
					base.getId(), SystemM.WEIGHT_TYPE_WEANING)!=null){
				return GlobalConfig.setAbnormal("该羊已添加过断奶重,不能重复添加");
			}
		}
		
		return GlobalConfig.setNormal("正确");
	}
	@Override
	public void refresh(BaseInfo base) {
		List<Weight> weights = weigthRepository.findByBase_codeOrderByWeighthDateDesc(base.getCode());
		for (int i=0;i<weights.size();i++){
			if (i+1<weights.size()){
				weights.get(i).setAgeReturnThis(weights.get(i+1),base);
			}
		}
		weigthRepository.save(weights);
	}

	@Override
	public void addBirthWeight(BaseInfo baseInfo) {
		weigthRepository.save(new Weight().addBirthWeight(baseInfo));
	}

	@Override
	public List<WeightVo> weightExcel(String filepath,int code_type,int weightType,Long orgId) throws Exception {
		logger.info("fileName:"+filepath);
			List<WeightVo> listVo=new ArrayList<WeightVo>();
			 
			try {
				File file = new File(filepath);
		        InputStream is = new FileInputStream(file);
		        String fileName = file.getName();
		        Workbook hssfWorkbook = null;
		        if (fileName.endsWith("xlsx")){
		            hssfWorkbook = new XSSFWorkbook(is);	//Excel 2007
		        }else if (fileName.endsWith("xls")){
		            hssfWorkbook = new HSSFWorkbook(is);	//Excel 2003
		        }
		        // 循环工作表Sheet
		        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
		            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
		            if (hssfSheet == null) {
		                continue;
		            }
		            // 循环行Row
		            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		                Row row = hssfSheet.getRow(rowNum);
		                if (row == null) {
		                    continue;
		                }
		                WeightVo vo=new WeightVo();
		                BaseInfo base=null;
						try {
							 
							Cell cell0 = row.getCell(0);
							//String devuid = CommonUtil.transformIMEI(getValue(cell0));
							////devInfo.setDevUid(devuid);
							logger.info("cell0..."+cell0);
							
							Cell cell1 = row.getCell(1);
							////devInfo.setSn(getValue(cell1));
							logger.info("cell1..."+cell1);
							if(code_type==1) {//可视耳号
								 vo.setCode(String.valueOf(cell1));
								 base = baseInfoService.findByCodeOrRfid(String.valueOf(cell1));
								 if(null!=base) {
									 vo.setRfid(base.getRfid());
								 }
							}
							Cell cell2 = row.getCell(2);
							//devInfo.setBrand(getValue(cell2));
							logger.info("cell2..."+cell2);
							
							if(code_type==2) {//电子耳号
								vo.setRfid(String.valueOf(cell2));
							    base = baseInfoService.findByCodeOrRfid(String.valueOf(cell2));
								 if(null!=base) {
									 vo.setCode(base.getCode());
								 }
							}
							
							 
							Cell cell3 = row.getCell(3);
							Date weightDate = null;
							if(0 == cell3.getCellType()) {
								if(HSSFDateUtil.isCellDateFormatted(cell3)) {//如果是日期格式
									weightDate = cell3.getDateCellValue();
									String time=DateUtils.getStrDate(weightDate, "yyyy-MM-dd");
									logger.info("cell3..."+cell3);
									vo.setTime(time);
								}else {
									weightDate=cell3.getDateCellValue();
									String time=DateUtils.getStrDate(cell3.getDateCellValue(), "yyyy-MM-dd");
									vo.setTime(time);
								}
							}else {
								weightDate=DateUtils.StrToDate(cell3.getStringCellValue(),"yyyy/MM/dd");
								logger.info("cell3..."+cell3);
								vo.setTime(cell3.getStringCellValue());
							}
							
							//devInfo.setModel(getValue(cell3));
							
							
							Cell cell4 = row.getCell(4);
							String olstate = getValue(cell4);
							//devInfo.setSdkVer((int)Double.parseDouble(olstate));
							logger.info("cell4..."+olstate);
							vo.setType(String.valueOf(weightType));
							if(SystemM.WEIGHT_TYPE_WEANING.equals(String.valueOf(weightType))) {//如果是断奶重，1
								vo.setWeight(Float.valueOf(olstate));
							}
							
							Cell cell5 = row.getCell(5);
							String mothWeight = getValue(cell5);
							//devInfo.setSdkVer((int)Double.parseDouble(olstate));
							logger.info("mothWeight..."+mothWeight);
							if(SystemM.WEIGHT_TYPE_NORMAL.equals(String.valueOf(weightType))) {//普通中2
								vo.setWeight(Float.valueOf(mothWeight));
							}
							
							if (base==null){
								vo.setMessage(GlobalConfig.setAbnormal(String.valueOf(cell1)+":该羊不存在"));
							}else {
							    Message msg=checkWeight(base,weightDate, orgId, String.valueOf(weightType));
								vo.setMessage(msg);
							}
							vo.setDocNum(String.valueOf(rowNum));
							listVo.add(vo);
						} catch (Exception e) {
							vo.setMessage(GlobalConfig.setAbnormal("数据格式不正确"));
							listVo.add(vo);
							logger.error("读取数据遇到异常..."+e.toString());
						}
		            }
		        }
		    } catch (Exception e) {
		    	logger.error("读取数据遇到异常..."+e.toString());
		    }
			return listVo;
	}
		private static String getValue(Cell hssfCell) {
			if(hssfCell!=null) {
				if (hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING) {
					return String.valueOf(hssfCell.getStringCellValue());
				} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
					return String.valueOf(hssfCell.getNumericCellValue());
				} else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN){
					return String.valueOf(hssfCell.getBooleanCellValue());
				}  else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_FORMULA){
					return String.valueOf(hssfCell.getCellFormula());
				} else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_BLANK){
					return String.valueOf(hssfCell.getStringCellValue());
				} 
			}
			return "";
		}
}
