package com.classmanagement.service;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Instructor;
import java.util.List;

/**
 * Instructor Service Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface InstructorService {
    
    Instructor createInstructor(Instructor instructor) throws DatabaseException, ValidationException;
    
    boolean updateInstructor(Instructor instructor) throws DatabaseException, ValidationException;
    
    boolean deleteInstructor(int instructorId) throws DatabaseException;
    
    Instructor getInstructorById(int instructorId) throws DatabaseException;
    
    List<Instructor> getAllInstructors() throws DatabaseException;
    
    Instructor getInstructorByEmail(String email) throws DatabaseException;
    
    List<Instructor> searchInstructorsByName(String searchTerm) throws DatabaseException;
}