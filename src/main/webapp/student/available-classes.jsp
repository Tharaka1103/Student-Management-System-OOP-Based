<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.ClassEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<ClassEntity> classes = (List<ClassEntity>) request.getAttribute("classes");
    String successMessage = (String) session.getAttribute(Constants.SUCCESS_MESSAGE);
    String errorMessage = (String) request.getAttribute(Constants.ERROR_MESSAGE);
    session.removeAttribute(Constants.SUCCESS_MESSAGE);
    
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Classes - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Available Classes</h1>
            <a href="${pageContext.request.contextPath}/student/enrollments" class="btn btn-secondary">← Back to My Enrollments</a>
        </div>
        
        <%-- Display messages --%>
        <% if (successMessage != null) { %>
            <div class="alert alert-success"><%= successMessage %></div>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <% } %>
        
        <%-- Available classes grid --%>
        <div class="classes-grid">
            <% if (classes != null && !classes.isEmpty()) { %>
                <% for (ClassEntity classEntity : classes) { %>
                    <div class="class-card">
                        <div class="class-card-header">
                            <h3><%= classEntity.getClassName() %></h3>
                            <span class="status-badge active">Available</span>
                        </div>
                        <div class="class-card-body">
                            <p><strong>📖 Course:</strong> <%= classEntity.getCourseName() != null ? classEntity.getCourseName() : "N/A" %></p>
                            <p><strong>👨‍🏫 Instructor:</strong> <%= classEntity.getInstructorName() != null ? classEntity.getInstructorName() : "N/A" %></p>
                            <p><strong>📅 Schedule:</strong> <%= classEntity.getSchedule() %></p>
                            <p><strong>🏫 Room:</strong> <%= classEntity.getRoom() %></p>
                            <p><strong>👥 Capacity:</strong> <%= classEntity.getCapacity() %> students</p>
                            <% if (classEntity.getStartDate() != null) { %>
                                <p><strong>🗓️ Start Date:</strong> <%= classEntity.getStartDate().format(dateFormatter) %></p>
                            <% } %>
                            <% if (classEntity.getEndDate() != null) { %>
                                <p><strong>🏁 End Date:</strong> <%= classEntity.getEndDate().format(dateFormatter) %></p>
                            <% } %>
                        </div>
                        <div class="class-card-footer">
                            <form action="${pageContext.request.contextPath}/student/enrollments" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="enroll">
                                <input type="hidden" name="classId" value="<%= classEntity.getClassId() %>">
                                <input type="hidden" name="courseId" value="<%= classEntity.getCourseId() %>">
                                <button type="submit" class="btn btn-primary btn-block" 
                                        onclick="return confirm('🎓 Are you sure you want to enroll in <%= classEntity.getClassName() %>?')">
                                    Enroll Now
                                </button>
                            </form>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <div class="no-data">
                    <p>📭 No available classes at the moment.</p>
                    <p>Please check back later for new classes!</p>
                    <a href="${pageContext.request.contextPath}/student/dashboard.jsp" class="btn btn-primary">Back to Dashboard</a>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>