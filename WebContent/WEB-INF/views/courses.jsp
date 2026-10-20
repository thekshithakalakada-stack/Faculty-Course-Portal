<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="com.portal.model.Course" %>
<%-- ===================================================================
     VIEW: Registered Courses Directory (courses.jsp)
     
     Syllabus Demonstration:
       1. Directive Elements:
          - <%@ page ... %> (contentType, errorPage)
          - <%@ page import="..." %>
          - <%@ include file="header.jsp" %> (Static Translation-Time Include)
       2. Scripting Elements:
          - <%! ... %> Declaration element (Helper method for badge class formatting)
          - <% ... %> Scriptlet element (Extracting request attribute, iteration)
          - <%= ... %> Expression element (Rendering JavaBean properties)
       3. JavaBean Access:
          - Invoking getters on com.portal.model.Course instances
       4. MVC Architecture:
          - Pure View component receiving List<Course> prepared by CourseServlet
     =================================================================== --%>

<%!
    // =================================================================
    // 1. JSP DECLARATION: Helper method declared in generated servlet class
    // =================================================================
    public String getBadgeClass(String courseType) {
        if (courseType == null) return "badge";
        if (courseType.contains("Lab") && courseType.contains("Theory")) {
            return "badge badge-both";
        } else if (courseType.contains("Lab")) {
            return "badge badge-lab";
        } else {
            return "badge badge-theory";
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses - Faculty Course Portal</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <%-- Static include of header view --%>
    <%@ include file="header.jsp" %>

    <main>

        <!-- STATUS ALERTS -->
        <%
            String addedParam = request.getParameter("added");
            String updatedParam = request.getParameter("updated");
            String deletedParam = request.getParameter("deleted");
            String errorParam = request.getParameter("error");

            if ("success".equals(addedParam)) {
        %>
            <div style="background-color: #dcfce7; border: 1px solid #86efac; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                <strong>Success:</strong> Course has been successfully added to the database.
            </div>
        <%
            } else if ("success".equals(updatedParam)) {
        %>
            <div style="background-color: #dbeafe; border: 1px solid #93c5fd; color: #1e40af; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                <strong>Success:</strong> Course details have been updated successfully.
            </div>
        <%
            } else if ("success".equals(deletedParam)) {
        %>
            <div style="background-color: #fee2e2; border: 1px solid #fca5a5; color: #991b1b; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                <strong>Notice:</strong> Course record was removed from the database.
            </div>
        <%
            } else if (errorParam != null) {
        %>
            <div style="background-color: #fee2e2; border: 1px solid #fca5a5; color: #991b1b; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                <strong>Error:</strong> Action could not be completed (<%= errorParam %>).
            </div>
        <%
            }
        %>

        <section>
            <h2>Department Course Directory</h2>
            <p>
                Browse, search, and manage registered departmental courses.
            </p>
            <p>
                <a href="<%= request.getContextPath() %>/addCourse" class="btn btn-edit">+ Add New Course</a>
                <a href="<%= request.getContextPath() %>/dashboard" class="btn btn-secondary">Faculty Dashboard</a>
            </p>
        </section>

        <!-- SEARCH AND FILTER FORM (Submits to CourseServlet via GET) -->
        <section>
            <h2>Filter Courses</h2>
            <form action="<%= request.getContextPath() %>/courses" method="get" style="margin: 0; padding: 15px; max-width: 100%;">
                <div class="filter-bar" style="margin: 0;">
                    <div class="filter-group">
                        <label for="keyword">Keyword:</label>
                        <input type="text" id="keyword" name="keyword" 
                               value="<%= request.getAttribute("selectedKeyword") != null ? request.getAttribute("selectedKeyword") : "" %>" 
                               placeholder="Search ID, title, or faculty...">
                    </div>
                    <div class="filter-group">
                        <label for="type">Delivery Mode:</label>
                        <select id="type" name="type">
                            <option value="All">All Course Types</option>
                            <option value="Theory" <%= "Theory".equals(request.getAttribute("selectedType")) ? "selected" : "" %>>Theory</option>
                            <option value="Lab" <%= "Lab".equals(request.getAttribute("selectedType")) ? "selected" : "" %>>Laboratory</option>
                            <option value="Theory and Lab" <%= "Theory and Lab".equals(request.getAttribute("selectedType")) ? "selected" : "" %>>Theory and Lab</option>
                        </select>
                    </div>
                    <button type="submit" class="btn">Filter</button>
                    <a href="<%= request.getContextPath() %>/courses" class="btn btn-reset">Reset</a>
                </div>
            </form>
        </section>

        <!-- COURSES TABLE -->
        <section>
            <h2>Registered Courses Table</h2>
            <div style="overflow-x: auto;">
                <table>
                    <thead>
                        <tr>
                            <th>Course ID</th>
                            <th>Course Name</th>
                            <th>Faculty</th>
                            <th>Credits</th>
                            <th>Course Type</th>
                            <th>Level</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // =========================================================
                            // 2. SCRIPTLET: Extracting courseList from request attribute
                            // =========================================================
                            @SuppressWarnings("unchecked")
                            List<Course> courses = (List<Course>) request.getAttribute("courseList");

                            if (courses != null && !courses.isEmpty()) {
                                for (Course c : courses) {
                        %>
                            <tr>
                                <!-- 3. EXPRESSIONS: Rendering JavaBean properties via getters -->
                                <td><strong><%= c.getCourseId() %></strong></td>
                                <td><%= c.getCourseName() %></td>
                                <td><%= c.getFacultyName() %></td>
                                <td><%= c.getCredits() %></td>
                                <td>
                                    <span class="<%= getBadgeClass(c.getCourseType()) %>">
                                        <%= c.getCourseType() %>
                                    </span>
                                </td>
                                <td><%= c.getAcademicLevel() %></td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="<%= request.getContextPath() %>/editCourse?courseId=<%= c.getCourseId() %>" class="btn btn-edit">Edit</a>
                                        <a href="<%= request.getContextPath() %>/deleteCourse?courseId=<%= c.getCourseId() %>" 
                                           class="btn btn-delete" 
                                           onclick="return confirm('Are you sure you want to delete course <%= c.getCourseId() %>?');">
                                            Delete
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="7" style="text-align: center; color: #64748b; padding: 25px;">
                                    No courses found matching the criteria.
                                </td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </section>
    </main>

    <footer>
        <p>&copy; Faculty Course Management Portal</p>
    </footer>

</body>
</html>
