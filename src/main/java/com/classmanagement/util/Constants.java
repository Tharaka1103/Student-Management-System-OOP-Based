package com.classmanagement.util;

/**
 * Application-wide constants
 * @author Class Management Team
 * @version 1.0
 */
public final class Constants {
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
    
    // User Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STUDENT = "STUDENT";
    
    // Session Attributes
    public static final String SESSION_USER = "loggedInUser";
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_USERNAME = "username";
    
    // Request Parameters
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_ID = "id";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_EMAIL = "email";
    
    // Actions
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_VIEW = "view";
    public static final String ACTION_LIST = "list";
    
    // Messages
    public static final String SUCCESS_MESSAGE = "successMessage";
    public static final String ERROR_MESSAGE = "errorMessage";
    
    // Enrollment Status
    public static final String ENROLLMENT_STATUS_ACTIVE = "ACTIVE";
    public static final String ENROLLMENT_STATUS_COMPLETED = "COMPLETED";
    public static final String ENROLLMENT_STATUS_CANCELLED = "CANCELLED";
    
    // Date Formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}