<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Enrollment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<Enrollment> enrollments = (List<Enrollment>) request.getAttribute("enrollments");
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
    <title>My Enrollments - ClassHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>My Enrollments</h1>
            <a href="${pageContext.request.contextPath}/student/enrollments?action=available" class="btn btn-primary">+ Enroll in New Class</a>
        </div>
        
        <%-- Display messages --%>
        <% if (successMessage != null) { %>
            <div class="alert alert-success"><%= successMessage %></div>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <% } %>
        
        <%-- Enrollment table --%>
        <div class="table-container">
            <% if (enrollments != null && !enrollments.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Course Name</th>
                            <th>Class Name</th>
                            <th>Enrollment Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Enrollment enrollment : enrollments) { %>
                            <tr>
                                <td>#<%= enrollment.getEnrollmentId() %></td>
                                <td><strong><%= enrollment.getCourseName() != null ? enrollment.getCourseName() : "N/A" %></strong></td>
                                <td><%= enrollment.getClassName() != null ? enrollment.getClassName() : "N/A" %></td>
                                <td><%= enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().format(dateFormatter) : "N/A" %></td>
                                <td>
                                    <span class="status-badge <%= enrollment.getStatus() != null ? enrollment.getStatus().toLowerCase() : "" %>">
                                        <%= enrollment.getStatus() != null ? enrollment.getStatus() : "N/A" %>
                                    </span>
                                </td>
                                <td class="actions">
                                    <% if ("ACTIVE".equals(enrollment.getStatus())) { %>
                                        <a href="${pageContext.request.contextPath}/student/enrollments?action=cancel&id=<%= enrollment.getEnrollmentId() %>" 
                                           class="btn btn-danger btn-sm" 
                                           onclick="return confirm('⚠️ Are you sure you want to cancel this enrollment? This action cannot be undone.')">
                                           Cancel Enrollment
                                        </a>
                                    <% } else { %>
                                        <span class="text-muted">No actions available</span>
                                    <% } %>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <p>📚 You haven't enrolled in any classes yet.</p>
                    <p>Start your learning journey by browsing available classes!</p>
                    <a href="${pageContext.request.contextPath}/student/enrollments?action=available" class="btn btn-primary">Browse Available Classes</a>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>