package com.beiqisoft.aoqun.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.BreedingOnHand;
import com.beiqisoft.aoqun.service.BreedingOnHandService;
@RestController
@RequestMapping(value = "breedingOnHand")
public class BreedingOnHandController extends BaseController<BreedingOnHand,BreedingOnHandService> {
	@RequestMapping(value ="list")
    public Page<BreedingOnHand> list(BreedingOnHand breedingOnHand) throws InterruptedException{
		return breedingOnHandService.find(breedingOnHand);
    }
	
	@RequestMapping(value ="findByList")
	public List<BreedingOnHand> findByList(BreedingOnHand breedingOnHand){
		return breedingOnHandService.findByList(breedingOnHand);
	}
}
