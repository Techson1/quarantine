package com.beiqisoft.aoqun.repository;

import com.beiqisoft.aoqun.base.BaseRepository;
import com.beiqisoft.aoqun.entity.Formula;

public interface FormulaRepository extends BaseRepository<Formula>{

	Formula findByFormulaName(String formulaName);
}
