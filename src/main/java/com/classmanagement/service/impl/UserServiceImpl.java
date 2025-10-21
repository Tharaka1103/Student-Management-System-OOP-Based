package com.classmanagement.service.impl;

import com.classmanagement.dao.UserDAO;
import com.classmanagement.dao.impl.UserDAOImpl;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.User;
import com.classmanagement.service.UserService;
import com.classmanagement.util.LoggerUtil;
import com.classmanagement.util.PasswordUtil;
import com.classmanagement.util.ValidationUtil;

import java.util.List;

/**
 * User Service Implementation
 * Demonstrates Polymorphism - implements UserService interface
 * @author Class Management Team
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    
    private final UserDAO userDAO;
    
    // Constructor with dependency injection
    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }
    
    // Constructor for testing with DAO injection
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    public User registerUser(User user) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to register user: " + user.getUsername());
        
        // Validate user data
        validateUser(user);
        
        // Check if username already exists
        User existingUser = userDAO.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new ValidationException("Username already exists");
        }
        
        // Check if email already exists
        existingUser = userDAO.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new ValidationException("Email already exists");
        }
        
        // Hash password before saving
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        
        // Create user
        return userDAO.create(user);
    }
    
    @Override
    public User authenticateUser(String username, String password) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to authenticate user: " + username);
        
        if (ValidationUtil.isNullOrEmpty(username) || ValidationUtil.isNullOrEmpty(password)) {
            return null;
        }
        
        // Hash the password
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        // Authenticate
        return userDAO.authenticate(username, hashedPassword);
    }
    
    @Override
    public User getUserById(int userId) throws DatabaseException {
        return userDAO.findById(userId);
    }
    
    @Override
    public List<User> getAllUsers() throws DatabaseException {
        return userDAO.findAll();
    }
    
    @Override
    public boolean updateUser(User user) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to update user: " + user.getUserId());
        
        // Validate user data
        validateUserUpdate(user);
        
        // Check if user exists
        User existingUser = userDAO.findById(user.getUserId());
        if (existingUser == null) {
            throw new ValidationException("User not found");
        }
        
        // Check if email is being changed to an existing email
        User userWithEmail = userDAO.findByEmail(user.getEmail());
        if (userWithEmail != null && userWithEmail.getUserId() != user.getUserId()) {
            throw new ValidationException("Email already exists");
        }
        
        return userDAO.update(user);
    }
    
    @Override
    public boolean deleteUser(int userId) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to delete user: " + userId);
        return userDAO.delete(userId);
    }
    
    @Override
    public User getUserByUsername(String username) throws DatabaseException {
        return userDAO.findByUsername(username);
    }
    
    @Override
    public User getUserByEmail(String email) throws DatabaseException {
        return userDAO.findByEmail(email);
    }
    
    /**
     * Validate user data for registration
     */
    private void validateUser(User user) throws ValidationException {
        if (ValidationUtil.isNullOrEmpty(user.getUsername())) {
            throw new ValidationException("Username is required");
        }
        
        if (!ValidationUtil.isValidUsername(user.getUsername())) {
            throw new ValidationException("Username must be 3-20 characters and contain only letters, numbers, and underscores");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getPassword())) {
            throw new ValidationException("Password is required");
        }
        
        if (!ValidationUtil.isValidPassword(user.getPassword())) {
            throw new ValidationException("Password must be at least 6 characters");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new ValidationException("Invalid email format");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getFirstName())) {
            throw new ValidationException("First name is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getLastName())) {
            throw new ValidationException("Last name is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getRole())) {
            throw new ValidationException("Role is required");
        }
    }
    
    /**
     * Validate user data for update
     */
    private void validateUserUpdate(User user) throws ValidationException {
        if (ValidationUtil.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new ValidationException("Invalid email format");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getFirstName())) {
            throw new ValidationException("First name is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(user.getLastName())) {
            throw new ValidationException("Last name is required");
        }
    }
}