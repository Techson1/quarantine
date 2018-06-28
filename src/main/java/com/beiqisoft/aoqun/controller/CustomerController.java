package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Customer;
import com.beiqisoft.aoqun.service.CustomerService;

/**
 * 客户访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "customer")
public class CustomerController extends BaseController<Customer,CustomerService> {
	@RequestMapping(value ="list")
    public Page<Customer> list(Customer customer) throws InterruptedException{
		return customerService.find(customer);
    }
	
	/**
	 * 客户名称校验
	 * @param firstName
	 * @return Message
	 * */
	@RequestMapping(value ="firstNameVerify")
    public boolean firstNameVerify(String firstName,Long id) {
		Customer customer = customerService.getRepository().findByFirstName(firstName);
		if (id == null && customer!=null) return false;
		if (customer!=null && !customer.getId().equals(id)) 
			return false;
		return true;
    }
	
	/**
	 * 客户名称可用修改
	 * @param firstName
	 * @return Message
	 * */
	@RequestMapping(value ="flagUp/{id}/{flag}")
    public Message flagUp(@PathVariable Long id,@PathVariable String flag) {
		customerService.getRepository().save(customerService.getRepository().findOne(id).setFlagReturnThis(flag));
		return SUCCESS;
    }
	
	/**
	 * 查询名称
	 * */
	@RequestMapping(value="findByFirstName")
	public Customer findByFirstName(String firstName){
		return customerService.getRepository().findByFirstName(firstName);
	}
	
	/**
	 * 模糊查询名称
	 * */
	@RequestMapping(value="findByFirstNameLike")
	public List<Customer> findByFirstNameLike(String firstName){
		return customerService.getRepository().findByFirstNameLike("%"+firstName+"%");
	}
}
