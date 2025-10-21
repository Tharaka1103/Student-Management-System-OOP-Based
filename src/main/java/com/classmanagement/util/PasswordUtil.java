package com.classmanagement.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password encryption utility
 * @author Class Management Team
 * @version 1.0
 */
public final class PasswordUtil {
    
    private static final String ALGORITHM = "SHA-256";
    
    // Private constructor
    private PasswordUtil() {
        throw new AssertionError("Cannot instantiate PasswordUtil class");
    }
    
    /**
     * Hash password using SHA-256
     * @param password Plain text password
     * @return Hashed password
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LoggerUtil.logError("Error hashing password: " + e.getMessage());
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    
    /**
     * Verify password against hash
     * @param password Plain text password
     * @param hashedPassword Hashed password
     * @return true if passwords match, false otherwise
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashOfInput = hashPassword(password);
        return hashOfInput.equals(hashedPassword);
    }
}