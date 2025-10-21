package com.classmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class Entity Model
 * Represents a class session
 * @author Class Management Team
 * @version 1.0
 */
public class ClassEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int classId;
    private int courseId;
    private int instructorId;
    private String className;
    private String schedule;
    private String room;
    private int capacity;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // For joined data
    private String courseName;
    private String instructorName;
    
    // Default constructor
    public ClassEntity() {
    }
    
    // Parameterized constructor
    public ClassEntity(String className, int courseId, int instructorId, String schedule, String room, int capacity) {
        this.className = className;
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.schedule = schedule;
        this.room = room;
        this.capacity = capacity;
    }
    
    // Getters and Setters
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
    
    public int getInstructorId() {
        return instructorId;
    }
    
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getSchedule() {
        return schedule;
    }
    
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    
    public String getRoom() {
        return room;
    }
    
    public void setRoom(String room) {
        this.room = room;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getInstructorName() {
        return instructorName;
    }
    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    
    @Override
    public String toString() {
        return "ClassEntity{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", schedule='" + schedule + '\'' +
                ", room='" + room + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}