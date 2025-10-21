package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.Instructor;
import com.classmanagement.service.InstructorService;
import com.classmanagement.service.impl.InstructorServiceImpl;
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
 * Instructor Controller
 * Handles CRUD operations for instructors
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/admin/instructors")
public class InstructorServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private InstructorService instructorService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        instructorService = new InstructorServiceImpl();
        LoggerUtil.logInfo("InstructorServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter(Constants.PARAM_ACTION);
        
        try {
            if (action == null || Constants.ACTION_LIST.equals(action)) {
                listInstructors(request, response);
            } else if (Constants.ACTION_VIEW.equals(action)) {
                viewInstructor(request, response);
            } else if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("delete".equals(action)) {
                deleteInstructor(request, response);
            } else {
                listInstructors(request, response);
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
                createInstructor(request, response);
            } else if (Constants.ACTION_UPDATE.equals(action)) {
                updateInstructor(request, response);
            } else {
                listInstructors(request, response);
            }
        } catch (DatabaseException | ValidationException e) {
            handleError(request, response, e);
        }
    }
    
    /**
     * List all instructors
     */
    private void listInstructors(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        String searchTerm = request.getParameter("search");
        List<Instructor> instructors;
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            instructors = instructorService.searchInstructorsByName(searchTerm);
        } else {
            instructors = instructorService.getAllInstructors();
        }
        
        request.setAttribute("instructors", instructors);
        request.getRequestDispatcher("/admin/instructor-list.jsp").forward(request, response);
    }
    
    /**
     * View single instructor
     */
    private void viewInstructor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int instructorId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        Instructor instructor = instructorService.getInstructorById(instructorId);
        
        request.setAttribute("instructor", instructor);
        request.getRequestDispatcher("/admin/instructor-view.jsp").forward(request, response);
    }
    
    /**
     * Show edit form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int instructorId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        Instructor instructor = instructorService.getInstructorById(instructorId);
        
        request.setAttribute("instructor", instructor);
        request.getRequestDispatcher("/admin/instructor-form.jsp").forward(request, response);
    }
    
    /**
     * Create new instructor
     */
    private void createInstructor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        Instructor instructor = new Instructor();
        instructor.setFirstName(request.getParameter("firstName"));
        instructor.setLastName(request.getParameter("lastName"));
        instructor.setEmail(request.getParameter("email"));
        instructor.setPhone(request.getParameter("phone"));
        instructor.setExpertise(request.getParameter("expertise"));
        instructor.setQualification(request.getParameter("qualification"));
        
        instructorService.createInstructor(instructor);
        
        LoggerUtil.logInfo("Instructor created: " + instructor.getFullName());
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Instructor created successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/instructors");
    }
    
    /**
     * Update existing instructor
     */
    private void updateInstructor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException, ValidationException {
        
        Instructor instructor = new Instructor();
        instructor.setInstructorId(Integer.parseInt(request.getParameter("instructorId")));
        instructor.setFirstName(request.getParameter("firstName"));
        instructor.setLastName(request.getParameter("lastName"));
        instructor.setEmail(request.getParameter("email"));
        instructor.setPhone(request.getParameter("phone"));
        instructor.setExpertise(request.getParameter("expertise"));
        instructor.setQualification(request.getParameter("qualification"));
        
        instructorService.updateInstructor(instructor);
        
        LoggerUtil.logInfo("Instructor updated: " + instructor.getInstructorId());
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Instructor updated successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/instructors");
    }
    
    /**
     * Delete instructor
     */
    private void deleteInstructor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DatabaseException {
        
        int instructorId = Integer.parseInt(request.getParameter(Constants.PARAM_ID));
        instructorService.deleteInstructor(instructorId);
        
        LoggerUtil.logInfo("Instructor deleted: " + instructorId);
        request.getSession().setAttribute(Constants.SUCCESS_MESSAGE, "Instructor deleted successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/instructors");
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in InstructorServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        request.getRequestDispatcher("/admin/instructor-list.jsp").forward(request, response);
    }
}