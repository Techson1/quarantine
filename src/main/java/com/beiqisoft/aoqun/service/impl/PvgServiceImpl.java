package com.beiqisoft.aoqun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beiqisoft.aoqun.base.BaseServiceIml;
import com.beiqisoft.aoqun.entity.Pvg;
import com.beiqisoft.aoqun.repository.PvgRepository;
import com.beiqisoft.aoqun.service.PvgService;

@Service
public class PvgServiceImpl extends BaseServiceIml<Pvg,PvgRepository> implements PvgService{

	@Autowired
	public PvgRepository pvgRepository;

	@Override
	public PvgRepository getRepository() {
		return pvgRepository;
	}
}
