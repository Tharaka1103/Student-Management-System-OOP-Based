package com.classmanagement.service.impl;

import com.classmanagement.dao.ClassDAO;
import com.classmanagement.dao.impl.ClassDAOImpl;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.ClassEntity;
import com.classmanagement.service.ClassService;
import com.classmanagement.util.LoggerUtil;
import com.classmanagement.util.ValidationUtil;

import java.util.List;

/**
 * Class Service Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class ClassServiceImpl implements ClassService {
    
    private final ClassDAO classDAO;
    
    public ClassServiceImpl() {
        this.classDAO = new ClassDAOImpl();
    }
    
    public ClassServiceImpl(ClassDAO classDAO) {
        this.classDAO = classDAO;
    }
    
    @Override
    public ClassEntity createClass(ClassEntity classEntity) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to create class: " + classEntity.getClassName());
        
        // Validate class data
        validateClass(classEntity);
        
        return classDAO.create(classEntity);
    }
    
    @Override
    public boolean updateClass(ClassEntity classEntity) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to update class: " + classEntity.getClassId());
        
        // Validate class data
        validateClass(classEntity);
        
        // Check if class exists
        ClassEntity existingClass = classDAO.findById(classEntity.getClassId());
        if (existingClass == null) {
            throw new ValidationException("Class not found");
        }
        
        return classDAO.update(classEntity);
    }
    
    @Override
    public boolean deleteClass(int classId) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to delete class: " + classId);
        return classDAO.delete(classId);
    }
    
    @Override
    public ClassEntity getClassById(int classId) throws DatabaseException {
        return classDAO.findById(classId);
    }
    
    @Override
    public List<ClassEntity> getAllClasses() throws DatabaseException {
        return classDAO.findAll();
    }
    
    @Override
    public List<ClassEntity> getClassesByCourse(int courseId) throws DatabaseException {
        return classDAO.findByCourseId(courseId);
    }
    
    @Override
    public List<ClassEntity> getClassesByInstructor(int instructorId) throws DatabaseException {
        return classDAO.findByInstructorId(instructorId);
    }
    
    @Override
    public List<ClassEntity> getAvailableClasses() throws DatabaseException {
        return classDAO.findAvailableClasses();
    }
    
    /**
     * Validate class data
     */
    private void validateClass(ClassEntity classEntity) throws ValidationException {
        if (ValidationUtil.isNullOrEmpty(classEntity.getClassName())) {
            throw new ValidationException("Class name is required");
        }
        
        if (classEntity.getCourseId() <= 0) {
            throw new ValidationException("Course is required");
        }
        
        if (classEntity.getInstructorId() <= 0) {
            throw new ValidationException("Instructor is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(classEntity.getSchedule())) {
            throw new ValidationException("Schedule is required");
        }
        
        if (ValidationUtil.isNullOrEmpty(classEntity.getRoom())) {
            throw new ValidationException("Room is required");
        }
        
        if (classEntity.getCapacity() <= 0) {
            throw new ValidationException("Capacity must be greater than 0");
        }
    }
}