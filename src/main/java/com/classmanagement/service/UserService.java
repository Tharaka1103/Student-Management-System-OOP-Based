package com.classmanagement.service;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.User;
import java.util.List;

/**
 * User Service Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface UserService {
    
    User registerUser(User user) throws DatabaseException, ValidationException;
    
    User authenticateUser(String username, String password) throws DatabaseException;
    
    User getUserById(int userId) throws DatabaseException;
    
    List<User> getAllUsers() throws DatabaseException;
    
    boolean updateUser(User user) throws DatabaseException, ValidationException;
    
    boolean deleteUser(int userId) throws DatabaseException;
    
    User getUserByUsername(String username) throws DatabaseException;
    
    User getUserByEmail(String email) throws DatabaseException;
}