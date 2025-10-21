package com.classmanagement.service.impl;

import com.classmanagement.dao.InstructorDAO;
import com.classmanagement.dao.impl.InstructorDAOImpl;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Instructor;
import com.classmanagement.service.InstructorService;
import com.classmanagement.util.LoggerUtil;
import com.classmanagement.util.ValidationUtil;

import java.util.List;

/**
 * Instructor Service Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class InstructorServiceImpl implements InstructorService {
    
    private final InstructorDAO instructorDAO;
    
    public InstructorServiceImpl() {
        this.instructorDAO = new InstructorDAOImpl();
    }
    
    public InstructorServiceImpl(InstructorDAO instructorDAO) {
        this.instructorDAO = instructorDAO;
    }
    
    @Override
    public Instructor createInstructor(Instructor instructor) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to create instructor: " + instructor.getFullName());
        
        // Validate instructor data
        validateInstructor(instructor);
        
        // Check if email already exists
        Instructor existingInstructor = instructorDAO.findByEmail(instructor.getEmail());
        if (existingInstructor != null) {
            throw new ValidationException("Email already exists");
        }
        
        return instructorDAO.create(instructor);
    }
    
    @Override
    public boolean updateInstructor(Instructor instructor) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to update instructor: " + instructor.getInstructorId());
        
        // Validate instructor data
        validateInstructor(instructor);
        
        // Check if instructor exists
        Instructor existingInstructor = instructorDAO.findById(instructor.getInstructorId());
        if (existingInstructor == null) {
            throw new ValidationException("Instructor not found");
        }
        
        // Check if email is being changed to an existing email
        Instructor instructorWithEmail = instructorDAO.findByEmail(instructor.getEmail());
        if (instructorWithEmail != null && instructorWithEmail.getInstructorId() != instructor.getInstructorId()) {
            throw new ValidationException("Email already exists");
        }
        
        return instructorDAO.update(instructor);
    }
    
    @Override
    public boolean deleteInstructor(int instructorId) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to delete instructor: " + instructorId);
        return instructorDAO.delete(instructorId);
    }
    
    @Override
    public Instructor getInstructorById(int instructorId) throws DatabaseException {
        return instructorDAO.findById(instructorId);
    }
    
    @Override
    public List<Instructor> getAllInstructors() throws DatabaseException {
        return instructorDAO.findAll();
    }
    
    @Override
    public Instructor getInstructorByEmail(String email) throws DatabaseException {
        return instructorDAO.findByEmail(email);
    }
    
    @Override
    public List<Instructor> searchInstructorsByName(String searchTerm) throws DatabaseException {
        return instructorDAO.searchByName(searchTerm);
    }
    
    /**
     * Validate instructor data
     */
    private void validateInstructor(Instructor instructor) throws ValidationException {
        if (ValidationUtil.isNullOrEmpty(instructor.getFirstName())) {
            throw new ValidationException("First name is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(instructor.getLastName())) {
            throw new ValidationException("Last name is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(instructor.getEmail())) {
            throw new ValidationException("Email is required");
        }
        
        if (!ValidationUtil.isValidEmail(instructor.getEmail())) {
            throw new ValidationException("Invalid email format");
        }
        
        if (ValidationUtil.isNullOrEmpty(instructor.getExpertise())) {
            throw new ValidationException("Expertise is required");
        }
    }
}