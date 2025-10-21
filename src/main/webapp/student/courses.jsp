<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    String searchTerm = (String) request.getAttribute("searchTerm");
    String errorMessage = (String) request.getAttribute(Constants.ERROR_MESSAGE);
    
    // If courses is null, redirect to servlet
    if (courses == null && errorMessage == null) {
        response.sendRedirect(request.getContextPath() + "/student/courses");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Catalog - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Course Catalog</h1>
            <a href="${pageContext.request.contextPath}/student/enrollments?action=available" class="btn btn-primary">View Available Classes</a>
        </div>
        
        <%-- Display error message --%>
        <% if (errorMessage != null) { %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <% } %>
        
        <!-- Search Section -->
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/student/courses" method="get">
                <input type="hidden" name="action" value="search">
                <input type="text" name="q" placeholder="🔍 Search courses by name, code, or category..." 
                       value="<%= searchTerm != null ? searchTerm : "" %>">
                <button type="submit" class="btn btn-primary">Search</button>
                <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                    <a href="${pageContext.request.contextPath}/student/courses" class="btn btn-secondary">Clear</a>
                <% } %>
            </form>
        </div>
        
        <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
            <div class="search-results-info">
                <p>🔎 Search results for: <strong>"<%= searchTerm %>"</strong></p>
                <p>Found <strong><%= courses != null ? courses.size() : 0 %></strong> course(s)</p>
            </div>
        <% } %>
        
        <!-- Course Grid -->
        <div class="courses-grid">
            <% if (courses != null && !courses.isEmpty()) { %>
                <% for (Course course : courses) { %>
                    <div class="course-card">
                        <div class="course-card-header">
                            <h3><%= course.getCourseName() %></h3>
                            <span class="course-code"><%= course.getCourseCode() %></span>
                        </div>
                        <div class="course-card-body">
                            <% if (course.getCategory() != null) { %>
                                <p class="course-category">
                                    <strong>📁 Category:</strong> <%= course.getCategory() %>
                                </p>
                            <% } %>
                            <p class="course-duration">
                                <strong>⏱️ Duration:</strong> <%= course.getDuration() %> hours
                            </p>
                            <p class="course-description">
                                <%= course.getDescription() != null && course.getDescription().length() > 150 ? 
                                    course.getDescription().substring(0, 150) + "..." : 
                                    (course.getDescription() != null ? course.getDescription() : "No description available") %>
                            </p>
                        </div>
                        <div class="course-card-footer">
                            <a href="${pageContext.request.contextPath}/student/courses?action=view&id=<%= course.getCourseId() %>" 
                               class="btn btn-info btn-block">View Details</a>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <div class="no-data">
                    <p>
                        <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                            🔍 No courses found matching "<%= searchTerm %>".
                        <% } else { %>
                            📚 No courses available at the moment.
                        <% } %>
                    </p>
                    <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                        <a href="${pageContext.request.contextPath}/student/courses" class="btn btn-primary">View All Courses</a>
                    <% } else { %>
                        <a href="${pageContext.request.contextPath}/student/dashboard.jsp" class="btn btn-primary">Back to Dashboard</a>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>