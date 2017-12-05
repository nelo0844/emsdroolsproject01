package com.sap.ems.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.ems.entity.Rule;

public interface RuleDao {

	/**
	 * query rule by id
	 * 
	 * @param ruleId
	 * @return
	 */
	Rule queryById(@Param("id") long id);

	/**
	 * query all rules
	 * 
	 * @return
	 */
	List<Rule> queryAll();

	/**
	 * 
	 * @param rule
	 * @return
	 */
	int insertRule(Rule rule);

	/**
	 * 
	 * @param rule
	 * @return
	 */
	int updateRule(Rule rule);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(@Param("id") long id);
	
}
