package com.beiqisoft.aoqun.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beiqisoft.aoqun.base.BaseController;
import com.beiqisoft.aoqun.entity.BreedingState;
import com.beiqisoft.aoqun.service.BreedingStateService;
@RestController
@RequestMapping(value = "breedingState")
public class BreedingStateController extends BaseController<BreedingState,BreedingStateService> {
	@RequestMapping(value ="list")
    public Page<BreedingState> list(BreedingState breedingState) throws InterruptedException{
		return breedingStateService.find(breedingState);
    }
}
