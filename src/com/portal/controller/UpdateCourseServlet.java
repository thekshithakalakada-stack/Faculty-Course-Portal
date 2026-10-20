package com.portal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.CourseDAO;
import com.portal.model.Course;

/**
 * =====================================================================
 * Controller Servlet: UpdateCourseServlet
 * 
 * Syllabus Demonstration:
 *   - Reading update parameters from HttpServletRequest
 *   - Updating database records via CourseDAO.updateCourse()
 *   - HTTP Redirection
 * =====================================================================
 */
public class UpdateCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.courseDAO = new CourseDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("facultyUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=please_login");
            return;
        }

        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String facultyName = request.getParameter("faculty");
        String creditsStr = request.getParameter("credits");
        String courseType = request.getParameter("courseType");
        String academicLevel = request.getParameter("courseLevel");
        String isCoreStr = request.getParameter("isCore");
        String description = request.getParameter("description");

        if (courseId == null || courseId.trim().isEmpty() ||
            courseName == null || courseName.trim().isEmpty() ||
            facultyName == null || facultyName.trim().isEmpty() ||
            creditsStr == null || creditsStr.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/courses?error=update_missing_fields");
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/courses?error=invalid_credits");
            return;
        }

        if (academicLevel == null || academicLevel.trim().isEmpty()) {
            academicLevel = "Undergraduate";
        }
        boolean isCore = (isCoreStr != null && (isCoreStr.equalsIgnoreCase("Core") || isCoreStr.equalsIgnoreCase("true") || isCoreStr.equalsIgnoreCase("on")));

        // Populate Course model
        Course course = new Course();
        course.setCourseId(courseId.trim());
        course.setCourseName(courseName.trim());
        course.setFacultyName(facultyName.trim());
        course.setCredits(credits);
        course.setCourseType(courseType != null ? courseType.trim() : "Theory");
        course.setAcademicLevel(academicLevel.trim());
        course.setCore(isCore);
        course.setDescription(description != null ? description.trim() : "");

        boolean success = courseDAO.updateCourse(course);

        if (success) {
            System.out.println("[UpdateCourseServlet] Course " + courseId + " updated successfully.");
            response.sendRedirect(request.getContextPath() + "/courses?updated=success");
        } else {
            System.err.println("[UpdateCourseServlet] Failed to update course: " + courseId);
            response.sendRedirect(request.getContextPath() + "/courses?error=update_failed");
        }
    }
}
