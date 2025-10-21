package com.classmanagement.service;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Course;
import java.util.List;

/**
 * Course Service Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface CourseService {
    
    Course createCourse(Course course) throws DatabaseException, ValidationException;
    
    boolean updateCourse(Course course) throws DatabaseException, ValidationException;
    
    boolean deleteCourse(int courseId) throws DatabaseException;
    
    Course getCourseById(int courseId) throws DatabaseException;
    
    List<Course> getAllCourses() throws DatabaseException;
    
    Course getCourseByCourseCode(String courseCode) throws DatabaseException;
    
    List<Course> searchCoursesByName(String searchTerm) throws DatabaseException;
}