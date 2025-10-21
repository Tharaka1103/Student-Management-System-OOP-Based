<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.User" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    User headerUser = (User) session.getAttribute(Constants.SESSION_USER);
    String currentPage = request.getRequestURI();
%>
<header class="student-header">
    <div class="header-container">
        <div class="logo">
            <h2>📚</h2>
            <span class="logo-text">ClassHub</span>
        </div>
        
        <button class="mobile-menu-toggle" onclick="toggleMobileMenu()" aria-label="Toggle Menu">
            ☰
        </button>
        
        <nav class="main-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/student/dashboard.jsp" 
                       class="<%= currentPage.contains("dashboard") ? "active" : "" %>">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/student/enrollments" 
                       class="<%= currentPage.contains("enrollments") ? "active" : "" %>">My Enrollments</a></li>
                <li><a href="${pageContext.request.contextPath}/student/courses" 
                       class="<%= currentPage.contains("courses") ? "active" : "" %>">Courses</a></li>
                <li><a href="${pageContext.request.contextPath}/student/profile" 
                       class="<%= currentPage.contains("profile") ? "active" : "" %>">Profile</a></li>
            </ul>
        </nav>
        
        <div class="user-menu">
            <span>👤 <%= headerUser != null ? headerUser.getFirstName() : "Student" %></span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-logout">Logout</a>
        </div>
    </div>
</header>

<script>
function toggleMobileMenu() {
    const nav = document.querySelector('.main-nav');
    if (nav) {
        nav.classList.toggle('active');
    }
}
</script>