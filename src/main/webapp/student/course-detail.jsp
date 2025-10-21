<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Course" %>
<%
    Course course = (Course) request.getAttribute("course");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= course != null ? course.getCourseName() : "Course Details" %> - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Course Details</h1>
            <a href="${pageContext.request.contextPath}/student/courses" class="btn btn-secondary">← Back to Catalog</a>
        </div>
        
        <% if (course != null) { %>
            <div class="course-detail-container">
                <div class="course-detail-card">
                    <div class="course-detail-header">
                        <h2><%= course.getCourseName() %></h2>
                        <span class="course-code-badge"><%= course.getCourseCode() %></span>
                    </div>
                    
                    <div class="course-detail-body">
                        <div class="course-info-grid">
                            <div class="info-item">
                                <div class="info-icon">📁</div>
                                <div class="info-content">
                                    <label>Category</label>
                                    <p><%= course.getCategory() != null ? course.getCategory() : "General" %></p>
                                </div>
                            </div>
                            
                            <div class="info-item">
                                <div class="info-icon">⏱️</div>
                                <div class="info-content">
                                    <label>Duration</label>
                                    <p><%= course.getDuration() %> hours</p>
                                </div>
                            </div>
                            
                            <div class="info-item">
                                <div class="info-icon">🎯</div>
                                <div class="info-content">
                                    <label>Course Code</label>
                                    <p><%= course.getCourseCode() %></p>
                                </div>
                            </div>
                        </div>
                        
                        <div class="course-description-section">
                            <h3>📖 Course Description</h3>
                            <p><%= course.getDescription() != null ? course.getDescription() : "No description available for this course." %></p>
                        </div>
                        
                        <div class="course-actions">
                            <a href="${pageContext.request.contextPath}/student/enrollments?action=available" 
                               class="btn btn-primary">View Available Classes</a>
                            <a href="${pageContext.request.contextPath}/student/courses" 
                               class="btn btn-secondary">Back to Course Catalog</a>
                        </div>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="no-data">
                <p>❌ Course not found.</p>
                <p>The course you're looking for doesn't exist or has been removed.</p>
                <a href="${pageContext.request.contextPath}/student/courses" class="btn btn-primary">Back to Course Catalog</a>
            </div>
        <% } %>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>