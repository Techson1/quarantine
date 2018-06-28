package com.beiqisoft.aoqun.controller.rep;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.rep.DamBreedStateRep;
import com.beiqisoft.aoqun.service.DamBreedStateRepService;
import com.beiqisoft.aoqun.util.DateUtils;
@RestController
@RequestMapping(value = "damBreedStateRep")
public class DamBreedStateRepController extends BaseController<DamBreedStateRep,DamBreedStateRepService> {
	@RequestMapping(value ="list")
    public Page<DamBreedStateRep> list(DamBreedStateRep damBreedStateRep) throws InterruptedException{
		return damBreedStateRepService.find(damBreedStateRep);
    }
	
	@RequestMapping(value="findByList")
	public List<DamBreedStateRep> findByList(Long orgId,Date date){
		List<DamBreedStateRep> list = damBreedStateRepService
				.getRepository()
				.findByCtimeAndOrg_id(date,DateUtils.dateAddInteger(date, 1),orgId);
		DamBreedStateRep dam= new DamBreedStateRep().statistics(list);
		list.add(dam);
		return list.stream().map(x->x.percentage(dam)).collect(Collectors.toList());
	}
}
