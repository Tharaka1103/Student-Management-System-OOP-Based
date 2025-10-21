<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.User" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    User profileUser = (User) request.getAttribute("user");
    if (profileUser == null) {
        profileUser = (User) session.getAttribute(Constants.SESSION_USER);
    }
    
    String successMessage = (String) request.getAttribute(Constants.SUCCESS_MESSAGE);
    String errorMessage = (String) request.getAttribute(Constants.ERROR_MESSAGE);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>My Profile</h1>
            <a href="${pageContext.request.contextPath}/student/dashboard.jsp" class="btn btn-secondary">← Back to Dashboard</a>
        </div>
        
        <%-- Display messages --%>
        <% if (successMessage != null) { %>
            <div class="alert alert-success"><%= successMessage %></div>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <% } %>
        
        <div class="profile-container">
            <div class="profile-info">
                <h2>Profile Information</h2>
                <div class="info-group">
                    <label>Username</label>
                    <p><%= profileUser.getUsername() %></p>
                </div>
                <div class="info-group">
                    <label>Email Address</label>
                    <p><%= profileUser.getEmail() %></p>
                </div>
                <div class="info-group">
                    <label>Full Name</label>
                    <p><%= profileUser.getFullName() %></p>
                </div>
                <div class="info-group">
                    <label>Phone Number</label>
                    <p><%= profileUser.getPhone() != null && !profileUser.getPhone().isEmpty() ? profileUser.getPhone() : "Not provided" %></p>
                </div>
                <div class="info-group">
                    <label>Role</label>
                    <p><%= profileUser.getRole() %></p>
                </div>
            </div>
            
            <div class="profile-edit">
                <h2>Update Profile</h2>
                <form action="${pageContext.request.contextPath}/student/profile" method="post" novalidate onsubmit="return validateProfileForm()">
                    <div class="form-group">
                        <label for="username">Username <span class="required">*</span></label>
                        <input type="text" id="username" name="username" 
                               value="<%= profileUser.getUsername() %>" readonly>
                        <small>Username cannot be changed</small>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name <span class="required">*</span></label>
                            <input type="text" id="firstName" name="firstName" 
                                   value="<%= profileUser.getFirstName() %>">
                            <span class="error" id="firstNameError"></span>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastName">Last Name <span class="required">*</span></label>
                            <input type="text" id="lastName" name="lastName" 
                                   value="<%= profileUser.getLastName() %>">
                            <span class="error" id="lastNameError"></span>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email Address <span class="required">*</span></label>
                        <input type="email" id="email" name="email" 
                               value="<%= profileUser.getEmail() %>">
                        <span class="error" id="emailError"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="tel" id="phone" name="phone" 
                               value="<%= profileUser.getPhone() != null ? profileUser.getPhone() : "" %>"
                               placeholder="Enter 10-digit phone number">
                        <span class="error" id="phoneError"></span>
                        <small>Enter 10 digits without spaces or dashes</small>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                        <a href="${pageContext.request.contextPath}/student/dashboard.jsp" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
    <script src="${pageContext.request.contextPath}/js/uservalidation.js"></script>
</body>
</html>