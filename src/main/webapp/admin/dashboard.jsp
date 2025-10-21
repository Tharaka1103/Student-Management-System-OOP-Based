<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.User" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    User user = (User) session.getAttribute(Constants.SESSION_USER);
    if (user == null || !Constants.ROLE_ADMIN.equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="dashboard-container">
        <h1>Welcome Back, <%= user.getFirstName() %>! 👋</h1>
        <p>Manage your educational institution from your admin dashboard</p>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <div class="card-icon">📚</div>
                <h3>Courses</h3>
                <p>Create, edit, and manage all courses in the system</p>
                <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-primary">Manage Courses</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">👨‍🏫</div>
                <h3>Instructors</h3>
                <p>Add and manage instructor profiles and assignments</p>
                <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-primary">Manage Instructors</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">🏫</div>
                <h3>Classes</h3>
                <p>Schedule and organize classes with instructors</p>
                <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-primary">Manage Classes</a>
            </div>
            
            <div class="dashboard-card">
                <div class="card-icon">👥</div>
                <h3>Enrollments</h3>
                <p>View and monitor all student enrollments</p>
                <a href="${pageContext.request.contextPath}/admin/enrollments" class="btn btn-primary">View Enrollments</a>
            </div>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>