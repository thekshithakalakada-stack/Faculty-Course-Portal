<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- ===================================================================
     HEADER COMPONENT (Reusable JSP View Fragment)
     Syllabus Demonstration:
       - Directive Include (<%@ include ... %>) / Action Include (<jsp:include ... />)
       - JSP expression (<%= ... %>)
     =================================================================== -->
<header>
    <h1>Faculty Course Management Portal</h1>
    <p>Academic Course Management System (MVC JSP View)</p>
</header>

<nav>
    <a href="<%= request.getContextPath() %>/index.html">Home</a>
    <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
    <a href="<%= request.getContextPath() %>/courses">Courses</a>
    <a href="<%= request.getContextPath() %>/addCourse">Add Course</a>
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</nav>
