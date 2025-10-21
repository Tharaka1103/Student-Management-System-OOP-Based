<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    List<Instructor> instructors = (List<Instructor>) request.getAttribute("instructors");
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
    <title>Manage Instructors - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Manage Instructors</h1>
            <a href="${pageContext.request.contextPath}/admin/instructor-form.jsp" class="btn btn-primary">+ Add New Instructor</a>
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
            <form action="${pageContext.request.contextPath}/admin/instructors" method="get">
                <input type="text" name="search" placeholder="🔍 Search instructors by name or expertise..." 
                       value="<%= searchTerm != null ? searchTerm : "" %>">
                <button type="submit" class="btn btn-primary">Search</button>
                <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                    <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-secondary">Clear</a>
                <% } %>
            </form>
        </div>
        
        <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
            <div class="filter-info">
                <p>🔎 Search results for: <strong>"<%= searchTerm %>"</strong> - Found <strong><%= instructors != null ? instructors.size() : 0 %></strong> instructor(s)</p>
            </div>
        <% } %>
        
        <%-- Instructor table --%>
        <div class="table-container">
            <% if (instructors != null && !instructors.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Expertise</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Instructor instructor : instructors) { %>
                            <tr>
                                <td>#<%= instructor.getInstructorId() %></td>
                                <td><strong><%= instructor.getFullName() %></strong></td>
                                <td><%= instructor.getEmail() %></td>
                                <td><%= instructor.getPhone() != null ? instructor.getPhone() : "N/A" %></td>
                                <td><%= instructor.getExpertise() %></td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/admin/instructors?action=view&id=<%= instructor.getInstructorId() %>" 
                                       class="btn btn-info btn-sm">View</a>
                                    <a href="${pageContext.request.contextPath}/admin/instructors?action=edit&id=<%= instructor.getInstructorId() %>" 
                                       class="btn btn-warning btn-sm">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/instructors?action=delete&id=<%= instructor.getInstructorId() %>" 
                                       class="btn btn-danger btn-sm" 
                                       onclick="return confirm('⚠️ Are you sure you want to delete this instructor?')">Delete</a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <% if (searchTerm != null && !searchTerm.isEmpty()) { %>
                        <p>🔍 No instructors found matching "<%= searchTerm %>"</p>
                        <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-primary">View All Instructors</a>
                    <% } else { %>
                        <p>👨‍🏫 No instructors available. Add your first instructor to get started!</p>
                        <a href="${pageContext.request.contextPath}/admin/instructors?action=new" class="btn btn-primary">Add New Instructor</a>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>