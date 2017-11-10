package com.sap.ems.entity;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.sap.ems.config.CommonsConfig;

public class Rule {

	public static final String RULE_NAME_PREFIX = "rule_~";
	public static final String RULE_NAME_SUFFIX = "~_id";
	public static final String RULE_NAME_PATTERN = "a-zA-Z0-9\\-_@]*";
	
	private static final Logger logger = LoggerFactory.getLogger(Rule.class);
	
	private long id;
	
	private String name;
	
	private String displayName;
	
	private byte[] whenClause;
	
	private byte[] thenClause;
	
	private Date validFrom;
	
	private Date validTo;
	
	private String delay;
	
	private Integer priority;
	
	private String description;
	
	private boolean isInternal = false;
	
	private int version;
	
	private byte[] model;
	
	/**
	 * indicates that the rule should become enabled with the next rule engine
	 * update
	 */
	private boolean isEnabled;
	
	/**
	 * indicates that rule engine relevant properties have changed. a rule
	 * engine update is required apply these changes
	 */
	private boolean isDirty;
	
	/**
	 * indicates that the rule is currently deployed in the rule engine
	 */
	private boolean isDeployed;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getWhen() {
		return (null == this.whenClause) ? null : new String(this.whenClause, CommonsConfig.CHARSET);
	}

	public void setWhen(String param) {
		this.whenClause = param.getBytes(CommonsConfig.CHARSET);
	}

	public String getThen() {
		return (null == this.thenClause) ? null : new String(this.thenClause, CommonsConfig.CHARSET);
	}

	public void setThen(String param) {
		this.thenClause = param.getBytes(CommonsConfig.CHARSET);
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
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

	public boolean isInternal() {
		return isInternal;
	}

	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public byte[] getModel() {
		return model;
	}

	public void setModel(byte[] model) {
		this.model = model;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public boolean isDeployed() {
		return isDeployed;
	}

	public void setDeployed(boolean isDeployed) {
		this.isDeployed = isDeployed;
	}

	public static String getRuleNamePrefix() {
		return RULE_NAME_PREFIX;
	}

	public static String getRuleNameSuffix() {
		return RULE_NAME_SUFFIX;
	}

	public static String getRuleNamePattern() {
		return RULE_NAME_PATTERN;
	}

	public static Logger getLogger() {
		return logger;
	}

	private final String slat = "sdfsdfjoijefikdjflsjdflksajf943829u99(*(**&^^%^%$#@";
	
	@Override
	public String toString() {

	      String rule = "rule '" + RULE_NAME_PREFIX + this.getName() + RULE_NAME_SUFFIX + "' \n";
	      // String rule = "rule '" + this.getName() + "' \n";
	      // "enabled(!RuleEngineConf.LOAD) \n";

	      // ASE (in contrast to Derby) stores an empty string as a single blank! This is why we also have to check for " "
	      // here.
	      if (this.getDelay() != null && !this.getDelay().isEmpty() && !(this.getDelay().compareTo(" ") == 0)) {
	         rule += "timer(" + this.getDelay() + ") \n";
	      }

	      if (this.getPriority() != null) {
	         rule += "salience " + this.getPriority() + " \n";
	      }

	      // used for handling exceptions in then-block
	      String exceptionHandling = "   engine.handleRuleException(drools.getRule(), e);\n";

	      // generating hash code from name, when and then. name is immutable. If when or then change, then hashcode can
	      // change as well. Scheduled activations are dropped anyway.
	      String securityManagerPass = this.generateSecurityManagerKey(this.getName() + this.getWhen() + this.getThen());

	      // note: the " + " between "System." and "[g|s]etSecurityManager" are necessary to avoid checkstyle issues there.
	      // These issues are of no use, as we need to use this code here anyway
	      String enableSecurityManager = "  ((RuleEngineSecurityManager)java.lang.System." + "getSecurityManager()).enable(\""
	            + securityManagerPass + "\");\n";
	      // disable security manager one and reset to old one.
	      String disableSecurityManager = "  ((RuleEngineSecurityManager)java.lang.System." + "getSecurityManager()).disable(\""
	            + securityManagerPass + "\");\n";

	      rule += "no-loop true \n";

	      // when and then shouldn't add "null" to the code, but skip their whens and thens
	      String when = this.getWhen() == null ? "" : this.getWhen();

	      rule += "when \n " + when + " \n" + "then \n try {\n   ";

	      // if the rule is internal, the security manager doesn't have to be enabled.
	      if (!this.isInternal()) {
	         rule += enableSecurityManager + "\n";
	      }
	      String then = this.getThen() == null ? "" : this.getThen();

	      rule += then + "\n } catch (Exception e) {\n " + disableSecurityManager + "\n" + exceptionHandling + " \n}\n";

	      // if the rule is internal, the security manager doesn't have to be disabled, as it wasn't enabled anyway.
	      if (!this.isInternal()) {
	         rule += "finally {\n" + disableSecurityManager + "\n}\n";
	      }
	      rule += "end\n";

	      return rule;
	   
	}
	
	private String generateSecurityManagerKey(String input) {
		String base = input + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
}
