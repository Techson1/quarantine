package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.config.Message;
import com.beiqisoft.aoqun.config.SystemM;
import com.beiqisoft.aoqun.entity.BreedParameter;
import com.beiqisoft.aoqun.service.BreedParameterService;

/**
 * 繁殖参数访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "breedParameter")
public class BreedParameterController extends BaseController<BreedParameter,BreedParameterService> {
	@RequestMapping(value ="list")
    public Page<BreedParameter> list(BreedParameter breedParameter) throws InterruptedException{
		return breedParameterService.find(breedParameter);
    }
	
	/**
	 * 繁殖参数初始化
	 * */
	@RequestMapping(value="init")
	public Message init(){
		breedParameterService.getRepository().deleteAll();
		List<BreedParameter> list =new ArrayList<BreedParameter>();
		list.add(new BreedParameter(SystemM.BREED_PARAMETER_YOUTH,"大于",100));
		list.add(new BreedParameter(SystemM.BREED_PARAMETER_GROW,"大于",270));
		list.add(new BreedParameter(SystemM.BREED_PARAMETER_DONOR_REPLY,"",45));
		list.add(new BreedParameter(SystemM.SEMEN_DAY_AGE,"",54));
		list.add(new BreedParameter(SystemM.BREED_PARAMETER_CROSS,"",180));
		list.add(new BreedParameter(SystemM.BREED_PREGNANCY_ONE,"",30));
		list.add(new BreedParameter(SystemM.BREED_PREGNANCY_TWO,"",45));
		list.add(new BreedParameter(SystemM.BREED_PRODUCTION,"",147));
		list.add(new BreedParameter(SystemM.BREED_WEANING,"",60));
		breedParameterService.getRepository().save(list);
		return SUCCESS;
	}
}
