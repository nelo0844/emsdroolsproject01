package com.sap.ems.dto;

import java.util.List;

public class RulePartDto {

	private long propertyId;
	
	private String propertyName;
	
	private List<PropertiesDto> properties;
	
	private String technicalName;

	public RulePartDto() {
		
	}

	public RulePartDto(long propertyId, String propertyName, List<PropertiesDto> properties, String technicalName) {
		super();
		this.propertyId = propertyId;
		this.propertyName = propertyName;
		this.properties = properties;
		this.technicalName = technicalName;
	}

	public long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public List<PropertiesDto> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertiesDto> properties) {
		this.properties = properties;
	}
	
	public String getTechnicalName() {
		return technicalName;
	}

	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}
	
}
