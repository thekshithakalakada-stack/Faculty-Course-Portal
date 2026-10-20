<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%-- ===================================================================
     VIEW: Add Course Form (addCourse.jsp)
     
     Syllabus Demonstration:
       1. Directive Elements:
          - <%@ page ... %> (contentType, errorPage)
          - <%@ include file="header.jsp" %>
       2. Action Elements:
          - <jsp:useBean> to instantiate default Course JavaBean
       3. MVC Flow:
          - Submits form data via HTTP POST to AddCourseServlet controller
     =================================================================== --%>

<!-- Action Element: Instantiate default Course JavaBean -->
<jsp:useBean id="newCourse" class="com.portal.model.Course" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Course - Faculty Course Management Portal</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <!-- Directive Element: Static Include -->
    <%@ include file="header.jsp" %>

    <main>
        <section>
            <h2>Add New Course</h2>
            <p>
                Enter the details of the new course offering below.
            </p>

            <!-- Error Banner -->
            <%
                String errorParam = request.getParameter("error");
                if (errorParam != null) {
            %>
                <div style="background-color: #fee2e2; border: 1px solid #fca5a5; color: #991b1b; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                    <strong>Validation Error:</strong> <%= errorParam %>
                </div>
            <%
                }
            %>

            <!-- ADD COURSE FORM -->
            <form id="courseForm" action="<%= request.getContextPath() %>/addCourse" method="post" onsubmit="return validateCourse()">
                <fieldset>
                    <legend>Basic Course Details</legend>

                    <label for="courseId">Course ID <span style="color: red;">*</span></label>
                    <input type="text" id="courseId" name="courseId" placeholder="Example: C101" required>

                    <label for="courseName">Course Name <span style="color: red;">*</span></label>
                    <input type="text" id="courseName" name="courseName" placeholder="Enter course title" required>

                    <label for="faculty">Faculty Name <span style="color: red;">*</span></label>
                    <input type="text" id="faculty" name="faculty" 
                           value="<%= session.getAttribute("facultyName") != null ? session.getAttribute("facultyName") : "" %>" 
                           placeholder="Enter faculty in charge" required>

                    <label for="credits">Credits (1 - 6) <span style="color: red;">*</span></label>
                    <input type="number" id="credits" name="credits" min="1" max="6" placeholder="1 - 6" required>
                </fieldset>

                <fieldset>
                    <legend>Course Delivery &amp; Attributes</legend>

                    <label for="courseType">Course Delivery Mode <span style="color: red;">*</span></label>
                    <select id="courseType" name="courseType" required>
                        <option value="">-- Select Course Type --</option>
                        <option value="Theory">Theory</option>
                        <option value="Lab">Laboratory</option>
                        <option value="Theory and Lab">Theory and Lab</option>
                    </select>

                    <label>Academic Level</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="courseLevel" value="Undergraduate" checked>
                            Undergraduate (UG)
                        </label>
                        <label>
                            <input type="radio" name="courseLevel" value="Postgraduate">
                            Postgraduate (PG)
                        </label>
                    </div>

                    <label>Subject Attributes</label>
                    <div class="checkbox-group">
                        <label>
                            <input type="checkbox" name="isCore" value="Core" checked>
                            Core Curriculum
                        </label>
                    </div>

                    <label for="description">Course Syllabus Description</label>
                    <textarea id="description" name="description" rows="4" placeholder="Enter course syllabus summary..." required></textarea>
                </fieldset>

                <div class="form-actions">
                    <button type="submit" class="btn">Save Course</button>
                    <button type="reset" class="btn btn-reset">Reset</button>
                    <a href="<%= request.getContextPath() %>/courses" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </section>
    </main>

    <footer>
        <p>&copy; Faculty Course Management Portal</p>
    </footer>

    <!-- Client-Side JavaScript Validation -->
    <script src="<%= request.getContextPath() %>/js/script.js"></script>

</body>
</html>
