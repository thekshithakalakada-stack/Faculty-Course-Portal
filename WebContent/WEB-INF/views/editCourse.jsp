<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ page import="com.portal.model.Course" %>
<%-- ===================================================================
     VIEW: Edit Course Form (editCourse.jsp)
     
     Syllabus Demonstration:
       1. Directive Elements:
          - <%@ page ... %>
          - <%@ include file="header.jsp" %>
       2. Action Elements & JavaBean Access:
          - <jsp:useBean id="course" class="com.portal.model.Course" scope="request" />
          - <jsp:getProperty> reading properties from request-scoped JavaBean
       3. MVC Flow:
          - EditCourseServlet fetches Course entity -> binds to request -> forwards here
          - Submits updated values to UpdateCourseServlet controller via POST
     =================================================================== --%>

<!-- Action Element: Accessing the Course JavaBean bound by EditCourseServlet -->
<jsp:useBean id="course" class="com.portal.model.Course" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Course - Faculty Portal (JSP View)</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <%@ include file="header.jsp" %>

    <main>
        <section>
            <h2>Edit Course Details (JavaBean Access Demonstration)</h2>
            <p>
                Editing Course ID: <strong><jsp:getProperty name="course" property="courseId" /></strong>.
                Properties below are populated directly from the <code>com.portal.model.Course</code> JavaBean 
                bound to request scope by <code>EditCourseServlet</code>.
            </p>

            <!-- EDIT COURSE FORM -->
            <form id="editCourseForm" action="<%= request.getContextPath() %>/updateCourse" method="post" onsubmit="return validateCourse()">
                <fieldset>
                    <legend>Modify Course Information</legend>

                    <!-- Course ID (Read-only key) -->
                    <label for="courseId">Course ID (Primary Key)</label>
                    <input type="text" id="courseId" name="courseId" 
                           value="<jsp:getProperty name="course" property="courseId" />" 
                           readonly style="background-color: #f1f5f9; cursor: not-allowed;">

                    <!-- Course Name -->
                    <label for="courseName">Course Name <span style="color: red;">*</span></label>
                    <input type="text" id="courseName" name="courseName" 
                           value="<jsp:getProperty name="course" property="courseName" />" required>

                    <!-- Faculty Name -->
                    <label for="faculty">Faculty Name <span style="color: red;">*</span></label>
                    <input type="text" id="faculty" name="faculty" 
                           value="<jsp:getProperty name="course" property="facultyName" />" required>

                    <!-- Credits -->
                    <label for="credits">Credits (1 - 6) <span style="color: red;">*</span></label>
                    <input type="number" id="credits" name="credits" min="1" max="6" 
                           value="<jsp:getProperty name="course" property="credits" />" required>
                </fieldset>

                <fieldset>
                    <legend>Classification &amp; Details</legend>

                    <!-- Course Type Dropdown -->
                    <label for="courseType">Course Delivery Mode <span style="color: red;">*</span></label>
                    <select id="courseType" name="courseType" required>
                        <option value="Theory" <%= "Theory".equals(course.getCourseType()) ? "selected" : "" %>>Theory</option>
                        <option value="Lab" <%= "Lab".equals(course.getCourseType()) ? "selected" : "" %>>Laboratory</option>
                        <option value="Theory and Lab" <%= "Theory and Lab".equals(course.getCourseType()) ? "selected" : "" %>>Theory and Lab</option>
                    </select>

                    <!-- Academic Level Radio Buttons -->
                    <label>Academic Level</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="courseLevel" value="Undergraduate" 
                                   <%= "Undergraduate".equalsIgnoreCase(course.getAcademicLevel()) ? "checked" : "" %>>
                            Undergraduate (UG)
                        </label>
                        <label>
                            <input type="radio" name="courseLevel" value="Postgraduate"
                                   <%= "Postgraduate".equalsIgnoreCase(course.getAcademicLevel()) ? "checked" : "" %>>
                            Postgraduate (PG)
                        </label>
                    </div>

                    <!-- Core Checkbox -->
                    <label>Subject Attributes</label>
                    <div class="checkbox-group">
                        <label>
                            <input type="checkbox" name="isCore" value="Core" <%= course.isCore() ? "checked" : "" %>>
                            Core Curriculum
                        </label>
                    </div>

                    <!-- Description Textarea -->
                    <label for="description">Course Description</label>
                    <textarea id="description" name="description" rows="4" required><%= course.getDescription() != null ? course.getDescription() : "" %></textarea>
                </fieldset>

                <div class="form-actions">
                    <button type="submit" class="btn">Update Course</button>
                    <a href="<%= request.getContextPath() %>/courses" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </section>
    </main>

    <footer>
        <p>Faculty Course Management Portal - Edit Course JSP View (SDG 4)</p>
    </footer>

    <!-- Client-Side JavaScript Validation -->
    <script src="<%= request.getContextPath() %>/js/script.js"></script>

</body>
</html>
