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
	 * @param isEnable
	 * @param isDirty
	 * @param isDeployed
	 * @return
	 */
	int insertRule(@Param("name") String name, @Param("displayName") String displayName,
			@Param("whenClause") byte[] whenClause, @Param("thenClause") byte[] thenClause,
			@Param("whenString") String whenString, @Param("thenString") String thenString,
			@Param("whenDrl") String whenDrl, @Param("thenDrl") String thenDrl,
			@Param("validFrom") Date validFrom, @Param("validTo") Date validTo, @Param("delay") String delay,
			@Param("priority") Integer priority, @Param("description") String description,
			@Param("isInternal") boolean isInternal, @Param("version") double version,
			@Param("isEnable") boolean isEnable, @Param("isDirty") boolean isDirty,
			@Param("isDeployed") boolean isDeployed);

	/**
	 * 
	 * @param id
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
	 * @param isEnable
	 * @param isDirty
	 * @param isDeployed
	 * @return
	 */
	int updateRule(@Param("id") long id, @Param("name") String name, @Param("displayName") String displayName,
			@Param("whenClause") byte[] whenClause, @Param("thenClause") byte[] thenClause,
			@Param("whenString") String whenString, @Param("thenString") String thenString,
			@Param("whenDrl") String whenDrl, @Param("thenDrl") String thenDrl,
			@Param("validFrom") Date validFrom, @Param("validTo") Date validTo, @Param("delay") String delay,
			@Param("priority") Integer priority, @Param("description") String description,
			@Param("isInternal") boolean isInternal, @Param("version") double version,
			@Param("isEnable") boolean isEnable, @Param("isDirty") boolean isDirty,
			@Param("isDeployed") boolean isDeployed);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(@Param("id") long id);
	
}
