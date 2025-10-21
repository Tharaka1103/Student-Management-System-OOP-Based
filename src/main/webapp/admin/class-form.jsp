<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.ClassEntity" %>
<%@ page import="com.classmanagement.model.Course" %>
<%@ page import="com.classmanagement.model.Instructor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    ClassEntity classEntity = (ClassEntity) request.getAttribute("classEntity");
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    List<Instructor> instructors = (List<Instructor>) request.getAttribute("instructors");
    boolean isEdit = classEntity != null;
    
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= isEdit ? "Edit" : "Add" %> Class - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1><%= isEdit ? "✏️ Edit Class" : "➕ Add New Class" %></h1>
            <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-secondary">← Back to Classes</a>
        </div>
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/classes" method="post" novalidate onsubmit="return validateClassForm()">
                <input type="hidden" name="action" value="<%= isEdit ? "update" : "create" %>">
                <% if (isEdit) { %>
                    <input type="hidden" name="classId" value="<%= classEntity.getClassId() %>">
                <% } %>
                
                <div class="form-group">
                    <label for="className">Class Name <span class="required">*</span></label>
                    <input type="text" id="className" name="className" 
                           value="<%= isEdit ? classEntity.getClassName() : "" %>" 
                           placeholder="e.g., CS101 - Morning Batch">
                    <span class="error" id="classNameError"></span>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="courseId">Course <span class="required">*</span></label>
                        <select id="courseId" name="courseId">
                            <option value="">-- Select Course --</option>
                            <% if (courses != null) {
                                for (Course course : courses) { %>
                                    <option value="<%= course.getCourseId() %>" 
                                        <%= isEdit && classEntity.getCourseId() == course.getCourseId() ? "selected" : "" %>>
                                        <%= course.getCourseCode() %> - <%= course.getCourseName() %>
                                    </option>
                            <%  }
                            } %>
                        </select>
                        <span class="error" id="courseIdError"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="instructorId">Instructor <span class="required">*</span></label>
                        <select id="instructorId" name="instructorId">
                            <option value="">-- Select Instructor --</option>
                            <% if (instructors != null) {
                                for (Instructor instructor : instructors) { %>
                                    <option value="<%= instructor.getInstructorId() %>" 
                                        <%= isEdit && classEntity.getInstructorId() == instructor.getInstructorId() ? "selected" : "" %>>
                                        <%= instructor.getFullName() %>
                                    </option>
                            <%  }
                            } %>
                        </select>
                        <span class="error" id="instructorIdError"></span>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="schedule">Schedule <span class="required">*</span></label>
                        <input type="text" id="schedule" name="schedule" 
                               value="<%= isEdit ? classEntity.getSchedule() : "" %>" 
                               placeholder="e.g., Mon/Wed/Fri 10:00-12:00">
                        <span class="error" id="scheduleError"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="room">Room <span class="required">*</span></label>
                        <input type="text" id="room" name="room" 
                               value="<%= isEdit ? classEntity.getRoom() : "" %>" 
                               placeholder="e.g., Room 101">
                        <span class="error" id="roomError"></span>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="capacity">Capacity <span class="required">*</span></label>
                        <input type="number" id="capacity" name="capacity" min="1"
                               value="<%= isEdit ? classEntity.getCapacity() : "" %>" 
                               placeholder="e.g., 30">
                        <span class="error" id="capacityError"></span>
                        <small>Maximum number of students</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">Status <span class="required">*</span></label>
                        <select id="status" name="status">
                            <option value="ACTIVE" <%= isEdit && "ACTIVE".equals(classEntity.getStatus()) ? "selected" : "" %>>Active</option>
                            <option value="INACTIVE" <%= isEdit && "INACTIVE".equals(classEntity.getStatus()) ? "selected" : "" %>>Inactive</option>
                            <option value="COMPLETED" <%= isEdit && "COMPLETED".equals(classEntity.getStatus()) ? "selected" : "" %>>Completed</option>
                        </select>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="startDate">Start Date</label>
                        <input type="date" id="startDate" name="startDate" 
                               value="<%= isEdit && classEntity.getStartDate() != null ? classEntity.getStartDate().format(dateFormatter) : "" %>">
                        <small>Optional</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="endDate">End Date</label>
                        <input type="date" id="endDate" name="endDate" 
                               value="<%= isEdit && classEntity.getEndDate() != null ? classEntity.getEndDate().format(dateFormatter) : "" %>">
                        <small>Optional</small>
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <%= isEdit ? "💾 Update Class" : "➕ Create Class" %>
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/classes" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>