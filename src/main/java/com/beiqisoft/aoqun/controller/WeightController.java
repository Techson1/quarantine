package com.beiqisoft.aoqun.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.entity.Paddock;
import com.beiqisoft.aoqun.entity.Weight;
import com.beiqisoft.aoqun.service.WeightService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.excel.ExportDate;
import com.beiqisoft.aoqun.util.json.JSON;
import com.beiqisoft.aoqun.util.page.PageRewrite;
import com.beiqisoft.aoqun.vo.WeightVo;
@RestController
@RequestMapping(value = "wigth")
public class WeightController extends BaseController<Weight,WeightService> {
	private Logger logger = LoggerFactory.getLogger(WeightController.class);
	 
	
	@JSON(type=BaseInfo.class,include="code,breed,sex,birthDay")
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Paddock.class,include="name")
	@JSON(type=Organization.class,include="brief")
	@RequestMapping(value ="list")
    public PageRewrite<Weight> list(Weight wigth) throws InterruptedException{
		PageRewrite<Weight> weights = new PageRewrite<Weight>(wigthService.find(wigth));
		return weights;
    }
	
	public List<Weight> verifyExcel(String fileName) throws Exception{
		// return 
		return null;
	}
	
	/**
	 * 原始体重导出
	 * 
	 * @param wigth 查询条件
	 * @param number 页数
	 * @throws IOException 
	 * */
	@RequestMapping(value ="export")
	public void  export(HttpServletRequest request, HttpServletResponse response,Weight wigth,int number) throws IOException{
		List<Weight> weights = wigthService.find(wigth,PAGE_SIZE*number).getContent();
		for (Weight w:weights){
			w.setCode(w.getBase().getCode());
			w.setPaddockName(w.getPaddock()!=null?w.getPaddock().getName():"");
			w.setOrgName(w.getOrg()!=null?w.getOrg().getOrgName():"");
		}
		ExportDate.writeExcel("weight",response,weights,
				new String[]{"code","weights","weighthDate","dayAge","monthAge","operator","lastDay","daily","paddockName","orgName"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"耳号","体重","称重日期","称重日龄","称重月龄","操作员","距上次天数","日增重","称重时圈舍","分厂名称"});
	}
	
	@RequestMapping(value = "/admin/excel")
	    public List<WeightVo> upload(HttpServletRequest request, HttpServletResponse response,@RequestParam("file_Name") MultipartFile file,Long orgId) {
		   logger.info("start upload file ...."+file);
		   List<WeightVo> mesList=new ArrayList<WeightVo>();
	        String code_type=request.getParameter("code_type");//耳号类型  1表示可视耳号 2 表示电子耳号
	        String weight_type=request.getParameter("weight_type");//4表示 断奶重  5表示月龄重
	        
	        if(file==null) {
	        	WeightVo vo=new WeightVo();
	        	vo.setCode(null);
	        	vo.setMessage(new Message(1, "上传文件不能为空"));
	        	mesList.add(vo) ;
	            return mesList;
	        }
			String fileName = file.getOriginalFilename();
			if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {  
				WeightVo vo=new WeightVo();
	        	vo.setCode(null);
	        	vo.setMessage(new Message(2, "上传文件格式错误，请上传后缀为.xls或.xlsx的文件"));
	        	mesList.add(vo) ;
	            return mesList;
				 
		    } 
			HttpSession session=request.getSession();
			
		    String filePath = request.getSession().getServletContext().getRealPath("upload/");
		    String path = filePath+fileName;
	        try {
				File targetFile = new File(filePath);
				if(!targetFile.exists()){    
				    targetFile.mkdirs();    
				}
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
				out.write(file.getBytes());
				out.flush();
				out.close(); 
				//addFileTask(path,Integer.valueOf(code_type),Integer.valueOf(weight_type));
				mesList=wigthService.weightExcel(path, Integer.valueOf(code_type),Integer.valueOf(weight_type),orgId);
	        } catch (Exception e) {
	            e.printStackTrace();
	            WeightVo vo=new WeightVo();
	        	vo.setCode(null);
	        	vo.setMessage(new Message(500, "上传失败"+e.getMessage()));
	        	mesList.add(vo) ;
	            return mesList;
	             
	        }
	        session.setAttribute(session.getId()+"_mesList", mesList);
			return mesList;
	   }
	    

	/**
	 * 
	 * 添加校验
     * @param type
     * 			1:断奶重
     *          2:月龄重
     * */
    @RequestMapping(value="addVerify")
    public Message addVerify(Weight wigth,String earTag,String type){
    	return wigthService.addVerify(wigth,earTag,type);
    }
	
    /**
     * @param type
     * 			1:断奶重
     *          2:月龄重
     * */
	@RequestMapping(value="add")
	public Message add(Weight wigth,String earTag,String type){
		BaseInfo base =baseInfoService.findByCodeOrRfid(earTag);
		wigth.setBase(base);
		wigthService.getRepository().save(wigth);
		if (SystemM.WEIGHT_TYPE_WEANING.equals(type)){
			base.setWeaningDate(wigth.getWeighthDate());
			base.setWeaningWeight(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(base);
		}
		//刷新上一次体重数据
		 wigthService.refresh(base);
		//刷新矫正数据
	    reviseWeightService.anew(base.getCode());
		return SUCCESS;
	}
	/**
	 * @author json
	 * 批量导入excle，点击提交按钮
	 * @return List<Message>
	 */
	@PostMapping("addAllWeight")
	public Message addAllWeight(HttpServletRequest request, HttpServletResponse response,@RequestBody WeightVo vos[],Long orgId){
		
		for(WeightVo vo:vos) {
			Weight wigth=new Weight();
			wigth.setWeights(String.valueOf(vo.getWeight()));
			wigth.setCode(vo.getCode());
			wigth.setWeighthDate(DateUtils.StrToDate(vo.getTime()));
			wigth.setRecorder(vo.getRecorder());
			wigth.setOperator(vo.getOperator());
			wigth.setType(vo.getType());
			Organization org=new Organization();
			org.setId(orgId);
			wigth.setOrg(org);
			
			BaseInfo base =baseInfoService.findByCodeOrRfid(vo.getCode());
			wigth.setBase(base);
			wigthService.getRepository().save(wigth);
			if (SystemM.WEIGHT_TYPE_WEANING.equals(vo.getType())){//断奶重
				base.setWeaningDate(DateUtils.StrToDate(vo.getTime()));
				base.setWeaningWeight(Double.parseDouble(wigth.getWeights()));
				baseInfoService.getRepository().save(base);
			}
			//刷新上一次体重数据+
			 wigthService.refresh(base);
			//刷新矫正数据
		    reviseWeightService.anew(base.getCode());
		    wigth=null;
		}
		
		return SUCCESS;
	}
	/**
	 * 修改称重接口
	 */
	@RequestMapping(value="update")
	public Message update(Weight wigth){
		if (SystemM.WEIGHT_TYPE_INITIAL.equals(wigth.getType())){
			wigth.setInitial(wigthService.getRepository().findOne(wigth.getId()));
			wigth.getBase().setInitialWeigh(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(wigth.getBase());
		}
		if (SystemM.WEIGHT_TYPE_WEANING.equals(wigth.getType())){
			wigth.getBase().setInitialWeigh(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(wigth.getBase());
		}
		wigthService.getRepository().save(wigth);
		//刷新上一次体重数据
		 wigthService.refresh(wigth.getBase());
		//刷新矫正数据
	    reviseWeightService.anew(wigth.getBase().getCode());
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * */
	@RequestMapping(value="deleteRefresh/{id}")
	public Message deleteRefresh(@PathVariable Long id){
		Weight weight= wigthService.getRepository().findOne(id);
		BaseInfo base=weight.getBase();
		wigthService.getRepository().delete(id);
		//刷新上一次体重数据
		 wigthService.refresh(base);
		//刷新矫正数据
	    reviseWeightService.anew(base.getCode());
		return SUCCESS;
	}
	
	 /**
     * @param type
     * 			1:断奶重
     *          2:月龄重
     * */
	@RequestMapping(value="appAdd")
	public Weight appAdd(Weight wigth,String earTag,String type){
		BaseInfo base =baseInfoService.findByCodeOrRfid(earTag);
		wigth.setBase(base);
		wigthService.getRepository().save(wigth);
		if (SystemM.WEIGHT_TYPE_WEANING.equals(type)){
			base.setWeaningDate(wigth.getWeighthDate());
			base.setWeaningWeight(Double.parseDouble(wigth.getWeights()));
			baseInfoService.getRepository().save(base);
		}
		//刷新上一次体重数据
		 wigthService.refresh(base);
		//刷新矫正数据
	    reviseWeightService.anew(base.getCode());
		return wigth;
	}
}
