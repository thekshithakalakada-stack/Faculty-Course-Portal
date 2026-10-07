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

<%!
    // =================================================================
    // 1. JSP DECLARATION ELEMENT (<%! ... %>)
    // Declares methods and variables at the class level in the generated servlet
    // =================================================================
    public String getGreetingMessage(String facultyName) {
        if (facultyName == null || facultyName.trim().isEmpty()) {
            return "Welcome, Faculty Member!";
        }
        return "Welcome, " + facultyName + "!";
    }
%>

<%
    // =================================================================
    // 2. JSP SCRIPTLET ELEMENT (<% ... %>)
    // Java statements executed inside the generated _jspService() method
    // =================================================================
    String username = (String) session.getAttribute("username");
    if (username == null) {
        // If no active session, redirect to login
        response.sendRedirect(request.getContextPath() + "/login.html?error=session_expired");
        return;
    }
%>

<!-- ===================================================================
     3. JSP ACTION ELEMENT: <jsp:useBean>
     Accesses or instantiates the JavaBean within the specified scope (session)
     =================================================================== -->
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

    <!-- ===============================================================
         4. JSP ACTION ELEMENT: <jsp:include>
         Dynamically includes the header component at request execution time
         =============================================================== -->
    <jsp:include page="header.jsp" />

    <main>
        <!-- FACULTY PROFILE SECTION (JavaBean Properties) -->
        <section>
            <h2>Faculty Profile &amp; Dashboard Overview</h2>
            
            <!-- 5. JSP EXPRESSION ELEMENT (<%= ... %>) -->
            <p style="font-size: 18px; color: #1e3a5f; font-weight: bold;">
                <%= getGreetingMessage(facultyUser.getFullName()) %>
            </p>

            <table>
                <caption>Faculty Profile Details (Populated from JavaBean via &lt;jsp:getProperty&gt;)</caption>
                <tr>
                    <th style="width: 30%;">Attribute</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td><strong>Full Name:</strong></td>
                    <td>
                        <!-- Demonstrates <jsp:getProperty> reading JavaBean property -->
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

        <!-- JSP IMPLICIT OBJECTS DEMONSTRATION SECTION -->
        <section>
            <h2>JSP Implicit Objects Demonstration (Syllabus Topic)</h2>
            <p>
                The table below demonstrates all major JSP implicit objects available inside <code>_jspService()</code>:
            </p>

            <table>
                <thead>
                    <tr>
                        <th>Implicit Object</th>
                        <th>Java Type / Interface</th>
                        <th>Demonstration / Current Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><code>request</code></td>
                        <td><code>javax.servlet.http.HttpServletRequest</code></td>
                        <td>Context Path: <strong><%= request.getContextPath() %></strong> | Method: <strong><%= request.getMethod() %></strong></td>
                    </tr>
                    <tr>
                        <td><code>response</code></td>
                        <td><code>javax.servlet.http.HttpServletResponse</code></td>
                        <td>Character Encoding: <strong><%= response.getCharacterEncoding() %></strong></td>
                    </tr>
                    <tr>
                        <td><code>session</code></td>
                        <td><code>javax.servlet.http.HttpSession</code></td>
                        <td>Session ID: <code><%= session.getId() %></code> (Max Inactive: <%= session.getMaxInactiveInterval() %>s)</td>
                    </tr>
                    <tr>
                        <td><code>application</code></td>
                        <td><code>javax.servlet.ServletContext</code></td>
                        <td>Server: <strong><%= application.getServerInfo() %></strong> | Dept: <%= application.getInitParameter("institutionName") %></td>
                    </tr>
                    <tr>
                        <td><code>out</code></td>
                        <td><code>javax.servlet.jsp.JspWriter</code></td>
                        <td>
                            <%-- Demonstrates writing directly using the out implicit object --%>
                            <% out.print("Rendered dynamically via JspWriter out.print()"); %>
                        </td>
                    </tr>
                    <tr>
                        <td><code>config</code></td>
                        <td><code>javax.servlet.ServletConfig</code></td>
                        <td>Servlet Class: <%= config.getServletName() %></td>
                    </tr>
                    <tr>
                        <td><code>pageContext</code></td>
                        <td><code>javax.servlet.jsp.PageContext</code></td>
                        <td>Page Scope Active: <%= pageContext != null %></td>
                    </tr>
                    <tr>
                        <td><code>page</code></td>
                        <td><code>java.lang.Object</code> (Servlet instance)</td>
                        <td>Generated Class: <%= page.getClass().getSimpleName() %></td>
                    </tr>
                </tbody>
            </table>
        </section>
    </main>

    <footer>
        <p>Faculty Course Management Portal - JSP Presentation Layer (SDG 4)</p>
    </footer>

</body>
</html>
