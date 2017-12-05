package com.sap.ems.service;

import java.util.List;
import java.util.Map;

import com.sap.ems.dto.RuleDto;
import com.sap.ems.entity.Rule;

public interface EMSService {

	public List<Map> getMappingFields();
	
	public List<RuleDto> getAllRules();
	
	public RuleDto getRuleById(long ruleId);

	public RuleDto insertRule(RuleDto rule);
	
	public RuleDto updateRule(RuleDto rule);
	
	public Integer deleteRule(long ruleId);
	
}
