package com.classmanagement.controller;

import com.classmanagement.exception.DatabaseException;
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
 * Login Controller
 * Handles user authentication
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        LoggerUtil.logInfo("LoginServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            // Authenticate user
            User user = userService.authenticateUser(username, password);
            
            if (user != null) {
                // Create session
                HttpSession session = request.getSession();
                session.setAttribute(Constants.SESSION_USER, user);
                session.setAttribute(Constants.SESSION_USER_ID, user.getUserId());
                session.setAttribute(Constants.SESSION_USER_ROLE, user.getRole());
                session.setAttribute(Constants.SESSION_USERNAME, user.getUsername());
                
                LoggerUtil.logInfo("User logged in successfully: " + username);
                
                // Redirect based on role
                if (Constants.ROLE_ADMIN.equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/student/dashboard.jsp");
                }
            } else {
                // Authentication failed
                request.setAttribute(Constants.ERROR_MESSAGE, "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        } catch (DatabaseException e) {
            LoggerUtil.logError("Error during login: " + e.getMessage());
            request.setAttribute(Constants.ERROR_MESSAGE, "An error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}