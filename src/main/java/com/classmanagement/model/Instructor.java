package com.classmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Instructor Model Class
 * @author Class Management Team
 * @version 1.0
 */
public class Instructor implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String expertise;
    private String qualification;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Default constructor
    public Instructor() {
    }
    
    // Parameterized constructor
    public Instructor(String firstName, String lastName, String email, String expertise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.expertise = expertise;
    }
    
    // Getters and Setters
    public int getInstructorId() {
        return instructorId;
    }
    
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getExpertise() {
        return expertise;
    }
    
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
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
    
    /**
     * Get full name of instructor
     * @return Full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", expertise='" + expertise + '\'' +
                '}';
    }
}