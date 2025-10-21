package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.ClassEntity;
import com.classmanagement.model.Course;
import com.classmanagement.model.Enrollment;
import com.classmanagement.service.ClassService;
import com.classmanagement.service.CourseService;
import com.classmanagement.service.EnrollmentService;
import com.classmanagement.service.impl.ClassServiceImpl;
import com.classmanagement.service.impl.CourseServiceImpl;
import com.classmanagement.service.impl.EnrollmentServiceImpl;
import com.classmanagement.util.Constants;
import com.classmanagement.util.LoggerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Admin Enrollment Controller
 * Admin can view all enrollments and enrollments by class/course
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/admin/enrollments")
public class AdminEnrollmentServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private EnrollmentService enrollmentService;
    private ClassService classService;
    private CourseService courseService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        enrollmentService = new EnrollmentServiceImpl();
        classService = new ClassServiceImpl();
        courseService = new CourseServiceImpl();
        LoggerUtil.logInfo("AdminEnrollmentServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        
        try {
            if ("viewByClass".equals(action)) {
                viewEnrollmentsByClass(request, response);
            } else if ("viewByCourse".equals(action)) {
                viewEnrollmentsByCourse(request, response);
            } else {
                viewAllEnrollments(request, response);
            }
        } catch (DatabaseException e) {
            handleError(request, response, e);
        }
    }
    
    /**
     * View all enrollments
     */
    private void viewAllEnrollments(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        List<ClassEntity> classes = classService.getAllClasses();
        List<Course> courses = courseService.getAllCourses();
        
        request.setAttribute("enrollments", enrollments);
        request.setAttribute("classes", classes);
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/admin/enrollments.jsp").forward(request, response);
    }
    
    /**
     * View enrollments by class
     */
    private void viewEnrollmentsByClass(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int classId = Integer.parseInt(request.getParameter("classId"));
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByClass(classId);
        ClassEntity classEntity = classService.getClassById(classId);
        
        request.setAttribute("enrollments", enrollments);
        request.setAttribute("classEntity", classEntity);
        request.setAttribute("filterType", "class");
        request.getRequestDispatcher("/admin/enrollments.jsp").forward(request, response);
    }
    
    /**
     * View enrollments by course
     */
    private void viewEnrollmentsByCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        Course course = courseService.getCourseById(courseId);
        
        // Filter enrollments by course
        enrollments.removeIf(e -> e.getCourseId() != courseId);
        
        request.setAttribute("enrollments", enrollments);
        request.setAttribute("course", course);
        request.setAttribute("filterType", "course");
        request.getRequestDispatcher("/admin/enrollments.jsp").forward(request, response);
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in AdminEnrollmentServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        request.getRequestDispatcher("/admin/enrollments.jsp").forward(request, response);
    }
}