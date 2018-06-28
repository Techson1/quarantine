package com.beiqisoft.aoqun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.Breed;
import com.beiqisoft.aoqun.entity.MonthRankOnHand;
import com.beiqisoft.aoqun.entity.RankTest;
import com.beiqisoft.aoqun.service.MonthRankOnHandService;
import com.beiqisoft.aoqun.util.DateUtils;
import com.beiqisoft.aoqun.util.json.JSON;
@RestController
@RequestMapping(value = "monthRankOnHand")
public class MonthRankOnHandController extends BaseController<MonthRankOnHand,MonthRankOnHandService> {
	@RequestMapping(value ="list")
    public Page<MonthRankOnHand> list(MonthRankOnHand monthRankOnHand) throws InterruptedException{
		return monthRankOnHandService.find(monthRankOnHand);
    }
	
	/**不分页查询*/
	@JSON(type=Breed.class,include="breedName")
	@JSON(type=RankTest.class,include="name,rank")
	@RequestMapping(value ="lists")
	public List<MonthRankOnHand> lists(MonthRankOnHand monthRankOnHand) throws InterruptedException{
		if (monthRankOnHand.getBreed()==null)
			return new ArrayList<>();
		if (monthRankOnHand.getBreed()!=null && monthRankOnHand.getBreed().getId()==null)
			return new ArrayList<>();
		List<MonthRankOnHand> list = new ArrayList<>();
		String time= DateUtils.DateToStr(monthRankOnHand.getCtime());
		//传输org.id
		for(Object o:monthRankOnHandService.getRepository().findByList(monthRankOnHand.getBreed().getId(),"%"+time.substring(0,7)+"%",1L)){
			Object[] params = (Object[]) o;
			list.add(new MonthRankOnHand(params));
		}
		return list;
	}
}
