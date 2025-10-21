<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Instructor" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Instructor instructor = (Instructor) request.getAttribute("instructor");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Details - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Instructor Details</h1>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/admin/instructors?action=edit&id=<%= instructor.getInstructorId() %>" 
                   class="btn btn-warning">✏️ Edit</a>
                <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-secondary">← Back to List</a>
            </div>
        </div>
        
        <% if (instructor != null) { %>
            <div class="view-container">
                <div class="view-card">
                    <h2>👨‍🏫 <%= instructor.getFullName() %></h2>
                    
                    <div class="detail-grid">
                        <div class="detail-item">
                            <label>Instructor ID</label>
                            <p>#<%= instructor.getInstructorId() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>First Name</label>
                            <p><%= instructor.getFirstName() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Last Name</label>
                            <p><%= instructor.getLastName() %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Email Address</label>
                            <p><a href="mailto:<%= instructor.getEmail() %>"><%= instructor.getEmail() %></a></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Phone Number</label>
                            <p><%= instructor.getPhone() != null ? instructor.getPhone() : "Not provided" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Expertise</label>
                            <p><%= instructor.getExpertise() %></p>
                        </div>
                        
                        <div class="detail-item full-width">
                            <label>Qualification</label>
                            <p><%= instructor.getQualification() != null ? instructor.getQualification() : "Not provided" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Created At</label>
                            <p><%= instructor.getCreatedAt() != null ? instructor.getCreatedAt().format(dateFormatter) : "N/A" %></p>
                        </div>
                        
                        <div class="detail-item">
                            <label>Last Updated</label>
                            <p><%= instructor.getUpdatedAt() != null ? instructor.getUpdatedAt().format(dateFormatter) : "N/A" %></p>
                        </div>
                    </div>
                    
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/admin/instructors?action=edit&id=<%= instructor.getInstructorId() %>" 
                           class="btn btn-primary">✏️ Edit Instructor</a>
                        <a href="${pageContext.request.contextPath}/admin/instructors?action=delete&id=<%= instructor.getInstructorId() %>" 
                           class="btn btn-danger" 
                           onclick="return confirm('⚠️ Are you sure you want to delete this instructor?')">🗑️ Delete Instructor</a>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="no-data">
                <p>❌ Instructor not found.</p>
                <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-primary">Back to Instructors</a>
            </div>
        <% } %>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>