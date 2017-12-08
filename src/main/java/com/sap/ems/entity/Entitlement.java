package com.sap.ems.entity;

import java.util.UUID;

import com.sap.ems.intl.IEntitlementUpdate;

public class Entitlement implements IEntitlementUpdate {

	private UUID guid;

	private String reason;

	private String memory;

	private String C000000003;

	private String sourceSystem;

	private String refItemNo;

	private int channel;

	private long validFrom;

	private long validTo;

	private int storage;

	private long createdOn;

	private long changedOn;

	private String changedBy;

	private String createdBy;

	private String generationMethod;

	private String uom;

	private long customerId;

	private int simulationEntitlementNo;

	private boolean performanceAndGoals;

	private String refDocNo;

	private float quantity;

	private String comments;

	private String distributorId;

	private String refDocType;

	private String entitlementType;

	private int version;

	private long entitlementNo;

	private boolean performanceAndReward;

	private String modules;

	private String advancedEdition;

	private String entitlementMasterId;

	private String component;

	private String tenantURL;

	private long namedUsers;

	private String CLOUDSTORAGE;

	private boolean persistence;

	private String status;

	public UUID getGuid() {
		return guid;
	}

	public void setGuid(UUID guid) {
		this.guid = guid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getC000000003() {
		return C000000003;
	}

	public void setC000000003(String c000000003) {
		C000000003 = c000000003;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getRefItemNo() {
		return refItemNo;
	}

	public void setRefItemNo(String refItemNo) {
		this.refItemNo = refItemNo;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public long getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(long validFrom) {
		this.validFrom = validFrom;
	}

	public long getValidTo() {
		return validTo;
	}

	public void setValidTo(long validTo) {
		this.validTo = validTo;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public long getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(long changedOn) {
		this.changedOn = changedOn;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getGenerationMethod() {
		return generationMethod;
	}

	public void setGenerationMethod(String generationMethod) {
		this.generationMethod = generationMethod;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public int getSimulationEntitlementNo() {
		return simulationEntitlementNo;
	}

	public void setSimulationEntitlementNo(int simulationEntitlementNo) {
		this.simulationEntitlementNo = simulationEntitlementNo;
	}

	public boolean isPerformanceAndGoals() {
		return performanceAndGoals;
	}

	public void setPerformanceAndGoals(boolean performanceAndGoals) {
		this.performanceAndGoals = performanceAndGoals;
	}

	public String getRefDocNo() {
		return refDocNo;
	}

	public void setRefDocNo(String refDocNo) {
		this.refDocNo = refDocNo;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getRefDocType() {
		return refDocType;
	}

	public void setRefDocType(String refDocType) {
		this.refDocType = refDocType;
	}

	public String getEntitlementType() {
		return entitlementType;
	}

	public void setEntitlementType(String entitlementType) {
		this.entitlementType = entitlementType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getEntitlementNo() {
		return entitlementNo;
	}

	public void setEntitlementNo(long entitlementNo) {
		this.entitlementNo = entitlementNo;
	}

	public boolean isPerformanceAndReward() {
		return performanceAndReward;
	}

	public void setPerformanceAndReward(boolean performanceAndReward) {
		this.performanceAndReward = performanceAndReward;
	}

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getAdvancedEdition() {
		return advancedEdition;
	}

	public void setAdvancedEdition(String advancedEdition) {
		this.advancedEdition = advancedEdition;
	}

	public String getEntitlementMasterId() {
		return entitlementMasterId;
	}

	public void setEntitlementMasterId(String entitlementMasterId) {
		this.entitlementMasterId = entitlementMasterId;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getTenantURL() {
		return tenantURL;
	}

	public void setTenantURL(String tenantURL) {
		this.tenantURL = tenantURL;
	}

	public long getNamedUsers() {
		return namedUsers;
	}

	public void setNamedUsers(long namedUsers) {
		this.namedUsers = namedUsers;
	}

	public String getCLOUDSTORAGE() {
		return CLOUDSTORAGE;
	}

	public void setCLOUDSTORAGE(String cLOUDSTORAGE) {
		CLOUDSTORAGE = cLOUDSTORAGE;
	}

	public boolean isPersistence() {
		return persistence;
	}

	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
