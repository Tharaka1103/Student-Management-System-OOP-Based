package com.classmanagement.exception;

/**
 * Custom exception for authentication failures
 * @author Class Management Team
 * @version 1.0
 */
public class AuthenticationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public AuthenticationException(String message) {
        super(message);
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}