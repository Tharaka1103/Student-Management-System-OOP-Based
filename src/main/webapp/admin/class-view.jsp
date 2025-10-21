<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.ClassEntity" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    ClassEntity classEntity = (ClassEntity) request.getAttribute("classEntity");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Class Details - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Class Details</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/admin/classes?action=edit&id=<%= classEntity.getClassId() %>" 
                   class="btn btn-warning">✏️ Edit</a>
                <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-secondary">← Back to List</a>
            </div>
        </div>
        
        <% if (classEntity != null) { %>
            <div class="view-container">
                <div class="view-card">
                    <h2>🏫 <%= classEntity.getClassName() %></h2>
                    
                    <div class="detail-grid">
                        <div class="detail-item">
                            <label>Class ID</label>
                            <p>#<%= classEntity.getClassId() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Course</label>
                            <p><%= classEntity.getCourseName() != null ? classEntity.getCourseName() : "N/A" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Instructor</label>
                            <p><%= classEntity.getInstructorName() != null ? classEntity.getInstructorName() : "N/A" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Schedule</label>
                            <p><%= classEntity.getSchedule() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Room</label>
                            <p><%= classEntity.getRoom() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Capacity</label>
                            <p><%= classEntity.getCapacity() %> students</p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Status</label>
                            <p>
                                <span class="status-badge <%= classEntity.getStatus() != null ? classEntity.getStatus().toLowerCase() : "" %>">
                                    <%= classEntity.getStatus() != null ? classEntity.getStatus() : "N/A" %>
                                </span>
                            </p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Start Date</label>
                            <p><%= classEntity.getStartDate() != null ? classEntity.getStartDate().format(dateFormatter) : "Not set" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>End Date</label>
                            <p><%= classEntity.getEndDate() != null ? classEntity.getEndDate().format(dateFormatter) : "Not set" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Created At</label>
                            <p><%= classEntity.getCreatedAt() != null ? classEntity.getCreatedAt().format(dateTimeFormatter) : "N/A" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Last Updated</label>
                            <p><%= classEntity.getUpdatedAt() != null ? classEntity.getUpdatedAt().format(dateTimeFormatter) : "N/A" %></p>
                        </div>
                    </div>
                    
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/admin/classes?action=edit&id=<%= classEntity.getClassId() %>" 
                           class="btn btn-primary">✏️ Edit Class</a>
                        <a href="${pageContext.request.contextPath}/admin/enrollments?action=viewByClass&classId=<%= classEntity.getClassId() %>" 
                           class="btn btn-info">👥 View Enrollments</a>
                        <a href="${pageContext.request.contextPath}/admin/classes?action=delete&id=<%= classEntity.getClassId() %>" 
                           class="btn btn-danger" 
                           onclick="return confirm('⚠️ Are you sure you want to delete this class?')">🗑️ Delete Class</a>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="no-data">
                <p>❌ Class not found.</p>
                <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-primary">Back to Classes</a>
            </div>
        <% } %>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>