<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Course" %>
<%
    Course course = (Course) request.getAttribute("course");
    boolean isEdit = course != null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= isEdit ? "Edit" : "Add" %> Course - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1><%= isEdit ? "✏️ Edit Course" : "➕ Add New Course" %></h1>
            <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-secondary">← Back to Courses</a>
        </div>
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/courses" method="post" novalidate onsubmit="return validateCourseForm()">
                <input type="hidden" name="action" value="<%= isEdit ? "update" : "create" %>">
                <% if (isEdit) { %>
                    <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
                <% } %>
                
                <div class="form-group">
                    <label for="courseCode">Course Code <span class="required">*</span></label>
                    <input type="text" id="courseCode" name="courseCode" 
                           value="<%= isEdit ? course.getCourseCode() : "" %>" 
                           placeholder="e.g., CS101">
                    <span class="error" id="courseCodeError"></span>
                </div>
                
                <div class="form-group">
                    <label for="courseName">Course Name <span class="required">*</span></label>
                    <input type="text" id="courseName" name="courseName" 
                           value="<%= isEdit ? course.getCourseName() : "" %>" 
                           placeholder="e.g., Introduction to Computer Science">
                    <span class="error" id="courseNameError"></span>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="category">Category</label>
                        <input type="text" id="category" name="category" 
                               value="<%= isEdit && course.getCategory() != null ? course.getCategory() : "" %>" 
                               placeholder="e.g., Computer Science">
                        <small>Optional - helps organize courses</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="duration">Duration (hours) <span class="required">*</span></label>
                        <input type="number" id="duration" name="duration" min="1"
                               value="<%= isEdit ? course.getDuration() : "" %>" 
                               placeholder="e.g., 40">
                        <span class="error" id="durationError"></span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="description">Course Description <span class="required">*</span></label>
                    <textarea id="description" name="description" rows="5" 
                              placeholder="Enter a detailed description of the course..."><%= isEdit ? course.getDescription() : "" %></textarea>
                    <span class="error" id="descriptionError"></span>
                    <small>Minimum 10 characters</small>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <%= isEdit ? "💾 Update Course" : "➕ Create Course" %>
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>