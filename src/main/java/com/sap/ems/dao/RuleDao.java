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
	 * @param name
	 * @param displayName
	 * @param whenClause
	 * @param thenClause
	 * @param validFrom
	 * @param validTo
	 * @param delay
	 * @param priority
	 * @param description
	 * @param isInternal
	 * @param version
	 * @param model
	 * @return
	 */
	int insertRule(@Param("name") String name, @Param("displayName") String displayName,
			@Param("whenClause") byte[] whenClause, @Param("thenClause") byte[] thenClause,
			@Param("validFrom") Date validFrom, @Param("validTo") Date validTo, @Param("delay") String delay,
			@Param("priority") Integer priority, @Param("description") String description,
			@Param("isInternal") boolean isInternal, @Param("version") double version, @Param("model") byte[] model);
	
}
