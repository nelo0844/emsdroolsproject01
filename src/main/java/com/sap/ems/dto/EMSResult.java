package com.sap.ems.dto;

public class EMSResult<T> {

	private boolean status;

	private T data;

	private String error;

	public EMSResult(boolean status, T data) {
		this.status = status;
		this.data = data;
	}

	public EMSResult(boolean status, String error) {
		this.status = status;
		this.error = error;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
