<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    String successMessage = (String) session.getAttribute(Constants.SUCCESS_MESSAGE);
    String errorMessage = (String) request.getAttribute(Constants.ERROR_MESSAGE);
    session.removeAttribute(Constants.SUCCESS_MESSAGE);
    String searchTerm = request.getParameter("search");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Courses - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Manage Courses</h1>
            <a href="${pageContext.request.contextPath}/admin/course-form.jsp" class="btn btn-primary">+ Add New Course</a>
        </div>
        
        <%-- Display messages --%>
        <% if (successMessage != null) { %>
            <div class="alert alert-success"><%= successMessage %></div>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <% } %>
        
        <%-- Search form --%>
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/admin/courses" method="get">
                <input type="text" name="search" placeholder="🔍 Search courses by name or code..." 
                       value="<%= searchTerm != null ? searchTerm : "" %>">
                <button type="submit" class="btn btn-primary">Search</button>
                <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                    <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-secondary">Clear</a>
                <% } %>
            </form>
        </div>
        
        <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
            <div class="filter-info">
                <p>🔎 Search results for: <strong>"<%= searchTerm %>"</strong> - Found <strong><%= courses != null ? courses.size() : 0 %></strong> course(s)</p>
            </div>
        <% } %>
        
        <%-- Course table --%>
        <div class="table-container">
            <% if (courses != null && !courses.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Course Code</th>
                            <th>Course Name</th>
                            <th>Category</th>
                            <th>Duration (hrs)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Course course : courses) { %>
                            <tr>
                                <td>#<%= course.getCourseId() %></td>
                                <td><strong><%= course.getCourseCode() %></strong></td>
                                <td><%= course.getCourseName() %></td>
                                <td><%= course.getCategory() != null ? course.getCategory() : "General" %></td>
                                <td><%= course.getDuration() %></td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/admin/courses?action=view&id=<%= course.getCourseId() %>" 
                                       class="btn btn-info btn-sm">View</a>
                                    <a href="${pageContext.request.contextPath}/admin/courses?action=edit&id=<%= course.getCourseId() %>" 
                                       class="btn btn-warning btn-sm">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/courses?action=delete&id=<%= course.getCourseId() %>" 
                                       class="btn btn-danger btn-sm" 
                                       onclick="return confirm('⚠️ Are you sure you want to delete this course? This action cannot be undone.')">Delete</a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                        <p>🔍 No courses found matching "<%= searchTerm %>"</p>
                        <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-primary">View All Courses</a>
                    <% } else { %>
                        <p>📚 No courses available. Create your first course to get started!</p>
                        <a href="${pageContext.request.contextPath}/admin/courses?action=new" class="btn btn-primary">Add New Course</a>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>