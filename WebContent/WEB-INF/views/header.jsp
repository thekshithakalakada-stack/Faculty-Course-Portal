<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- ===================================================================
     HEADER COMPONENT (Reusable JSP View Fragment)
     =================================================================== --%>
<header>
    <h1>Faculty Course Management Portal</h1>
    <p>Faculty Course Management System</p>
</header>

<nav>
    <a href="<%= request.getContextPath() %>/index.html">Home</a>
    <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
    <a href="<%= request.getContextPath() %>/courses">Courses</a>
    <a href="<%= request.getContextPath() %>/addCourse">Add Course</a>
    <a href="<%= request.getContextPath() %>/xml/courses_transformed.html" target="_blank">XML Catalog</a>
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</nav>
