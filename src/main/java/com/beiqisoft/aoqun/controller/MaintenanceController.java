package com.beiqisoft.aoqun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.GlobalConfig;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.service.BreedService;
import com.beiqisoft.aoqun.task.realize.BaseInfoTaskRealize;
@RestController
@RequestMapping(value = "maintenance")
public class MaintenanceController extends BaseController<Breed,BreedService> {
	
	/**
	 * 全部
	 * */
	@RequestMapping(value ="taskAll")
	public Message taskAll(){
		try{
			BaseInfoTaskRealize.getBaseInfoTaskRealize().openRealize();
			BaseInfoTaskRealize.getBaseInfoTaskRealize().task();
			return SUCCESS;
		}catch (Exception e) {
			return GlobalConfig.setAbnormal("任务执行出错,请联系维护人员");
		}
	}
	
	/**
	 * 羊只库存状态
	 * */
	@RequestMapping(value="taskBornStatus")
	public Message taskBornStatus(){
		try{
			BaseInfoTaskRealize.getBaseInfoTaskRealize().bornStatus();
			return SUCCESS;
		}catch (Exception e) {
			return GlobalConfig.setAbnormal("任务执行出错,请联系维护人员");
		}
	}
	
	/**
	 * 羊只月龄计算
	 * */
	@RequestMapping(value="taskMoonAge")
	public Message taskMoonAge(){
		try{
			BaseInfoTaskRealize.getBaseInfoTaskRealize().moonAge();
			return SUCCESS;
		}catch (Exception e) {
			return GlobalConfig.setAbnormal("任务执行出错,请联系维护人员");
		}
	}
	
	/**
	 * 羊只繁殖状态
	 * */
	@RequestMapping(value="taskBreedingState")
	public Message taskBreedingState(){
		try{
			BaseInfoTaskRealize.getBaseInfoTaskRealize().breedingState();
			return SUCCESS;
		}catch (Exception e) {
			return GlobalConfig.setAbnormal("任务执行出错,请联系维护人员");
		}
	}
	
	/**
	 * 绩效考核
	 * */
	@RequestMapping(value="taskPerformance")
	public Message taskPerformance(){
		try{
			BaseInfoTaskRealize.getBaseInfoTaskRealize().performance();
			return SUCCESS;
		}catch (Exception e) {
			return GlobalConfig.setAbnormal("任务执行出错,请联系维护人员");
		}
	}
	
	/**
	 * 存栏统计
	 * */
	@RequestMapping(value="taskPaddockNumber")
	public Message taskPaddockNumber(){
		try{
			BaseInfoTaskRealize.getBaseInfoTaskRealize().paddockNumber();
			return SUCCESS;
		}catch (Exception e) {
			return GlobalConfig.setAbnormal("任务执行出错,请联系维护人员");
		}
	}
}
