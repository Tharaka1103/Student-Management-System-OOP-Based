package com.classmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Enrollment Model Class
 * @author Class Management Team
 * @version 1.0
 */
public class Enrollment implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int enrollmentId;
    private int userId;
    private int classId;
    private int courseId;
    private LocalDateTime enrollmentDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // For joined data
    private String studentName;
    private String courseName;
    private String className;
    
    // Default constructor
    public Enrollment() {
    }
    
    // Parameterized constructor
    public Enrollment(int userId, int classId, int courseId, String status) {
        this.userId = userId;
        this.classId = classId;
        this.courseId = courseId;
        this.status = status;
    }
    
    // Getters and Setters
    public int getEnrollmentId() {
        return enrollmentId;
    }
    
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getClassId() {
        return classId;
    }
    
    public void setClassId(int classId) {
        this.classId = classId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", userId=" + userId +
                ", classId=" + classId +
                ", courseId=" + courseId +
                ", status='" + status + '\'' +
                '}';
    }
}