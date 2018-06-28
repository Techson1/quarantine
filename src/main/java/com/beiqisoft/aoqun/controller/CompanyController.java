package com.beiqisoft.aoqun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Company;
import com.beiqisoft.aoqun.repository.CompanyRepository;
import com.beiqisoft.aoqun.service.CompanyService;

/**
 * 集团访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "company")
public class CompanyController extends BaseController<Company,CompanyService> {
	
	@Autowired
	CompanyRepository companyRepository;
	
	@RequestMapping(value ="list")
    public Page<Company> list(Company company) throws InterruptedException{
		return companyService.find(company);
    }
	
	/**
	 * 初始化集团
	 * @return "ok"
	 * */
	@RequestMapping(value ="init")
    public String init(){
		Company company=new Company();
		company.setCompanyName("奥群集团");
		company.setBrief("奥群");
		company.setAddress("天津");
		companyRepository.save(company);
		return "ok";
    }
	
	/**
	 * 集团修改
	 * @param companyName
	 * 			集团名称
	 * @param brief
	 * 			简称
	 * @param address
	 * 			地址
	 * @param people
	 * 			联系人
	 * @param ramarks
	 * 			备注
	 * */
	@RequestMapping(value ="edit")
	public Message edit(Company compan){
		companyRepository.save(compan);
		return SUCCESS;
	}
}
