package com.sap.ems.dto;

public class PropertiesDto {
	
	private String property;
	
	private PropertiesDto selectedChildProperty;
	
	private String type;
	
	private String operation;
	
	private String value;
	
	private String technicalName;

	public PropertiesDto() {
		
	}

	public PropertiesDto(String property, PropertiesDto selectedChildProperty, String type, String operation,
			String value, String technicalName) {
		this.property = property;
		this.selectedChildProperty = selectedChildProperty;
		this.type = type;
		this.operation = operation;
		this.value = value;
		this.technicalName = technicalName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public PropertiesDto getSelectedChildProperty() {
		return selectedChildProperty;
	}

	public void setSelectedChildProperty(PropertiesDto selectedChildProperty) {
		this.selectedChildProperty = selectedChildProperty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getTechnicalName() {
		return technicalName;
	}

	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}
	
}
