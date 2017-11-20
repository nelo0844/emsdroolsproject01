package com.sap.ems.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.ems.service.RuleEngine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class RuleEngineTest {
	
	@Resource
	private RuleEngine ruleEngine;

	@Test
	public void testApplyRuleChanges() {
		ruleEngine.applyRuleChanges();
	}

}
