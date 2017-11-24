package com.sap.ems.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.builder.ReleaseId;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.ems.dao.RuleDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class RuleEngineTest {

	@Resource
	private RuleEngine ruleEngine;
	@Resource
	private RuleDao ruleDao;

	@Test
	public void testApplyRuleChanges() {
		ruleEngine.applyRuleChanges();
	}

	@Test
	public void TestSingleRule() {
		this.insertSingleRule();

		ReleaseId releaseId1 = ruleEngine.generateReleaseId(1);

	}

	public void insertSingleRule() {
		StringBuffer whenString = new StringBuffer();
		whenString.append("package com.sap.gamification.ruleengine\n");
		whenString.append("global java.util.List list;\n");
		whenString.append("global java.util.List list2;\n");

		whenString.append("rule R1\n");
		whenString.append(" timer (int: 3s)\n");
		whenString.append(" when\n");
		whenString.append("   $m : Message( message == \"Hello World1\" )\n");

		StringBuffer thenString = new StringBuffer();
		thenString.append(" then\n");
		thenString.append("   list.add( $m );\n");
		thenString.append("   retract( $m );\n");
		thenString.append("   Message newMessage = new Message(\"Hello World2\");\n");
		thenString.append("   insert(newMessage);\n");
		thenString.append("end\n");

		String name = "testName03";
		String displayName = "testDisplayName03";
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
//		ruleDao.insertRule(name, displayName, whenClause, thenClause, validFrom, validTo, delay, priority, description,
//				isInternal, version, model, isEnable, isDirty, isDeployed);
	}

}
