package com.yann.demo.handler;

public class CheckException extends RuntimeException {
	private static final long serialVersionUID = -625722187144418991L;

	private String fieldName;
	
	private String fieldValue;
	
	public CheckException() {
		super();
	}

	public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckException(String message) {
		super(message);
	}

	public CheckException(Throwable cause) {
		super(cause);
	}

	public CheckException(String fieldName, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
