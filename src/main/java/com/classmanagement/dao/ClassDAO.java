package com.classmanagement.dao;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.ClassEntity;
import java.util.List;

/**
 * Class DAO Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface ClassDAO extends GenericDAO<ClassEntity> {
    
    /**
     * Find classes by course ID
     * @param courseId Course ID
     * @return List of classes
     * @throws DatabaseException if operation fails
     */
    List<ClassEntity> findByCourseId(int courseId) throws DatabaseException;
    
    /**
     * Find classes by instructor ID
     * @param instructorId Instructor ID
     * @return List of classes
     * @throws DatabaseException if operation fails
     */
    List<ClassEntity> findByInstructorId(int instructorId) throws DatabaseException;
    
    /**
     * Get available classes (with capacity)
     * @return List of available classes
     * @throws DatabaseException if operation fails
     */
    List<ClassEntity> findAvailableClasses() throws DatabaseException;
}