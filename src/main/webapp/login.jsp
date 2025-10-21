<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/authstyles.css">
</head>
<body>
    <div class="auth-container">
        <!-- Left Side - Full Image -->
        <div class="auth-image">
            <div class="auth-image-content">
                <img src="${pageContext.request.contextPath}/images/register.jpg" alt="Login Illustration">
            </div>
        </div>
        
        <!-- Right Side - Login Form (Scrollable) -->
        <div class="auth-form">
            <div class="auth-form-inner">
                <div class="auth-header">
                    <div class="auth-logo">📚</div>
                    <h1>ClassHub</h1>
                    <h2>Sign In to Your Account</h2>
                    <p>Enter your credentials to continue</p>
                </div>
                
                <%-- Display success message --%>
                <% if (request.getAttribute("successMessage") != null) { %>
                    <div class="alert alert-success">
                        <%= request.getAttribute("successMessage") %>
                    </div>
                <% } %>
                
                <%-- Display error message --%>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-error">
                        <%= request.getAttribute("errorMessage") %>
                    </div>
                <% } %>
                
                <form action="${pageContext.request.contextPath}/login" method="post" novalidate onsubmit="return validateLogin()">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" placeholder="Enter your username" autocomplete="username">
                        <span class="error" id="usernameError"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="password-wrapper">
                            <input type="password" id="password" name="password" placeholder="Enter your password" autocomplete="current-password">
                            <button type="button" class="password-toggle" onclick="togglePassword('password')" aria-label="Toggle password visibility">
                                👁️
                            </button>
                        </div>
                        <span class="error" id="passwordError"></span>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Sign In</button>
                </form>
                
                <div class="auth-link">
                    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register.jsp">Create Account</a></p>
                </div>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/authvalidation.js"></script>
</body>
</html>