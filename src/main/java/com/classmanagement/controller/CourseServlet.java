package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
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
 * Course Controller
 * Handles CRUD operations for courses
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/admin/courses")
public class CourseServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private CourseService courseService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        courseService = new CourseServiceImpl();
        LoggerUtil.logInfo("CourseServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        
        try {
            if (action == null || Constants.ACTION_LIST.equals(action)) {
                listCourses(request, response);
            } else if (Constants.ACTION_VIEW.equals(action)) {
                viewCourse(request, response);
            } else if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("delete".equals(action)) {
                deleteCourse(request, response);
            } else {
                listCourses(request, response);
            }
        } catch (DatabaseException e) {
            handleError(request, response, e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        
        try {
            if (Constants.ACTION_CREATE.equals(action)) {
                createCourse(request, response);
            } else if (Constants.ACTION_UPDATE.equals(action)) {
                updateCourse(request, response);
            } else {
                listCourses(request, response);
            }
        } catch (DatabaseException | ValidationException e) {
            handleError(request, response, e);
        }
    }
    
    /**
     * List all courses
     */
    private void listCourses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        String searchTerm = request.getParameter("search");
        List<Course> courses;
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            courses = courseService.searchCoursesByName(searchTerm);
        } else {
            courses = courseService.getAllCourses();
        }
        
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/admin/course-list.jsp").forward(request, response);
    }
    
    /**
     * View single course
     */
    private void viewCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int courseId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        Course course = courseService.getCourseById(courseId);
        
        request.setAttribute("course", course);
        request.getRequestDispatcher("/admin/course-view.jsp").forward(request, response);
    }
    
    /**
     * Show edit form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int courseId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        Course course = courseService.getCourseById(courseId);
        
        request.setAttribute("course", course);
        request.getRequestDispatcher("/admin/course-form.jsp").forward(request, response);
    }
    
    /**
     * Create new course
     */
    private void createCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        Course course = new Course();
        course.setCourseName(request.getParameter("courseName"));
        course.setCourseCode(request.getParameter("courseCode"));
        course.setDescription(request.getParameter("description"));
        course.setDuration(Integer.parseInt(request.getParameter("duration")));
        course.setCategory(request.getParameter("category"));
        
        courseService.createCourse(course);
        
        LoggerUtil.logInfo("Course created: " + course.getCourseName());
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Course created successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/courses");
    }
    
    /**
     * Update existing course
     */
    private void updateCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        Course course = new Course();
        course.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        course.setCourseName(request.getParameter("courseName"));
        course.setCourseCode(request.getParameter("courseCode"));
        course.setDescription(request.getParameter("description"));
        course.setDuration(Integer.parseInt(request.getParameter("duration")));
        course.setCategory(request.getParameter("category"));
        
        courseService.updateCourse(course);
        
        LoggerUtil.logInfo("Course updated: " + course.getCourseId());
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Course updated successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/courses");
    }
    
    /**
     * Delete course
     */
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int courseId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        courseService.deleteCourse(courseId);
        
        LoggerUtil.logInfo("Course deleted: " + courseId);
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Course deleted successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/courses");
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in CourseServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        request.getRequestDispatcher("/admin/course-list.jsp").forward(request, response);
    }
}