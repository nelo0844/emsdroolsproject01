package com.sap.ems.entity;

import java.util.Date;

import com.sap.ems.util.EntityToString;

public class SalesOrderItem {
	
	private long soId;
	
	private int itemId;
	
	private String productId;
	
	private String productName;
	
	private Date itemValidFrom;
	
	private Date itemValidTo;
	
	private double quantity;
	
	private String documentNumber;
	
	private String cardinality;
	
	private String parentId;

	public long getSoId() {
		return soId;
	}

	public void setSoId(long soId) {
		this.soId = soId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getItemValidFrom() {
		return itemValidFrom;
	}

	public void setItemValidFrom(Date itemValidFrom) {
		this.itemValidFrom = itemValidFrom;
	}

	public Date getItemValidTo() {
		return itemValidTo;
	}

	public void setItemValidTo(Date itemValidTo) {
		this.itemValidTo = itemValidTo;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "SalesOrderItem [soId=" + soId + ", itemId=" + itemId + ", productId=" + productId + ", productName="
				+ productName + ", itemValidFrom=" + itemValidFrom + ", itemValidTo=" + itemValidTo + ", quantity="
				+ quantity + ", documentNumber=" + documentNumber + ", cardinality=" + cardinality + ", parentId="
				+ parentId + "]";
	}
	
	public String getString(){
		return EntityToString.getString(this, this.getClass());
	}
	
}
