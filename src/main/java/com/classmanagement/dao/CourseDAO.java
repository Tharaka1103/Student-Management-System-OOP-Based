package com.classmanagement.dao;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Course;
import java.util.List;

/**
 * Course DAO Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface CourseDAO extends GenericDAO<Course> {
    
    /**
     * Find course by code
     * @param courseCode Course code
     * @return Course if found, null otherwise
     * @throws DatabaseException if operation fails
     */
    Course findByCourseCode(String courseCode) throws DatabaseException;
    
    /**
     * Search courses by name
     * @param searchTerm Search term
     * @return List of matching courses
     * @throws DatabaseException if operation fails
     */
    List<Course> searchByName(String searchTerm) throws DatabaseException;
}