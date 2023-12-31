package com.lvho.invoice.custom;

public final class Constants {
    private Constants() {}
	
	public static final String MESSAGE_INVALID_EMAIL = "email is invalid.";
	public static final String MESSAGE_INVALID_NAME = "name is invalid.";
    public static final String MESSAGE_INVALID_START_DATE = "startDate is invalid.";
	public static final String MESSAGE_INVALID_DUE_DATE = "dueDate is invalid.";

	public static final String MESSAGE_EMPLOYEE_ID_NOT_EXIST = "employeeId does not exist.";
    public static final String MESSAGE_PROJECT_ID_NOT_EXIST = "projectId does not exist.";
    public static final String MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST = "puchaseOrderId does not exist.";

	public static final String MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST = "employee with the same email exists.";
    public static final String MESSAGE_SAME_PURCHASE_ORDER_NAME_EXIST = "purchase order with the same name exists.";
    public static final String MESSAGE_SAME_PROJECT_NAME_EXIST = "project with the same name exists.";
}
