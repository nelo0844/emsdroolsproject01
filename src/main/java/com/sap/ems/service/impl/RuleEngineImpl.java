package com.sap.ems.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.TimedRuleExectionOption;
import org.kie.api.runtime.conf.TimerJobFactoryOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.ems.dao.RuleDao;
import com.sap.ems.dao.SessionPersistenceDao;
import com.sap.ems.dto.EMSResult;
import com.sap.ems.dto.RuleErrorMessage;
import com.sap.ems.entity.Entitlement;
import com.sap.ems.entity.Rule;
import com.sap.ems.entity.SalesOrder;
import com.sap.ems.entity.SalesOrderItem;
import com.sap.ems.service.RuleEngine;

@Service
public class RuleEngineImpl implements RuleEngine {

	@Autowired
	private RuleDao ruleDao;
	@Autowired
	private SessionPersistenceDao sessionPersistenceDao;

	protected KieSession kSession = null;
	protected KieBase kBase = null;
	protected KieServices kServices = null;
	protected KieContainer kContainer = null;
	protected KieModule kModule = null;

	private int minorVersion = 0;

	public void fireRules(EventObject obj) {

		this.setKsession();

		try {
			int fireActivations = this.kSession.fireAllRules(1000000 * 2);

			if (fireActivations >= 1000000 * 2) {
				System.out.println("Firing rules time out.");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("EngineSync: fireRules finalised");
		}
	}

	public void setKsession() {
		KieSessionConfiguration ksconf = null;
		this.kBase = this.kContainer.getKieBase();
		ksconf = this.kServices.newKieSessionConfiguration();
		ksconf.setOption(TimedRuleExectionOption.YES);
		ksconf.setOption(TimerJobFactoryOption.get("trackable"));
		this.kSession = this.kBase.newKieSession(ksconf, null);
	}

	public KieSession getKession() {
		return this.kSession;
	}

	public EMSResult<Integer> applyRuleChanges() {

		// Get Rules
		Collection<Rule> rules = ruleDao.queryAll();

		int nextVersion = getHighestSnapshotVersion() + 1;

		EMSResult<Integer> emsResult = this.deployRuleSet(rules, nextVersion, 0, true);

		if (emsResult.isStatus() == true) {
			this.fireRules(null);
		}

		return emsResult;
	}

	@Override
	public EMSResult<Entitlement> applyAllRules(SalesOrder salesOrder) {
		// apply Rule Changes without object in Memorry
		this.applyRuleChanges();

		Entitlement entitlement = new Entitlement();
		Iterator<SalesOrderItem> iterator = salesOrder.getSalesOrderItem().iterator();

		this.getKession().insert(salesOrder);
		this.getKession().insert(entitlement);

		while (iterator != null && iterator.hasNext()) {
			this.getKession().insert(iterator.next());
		}

		this.getKession().fireAllRules();

		EMSResult<Entitlement> result = new EMSResult<Entitlement>(true, entitlement);

		return result;

	}

	public int getHighestSnapshotVersion() {
		Integer version = null;
		try {
			version = sessionPersistenceDao.queryHighestVersion();
		} catch (Exception e) {
			// logger.debug("No session snapshots found.");
			version = 0;
		}

		return version.intValue();
	}

	public EMSResult<Integer> deployRuleSet(Collection<Rule> rulesToDeploy, int releaseVersion, int ruleVersion,
			boolean upgrade) {
		// reset lists and buffer
		List<Rule> activeRules = new ArrayList<Rule>();
		List<Rule> inactiveRules = new ArrayList<Rule>();
		EMSResult<Integer> emsResult = new EMSResult<Integer>(false, "");

		try {
			for (Rule rule : rulesToDeploy) {
				if (rule.isEnable()) {
					activeRules.add(rule);
				} else {
					inactiveRules.add(rule);
				}
			}

			// try loading the active rules
			ReleaseId releaseId = generateReleaseId(releaseVersion);

			KieBuilder kb = loadRules(this.kServices, releaseId, activeRules);

			Results results = kb.getResults();
			// assertEquals(0, results.getMessages(Message.Level.ERROR).size());
			if (results.getMessages(Message.Level.ERROR).size() != 0) {

				String[] aErrorMessage = results.getMessages(Message.Level.ERROR).toString().split("\n");

				ArrayList<RuleErrorMessage> errorMessageList = new ArrayList<RuleErrorMessage>();

				for (int i = 0; i < aErrorMessage.length; i++) {
					errorMessageList.add(new RuleErrorMessage(aErrorMessage[i]));
				}

				emsResult.setData(errorMessageList);

				return emsResult;
			}

			this.kModule = kb.getKieModule();

			if (upgrade) {
				this.kContainer = this.kServices.newKieContainer(releaseId);
				this.kContainer.updateToVersion(releaseId);
			}

		} catch (Exception e) {
			emsResult.setError(e.toString());
			return emsResult;
		}

		emsResult.setStatus(true);

		return emsResult;
	}

	protected final String getRulePrefix() {
		return "package com.sap.ems.service.impl\n" + "import com.sap.ems.service.impl.Message;\n"
				+ "import com.sap.ems.entity.SalesOrder;\n" + "import com.sap.ems.entity.SalesOrderItem;\n"
				+ "import com.sap.ems.entity.Entitlement;\n" + "import com.sap.ems.intl.IEntitlementUpdate;\n"
				+ "global IEntitlementUpdate entitlementUpdate;\n";

	};

	public ReleaseId generateReleaseId(int ruleVersion) {
		this.kServices = KieServices.Factory.get();
		// minorVersion used to ensure uniqueness of releaseId.
		ReleaseId releaseId = this.kServices.newReleaseId("com.sap", "emsdroolsproject01",
				"0." + ruleVersion + "." + "1-SNAPSHOT");
		return releaseId;
	}

	private KieBuilder loadRules(KieServices ks, ReleaseId releaseId, Collection<Rule> rulesToDeploy) {

		ArrayList<RuleDRLString> ruleDRLs = new ArrayList<RuleDRLString>();

		// Add Deletion Rules (default) in one DRL
		StringBuffer ruleStringBuffer = new StringBuffer();

		String prefix = getRulePrefix();

		ruleDRLs.add(new RuleDRLString(prefix + ruleStringBuffer.toString(), "_DeletionRuleSet"));
		// Add User Rules, if enabled.
		for (Rule rule : rulesToDeploy) {
			// if (rule.isEnabled()) {
			ruleDRLs.add(new RuleDRLString(prefix + rule.toString(),
					Rule.RULE_NAME_PREFIX + rule.getName() + Rule.RULE_NAME_SUFFIX));
			// }
		}

		KieModuleModel module = ks.newKieModuleModel();

		KieBaseModel defaultBase = module.newKieBaseModel("kBase1");
		defaultBase.setEventProcessingMode(EventProcessingOption.STREAM).setDefault(true);
		defaultBase.setEqualsBehavior(EqualityBehaviorOption.EQUALITY);

		defaultBase.newKieSessionModel("defaultKSession").setDefault(true);

		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.generateAndWritePomXML(releaseId);
		kfs.writeKModuleXML(module.toXML());

		for (RuleDRLString r : ruleDRLs) {
			kfs.write("src/main/resources/" + r.getRuleName() + ".drl", r.getRuleDRLCode());
		}

		KieBuilder kb = ks.newKieBuilder(kfs);
		kb.buildAll();

		return kb;
	}

}
