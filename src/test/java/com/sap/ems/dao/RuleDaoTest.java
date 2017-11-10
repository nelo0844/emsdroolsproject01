package com.sap.ems.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.ems.entity.Rule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RuleDaoTest {
	
	@Resource
	private RuleDao ruleDao;

	@Test
	public void testQueryById() {
		long id = 10000;
		Rule rule = ruleDao.queryById(id);
		String delay = rule.getDelay();
		String when = rule.getWhen();
		String then = rule.getThen();
		System.out.println(rule.toString());
		System.out.println(delay);
		System.out.println(when);
		System.out.println(then);
	}

	@Test
	public void testQueryAll() {
		List<Rule> rules = ruleDao.queryAll();
		for(Rule rule : rules) {
			System.out.println(rule);
		}
	}

	@Test
	public void testInsertRules() {
		String name = "testName01";
		String displayName = "testDisplayName01";
		byte[] whenClause = "testWhenClause".getBytes();
		byte[] thenClause = "testThenClause".getBytes();
		Date validFrom = new Date();
		Date validTo = new Date();
		String delay = "testDelay";
		Integer priority = 3;
		String description = "testDescription";
		boolean isInternal = false;
		double version = 1.0;
		byte[] model = "model".getBytes();
		ruleDao.insertRule(name, displayName, whenClause, thenClause, validFrom, validTo, delay, priority, description,
				isInternal, version, model);
	}

}
