package com.sap.ems.entity;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionPersistence {
	
	protected long id;
	
	protected byte[] sessionBinary;
	
	protected Date lastUpdate;
	
	protected int version;
	
	protected boolean active;
	
	protected int slot;
	
	protected int factCount;
	
	protected int activeRules;
	
	protected Date lastVerification;
	
	protected boolean migrationDone = false;
	
	protected boolean verified;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSessionBinary(byte[] param) {
		this.sessionBinary = param;
	}

	public byte[] getSessionBinary() {
		byte[] result = new byte[this.sessionBinary.length];
		System.arraycopy(this.sessionBinary, 0, result, 0, this.sessionBinary.length);
		return result;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getFactCount() {
		return factCount;
	}

	public void setFactCount(int factCount) {
		this.factCount = factCount;
	}

	public int getActiveRules() {
		return activeRules;
	}

	public void setActiveRules(int activeRules) {
		this.activeRules = activeRules;
	}

	public Date getLastVerification() {
		return lastVerification;
	}

	public void setLastVerification(Date lastVerification) {
		this.lastVerification = lastVerification;
	}

	public boolean isVerified() {
		return verified;
	}

	public boolean isActive() {
		return active;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	   
	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isMigrationDone() {
		return migrationDone;
	}

	public void setMigrationDone(boolean migrationDone) {
		this.migrationDone = migrationDone;
	}
	
}
