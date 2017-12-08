package com.sap.ems.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.ems.dao.RuleDao;
import com.sap.ems.entity.Entitlement;
import com.sap.ems.entity.Rule;
import com.sap.ems.entity.SalesOrder;
import com.sap.ems.service.impl.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class RuleEngineTest {

	@Resource
	private RuleEngine ruleEngine;
	@Resource
	private RuleDao ruleDao;

	@Test
	public void TestSingleRule() {
		Rule rule = ruleDao.queryById(10000);
		rule.setEnable(true);
		System.out.println(rule.toString());

		Collection<Rule> rules = new ArrayList<Rule>();
		rules.add(rule);

		ruleEngine.deployRuleSet(rules, 0, 0, true);

		Message message = new Message();
		message.setMessage("Good Bye My Hello World");
		message.setStatus(Message.GOODBYE);

		ruleEngine.setKsession();
		ruleEngine.getKession().insert(message);
		ruleEngine.getKession().fireAllRules(1000000 * 2);
	}

	@Test
	public void TestFireAllRule() {
		// this.insertSingleRule();

		ruleEngine.applyRuleChanges();
		ruleEngine.getKession().fireAllRules(1000000 * 2);

	}

	@Test
	public void TestEntitlMentAdjustment() {
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setCustomerId(30000);
		Entitlement entitlement = new Entitlement();

		System.out.println("Before fire the Rules,  EntitleMent Status is : " + entitlement.getStatus());
		// fireRuleEngine
		ruleEngine.applyRuleChanges();
		ruleEngine.setKsession();
		ruleEngine.getKession().insert(salesOrder);
		ruleEngine.getKession().insert(entitlement);
		ruleEngine.getKession().fireAllRules(1000000 * 2);

		System.out.println("After fire the Rules,  EntitleMent Status is : " + entitlement.getStatus());

	}

	public void insertSingleRule() {
		StringBuffer whenString = new StringBuffer();
		whenString.append(" Message( status == Message.GOODBYE, myMessage :message )\n");

		StringBuffer thenString = new StringBuffer();
		thenString.append(" System.out.println( myMessage );\n");

		String whenDrl = whenString.toString();
		String thenDrl = thenString.toString();

		String name = "Hello Word";
		String displayName = "TestRule";
		byte[] whenClause = whenString.toString().getBytes();
		byte[] thenClause = thenString.toString().getBytes();
		Date validFrom = new Date();
		Date validTo = new Date();
		String delay = "testDelay";
		Integer priority = 3;
		String description = "testDescription";
		boolean isInternal = false;
		double version = 1.0;
		byte[] model = "model".getBytes();
		boolean isEnable = true;
		boolean isDirty = true;
		boolean isDeployed = true;

		Rule rule = new Rule(name, displayName, whenClause, thenClause, whenString.toString(), thenString.toString(),
				whenDrl, thenDrl, validFrom, validTo, delay, priority, description, isInternal, version, model,
				isEnable, isDirty, isDeployed);

		ruleDao.insertRule(rule);

	}

}
