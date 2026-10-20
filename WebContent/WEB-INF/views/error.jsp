<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%-- ===================================================================
     VIEW: Error Page (error.jsp)
     
     Syllabus Demonstration:
       1. Directive Element:
          - <%@ page isErrorPage="true" %> declaring this JSP as an error handler
       2. JSP Implicit Object:
          - exception (available ONLY when isErrorPage="true")
       3. Error Handling Architecture:
          - Sanitized display without exposing database passwords or stack traces
     =================================================================== --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Application Error - Faculty Portal</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <header>
        <h1>Faculty Course Management Portal</h1>
        <p>System Notification</p>
    </header>

    <main>
        <section style="border-left: 5px solid #dc2626;">
            <h2 style="color: #dc2626;">An Application Error Has Occurred</h2>
            <p>
                The requested operation could not be completed successfully.
            </p>

            <div style="background-color: #fef2f2; border: 1px solid #fecaca; padding: 15px; border-radius: 6px; margin: 20px 0;">
                <p><strong>Status Message:</strong></p>
                <p style="color: #991b1b; font-family: monospace; font-size: 15px;">
                    <%
                        // Demonstrating the 'exception' implicit object
                        if (exception != null && exception.getMessage() != null) {
                            out.print(exception.getMessage());
                        } else {
                            out.print("The requested resource encountered an operational exception or invalid state.");
                        }
                    %>
                </p>
            </div>

            <div style="margin-top: 25px;">
                <a href="<%= request.getContextPath() %>/dashboard" class="btn">Return to Dashboard</a>
                <a href="<%= request.getContextPath() %>/courses" class="btn btn-secondary">View Courses</a>
                <a href="<%= request.getContextPath() %>/index.html" class="btn btn-reset">Portal Home</a>
            </div>
        </section>
    </main>

    <footer>
        <p>&copy; Faculty Course Management Portal</p>
    </footer>

</body>
</html>
