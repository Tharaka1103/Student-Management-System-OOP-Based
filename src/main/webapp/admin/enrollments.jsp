<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.Enrollment" %>
<%@ page import="com.classmanagement.model.ClassEntity" %>
<%@ page import="com.classmanagement.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<Enrollment> enrollments = (List<Enrollment>) request.getAttribute("enrollments");
    List<ClassEntity> classes = (List<ClassEntity>) request.getAttribute("classes");
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    String filterType = (String) request.getAttribute("filterType");
    ClassEntity selectedClass = (ClassEntity) request.getAttribute("classEntity");
    Course selectedCourse = (Course) request.getAttribute("course");
    
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    long totalEnrollments = enrollments != null ? enrollments.size() : 0;
    long activeEnrollments = enrollments != null ? enrollments.stream().filter(e -> "ACTIVE".equals(e.getStatus())).count() : 0;
    long completedEnrollments = enrollments != null ? enrollments.stream().filter(e -> "COMPLETED".equals(e.getStatus())).count() : 0;
    long cancelledEnrollments = enrollments != null ? enrollments.stream().filter(e -> "CANCELLED".equals(e.getStatus())).count() : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Enrollments - ClassHub Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstyles.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="content-container">
        <div class="page-header">
            <h1>Student Enrollments</h1>
        </div>
        
        <% if (filterType != null) { %>
            <div class="filter-info">
                <div>
                    <% if ("class".equals(filterType) && selectedClass != null) { %>
                        <p><strong>📊 Filtered by Class:</strong> <%= selectedClass.getClassName() %></p>
                    <% } else if ("course".equals(filterType) && selectedCourse != null) { %>
                        <p><strong>📊 Filtered by Course:</strong> <%= selectedCourse.getCourseName() %></p>
                    <% } %>
                </div>
                <a href="${pageContext.request.contextPath}/admin/enrollments" class="btn btn-secondary">View All Enrollments</a>
            </div>
        <% } %>
        
        <!-- Filter Section -->
        <div class="filter-section">
            <div class="filter-box">
                <h3>🔍 Filter Enrollments</h3>
                <div class="filter-options">
                    <div class="form-group">
                        <label for="filterByClass">Filter by Class</label>
                        <select id="filterByClass" onchange="filterByClass(this.value)" class="filter-select">
                            <option value="">-- Select Class --</option>
                            <% if (classes != null) {
                                for (ClassEntity classEntity : classes) { %>
                                    <option value="<%= classEntity.getClassId() %>">
                                        <%= classEntity.getClassName() %> - <%= classEntity.getCourseName() %>
                                    </option>
                            <%  }
                            } %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="filterByCourse">Filter by Course</label>
                        <select id="filterByCourse" onchange="filterByCourse(this.value)" class="filter-select">
                            <option value="">-- Select Course --</option>
                            <% if (courses != null) {
                                for (Course course : courses) { %>
                                    <option value="<%= course.getCourseId() %>">
                                        <%= course.getCourseCode() %> - <%= course.getCourseName() %>
                                    </option>
                            <%  }
                            } %>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Statistics Section -->
        <div class="stats-section">
            <div class="stat-card">
                <h4>Total Enrollments</h4>
                <p class="stat-number"><%= totalEnrollments %></p>
            </div>
            <div class="stat-card">
                <h4>Active</h4>
                <p class="stat-number" style="color: var(--success);"><%= activeEnrollments %></p>
            </div>
            <div class="stat-card">
                <h4>Completed</h4>
                <p class="stat-number" style="color: var(--info);"><%= completedEnrollments %></p>
            </div>
            <div class="stat-card">
                <h4>Cancelled</h4>
                <p class="stat-number" style="color: var(--error);"><%= cancelledEnrollments %></p>
            </div>
        </div>
        
        <!-- Enrollments Table -->
        <div class="table-container">
            <% if (enrollments != null && !enrollments.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Student Name</th>
                            <th>Course</th>
                            <th>Class</th>
                            <th>Enrollment Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Enrollment enrollment : enrollments) { %>
                            <tr>
                                <td>#<%= enrollment.getEnrollmentId() %></td>
                                <td><strong><%= enrollment.getStudentName() != null ? enrollment.getStudentName() : "N/A" %></strong></td>
                                <td><%= enrollment.getCourseName() != null ? enrollment.getCourseName() : "N/A" %></td>
                                <td><%= enrollment.getClassName() != null ? enrollment.getClassName() : "N/A" %></td>
                                <td><%= enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().format(dateFormatter) : "N/A" %></td>
                                <td>
                                    <span class="status-badge <%= enrollment.getStatus() != null ? enrollment.getStatus().toLowerCase() : "" %>">
                                        <%= enrollment.getStatus() != null ? enrollment.getStatus() : "N/A" %>
                                    </span>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <p>👥 No enrollments found.</p>
                </div>
            <% } %>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
    
    <script>
        function filterByClass(classId) {
            if (classId) {
                window.location.href = '${pageContext.request.contextPath}/admin/enrollments?action=viewByClass&classId=' + classId;
            }
        }
        
        function filterByCourse(courseId) {
            if (courseId) {
                window.location.href = '${pageContext.request.contextPath}/admin/enrollments?action=viewByCourse&courseId=' + courseId;
            }
        }
    </script>
</body>
</html>