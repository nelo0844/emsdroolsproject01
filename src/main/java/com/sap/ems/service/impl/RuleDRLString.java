package com.sap.ems.service.impl;

public class RuleDRLString {

	   private String ruleDRLCode;
	   private String ruleName;

	   public RuleDRLString(String ruleDRLCode, String ruleName) {
	      super();
	      this.ruleDRLCode = ruleDRLCode;
	      this.ruleName = ruleName;
	   }

	   public String getRuleDRLCode() {
	      return ruleDRLCode;
	   }

	   public void setRuleDRLCode(String ruleDRLCode) {
	      this.ruleDRLCode = ruleDRLCode;
	   }

	   public String getRuleName() {
	      return ruleName;
	   }

	   public void setRuleName(String ruleName) {
	      this.ruleName = ruleName;
	   }
	
}
