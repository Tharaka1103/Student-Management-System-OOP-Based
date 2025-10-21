package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.ClassEntity;
import com.classmanagement.model.Course;
import com.classmanagement.model.Instructor;
import com.classmanagement.service.ClassService;
import com.classmanagement.service.CourseService;
import com.classmanagement.service.InstructorService;
import com.classmanagement.service.impl.ClassServiceImpl;
import com.classmanagement.service.impl.CourseServiceImpl;
import com.classmanagement.service.impl.InstructorServiceImpl;
import com.classmanagement.util.Constants;
import com.classmanagement.util.LoggerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class Controller
 * Handles CRUD operations for classes
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/admin/classes")
public class ClassServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ClassService classService;
    private CourseService courseService;
    private InstructorService instructorService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        classService = new ClassServiceImpl();
        courseService = new CourseServiceImpl();
        instructorService = new InstructorServiceImpl();
        LoggerUtil.logInfo("ClassServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        
        try {
            if (action == null || Constants.ACTION_LIST.equals(action)) {
                listClasses(request, response);
            } else if (Constants.ACTION_VIEW.equals(action)) {
                viewClass(request, response);
            } else if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("new".equals(action)) {
                showCreateForm(request, response);
            } else if ("delete".equals(action)) {
                deleteClass(request, response);
            } else {
                listClasses(request, response);
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
                createClass(request, response);
            } else if (Constants.ACTION_UPDATE.equals(action)) {
                updateClass(request, response);
            } else {
                listClasses(request, response);
            }
        } catch (DatabaseException | ValidationException e) {
            handleError(request, response, e);
        }
    }
    
    /**
     * List all classes
     */
    private void listClasses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        List<ClassEntity> classes = classService.getAllClasses();
        request.setAttribute("classes", classes);
        request.getRequestDispatcher("/admin/class-list.jsp").forward(request, response);
    }
    
    /**
     * View single class
     */
    private void viewClass(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int classId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        ClassEntity classEntity = classService.getClassById(classId);
        
        request.setAttribute("classEntity", classEntity);
        request.getRequestDispatcher("/admin/class-view.jsp").forward(request, response);
    }
    
    /**
     * Show create form
     */
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        List<Course> courses = courseService.getAllCourses();
        List<Instructor> instructors = instructorService.getAllInstructors();
        
        request.setAttribute("courses", courses);
        request.setAttribute("instructors", instructors);
        request.getRequestDispatcher("/admin/class-form.jsp").forward(request, response);
    }
    
    /**
     * Show edit form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int classId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        ClassEntity classEntity = classService.getClassById(classId);
        
        List<Course> courses = courseService.getAllCourses();
        List<Instructor> instructors = instructorService.getAllInstructors();
        
        request.setAttribute("classEntity", classEntity);
        request.setAttribute("courses", courses);
        request.setAttribute("instructors", instructors);
        request.getRequestDispatcher("/admin/class-form.jsp").forward(request, response);
    }
    
    /**
     * Create new class
     */
    private void createClass(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(request.getParameter("className"));
        classEntity.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        classEntity.setInstructorId(Integer.parseInt(request.getParameter("instructorId")));
        classEntity.setSchedule(request.getParameter("schedule"));
        classEntity.setRoom(request.getParameter("room"));
        classEntity.setCapacity(Integer.parseInt(request.getParameter("capacity")));
        classEntity.setStatus(request.getParameter("status"));
        
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (startDate != null && !startDate.isEmpty()) {
            classEntity.setStartDate(LocalDateTime.parse(startDate + "T00:00:00"));
        }
        
        if (endDate != null && !endDate.isEmpty()) {
            classEntity.setEndDate(LocalDateTime.parse(endDate + "T00:00:00"));
        }
        
        classService.createClass(classEntity);
        
        LoggerUtil.logInfo("Class created: " + classEntity.getClassName());
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Class created successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/classes");
    }
    
    /**
     * Update existing class
     */
    private void updateClass(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassId(Integer.parseInt(request.getParameter("classId")));
        classEntity.setClassName(request.getParameter("className"));
        classEntity.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        classEntity.setInstructorId(Integer.parseInt(request.getParameter("instructorId")));
        classEntity.setSchedule(request.getParameter("schedule"));
        classEntity.setRoom(request.getParameter("room"));
        classEntity.setCapacity(Integer.parseInt(request.getParameter("capacity")));
        classEntity.setStatus(request.getParameter("status"));
        
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (startDate != null && !startDate.isEmpty()) {
            classEntity.setStartDate(LocalDateTime.parse(startDate + "T00:00:00"));
        }
        
        if (endDate != null && !endDate.isEmpty()) {
            classEntity.setEndDate(LocalDateTime.parse(endDate + "T00:00:00"));
        }
        
        classService.updateClass(classEntity);
        
        LoggerUtil.logInfo("Class updated: " + classEntity.getClassId());
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Class updated successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/classes");
    }
    
    /**
     * Delete class
     */
    private void deleteClass(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int classId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        classService.deleteClass(classId);
        
        LoggerUtil.logInfo("Class deleted: " + classId);
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Class deleted successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/classes");
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in ClassServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        request.getRequestDispatcher("/admin/class-list.jsp").forward(request, response);
    }
}