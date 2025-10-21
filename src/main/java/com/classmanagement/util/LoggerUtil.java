package com.classmanagement.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logging utility class
 * @author Class Management Team
 * @version 1.0
 */
public final class LoggerUtil {
    
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Private constructor to prevent instantiation
    private LoggerUtil() {
        throw new AssertionError("Cannot instantiate LoggerUtil class");
    }
    
    /**
     * Log information message
     * @param message Message to log
     */
    public static void logInfo(String message) {
        System.out.println("[INFO] " + getCurrentTimestamp() + " - " + message);
    }
    
    /**
     * Log error message
     * @param message Message to log
     */
    public static void logError(String message) {
        System.err.println("[ERROR] " + getCurrentTimestamp() + " - " + message);
    }
    
    /**
     * Log warning message
     * @param message Message to log
     */
    public static void logWarning(String message) {
        System.out.println("[WARNING] " + getCurrentTimestamp() + " - " + message);
    }
    
    /**
     * Log debug message
     * @param message Message to log
     */
    public static void logDebug(String message) {
        System.out.println("[DEBUG] " + getCurrentTimestamp() + " - " + message);
    }
    
    /**
     * Get current timestamp
     * @return Formatted timestamp
     */
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}