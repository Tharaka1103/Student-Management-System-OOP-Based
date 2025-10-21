package com.classmanagement.dao.impl;

import com.classmanagement.dao.ClassDAO;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.ClassEntity;
import com.classmanagement.util.DatabaseConnection;
import com.classmanagement.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class DAO Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class ClassDAOImpl implements ClassDAO {
    
    private static final String INSERT_CLASS = 
        "INSERT INTO classes (class_name, course_id, instructor_id, schedule, room, capacity, status, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_CLASS = 
        "UPDATE classes SET class_name=?, course_id=?, instructor_id=?, schedule=?, room=?, capacity=?, status=?, start_date=?, end_date=?, updated_at=NOW() WHERE class_id=?";
    
    private static final String DELETE_CLASS = 
        "DELETE FROM classes WHERE class_id=?";
    
    private static final String SELECT_CLASS_BY_ID = 
        "SELECT c.*, co.course_name, CONCAT(i.first_name, ' ', i.last_name) as instructor_name " +
        "FROM classes c " +
        "LEFT JOIN courses co ON c.course_id = co.course_id " +
        "LEFT JOIN instructors i ON c.instructor_id = i.instructor_id " +
        "WHERE c.class_id=?";
    
    private static final String SELECT_ALL_CLASSES = 
        "SELECT c.*, co.course_name, CONCAT(i.first_name, ' ', i.last_name) as instructor_name " +
        "FROM classes c " +
        "LEFT JOIN courses co ON c.course_id = co.course_id " +
        "LEFT JOIN instructors i ON c.instructor_id = i.instructor_id " +
        "ORDER BY c.created_at DESC";
    
    private static final String SELECT_CLASSES_BY_COURSE = 
        "SELECT c.*, co.course_name, CONCAT(i.first_name, ' ', i.last_name) as instructor_name " +
        "FROM classes c " +
        "LEFT JOIN courses co ON c.course_id = co.course_id " +
        "LEFT JOIN instructors i ON c.instructor_id = i.instructor_id " +
        "WHERE c.course_id=?";
    
    private static final String SELECT_CLASSES_BY_INSTRUCTOR = 
        "SELECT c.*, co.course_name, CONCAT(i.first_name, ' ', i.last_name) as instructor_name " +
        "FROM classes c " +
        "LEFT JOIN courses co ON c.course_id = co.course_id " +
        "LEFT JOIN instructors i ON c.instructor_id = i.instructor_id " +
        "WHERE c.instructor_id=?";
    
    private static final String SELECT_AVAILABLE_CLASSES = 
        "SELECT c.*, co.course_name, CONCAT(i.first_name, ' ', i.last_name) as instructor_name " +
        "FROM classes c " +
        "LEFT JOIN courses co ON c.course_id = co.course_id " +
        "LEFT JOIN instructors i ON c.instructor_id = i.instructor_id " +
        "WHERE c.status='ACTIVE'";
    
    @Override
    public ClassEntity create(ClassEntity classEntity) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_CLASS, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, classEntity.getClassName());
            statement.setInt(2, classEntity.getCourseId());
            statement.setInt(3, classEntity.getInstructorId());
            statement.setString(4, classEntity.getSchedule());
            statement.setString(5, classEntity.getRoom());
            statement.setInt(6, classEntity.getCapacity());
            statement.setString(7, classEntity.getStatus());
            statement.setTimestamp(8, classEntity.getStartDate() != null ? Timestamp.valueOf(classEntity.getStartDate()) : null);
            statement.setTimestamp(9, classEntity.getEndDate() != null ? Timestamp.valueOf(classEntity.getEndDate()) : null);
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating class failed, no rows affected.");
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                classEntity.setClassId(resultSet.getInt(1));
            }
            
            LoggerUtil.logInfo("Class created successfully: " + classEntity.getClassName());
            return classEntity;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error creating class: " + e.getMessage());
            throw new DatabaseException("Error creating class", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean update(ClassEntity classEntity) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_CLASS);
            
            statement.setString(1, classEntity.getClassName());
            statement.setInt(2, classEntity.getCourseId());
            statement.setInt(3, classEntity.getInstructorId());
            statement.setString(4, classEntity.getSchedule());
            statement.setString(5, classEntity.getRoom());
            statement.setInt(6, classEntity.getCapacity());
            statement.setString(7, classEntity.getStatus());
            statement.setTimestamp(8, classEntity.getStartDate() != null ? Timestamp.valueOf(classEntity.getStartDate()) : null);
            statement.setTimestamp(9, classEntity.getEndDate() != null ? Timestamp.valueOf(classEntity.getEndDate()) : null);
            statement.setInt(10, classEntity.getClassId());
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Class updated successfully: " + classEntity.getClassName());
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error updating class: " + e.getMessage());
            throw new DatabaseException("Error updating class", e);
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
            statement = connection.prepareStatement(DELETE_CLASS);
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Class deleted successfully. ID: " + id);
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting class: " + e.getMessage());
            throw new DatabaseException("Error deleting class", e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    @Override
    public ClassEntity findById(int id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_CLASS_BY_ID);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToClass(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding class by ID: " + e.getMessage());
            throw new DatabaseException("Error finding class by ID", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<ClassEntity> findAll() throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ClassEntity> classes = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_CLASSES);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                classes.add(mapResultSetToClass(resultSet));
            }
            
            return classes;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding all classes: " + e.getMessage());
            throw new DatabaseException("Error finding all classes", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<ClassEntity> findByCourseId(int courseId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ClassEntity> classes = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_CLASSES_BY_COURSE);
            statement.setInt(1, courseId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                classes.add(mapResultSetToClass(resultSet));
            }
            
            return classes;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding classes by course: " + e.getMessage());
            throw new DatabaseException("Error finding classes by course", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<ClassEntity> findByInstructorId(int instructorId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ClassEntity> classes = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_CLASSES_BY_INSTRUCTOR);
            statement.setInt(1, instructorId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                classes.add(mapResultSetToClass(resultSet));
            }
            
            return classes;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding classes by instructor: " + e.getMessage());
            throw new DatabaseException("Error finding classes by instructor", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<ClassEntity> findAvailableClasses() throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ClassEntity> classes = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_AVAILABLE_CLASSES);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                classes.add(mapResultSetToClass(resultSet));
            }
            
            return classes;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding available classes: " + e.getMessage());
            throw new DatabaseException("Error finding available classes", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    /**
     * Map ResultSet to ClassEntity object
     */
    private ClassEntity mapResultSetToClass(ResultSet rs) throws SQLException {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassId(rs.getInt("class_id"));
        classEntity.setClassName(rs.getString("class_name"));
        classEntity.setCourseId(rs.getInt("course_id"));
        classEntity.setInstructorId(rs.getInt("instructor_id"));
        classEntity.setSchedule(rs.getString("schedule"));
        classEntity.setRoom(rs.getString("room"));
        classEntity.setCapacity(rs.getInt("capacity"));
        classEntity.setStatus(rs.getString("status"));
        
        Timestamp startDate = rs.getTimestamp("start_date");
        if (startDate != null) {
            classEntity.setStartDate(startDate.toLocalDateTime());
        }
        
        Timestamp endDate = rs.getTimestamp("end_date");
        if (endDate != null) {
            classEntity.setEndDate(endDate.toLocalDateTime());
        }
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            classEntity.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            classEntity.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        // Set joined data
        classEntity.setCourseName(rs.getString("course_name"));
        classEntity.setInstructorName(rs.getString("instructor_name"));
        
        return classEntity;
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