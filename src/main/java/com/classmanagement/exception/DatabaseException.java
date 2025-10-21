package com.classmanagement.exception;

/**
 * Custom exception for database operations
 * @author Class Management Team
 * @version 1.0
 */
public class DatabaseException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}