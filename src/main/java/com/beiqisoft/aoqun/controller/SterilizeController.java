package com.beiqisoft.aoqun.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.Sterilize;
import com.beiqisoft.aoqun.service.SterilizeService;
import com.beiqisoft.aoqun.util.excel.ExportDate;
@RestController
@RequestMapping(value = "sterilize")
public class SterilizeController extends BaseController<Sterilize,SterilizeService> {
	@RequestMapping(value ="list")
    public Page<Sterilize> list(Sterilize sterilize) throws InterruptedException{
		return sterilizeService.find(sterilize);
    }
	
	/**
	 * 消毒记录
	 * */
	@RequestMapping(value="export")
	public void export(HttpServletRequest request, HttpServletResponse response,Sterilize sterilize,int number) throws IOException{
		List<Sterilize> sterilizes = sterilizeService.find(sterilize,PAGE_SIZE*number).getContent();
		for (Sterilize s:sterilizes){
			s.setPaddockName(s.getPaddock().getName());
		}
		ExportDate.writeExcel("sterilize",response,sterilizes,
				new String[]{"paddockName","sterilizeDate","drugName","recorder"},
				SystemM.PATH+UUID.randomUUID().toString()+".xls",
				new String[]{"饲舍名称","日期","消毒药品","操作人"});
	}
}
