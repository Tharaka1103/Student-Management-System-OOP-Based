package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.ClassEntity;
import com.classmanagement.model.Enrollment;
import com.classmanagement.model.User;
import com.classmanagement.service.ClassService;
import com.classmanagement.service.EnrollmentService;
import com.classmanagement.service.impl.ClassServiceImpl;
import com.classmanagement.service.impl.EnrollmentServiceImpl;
import com.classmanagement.util.Constants;
import com.classmanagement.util.LoggerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Enrollment Controller
 * Handles student enrollment operations
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/student/enrollments")
public class EnrollmentServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private EnrollmentService enrollmentService;
    private ClassService classService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        enrollmentService = new EnrollmentServiceImpl();
        classService = new ClassServiceImpl();
        LoggerUtil.logInfo("EnrollmentServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            if (action == null || Constants.ACTION_LIST.equals(action)) {
                listMyEnrollments(request, response, user.getUserId());
            } else if ("available".equals(action)) {
                showAvailableClasses(request, response);
            } else if ("cancel".equals(action)) {
                cancelEnrollment(request, response);
            } else {
                listMyEnrollments(request, response, user.getUserId());
            }
        } catch (DatabaseException e) {
            handleError(request, response, e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            if ("enroll".equals(action)) {
                enrollInClass(request, response, user.getUserId());
            } else {
                listMyEnrollments(request, response, user.getUserId());
            }
        } catch (DatabaseException | ValidationException e) {
            handleError(request, response, e);
        }
    }
    
    /**
     * List student's enrollments
     */
    private void listMyEnrollments(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException, DatabaseException {
        
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(userId);
        request.setAttribute("enrollments", enrollments);
        request.getRequestDispatcher("/student/my-enrollments.jsp").forward(request, response);
    }
    
    /**
     * Show available classes for enrollment
     */
    private void showAvailableClasses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        List<ClassEntity> availableClasses = classService.getAvailableClasses();
        request.setAttribute("classes", availableClasses);
        request.getRequestDispatcher("/student/available-classes.jsp").forward(request, response);
    }
    
    /**
     * Enroll in a class
     */
    private void enrollInClass(HttpServletRequest request, HttpServletResponse response, int userId) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        int classId = Integer.parseInt(request.getParameter("classId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setClassId(classId);
        enrollment.setCourseId(courseId);
        enrollment.setStatus(Constants.ENROLLMENT_STATUS_ACTIVE);
        
        enrollmentService.enrollStudent(enrollment);
        
        LoggerUtil.logInfo("Student enrolled. User ID: " + userId + ", Class ID: " + classId);
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Successfully enrolled in class!");
        response.sendRedirect(request.getContextPath() + "/student/enrollments");
    }
    
    /**
     * Cancel enrollment
     */
    private void cancelEnrollment(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int enrollmentId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        enrollmentService.cancelEnrollment(enrollmentId);
        
        LoggerUtil.logInfo("Enrollment cancelled: " + enrollmentId);
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Enrollment cancelled successfully!");
        response.sendRedirect(request.getContextPath() + "/student/enrollments");
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in EnrollmentServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        request.getRequestDispatcher("/student/my-enrollments.jsp").forward(request, response);
    }
}