package com.classmanagement.service;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Enrollment;
import java.util.List;

/**
 * Enrollment Service Interface
 * @author Class Management Team
 * @version 1.0
 */
public interface EnrollmentService {
    
    Enrollment enrollStudent(Enrollment enrollment) throws DatabaseException, ValidationException;
    
    boolean updateEnrollment(Enrollment enrollment) throws DatabaseException;
    
    boolean cancelEnrollment(int enrollmentId) throws DatabaseException;
    
    Enrollment getEnrollmentById(int enrollmentId) throws DatabaseException;
    
    List<Enrollment> getAllEnrollments() throws DatabaseException;
    
    List<Enrollment> getEnrollmentsByStudent(int userId) throws DatabaseException;
    
    List<Enrollment> getEnrollmentsByClass(int classId) throws DatabaseException;
    
    boolean isStudentEnrolled(int userId, int classId) throws DatabaseException;
}