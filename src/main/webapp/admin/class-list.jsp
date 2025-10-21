<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.ClassEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    List<ClassEntity> classes = (List<ClassEntity>) request.getAttribute("classes");
    String successMessage = (String) session.getAttribute(Constants.SUCCESS_MESSAGE);
    String errorMessage = (String) request.getAttribute(Constants.ERROR_MESSAGE);
    session.removeAttribute(Constants.SUCCESS_MESSAGE);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Classes - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Manage Classes</h1>
            <a href="${pageContext.request.contextPath}/admin/class-form.jsp" class="btn btn-primary">+ Add New Class</a>
        </div>
        
        <%-- Display messages --%>
        <% if (successMessage != null) { %>
            <div class="alert alert-success"><%= successMessage %></div>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <% } %>
        
        <%-- Class table --%>
        <div class="table-container">
            <% if (classes != null && !classes.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Class Name</th>
                            <th>Course</th>
                            <th>Instructor</th>
                            <th>Schedule</th>
                            <th>Room</th>
                            <th>Capacity</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ClassEntity classEntity : classes) { %>
                            <tr>
                                <td>#<%= classEntity.getClassId() %></td>
                                <td><strong><%= classEntity.getClassName() %></strong></td>
                                <td><%= classEntity.getCourseName() != null ? classEntity.getCourseName() : "N/A" %></td>
                                <td><%= classEntity.getInstructorName() != null ? classEntity.getInstructorName() : "N/A" %></td>
                                <td><%= classEntity.getSchedule() %></td>
                                <td><%= classEntity.getRoom() %></td>
                                <td><%= classEntity.getCapacity() %></td>
                                <td>
                                    <span class="status-badge <%= classEntity.getStatus() != null ? classEntity.getStatus().toLowerCase() : "" %>">
                                        <%= classEntity.getStatus() != null ? classEntity.getStatus() : "N/A" %>
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/admin/classes?action=view&id=<%= classEntity.getClassId() %>" 
                                       class="btn btn-info btn-sm">View</a>
                                    <a href="${pageContext.request.contextPath}/admin/classes?action=edit&id=<%= classEntity.getClassId() %>" 
                                       class="btn btn-warning btn-sm">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/classes?action=delete&id=<%= classEntity.getClassId() %>" 
                                       class="btn btn-danger btn-sm" 
                                       onclick="return confirm('⚠️ Are you sure you want to delete this class?')">Delete</a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <p>🏫 No classes available. Create your first class to get started!</p>
                    <a href="${pageContext.request.contextPath}/admin/classes?action=new" class="btn btn-primary">Add New Class</a>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>