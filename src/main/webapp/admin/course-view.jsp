<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Course" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Course course = (Course) request.getAttribute("course");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Details - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Course Details</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/admin/courses?action=edit&id=<%= course.getCourseId() %>" 
                   class="btn btn-warning">✏️ Edit</a>
                <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-secondary">← Back to List</a>
            </div>
        </div>
        
        <% if (course != null) { %>
            <div class="view-container">
                <div class="view-card">
                    <h2>📚 <%= course.getCourseName() %></h2>
                    
                    <div class="detail-grid">
                        <div class="detail-item">
                            <label>Course ID</label>
                            <p>#<%= course.getCourseId() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Course Code</label>
                            <p><%= course.getCourseCode() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Category</label>
                            <p><%= course.getCategory() != null ? course.getCategory() : "General" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Duration</label>
                            <p><%= course.getDuration() %> hours</p>
                        </div>
                        
                        <div class="detail-item full-width">
                            <label>Description</label>
                            <p><%= course.getDescription() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Created At</label>
                            <p><%= course.getCreatedAt() != null ? course.getCreatedAt().format(dateFormatter) : "N/A" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Last Updated</label>
                            <p><%= course.getUpdatedAt() != null ? course.getUpdatedAt().format(dateFormatter) : "N/A" %></p>
                        </div>
                    </div>
                    
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/admin/courses?action=edit&id=<%= course.getCourseId() %>" 
                           class="btn btn-primary">✏️ Edit Course</a>
                        <a href="${pageContext.request.contextPath}/admin/enrollments?action=viewByCourse&courseId=<%= course.getCourseId() %>" 
                           class="btn btn-info">👥 View Enrollments</a>
                        <a href="${pageContext.request.contextPath}/admin/courses?action=delete&id=<%= course.getCourseId() %>" 
                           class="btn btn-danger" 
                           onclick="return confirm('⚠️ Are you sure you want to delete this course? This will affect all related classes and enrollments.')">🗑️ Delete Course</a>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="no-data">
                <p>❌ Course not found.</p>
                <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-primary">Back to Courses</a>
            </div>
        <% } %>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>