<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.classmanagement.model.User" %>
<%@ page import="com.classmanagement.util.Constants" %>
<%
    User headerUser = (User) session.getAttribute(Constants.SESSION_USER);
    String currentPage = request.getRequestURI();
%>
<header class="admin-header">
    <div class="header-container">
        <div class="logo">
            <h2>📚</h2>
            <span class="logo-text">Admin Panel</span>
        </div>
        
        <button class="mobile-menu-toggle" onclick="toggleMobileMenu()" aria-label="Toggle Menu">
            ☰
        </button>
        
        <nav class="main-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/dashboard.jsp" 
                       class="<%= currentPage.contains("dashboard") ? "active" : "" %>">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/courses" 
                       class="<%= currentPage.contains("course") ? "active" : "" %>">Courses</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/instructors" 
                       class="<%= currentPage.contains("instructor") ? "active" : "" %>">Instructors</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/classes" 
                       class="<%= currentPage.contains("class") ? "active" : "" %>">Classes</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/enrollments" 
                       class="<%= currentPage.contains("enrollment") ? "active" : "" %>">Enrollments</a></li>
            </ul>
        </nav>
        
        <div class="user-menu">
            <span>👤 <%= headerUser != null ? headerUser.getFirstName() : "Admin" %></span>
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