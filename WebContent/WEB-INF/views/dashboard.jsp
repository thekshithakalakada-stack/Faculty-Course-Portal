<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ page import="com.portal.model.Faculty" %>
<%-- ===================================================================
     VIEW: Faculty Dashboard (dashboard.jsp)
     
     Syllabus Demonstration:
       1. Directive Elements:
          - <%@ page ... %> specifying contentType, pageEncoding, errorPage
          - <%@ page import="..." %>
       2. Action Elements:
          - <jsp:include page="header.jsp" /> (Dynamic inclusion)
          - <jsp:useBean> accessing existing session-scoped Faculty JavaBean
          - <jsp:getProperty> reading JavaBean properties
       3. Scripting Elements:
          - <%! ... %> Declaration element (method definition)
          - <% ... %> Scriptlet element (procedural logic in _jspService)
          - <%= ... %> Expression element (rendering values directly)
       4. JSP Implicit Objects:
          - request, response, session, application, out, config, pageContext, page
       5. MVC Architecture: View layer displaying session data prepared by LoginServlet
     =================================================================== --%>



<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        // If no active session, redirect to login
        response.sendRedirect(request.getContextPath() + "/login.html?error=session_expired");
        return;
    }
%>

<%-- ===================================================================
     3. JSP ACTION ELEMENT: useBean
     Accesses or instantiates the JavaBean within the specified scope (session)
     =================================================================== --%>
<jsp:useBean id="facultyUser" class="com.portal.model.Faculty" scope="session" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Faculty Dashboard - Faculty Course Portal</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <!-- Directive include of header component -->
    <%@ include file="header.jsp" %>

    <main>
        <!-- FACULTY PROFILE SECTION (JavaBean Properties) -->
        <section>
            <h2>Faculty Dashboard</h2>
            
            <p style="font-size: 18px; color: #1e3a5f; font-weight: bold;">
                Welcome, <%= (facultyUser.getFullName() != null && !facultyUser.getFullName().trim().isEmpty()) ? facultyUser.getFullName() : "Faculty Member" %>!
            </p>

            <table>
                <caption>Faculty Profile Details</caption>
                <tr>
                    <th style="width: 30%;">Attribute</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td><strong>Full Name:</strong></td>
                    <td>
                        <jsp:getProperty name="facultyUser" property="fullName" />
                    </td>
                </tr>
                <tr>
                    <td><strong>Username / Faculty ID:</strong></td>
                    <td><jsp:getProperty name="facultyUser" property="username" /></td>
                </tr>
                <tr>
                    <td><strong>Department:</strong></td>
                    <td><jsp:getProperty name="facultyUser" property="department" /></td>
                </tr>
                <tr>
                    <td><strong>Designation:</strong></td>
                    <td><jsp:getProperty name="facultyUser" property="designation" /></td>
                </tr>
                <tr>
                    <td><strong>Email Address:</strong></td>
                    <td><jsp:getProperty name="facultyUser" property="email" /></td>
                </tr>
            </table>

            <div style="margin-top: 20px;">
                <a href="<%= request.getContextPath() %>/courses" class="btn">View My Courses</a>
                <a href="<%= request.getContextPath() %>/addCourse" class="btn btn-secondary">+ Add New Course</a>
                <a href="<%= request.getContextPath() %>/logout" class="btn btn-delete">Sign Out</a>
            </div>
        </section>
    </main>

    <footer>
        <p>&copy; Faculty Course Management Portal</p>
    </footer>

</body>
</html>
