package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.EmbryoGrading;
import com.beiqisoft.aoqun.service.EmbryoGradingService;

/**
 * 胚胎定价访问控制类
 * @author 程哲旭
 * @version 1.0
 * @time 2017年11月4日上午9:10:00
 */
@RestController
@RequestMapping(value = "embryoGrading")
public class EmbryoGradingController extends BaseController<EmbryoGrading,EmbryoGradingService> {
	@RequestMapping(value ="list")
    public Page<EmbryoGrading> list(EmbryoGrading embryoGrading) throws InterruptedException{
		return embryoGradingService.find(embryoGrading);
    }
	
	@RequestMapping(value ="verify")
    public boolean verify(EmbryoGrading embryoGrading){
		return embryoGradingService.getRepository().findByQualityGradeAndGeneGradeAndBreed_id(
				embryoGrading.getQualityGrade(), embryoGrading.getGeneGrade(),
					embryoGrading.getBreed().getId())==null;
    }
	
	
}