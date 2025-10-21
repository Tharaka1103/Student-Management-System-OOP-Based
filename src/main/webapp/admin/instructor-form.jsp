<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Instructor" %>
<%
    Instructor instructor = (Instructor) request.getAttribute("instructor");
    boolean isEdit = instructor != null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= isEdit ? "Edit" : "Add" %> Instructor - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1><%= isEdit ? "✏️ Edit Instructor" : "➕ Add New Instructor" %></h1>
            <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-secondary">← Back to Instructors</a>
        </div>
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/instructors" method="post" novalidate onsubmit="return validateInstructorForm()">
                <input type="hidden" name="action" value="<%= isEdit ? "update" : "create" %>">
                <% if (isEdit) { %>
                    <input type="hidden" name="instructorId" value="<%= instructor.getInstructorId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">First Name <span class="required">*</span></label>
                        <input type="text" id="firstName" name="firstName" 
                               value="<%= isEdit ? instructor.getFirstName() : "" %>" 
                               placeholder="e.g., John">
                        <span class="error" id="firstNameError"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="lastName">Last Name <span class="required">*</span></label>
                        <input type="text" id="lastName" name="lastName" 
                               value="<%= isEdit ? instructor.getLastName() : "" %>" 
                               placeholder="e.g., Smith">
                        <span class="error" id="lastNameError"></span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="email">Email Address <span class="required">*</span></label>
                    <input type="email" id="email" name="email" 
                           value="<%= isEdit ? instructor.getEmail() : "" %>" 
                           placeholder="e.g., john.smith@example.com">
                    <span class="error" id="emailError"></span>
                </div>
                
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" 
                           value="<%= isEdit && instructor.getPhone() != null ? instructor.getPhone() : "" %>" 
                           placeholder="1234567890">
                    <span class="error" id="phoneError"></span>
                    <small>10 digits without spaces or dashes</small>
                </div>
                
                <div class="form-group">
                    <label for="expertise">Expertise <span class="required">*</span></label>
                    <input type="text" id="expertise" name="expertise" 
                           value="<%= isEdit ? instructor.getExpertise() : "" %>" 
                           placeholder="e.g., Computer Science, Mathematics">
                    <span class="error" id="expertiseError"></span>
                </div>
                
                <div class="form-group">
                    <label for="qualification">Qualification</label>
                    <textarea id="qualification" name="qualification" rows="4" 
                              placeholder="Enter instructor's qualifications and credentials..."><%= isEdit && instructor.getQualification() != null ? instructor.getQualification() : "" %></textarea>
                    <small>Optional - educational background and certifications</small>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <%= isEdit ? "💾 Update Instructor" : "➕ Create Instructor" %>
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>