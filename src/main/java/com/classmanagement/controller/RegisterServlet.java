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
import java.io.IOException;

/**
 * Registration Controller
 * @author Class Management Team
 * @version 1.0
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        LoggerUtil.logInfo("RegisterServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to registration page
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        // Create user object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(Constants.ROLE_STUDENT); // Default role is student
        
        try {
            // Register user
            userService.registerUser(user);
            
            LoggerUtil.logInfo("User registered successfully: " + username);
            request.setAttribute(Constants.SUCCESS_MESSAGE, "Registration successful! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } catch (ValidationException e) {
            LoggerUtil.logError("Validation error during registration: " + e.getMessage());
            request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
            request.setAttribute("user", user);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            
        } catch (DatabaseException e) {
            LoggerUtil.logError("Database error during registration: " + e.getMessage());
            request.setAttribute(Constants.ERROR_MESSAGE, "An error occurred. Please try again.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}