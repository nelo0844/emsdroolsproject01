package com.sap.ems.dto;

import com.sap.ems.enums.MessageType;

public class RuleErrorMessage {

	private MessageType ID;
	private String message;

	public RuleErrorMessage(String message) {
		this.ID = MessageType.ERROR;
		this.message = message;
	}

	public MessageType getID() {
		return ID;
	}

	public void setID(MessageType iD) {
		ID = iD;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
