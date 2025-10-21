package com.classmanagement.dao.impl;

import com.classmanagement.dao.UserDAO;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.User;
import com.classmanagement.util.DatabaseConnection;
import com.classmanagement.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User DAO Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class UserDAOImpl implements UserDAO {
    
    private static final String INSERT_USER = 
        "INSERT INTO users (username, password, email, phone, role, first_name, last_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_USER = 
        "UPDATE users SET username=?, email=?, phone=?, first_name=?, last_name=?, updated_at=NOW() WHERE user_id=?";
    
    private static final String DELETE_USER = 
        "DELETE FROM users WHERE user_id=?";
    
    private static final String SELECT_USER_BY_ID = 
        "SELECT * FROM users WHERE user_id=?";
    
    private static final String SELECT_ALL_USERS = 
        "SELECT * FROM users ORDER BY created_at DESC";
    
    private static final String SELECT_USER_BY_USERNAME = 
        "SELECT * FROM users WHERE username=?";
    
    private static final String SELECT_USER_BY_EMAIL = 
        "SELECT * FROM users WHERE email=?";
    
    private static final String AUTHENTICATE_USER = 
        "SELECT * FROM users WHERE username=? AND password=?";
    
    @Override
    public User create(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getFirstName());
            statement.setString(7, user.getLastName());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating user failed, no rows affected.");
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
            }
            
            LoggerUtil.logInfo("User created successfully: " + user.getUsername());
            return user;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error creating user: " + e.getMessage());
            throw new DatabaseException("Error creating user", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean update(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setInt(6, user.getUserId());
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("User updated successfully: " + user.getUsername());
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error updating user: " + e.getMessage());
            throw new DatabaseException("Error updating user", e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    @Override
    public boolean delete(int id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_USER);
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("User deleted successfully. ID: " + id);
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting user: " + e.getMessage());
            throw new DatabaseException("Error deleting user", e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    @Override
    public User findById(int id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding user by ID: " + e.getMessage());
            throw new DatabaseException("Error finding user by ID", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<User> findAll() throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            
            return users;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding all users: " + e.getMessage());
            throw new DatabaseException("Error finding all users", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public User findByUsername(String username) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
            statement.setString(1, username);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding user by username: " + e.getMessage());
            throw new DatabaseException("Error finding user by username", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public User findByEmail(String email) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding user by email: " + e.getMessage());
            throw new DatabaseException("Error finding user by email", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public User authenticate(String username, String password) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(AUTHENTICATE_USER);
            statement.setString(1, username);
            statement.setString(2, password);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                LoggerUtil.logInfo("User authenticated successfully: " + username);
                return mapResultSetToUser(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error authenticating user: " + e.getMessage());
            throw new DatabaseException("Error authenticating user", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    /**
     * Map ResultSet to User object
     * @param rs ResultSet
     * @return User object
     * @throws SQLException if mapping fails
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return user;
    }
    
    /**
     * Close database resources
     * @param connection Connection to close
     * @param statement Statement to close
     * @param resultSet ResultSet to close
     */
    private void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            LoggerUtil.logError("Error closing resources: " + e.getMessage());
        }
    }
}