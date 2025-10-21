package com.classmanagement.dao;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Enrollment;
import java.util.List;

/**
 * Enrollment DAO Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface EnrollmentDAO extends GenericDAO<Enrollment> {
    
    /**
     * Find enrollments by user ID
     * @param userId User ID
     * @return List of enrollments
     * @throws DatabaseException if operation fails
     */
    List<Enrollment> findByUserId(int userId) throws DatabaseException;
    
    /**
     * Find enrollments by class ID
     * @param classId Class ID
     * @return List of enrollments
     * @throws DatabaseException if operation fails
     */
    List<Enrollment> findByClassId(int classId) throws DatabaseException;
    
    /**
     * Check if user is already enrolled
     * @param userId User ID
     * @param classId Class ID
     * @return true if enrolled, false otherwise
     * @throws DatabaseException if operation fails
     */
    boolean isEnrolled(int userId, int classId) throws DatabaseException;
    
    /**
     * Get enrollment count for a class
     * @param classId Class ID
     * @return Number of enrollments
     * @throws DatabaseException if operation fails
     */
    int getEnrollmentCount(int classId) throws DatabaseException;
}