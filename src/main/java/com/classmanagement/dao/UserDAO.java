package com.classmanagement.dao;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.User;

/**
 * User DAO Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface UserDAO extends GenericDAO<User> {
    
    /**
     * Find user by username
     * @param username Username
     * @return User if found, null otherwise
     * @throws DatabaseException if operation fails
     */
    User findByUsername(String username) throws DatabaseException;
    
    /**
     * Find user by email
     * @param email Email address
     * @return User if found, null otherwise
     * @throws DatabaseException if operation fails
     */
    User findByEmail(String email) throws DatabaseException;
    
    /**
     * Authenticate user
     * @param username Username
     * @param password Password (hashed)
     * @return User if authenticated, null otherwise
     * @throws DatabaseException if operation fails
     */
    User authenticate(String username, String password) throws DatabaseException;
}