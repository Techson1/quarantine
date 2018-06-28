package com.beiqisoft.aoqun.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.beiqisoft.aoqun.base.BaseService;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.CodeRegister;
import com.beiqisoft.aoqun.repository.CodeRegisterRepository;

public interface CodeRegisterService extends BaseService<CodeRegister, CodeRegisterRepository>{
	/**
	 * 分页获取用户对象
	 * @param codeRegister 查询条件
	 * @return
	 */
	Page<CodeRegister> find(CodeRegister codeRegister);
	
	Page<CodeRegister> find(CodeRegister codeRegister, int pageNum);
	
	 /**
     * 耳号添加校验
     * 		耳号添加时需要校验开始耳号和结束耳号是否存在中间耳号，存在则表示耳号添加失败
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
	Message codeAddsVerify(CodeRegister codeRegister);
	
	/**
	 * 可视耳标及电子耳标已使用修改
	 * @param code
	 * 			可视耳标
	 * @param rfid
	 * 			电子耳标
	 * @return boolean
	 * */
	boolean codeAndRfidUseAmend(String code,String rfid);

	/**
	 * 根据出生状态自动获取耳号
	 * */
	Map<String, Object> getCodeAndRfid(String state,Long breedId,String sex,Long orgId);
}
