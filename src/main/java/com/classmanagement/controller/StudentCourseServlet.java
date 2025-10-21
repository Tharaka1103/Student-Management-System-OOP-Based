package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.model.Course;
import com.classmanagement.service.CourseService;
import com.classmanagement.service.impl.CourseServiceImpl;
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
 * Student Course Controller
 * Students can view available courses
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/student/courses")
public class StudentCourseServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private CourseService courseService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        courseService = new CourseServiceImpl();
        LoggerUtil.logInfo("StudentCourseServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        
        try {
            if (Constants.ACTION_VIEW.equals(action)) {
                viewCourse(request, response);
            } else if ("search".equals(action)) {
                searchCourses(request, response);
            } else {
                // Default action - list all courses
                listCourses(request, response);
            }
        } catch (DatabaseException e) {
            handleError(request, response, e);
        } catch (NumberFormatException e) {
            LoggerUtil.logError("Invalid ID format: " + e.getMessage());
            handleError(request, response, new DatabaseException("Invalid course ID"));
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle POST requests the same as GET for search
        doGet(request, response);
    }
    
    /**
     * List all courses
     */
    private void listCourses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        LoggerUtil.logInfo("Listing all courses for student");
        List<Course> courses = courseService.getAllCourses();
        
        LoggerUtil.logInfo("Found " + (courses != null ? courses.size() : 0) + " courses");
        
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
    }
    
    /**
     * View single course
     */
    private void viewCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        String idParam = request.getParameter(Constants.PARAM_ID);
        if (idParam == null || idParam.trim().isEmpty()) {
            throw new DatabaseException("Course ID is required");
        }
        
        int courseId = Integer.parseInt(idParam);
        Course course = courseService.getCourseById(courseId);
        
        if (course == null) {
            throw new DatabaseException("Course not found");
        }
        
        request.setAttribute("course", course);
        request.getRequestDispatcher("/student/course-detail.jsp").forward(request, response);
    }
    
    /**
     * Search courses
     */
    private void searchCourses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        String searchTerm = request.getParameter("q");
        List<Course> courses;
        
        LoggerUtil.logInfo("Searching courses with term: " + searchTerm);
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            courses = courseService.searchCoursesByName(searchTerm.trim());
            request.setAttribute("searchTerm", searchTerm.trim());
        } else {
            // If search term is empty, show all courses
            courses = courseService.getAllCourses();
            request.setAttribute("searchTerm", "");
        }
        
        LoggerUtil.logInfo("Found " + (courses != null ? courses.size() : 0) + " courses");
        
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in StudentCourseServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        
        // Try to load courses even on error
        try {
            List<Course> courses = courseService.getAllCourses();
            request.setAttribute("courses", courses);
        } catch (DatabaseException ex) {
            LoggerUtil.logError("Failed to load courses in error handler: " + ex.getMessage());
        }
        
        request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
    }
}