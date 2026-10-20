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
 * Controller Servlet: EditCourseServlet
 * 
 * Syllabus Demonstration:
 *   - Reading request parameter (courseId)
 *   - Fetching entity from database via DAO
 *   - Binding model object to HttpServletRequest attribute
 *   - Forwarding to View layer (JSP / MVC)
 * =====================================================================
 */
public class EditCourseServlet extends HttpServlet {
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
            Course course = courseDAO.getCourseById(courseId.trim());

            if (course != null) {
                // Set Course model as request attribute for the View
                request.setAttribute("course", course);

                // Forward to protected JSP edit view
                String targetView = "/WEB-INF/views/editCourse.jsp";
                if (getServletContext().getResource(targetView) == null) {
                    targetView = "/addCourse.html?id=" + course.getCourseId();
                    response.sendRedirect(request.getContextPath() + targetView);
                    return;
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher(targetView);
                dispatcher.forward(request, response);
                return;
            }
        }

        // Course not found, redirect back to course list
        response.sendRedirect(request.getContextPath() + "/courses?error=course_not_found");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
