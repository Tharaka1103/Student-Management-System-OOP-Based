package com.classmanagement.dao.impl;

import com.classmanagement.dao.CourseDAO;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Course;
import com.classmanagement.util.DatabaseConnection;
import com.classmanagement.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Course DAO Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class CourseDAOImpl implements CourseDAO {
    
    private static final String INSERT_COURSE = 
        "INSERT INTO courses (course_name, course_code, description, duration, category) VALUES (?, ?, ?, ?, ?)";
    
    private static final String UPDATE_COURSE = 
        "UPDATE courses SET course_name=?, course_code=?, description=?, duration=?, category=?, updated_at=NOW() WHERE course_id=?";
    
    private static final String DELETE_COURSE = 
        "DELETE FROM courses WHERE course_id=?";
    
    private static final String SELECT_COURSE_BY_ID = 
        "SELECT * FROM courses WHERE course_id=?";
    
    private static final String SELECT_ALL_COURSES = 
        "SELECT * FROM courses ORDER BY created_at DESC";
    
    private static final String SELECT_COURSE_BY_CODE = 
        "SELECT * FROM courses WHERE course_code=?";
    
    private static final String SEARCH_COURSES_BY_NAME = 
        "SELECT * FROM courses WHERE course_name LIKE ? ORDER BY course_name";
    
    @Override
    public Course create(Course course) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseCode());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getDuration());
            statement.setString(5, course.getCategory());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating course failed, no rows affected.");
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                course.setCourseId(resultSet.getInt(1));
            }
            
            LoggerUtil.logInfo("Course created successfully: " + course.getCourseName());
            return course;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error creating course: " + e.getMessage());
            throw new DatabaseException("Error creating course", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public boolean update(Course course) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_COURSE);
            
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseCode());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getDuration());
            statement.setString(5, course.getCategory());
            statement.setInt(6, course.getCourseId());
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Course updated successfully: " + course.getCourseName());
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error updating course: " + e.getMessage());
            throw new DatabaseException("Error updating course", e);
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
            statement = connection.prepareStatement(DELETE_COURSE);
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            LoggerUtil.logInfo("Course deleted successfully. ID: " + id);
            return affectedRows > 0;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting course: " + e.getMessage());
            throw new DatabaseException("Error deleting course", e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    @Override
    public Course findById(int id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_COURSE_BY_ID);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToCourse(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding course by ID: " + e.getMessage());
            throw new DatabaseException("Error finding course by ID", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Course> findAll() throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Course> courses = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_COURSES);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                courses.add(mapResultSetToCourse(resultSet));
            }
            
            return courses;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding all courses: " + e.getMessage());
            throw new DatabaseException("Error finding all courses", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public Course findByCourseCode(String courseCode) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_COURSE_BY_CODE);
            statement.setString(1, courseCode);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return mapResultSetToCourse(resultSet);
            }
            return null;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error finding course by code: " + e.getMessage());
            throw new DatabaseException("Error finding course by code", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    @Override
    public List<Course> searchByName(String searchTerm) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Course> courses = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.prepareStatement(SEARCH_COURSES_BY_NAME);
            statement.setString(1, "%" + searchTerm + "%");
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                courses.add(mapResultSetToCourse(resultSet));
            }
            
            return courses;
            
        } catch (SQLException e) {
            LoggerUtil.logError("Error searching courses: " + e.getMessage());
            throw new DatabaseException("Error searching courses", e);
        } finally {
            closeResources(connection, statement, resultSet);
        }
    }
    
    /**
     * Map ResultSet to Course object
     */
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setCourseName(rs.getString("course_name"));
        course.setCourseCode(rs.getString("course_code"));
        course.setDescription(rs.getString("description"));
        course.setDuration(rs.getInt("duration"));
        course.setCategory(rs.getString("category"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            course.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            course.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return course;
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