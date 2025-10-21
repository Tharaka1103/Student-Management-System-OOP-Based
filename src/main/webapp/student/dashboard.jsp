<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.User" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null || !Constants.ROLE_STUDENT.equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="dashboard-container">
        <h1>Welcome Back, <%= user.getFirstName() %>! 👋</h1>
        <p>Manage your academic journey from your personalized dashboard</p>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <div class="card-icon">📚</div>
                <h3>My Enrollments</h3>
                <p>View and manage your enrolled classes and track your academic progress</p>
                <a href="${pageContext.request.contextPath}/student/enrollments" class="btn btn-primary">View Enrollments</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">🎯</div>
                <h3>Available Classes</h3>
                <p>Discover and enroll in new classes to expand your knowledge</p>
                <a href="${pageContext.request.contextPath}/student/enrollments?action=available" class="btn btn-primary">Browse Classes</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">📖</div>
                <h3>Course Catalog</h3>
                <p>Explore our comprehensive catalog of courses across various subjects</p>
                <a href="${pageContext.request.contextPath}/student/courses" class="btn btn-primary">View Catalog</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">👤</div>
                <h3>My Profile</h3>
                <p>Update your personal information and manage account settings</p>
                <a href="${pageContext.request.contextPath}/student/profile" class="btn btn-primary">Manage Profile</a>
            </div>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>