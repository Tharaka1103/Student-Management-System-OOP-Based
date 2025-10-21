package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
import com.classmanagement.exception.ValidationException;
import com.classmanagement.model.User;
import com.classmanagement.service.UserService;
import com.classmanagement.service.impl.UserServiceImpl;
import com.classmanagement.util.Constants;
import com.classmanagement.util.LoggerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Profile Controller
 * Handles user profile operations
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/student/profile")
public class ProfileServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        LoggerUtil.logInfo("ProfileServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            // Reload user data from database
            User currentUser = userService.getUserById(user.getUserId());
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
        } catch (DatabaseException e) {
            handleError(request, response, e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(Constants.SESSION_USER);
        
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            User user = new User();
            user.setUserId(sessionUser.getUserId());
            user.setUsername(request.getParameter("username"));
            user.setEmail(request.getParameter("email"));
            user.setPhone(request.getParameter("phone"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            
            userService.updateUser(user);
            
            // Update session
            User updatedUser = userService.getUserById(user.getUserId());
            session.setAttribute(Constants.SESSION_USER, updatedUser);
            
            LoggerUtil.logInfo("Profile updated for user: " + user.getUserId());
            request.setAttribute(Constants.SUCCESS_MESSAGE, "Profile updated successfully!");
            request.setAttribute("user", updatedUser);
            request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
            
        } catch (DatabaseException | ValidationException e) {
            handleError(request, response, e);
        }
    }
    
    /**
     * Handle errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        LoggerUtil.logError("Error in ProfileServlet: " + e.getMessage());
        request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
    }
}