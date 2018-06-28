package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.CodeRegister;
import com.beiqisoft.aoqun.entity.Customer;
import com.beiqisoft.aoqun.entity.Organization;
import com.beiqisoft.aoqun.service.CodeRegisterService;
import com.beiqisoft.aoqun.util.json.JSON;

/**
 * 耳标订购访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "codeRegister")
public class CodeRegisterController extends BaseController<CodeRegister,CodeRegisterService> {
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=Customer.class,include="firstName")
	@JSON(type=Organization.class,include="orgName")
	@JSON(type=CodeRegister.class,filter="codes")
	@RequestMapping(value ="list")
    public Page<CodeRegister> list(CodeRegister codeRegister) throws InterruptedException{
		return codeRegisterService.find(codeRegister);
    }
	
	/**
	 * 自动获取耳标
	 * @param state
	 * 			出生状态
	 * @return Map<String,Object>
	 * */
	@RequestMapping(value ="getCodeAndRfid")
	public Map<String,Object> getCodeAndRfid(String state,Long breedId,String sex){
		 return codeRegisterService.getCodeAndRfid(state,breedId,sex,currentUser().getOrganization().getId());
	}
	
	/**
	 * 耳标号批量添加
	 * */
    @RequestMapping(value ="codeAdds")
    public Message codeAdds(CodeRegister codeRegister){
    	List<CodeRegister> list= new ArrayList<>();
    	for (Integer i=zero;i<codeRegister.getNumber();++i){
    		//TODO 批量添加
    		list.add(new CodeRegister(codeRegister,i));
    	}
    	codeRegisterService.getRepository().save(list);
    	return SUCCESS;
    }
    
    /**
     * 删除全部
     * */
    @RequestMapping(value ="deleteAll")
    public Message deleteAll(){
    	codeRegisterService.getRepository().deleteAll();
    	return SUCCESS;
    }
    
    /**
     * 耳号添加校验
     * 		耳号添加时需要校验开始耳号和结束耳号是否存在中间耳号，存在则表示耳号不能添加
     * @param type
     * 			耳标类型
     * 				电子耳号：2
     * 				可视耳号：3
     * @param startCode
     * 			开始电子耳号
     * @param endCode
     * 			结束电子耳号
     * @param visualStartCode
     * 			可视开始耳号
     * @param visualEndCode
     * 			结束开始耳号
     * @return Message
     * 			校验通过返回100,失败返回101
     * */
    @RequestMapping(value ="codeAddsVerify")
    public Message codeAddsVerify(CodeRegister codeRegister){
    	return codeRegisterService.codeAddsVerify(codeRegister);
    }
    
    /**
     * 耳标删除
     * */
    @RequestMapping(value="delete/{id}")
    public Message delete(@PathVariable Long id){
    	if (SystemM.PUBLIC_TRUE.equals(codeRegisterService.getRepository().findOne(id).getUseState())){
    		return new Message(ABNORMAL,"该耳标已使用不能删除");
    	}
    	codeRegisterService.getRepository().delete(id);
    	return SUCCESS;
    }
}
