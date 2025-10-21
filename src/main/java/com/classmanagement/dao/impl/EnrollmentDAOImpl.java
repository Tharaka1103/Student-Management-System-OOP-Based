package com.classmanagement.dao.impl;

import com.classmanagement.dao.EnrollmentDAO;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Enrollment;
import com.classmanagement.util.DatabaseConnection;
import com.classmanagement.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Enrollment DAO Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class EnrollmentDAOImpl implements EnrollmentDAO {
    
    private static final String INSERT_ENROLLMENT = 
        "INSERT INTO enrollments (user_id, class_id, course_id, status, enrollment_date) VALUES (?, ?, ?, ?, NOW())";
    
    private static final String UPDATE_ENROLLMENT = 
        "UPDATE enrollments SET status=?, updated_at=NOW() WHERE enrollment_id=?";
    
    private static final String DELETE_ENROLLMENT = 
        "DELETE FROM enrollments WHERE enrollment_id=?";
    
    private static final String SELECT_ENROLLMENT_BY_ID = 
        "SELECT e.*, CONCAT(u.first_name, ' ', u.last_name) as student_name, c.course_name, cl.class_name " +
        "FROM enrollments e " +
        "LEFT JOIN users u ON e.user_id = u.user_id " +
        "LEFT JOIN courses c ON e.course_id = c.course_id " +
        "LEFT JOIN classes cl ON e.class_id = cl.class_id " +
        "WHERE e.enrollment_id=?";
    
    private static final String SELECT_ALL_ENROLLMENTS = 
        "SELECT e.*, CONCAT(u.first_name, ' ', u.last_name) as student_name, c.course_name, cl.class_name " +
        "FROM enrollments e " +
        "LEFT JOIN users u ON e.user_id = u.user_id " +
        "LEFT JOIN courses c ON e.course_id = c.course_id " +
        "LEFT JOIN classes cl ON e.class_id = cl.class_id " +
        "ORDER BY e.enrollment_date DESC";
    
    private static final String SELECT_ENROLLMENTS_BY_USER = 
        "SELECT e.*, CONCAT(u.first_name, ' ', u.last_name) as student_name, c.course_name, cl.class_name " +
        "FROM enrollments e " +
        "LEFT JOIN users u ON e.user_id = u.user_id " +
        "LEFT JOIN courses c ON e.course_id = c.course_id " +
        "LEFT JOIN classes cl ON e.class_id = cl.class_id " +
        "WHERE e.user_id=?";
    
    private static final String SELECT_ENROLLMENTS_BY_CLASS = 
        "SELECT e.*, CONCAT(u.first_name, ' ', u.last_name) as student_name, c.course_name, cl.class_name " +
        "FROM enrollments e " +
        "LEFT JOIN users u ON e.user_id = u.user_id " +
        "LEFT JOIN courses c ON e.course_id = c.course_id " +
        "LEFT JOIN classes cl ON e.class_id = cl.class_id " +
        "WHERE e.class_id=?";
    
    private static final String CHECK_ENROLLMENT = 
        "SELECT COUNT(*) FROM enrollments WHERE user_id=? AND class_id=? AND status='ACTIVE'";
    
    private static final String COUNT_ENROLLMENTS = 
        "SELECT COUNT(*) FROM enrollments WHERE class_id=? AND status='ACTIVE'";
    
    @Override
    public Enrollment create(Enrollment enrollment) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_ENROLLMENT, Statement.RETURN_GENERATED_KEYS);
            
            statement.setInt(1, enrollment.getUserId());
            statement.setInt(2, enrollment.getClassId());
            statement.setInt(3, enrollment.getCourseId());
            statement.setString(4, enrollment.getStatus());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating enrollment failed, no rows affected.");
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                enrollment.setEnrollmentId(resultSet.getInt(1));
            }
            
            LoggerUtil.logInfo("Enrollment created successfully. ID: " + enrollment.getEnrollmentId());
            return enrollment;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error creating enrollment: " + e.getMessage());
            throw new DatabaseException("Error creating enrollment", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean update(Enrollment enrollment) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_ENROLLMENT);
            
            statement.setString(1, enrollment.getStatus());
            statement.setInt(2, enrollment.getEnrollmentId());
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Enrollment updated successfully. ID: " + enrollment.getEnrollmentId());
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error updating enrollment: " + e.getMessage());
            throw new DatabaseException("Error updating enrollment", e);
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
            statement = connection.prepareStatement(DELETE_ENROLLMENT);
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Enrollment deleted successfully. ID: " + id);
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting enrollment: " + e.getMessage());
            throw new DatabaseException("Error deleting enrollment", e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    @Override
    public Enrollment findById(int id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ENROLLMENT_BY_ID);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToEnrollment(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding enrollment by ID: " + e.getMessage());
            throw new DatabaseException("Error finding enrollment by ID", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Enrollment> findAll() throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Enrollment> enrollments = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_ENROLLMENTS);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                enrollments.add(mapResultSetToEnrollment(resultSet));
            }
            
            return enrollments;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding all enrollments: " + e.getMessage());
            throw new DatabaseException("Error finding all enrollments", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Enrollment> findByUserId(int userId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Enrollment> enrollments = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ENROLLMENTS_BY_USER);
            statement.setInt(1, userId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                enrollments.add(mapResultSetToEnrollment(resultSet));
            }
            
            return enrollments;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding enrollments by user: " + e.getMessage());
            throw new DatabaseException("Error finding enrollments by user", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Enrollment> findByClassId(int classId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Enrollment> enrollments = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ENROLLMENTS_BY_CLASS);
            statement.setInt(1, classId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                enrollments.add(mapResultSetToEnrollment(resultSet));
            }
            
            return enrollments;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding enrollments by class: " + e.getMessage());
            throw new DatabaseException("Error finding enrollments by class", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean isEnrolled(int userId, int classId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(CHECK_ENROLLMENT);
            statement.setInt(1, userId);
            statement.setInt(2, classId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error checking enrollment: " + e.getMessage());
            throw new DatabaseException("Error checking enrollment", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public int getEnrollmentCount(int classId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(COUNT_ENROLLMENTS);
            statement.setInt(1, classId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error counting enrollments: " + e.getMessage());
            throw new DatabaseException("Error counting enrollments", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    /**
     * Map ResultSet to Enrollment object
     */
    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
        enrollment.setUserId(rs.getInt("user_id"));
        enrollment.setClassId(rs.getInt("class_id"));
        enrollment.setCourseId(rs.getInt("course_id"));
        enrollment.setStatus(rs.getString("status"));
        
        Timestamp enrollmentDate = rs.getTimestamp("enrollment_date");
        if (enrollmentDate != null) {
            enrollment.setEnrollmentDate(enrollmentDate.toLocalDateTime());
        }
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            enrollment.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            enrollment.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        // Set joined data
        enrollment.setStudentName(rs.getString("student_name"));
        enrollment.setCourseName(rs.getString("course_name"));
        enrollment.setClassName(rs.getString("class_name"));
        
        return enrollment;
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