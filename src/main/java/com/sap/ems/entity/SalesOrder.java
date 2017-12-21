package com.sap.ems.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.sap.ems.util.EntityToString;

public class SalesOrder {

	private long soId;

	private String customerName;

	private long customerId;

	private String referanceDocumentNo;

	private String documentNumber;

	private String parentId;

	private ArrayList<SalesOrderItem> salesOrderItem;

	public ArrayList<SalesOrderItem> getSalesOrderItem() {
		return salesOrderItem;
	}

	public long getSoId() {
		return soId;
	}

	public void setSoId(long soId) {
		this.soId = soId;
	}

	public void setSalesOrderItem(ArrayList<SalesOrderItem> salesOrderItem) {
		this.salesOrderItem = salesOrderItem;
	}

	public long getId() {
		return soId;
	}

	public void setId(long soId) {
		this.soId = soId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getReferanceDocumentNo() {
		return referanceDocumentNo;
	}

	public void setReferanceDocumentNo(String referanceDocumentNo) {
		this.referanceDocumentNo = referanceDocumentNo;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "SalesOrder [soId=" + soId + ", customerName=" + customerName + ", customerId=" + customerId
				+ ", referanceDocumentNo=" + referanceDocumentNo + ", documentNumber=" + documentNumber + ", parentId="
				+ parentId + "]";
	}

	public String getString() {
		return EntityToString.getString(this, this.getClass());
	}

}
