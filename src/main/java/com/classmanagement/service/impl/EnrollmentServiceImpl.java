package com.classmanagement.service.impl;

import com.classmanagement.dao.EnrollmentDAO;
import com.classmanagement.dao.impl.EnrollmentDAOImpl;
import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Enrollment;
import com.classmanagement.service.EnrollmentService;
import com.classmanagement.util.LoggerUtil;

import java.util.List;

/**
 * Enrollment Service Implementation
 * @author Class Management Team
 * @version 1.0
 */
public class EnrollmentServiceImpl implements EnrollmentService {
    
    private final EnrollmentDAO enrollmentDAO;
    
    public EnrollmentServiceImpl() {
        this.enrollmentDAO = new EnrollmentDAOImpl();
    }
    
    public EnrollmentServiceImpl(EnrollmentDAO enrollmentDAO) {
        this.enrollmentDAO = enrollmentDAO;
    }
    
    @Override
    public Enrollment enrollStudent(Enrollment enrollment) throws DatabaseException, ValidationException {
        LoggerUtil.logInfo("Attempting to enroll student: " + enrollment.getUserId() + " in class: " + enrollment.getClassId());
        
        // Check if already enrolled
        if (enrollmentDAO.isEnrolled(enrollment.getUserId(), enrollment.getClassId())) {
            throw new ValidationException("Student is already enrolled in this class");
        }
        
        return enrollmentDAO.create(enrollment);
    }
    
    @Override
    public boolean updateEnrollment(Enrollment enrollment) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to update enrollment: " + enrollment.getEnrollmentId());
        return enrollmentDAO.update(enrollment);
    }
    
    @Override
    public boolean cancelEnrollment(int enrollmentId) throws DatabaseException {
        LoggerUtil.logInfo("Attempting to cancel enrollment: " + enrollmentId);
        return enrollmentDAO.delete(enrollmentId);
    }
    
    @Override
    public Enrollment getEnrollmentById(int enrollmentId) throws DatabaseException {
        return enrollmentDAO.findById(enrollmentId);
    }
    
    @Override
    public List<Enrollment> getAllEnrollments() throws DatabaseException {
        return enrollmentDAO.findAll();
    }
    
    @Override
    public List<Enrollment> getEnrollmentsByStudent(int userId) throws DatabaseException {
        return enrollmentDAO.findByUserId(userId);
    }
    
    @Override
    public List<Enrollment> getEnrollmentsByClass(int classId) throws DatabaseException {
        return enrollmentDAO.findByClassId(classId);
    }
    
    @Override
    public boolean isStudentEnrolled(int userId, int classId) throws DatabaseException {
        return enrollmentDAO.isEnrolled(userId, classId);
    }
}