package com.classmanagement.dao.impl;

import com.classmanagement.dao.InstructorDAO;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Instructor;
import com.classmanagement.util.DatabaseConnection;
import com.classmanagement.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Instructor DAO Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class InstructorDAOImpl implements InstructorDAO {
    
    private static final String INSERT_INSTRUCTOR = 
        "INSERT INTO instructors (first_name, last_name, email, phone, expertise, qualification) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_INSTRUCTOR = 
        "UPDATE instructors SET first_name=?, last_name=?, email=?, phone=?, expertise=?, qualification=?, updated_at=NOW() WHERE instructor_id=?";
    
    private static final String DELETE_INSTRUCTOR = 
        "DELETE FROM instructors WHERE instructor_id=?";
    
    private static final String SELECT_INSTRUCTOR_BY_ID = 
        "SELECT * FROM instructors WHERE instructor_id=?";
    
    private static final String SELECT_ALL_INSTRUCTORS = 
        "SELECT * FROM instructors ORDER BY created_at DESC";
    
    private static final String SELECT_INSTRUCTOR_BY_EMAIL = 
        "SELECT * FROM instructors WHERE email=?";
    
    private static final String SEARCH_INSTRUCTORS_BY_NAME = 
        "SELECT * FROM instructors WHERE first_name LIKE ? OR last_name LIKE ? ORDER BY first_name";
    
    @Override
    public Instructor create(Instructor instructor) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_INSTRUCTOR, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, instructor.getFirstName());
            statement.setString(2, instructor.getLastName());
            statement.setString(3, instructor.getEmail());
            statement.setString(4, instructor.getPhone());
            statement.setString(5, instructor.getExpertise());
            statement.setString(6, instructor.getQualification());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating instructor failed, no rows affected.");
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                instructor.setInstructorId(resultSet.getInt(1));
            }
            
            LoggerUtil.logInfo("Instructor created successfully: " + instructor.getFullName());
            return instructor;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error creating instructor: " + e.getMessage());
            throw new DatabaseException("Error creating instructor", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean update(Instructor instructor) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_INSTRUCTOR);
            
            statement.setString(1, instructor.getFirstName());
            statement.setString(2, instructor.getLastName());
            statement.setString(3, instructor.getEmail());
            statement.setString(4, instructor.getPhone());
            statement.setString(5, instructor.getExpertise());
            statement.setString(6, instructor.getQualification());
            statement.setInt(7, instructor.getInstructorId());
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Instructor updated successfully: " + instructor.getFullName());
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error updating instructor: " + e.getMessage());
            throw new DatabaseException("Error updating instructor", e);
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
            statement = connection.prepareStatement(DELETE_INSTRUCTOR);
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Instructor deleted successfully. ID: " + id);
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting instructor: " + e.getMessage());
            throw new DatabaseException("Error deleting instructor", e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    @Override
    public Instructor findById(int id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_INSTRUCTOR_BY_ID);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToInstructor(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding instructor by ID: " + e.getMessage());
            throw new DatabaseException("Error finding instructor by ID", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Instructor> findAll() throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Instructor> instructors = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_INSTRUCTORS);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                instructors.add(mapResultSetToInstructor(resultSet));
            }
            
            return instructors;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding all instructors: " + e.getMessage());
            throw new DatabaseException("Error finding all instructors", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public Instructor findByEmail(String email) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_INSTRUCTOR_BY_EMAIL);
            statement.setString(1, email);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToInstructor(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding instructor by email: " + e.getMessage());
            throw new DatabaseException("Error finding instructor by email", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Instructor> searchByName(String searchTerm) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Instructor> instructors = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SEARCH_INSTRUCTORS_BY_NAME);
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                instructors.add(mapResultSetToInstructor(resultSet));
            }
            
            return instructors;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error searching instructors: " + e.getMessage());
            throw new DatabaseException("Error searching instructors", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    /**
     * Map ResultSet to Instructor object
     */
    private Instructor mapResultSetToInstructor(ResultSet rs) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(rs.getInt("instructor_id"));
        instructor.setFirstName(rs.getString("first_name"));
        instructor.setLastName(rs.getString("last_name"));
        instructor.setEmail(rs.getString("email"));
        instructor.setPhone(rs.getString("phone"));
        instructor.setExpertise(rs.getString("expertise"));
        instructor.setQualification(rs.getString("qualification"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            instructor.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            instructor.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return instructor;
    }
    
    /**
     * Close database resources
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