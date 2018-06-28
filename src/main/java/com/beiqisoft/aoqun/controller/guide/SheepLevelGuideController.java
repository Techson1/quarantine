package com.beiqisoft.aoqun.controller.guide;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.BaseInfo;
import com.beiqisoft.aoqun.entity.guide.SheepLevelGuide;
import com.beiqisoft.aoqun.service.guide.SheepLevelGuideService;
@RestController
@RequestMapping(value = "sheepLevelGuide")
public class SheepLevelGuideController extends BaseController<SheepLevelGuide,SheepLevelGuideService> {
	@RequestMapping(value ="list")
    public Page<SheepLevelGuide> list(SheepLevelGuide sheepLevelGuide) throws InterruptedException{
		return sheepLevelGuideService.find(sheepLevelGuide);
    }
	
	@RequestMapping(value="init")
	public String init(){
		List<SheepLevelGuide> sheeps=(List<SheepLevelGuide>) sheepLevelGuideService.getRepository().findAll();
		List<BaseInfo> bases= new ArrayList<>();
		for (SheepLevelGuide s:sheeps){
			BaseInfo base=baseInfoService.getRepository().findOne(s.getId());
			if (base==null){
				continue;
			}
			if (base.getRank()!=null && 4L!=base.getRank().getId()){
				continue;
			}
			base.setRank(rankTestService.getRepository().findOne(s.getPriceplanId()));
			bases.add(base);
		}
		baseInfoService.getRepository().save(bases);
		return "3";
	}
}
