package com.sap.ems.util;

import org.kie.api.KieServices;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {

	public static KieContainer CreateRuleBase() {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		return kieContainer;
	}
	
	public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName) {
		StatelessKieSession kieSession = kieContainer.newStatelessKieSession(sessionName);
		return kieSession;
	}
	
	public static KieSession getStatefulKnowledgeSession(KieContainer kieContain, String sessionName) {
		KieSession kieSession = kieContain.newKieSession(sessionName);
		return kieSession;
	}
	
	public static KieSession getStatefulKnowledgeSessionWithCallback(KieContainer kieContainer, String sessionName) {
		KieSession kieSession = kieContainer.newKieSession(sessionName);
		
		kieSession.addEventListener(new RuleRuntimeEventListener() {
			
			@Override
			public void objectUpdated(ObjectUpdatedEvent event) {
				System.out.println("Object was updated \n" + "new COntent \n" + event.getObject().toString());
			}
			
			@Override
			public void objectInserted(ObjectInsertedEvent event) {
				System.out.println("Objec inserted \n" + event.getObject().toString());
			}
			
			@Override
			public void objectDeleted(ObjectDeletedEvent event) {
				System.out.println("Object retracted \n" + event.getOldObject().toString());
			}
		});
		
		kieSession.addEventListener(new AgendaEventListener() {
			
			public void matchCreated(MatchCreatedEvent event) {
				System.out.println("THe rule " + event.getMatch().getRule().getName() + " can be fired in agenda");
			}
			
			public void matchCancelled(MatchCancelledEvent event) {
				System.out.println("The rule " + event.getMatch().getRule().getName() + " can not be fired in agenda");
			}
			
			public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
				
			}
			
			public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
				
			}
			
			public void beforeMatchFired(BeforeMatchFiredEvent event) {
				System.out.println("The rule" + event.getMatch().getRule().getName() + " will be fired");
			}
			
			public void agendaGroupPushed(AgendaGroupPushedEvent event) {
				
			}
			
			public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
				
			}
			
			public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
				
			}
			
			public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
				
			}
			
			public void afterMatchFired(AfterMatchFiredEvent event) {
				System.out.println("The rule " + event.getMatch().getRule().getName() + " has be fired");
			}
		});
		
		return kieSession;
	}

}
