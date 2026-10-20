package com.portal.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.CourseDAO;
import com.portal.model.Course;

/**
 * =====================================================================
 * Controller Servlet: AddCourseServlet
 * 
 * Syllabus Demonstration:
 *   - Reading Form Data from HTTP POST:
 *       * request.getParameter() for text, number, radio, checkbox, select
 *   - Server-Side Data Validation
 *   - Instantiating and Populating JavaBean (Course)
 *   - Database Insertion via DAO (CourseDAO.addCourse())
 *   - HTTP Redirection: response.sendRedirect()
 * =====================================================================
 */
public class AddCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Session authentication check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("facultyUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=please_login");
            return;
        }

        // Forward GET requests to the JSP course creation form view
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/addCourse.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Session authentication check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("facultyUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=please_login");
            return;
        }

        // 1. Reading form parameters sent from HTML/JSP form
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String facultyName = request.getParameter("faculty");
        String creditsStr = request.getParameter("credits");
        String courseType = request.getParameter("courseType");
        String academicLevel = request.getParameter("courseLevel");
        String isCoreStr = request.getParameter("isCore");
        String description = request.getParameter("description");

        System.out.println("[AddCourseServlet - Form Submission] Received courseId: " + courseId);

        // 2. Server-side Parameter Validation
        if (courseId == null || courseId.trim().isEmpty() ||
            courseName == null || courseName.trim().isEmpty() ||
            facultyName == null || facultyName.trim().isEmpty() ||
            creditsStr == null || creditsStr.trim().isEmpty() ||
            courseType == null || courseType.trim().isEmpty()) {

            System.err.println("[AddCourseServlet] Validation failed: Missing required fields.");
            response.sendRedirect(request.getContextPath() + "/addCourse.html?error=missing_fields");
            return;
        }

        // Validate Course ID format using regex (Matches client-side validation ^C[0-9]{3}$)
        if (!courseId.trim().matches("^C[0-9]{3}$")) {
            System.err.println("[AddCourseServlet] Validation failed: Invalid course ID format (" + courseId + ")");
            response.sendRedirect(request.getContextPath() + "/addCourse.html?error=invalid_id_format");
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr.trim());
            if (credits < 1 || credits > 6) {
                throw new NumberFormatException("Credits out of range");
            }
        } catch (NumberFormatException e) {
            System.err.println("[AddCourseServlet] Validation failed: Credits must be between 1 and 6.");
            response.sendRedirect(request.getContextPath() + "/addCourse.html?error=invalid_credits");
            return;
        }

        if (academicLevel == null || academicLevel.trim().isEmpty()) {
            academicLevel = "Undergraduate";
        }
        boolean isCore = (isCoreStr != null && (isCoreStr.equalsIgnoreCase("Core") || isCoreStr.equalsIgnoreCase("true") || isCoreStr.equalsIgnoreCase("on")));

        // 3. Create Course JavaBean (Model)
        Course course = new Course();
        course.setCourseId(courseId.trim().toUpperCase());
        course.setCourseName(courseName.trim());
        course.setFacultyName(facultyName.trim());
        course.setCredits(credits);
        course.setCourseType(courseType.trim());
        course.setAcademicLevel(academicLevel.trim());
        course.setCore(isCore);
        course.setDescription(description != null ? description.trim() : "");

        // 4. Call DAO to insert record into MySQL
        boolean success = courseDAO.addCourse(course);

        // 5. Redirection based on outcome
        if (success) {
            System.out.println("[AddCourseServlet] Course " + course.getCourseId() + " added successfully.");
            response.sendRedirect(request.getContextPath() + "/courses?added=success");
        } else {
            System.err.println("[AddCourseServlet] Database insertion failed for course: " + course.getCourseId());
            response.sendRedirect(request.getContextPath() + "/addCourse.html?error=database_error");
        }
    }
}
