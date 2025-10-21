package com.classmanagement.service;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.ClassEntity;
import java.util.List;

/**
 * Class Service Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface ClassService {
    
    ClassEntity createClass(ClassEntity classEntity) throws DatabaseException, ValidationException;
    
    boolean updateClass(ClassEntity classEntity) throws DatabaseException, ValidationException;
    
    boolean deleteClass(int classId) throws DatabaseException;
    
    ClassEntity getClassById(int classId) throws DatabaseException;
    
    List<ClassEntity> getAllClasses() throws DatabaseException;
    
    List<ClassEntity> getClassesByCourse(int courseId) throws DatabaseException;
    
    List<ClassEntity> getClassesByInstructor(int instructorId) throws DatabaseException;
    
    List<ClassEntity> getAvailableClasses() throws DatabaseException;
}