package com.classmanagement.dao;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Instructor;
import java.util.List;

/**
 * Instructor DAO Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface InstructorDAO extends GenericDAO<Instructor> {
    
    /**
     * Find instructor by email
     * @param email Email address
     * @return Instructor if found, null otherwise
     * @throws DatabaseException if operation fails
     */
    Instructor findByEmail(String email) throws DatabaseException;
    
    /**
     * Search instructors by name
     * @param searchTerm Search term
     * @return List of matching instructors
     * @throws DatabaseException if operation fails
     */
    List<Instructor> searchByName(String searchTerm) throws DatabaseException;
}