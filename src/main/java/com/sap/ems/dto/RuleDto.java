package com.sap.ems.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sap.ems.config.CommonsConfig;

public class RuleDto<T> {
	
	private long ruleId;
	
	private String ruleName;
	
	private String displayName;
	
	private Date validFrom;
	
	private Date validTo;
	
	private String delay;
	
	private Integer priority;
	
	private String description;
	
	private double version;
	
//	private List model;
	
	private boolean internal;
	
	private boolean enabled;
	
	private boolean dirty;
	
	private boolean deployed;
	
	private List<RulePartDto> whenPart;
	
	private List<RulePartDto> thenPart;
	
	private String whenString;
	
	private String thenString;
	
	private String whenDrl;
	
	private String thenDrl;

	public RuleDto(long ruleId, String ruleName, String displayName, Date validFrom, Date validTo, String delay,
			Integer priority, String description, double version, boolean internal, boolean enabled, boolean dirty,
			boolean deployed, List<RulePartDto> whenPart, List<RulePartDto> thenPart, String whenString, String thenString) {
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.displayName = displayName;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.delay = delay;
		this.priority = priority;
		this.description = description;
		this.version = version;
		this.internal = internal;
		this.enabled = enabled;
		this.dirty = dirty;
		this.deployed = deployed;
		this.whenPart = whenPart;
		this.thenPart = thenPart;
	}

	public RuleDto(long ruleId, String ruleName, String displayName, Date validFrom, Date validTo, String delay,
			Integer priority, String description, double version, boolean internal, boolean enabled, boolean dirty,
			boolean deployed, String whenString, String thenString, String whenDrl, String thenDrl) {
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.displayName = displayName;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.delay = delay;
		this.priority = priority;
		this.description = description;
		this.version = version;
		this.internal = internal;
		this.enabled = enabled;
		this.dirty = dirty;
		this.deployed = deployed;
		this.whenString = whenString;
		this.thenString = thenString;
		this.whenDrl = whenDrl;
		this.thenDrl = thenDrl;
	}

	public List<RulePartDto> getWhenPart() {
//		return (null == this.whenPart) ? null : new String(this.whenPart, CommonsConfig.CHARSET);
		return this.whenPart;
	}

	public void setWhenPart(List<RulePartDto> whenPart) {
//		this.whenPart = whenPart.getBytes(CommonsConfig.CHARSET);
		this.whenPart = whenPart;
	}

	public List<RulePartDto> getThenPart() {
//		return (null == this.thenPart) ? null : new String(this.thenPart, CommonsConfig.CHARSET);
		return this.thenPart;
	}

	public void setThenPart(List<RulePartDto> thenPart) {
//		this.thenPart = thenPart.getBytes(CommonsConfig.CHARSET);
		this.thenPart = thenPart;
	}

	public RuleDto() {
		
	}

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getValidFrom() {
		if (this.validFrom == null) {
			return new Date();
		}
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

//	public List getModel() {
//		return model;
//	}
//
//	public void setModel(List model) {
//		this.model = model;
//	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean isDeployed() {
		return deployed;
	}

	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}
	
	public String getWhenString() {
		return whenString;
	}

	public void setWhenString(String whenString) {
		this.whenString = whenString;
	}

	public String getThenString() {
		return thenString;
	}

	public void setThenString(String thenString) {
		this.thenString = thenString;
	}
	
	public String getWhenDrl() {
		return whenDrl;
	}

	public void setWhenDrl(String whenDrl) {
		this.whenDrl = whenDrl;
	}

	public String getThenDrl() {
		return thenDrl;
	}

	public void setThenDrl(String thenDrl) {
		this.thenDrl = thenDrl;
	}
	
}
