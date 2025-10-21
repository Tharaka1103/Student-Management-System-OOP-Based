<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.User" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        user = new User();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/authstyles.css">
</head>
<body>
    <div class="auth-container register-container">
        <!-- Left Side - Registration Form (Scrollable) -->
        <div class="auth-form">
            <div class="auth-form-inner">
                <div class="auth-header">
                    <div class="auth-logo">🎓</div>
                    <h1>ClassHub</h1>
                    <h2>Create Your Account</h2>
                    <p>Join thousands of learners today</p>
                </div>
                
                <%-- Display error message --%>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-error">
                        <%= request.getAttribute("errorMessage") %>
                    </div>
                <% } %>
                
                <form action="${pageContext.request.contextPath}/register" method="post" novalidate onsubmit="return validateRegistration()">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name <span class="required">*</span></label>
                            <input type="text" id="firstName" name="firstName" 
                                   value="<%= user.getFirstName() != null ? user.getFirstName() : "" %>" 
                                   placeholder="John" autocomplete="given-name">
                            <span class="error" id="firstNameError"></span>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastName">Last Name <span class="required">*</span></label>
                            <input type="text" id="lastName" name="lastName" 
                                   value="<%= user.getLastName() != null ? user.getLastName() : "" %>" 
                                   placeholder="Doe" autocomplete="family-name">
                            <span class="error" id="lastNameError"></span>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="username">Username <span class="required">*</span></label>
                        <input type="text" id="username" name="username" 
                               value="<%= user.getUsername() != null ? user.getUsername() : "" %>" 
                               placeholder="johndoe123" autocomplete="username">
                        <span class="error" id="usernameError"></span>
                        <small>3-20 characters, letters, numbers, and underscores only</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email Address <span class="required">*</span></label>
                        <input type="email" id="email" name="email" 
                               value="<%= user.getEmail() != null ? user.getEmail() : "" %>" 
                               placeholder="john.doe@example.com" autocomplete="email">
                        <span class="error" id="emailError"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="tel" id="phone" name="phone" 
                               value="<%= user.getPhone() != null ? user.getPhone() : "" %>" 
                               placeholder="1234567890" autocomplete="tel">
                        <span class="error" id="phoneError"></span>
                        <small>10 digits without spaces or dashes</small>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="password">Password <span class="required">*</span></label>
                            <div class="password-wrapper">
                                <input type="password" id="password" name="password" placeholder="••••••••" autocomplete="new-password">
                                <button type="button" class="password-toggle" onclick="togglePassword('password')" aria-label="Toggle password visibility">
                                    👁️
                                </button>
                            </div>
                            <span class="error" id="passwordError"></span>
                            <small>Minimum 6 characters</small>
                        </div>
                        
                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password <span class="required">*</span></label>
                            <div class="password-wrapper">
                                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="••••••••" autocomplete="new-password">
                                <button type="button" class="password-toggle" onclick="togglePassword('confirmPassword')" aria-label="Toggle password visibility">
                                    👁️
                                </button>
                            </div>
                            <span class="error" id="confirmPasswordError"></span>
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Create Account</button>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary">Back to Login</a>
                    </div>
                </form>
                
                <div class="auth-link">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Sign In</a></p>
                </div>
            </div>
        </div>
        
        <!-- Right Side - Full Image -->
        <div class="auth-image">
        	<img src="${pageContext.request.contextPath}/images/login.jpg" alt="Register Illustration">
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/authvalidation.js"></script>
</body>
</html>