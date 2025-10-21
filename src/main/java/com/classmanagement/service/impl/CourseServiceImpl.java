package com.classmanagement.service.impl;

import com.classmanagement.dao.CourseDAO;
import com.classmanagement.dao.impl.CourseDAOImpl;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Course;
import com.classmanagement.service.CourseService;
import com.classmanagement.util.LoggerUtil;
import com.classmanagement.util.ValidationUtil;

import java.util.List;

/**
 * Course Service Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class CourseServiceImpl implements CourseService {
    
    private final CourseDAO courseDAO;
    
    public CourseServiceImpl() {
        this.courseDAO = new CourseDAOImpl();
    }
    
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
    
    @Override
    public Course createCourse(Course course) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to create course: " + course.getCourseName());
        
        // Validate course data
        validateCourse(course);
        
        // Check if course code already exists
        Course existingCourse = courseDAO.findByCourseCode(course.getCourseCode());
        if (existingCourse != null) {
            throw new ValidationException("Course code already exists");
        }
        
        return courseDAO.create(course);
    }
    
    @Override
    public boolean updateCourse(Course course) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to update course: " + course.getCourseId());
        
        // Validate course data
        validateCourse(course);
        
        // Check if course exists
        Course existingCourse = courseDAO.findById(course.getCourseId());
        if (existingCourse == null) {
            throw new ValidationException("Course not found");
        }
        
        // Check if course code is being changed to an existing code
        Course courseWithCode = courseDAO.findByCourseCode(course.getCourseCode());
        if (courseWithCode != null && courseWithCode.getCourseId() != course.getCourseId()) {
            throw new ValidationException("Course code already exists");
        }
        
        return courseDAO.update(course);
    }
    
    @Override
    public boolean deleteCourse(int courseId) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to delete course: " + courseId);
        return courseDAO.delete(courseId);
    }
    
    @Override
    public Course getCourseById(int courseId) throws DatabaseException {
        return courseDAO.findById(courseId);
    }
    
    @Override
    public List<Course> getAllCourses() throws DatabaseException {
        return courseDAO.findAll();
    }
    
    @Override
    public Course getCourseByCourseCode(String courseCode) throws DatabaseException {
        return courseDAO.findByCourseCode(courseCode);
    }
    
    @Override
    public List<Course> searchCoursesByName(String searchTerm) throws DatabaseException {
        return courseDAO.searchByName(searchTerm);
    }
    
    /**
     * Validate course data
     */
    private void validateCourse(Course course) throws ValidationException {
        if (ValidationUtil.isNullOrEmpty(course.getCourseName())) {
            throw new ValidationException("Course name is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(course.getCourseCode())) {
            throw new ValidationException("Course code is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(course.getDescription())) {
            throw new ValidationException("Description is required");
        }
        
        if (course.getDuration() <= 0) {
            throw new ValidationException("Duration must be greater than 0");
        }
    }
}