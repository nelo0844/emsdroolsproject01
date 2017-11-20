package com.sap.ems.service;

import java.util.Collection;
import java.util.EventObject;

import org.kie.api.builder.ReleaseId;

import com.sap.ems.entity.Rule;

public interface RuleEngine {

	/**
	 * Triggers drools to fire all rules on the agenda.
	 *
	 * @param obj
	 *            The event object, which has caused the firing.
	 * @throws RuleRuntimeException
	 */
	void fireRules(EventObject obj);
	
	/**
	 * Tries to load the currently staged rules into the engine
	 * 
	 * @throws RuleRuntimeException
	 */
	void applyRuleChanges();
	
	/**
	 * 
	 * @param rulesToDeploy
	 * @param releaseVersion
	 * @param ruleVersion
	 * @param upgrade
	 */
	void deployRuleSet(Collection<Rule> rulesToDeploy, int releaseVersion, int ruleVersion, boolean upgrade);
	
	/**
	 * 
	 * @param ruleVersion
	 * @return
	 */
	ReleaseId generateReleaseId(int ruleVersion);
}
