package com.beiqisoft.aoqun.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.entity.EmbryoFlush;
import com.beiqisoft.aoqun.service.EmbryoFlushService;
/**
 * 冲胚检胚访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月30日上午9:10:00
 */
@RestController
@RequestMapping(value = "embryoFlush")
public class EmbryoFlushController extends BaseController<EmbryoFlush,EmbryoFlushService> {
	@RequestMapping(value ="list")
    public Page<EmbryoFlush> list(EmbryoFlush embryoFlush) throws InterruptedException{
		return embryoFlushService.find(embryoFlush);
    }
	
	/**冲胚校验*/
	@RequestMapping(value="addVerify")
	public Message addVerify(String donorCode,Date date,EmbryoFlush embryoFlush){
		return embryoFlushService.addVerify(donorCode,date,embryoFlush.getProject().getId());
	}
	
	/**冲胚添加*/
	@RequestMapping(value="add")
	public Message add(String donorCode,EmbryoFlush embryoFlush){
		return embryoFlushService.add(donorCode,embryoFlush);
	}
	
	/***/
	@RequestMapping(value="updateUiVerify/{id}")
	public Message updateUiVerify(@PathVariable Long id){
		return SUCCESS;
	}
	
	/**冲胚修改校验*/
	@RequestMapping(value="updateVerify")
	public Message updateVerify(EmbryoFlush embryoFlush){
		return embryoFlushService.updateVerify(embryoFlush);
	}
	
	/**冲胚修改*/
	@RequestMapping(value="update")
	public Message update(EmbryoFlush embryoFlush){
		embryoFlushService.getRepository().save(embryoFlushService.getRepository().findOne(embryoFlush.getId()).update(embryoFlush));
		return SUCCESS;
	}
	
	/**冲胚记录查询*/
	@RequestMapping(value="findByCode")
	public EmbryoFlush findByCode(String code,Long projectId){
		return embryoFlushService.getRepository().findByDonor_idAndProject_id(
				baseInfoService.findByCodeOrRfid(code).getId(), projectId);
	}
	
	/***/
	@RequestMapping(value="delVerify/{id}")
	public Message delVerify(@PathVariable Long id){
		return embryoFlushService.delVerify(id);
	}
	
}
