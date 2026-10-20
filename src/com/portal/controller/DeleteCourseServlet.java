package com.portal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.CourseDAO;

/**
 * =====================================================================
 * Controller Servlet: DeleteCourseServlet
 * 
 * Syllabus Demonstration:
 *   - Reading primary key parameter from request
 *   - Deleting database record via CourseDAO.deleteCourse()
 *   - HTTP Redirection to updated course directory
 * =====================================================================
 */
public class DeleteCourseServlet extends HttpServlet {
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
        processDelete(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processDelete(request, response);
    }

    private void processDelete(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("facultyUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=please_login");
            return;
        }

        String courseId = request.getParameter("courseId");
        if (courseId == null || courseId.trim().isEmpty()) {
            courseId = request.getParameter("id");
        }

        if (courseId != null && !courseId.trim().isEmpty()) {
            boolean success = courseDAO.deleteCourse(courseId.trim());
            System.out.println("[DeleteCourseServlet] Delete course " + courseId + ": " + (success ? "SUCCESS" : "FAILED"));
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/courses?deleted=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/courses?error=delete_failed");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/courses?error=missing_id");
        }
    }
}
