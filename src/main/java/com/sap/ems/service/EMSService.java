package com.sap.ems.service;

import java.util.List;
import java.util.Map;

import com.sap.ems.dto.RuleDto;
import com.sap.ems.entity.Rule;

public interface EMSService {

	public List<Map> getMappingFields();
	
	public List<Rule> getAllRules();
	
	public RuleDto getRuleById(long ruleId);

	public int insertRule(RuleDto rule);
	
	public int updateRule(RuleDto rule);
	
	public int deleteRule(long ruleId);
	
}
