package com.portal.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.portal.dao.CourseDAO;
import com.portal.model.Course;

/**
 * =====================================================================
 * Controller Servlet: CourseServlet (View Courses Controller)
 * 
 * Syllabus Demonstration:
 *   - MVC Architecture (Controller Layer):
 *       * Receives user request
 *       * Coordinates with Model/DAO (CourseDAO)
 *       * Binds data to HttpServletRequest attributes
 *       * Forwards request/response to View layer (JSP / Presentation)
 *   - HTTP Request Handling:
 *       * request.getParameter() for search and filtering
 *       * request.setAttribute() for passing model data to presentation layer
 *   - Request Dispatching:
 *       * RequestDispatcher.forward(request, response)
 *   - Database access using Servlets via DAO
 * =====================================================================
 */
public class CourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.courseDAO = new CourseDAO();
        System.out.println("[CourseServlet - Lifecycle] Initialized CourseDAO.");
    }

    /**
     * Handles HTTP GET requests to list and search courses.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Read optional search and filter parameters
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");

        List<Course> courses;

        if ((keyword != null && !keyword.trim().isEmpty()) || (type != null && !type.equals("All") && !type.trim().isEmpty())) {
            // Parameterized search query via DAO
            courses = courseDAO.searchCourses(keyword, type);
            System.out.println("[CourseServlet - Search] Retrieved " + courses.size() + " courses matching query.");
        } else {
            // Standard full retrieval via DAO
            courses = courseDAO.getAllCourses();
            System.out.println("[CourseServlet - Retrieve] Retrieved all " + courses.size() + " courses.");
        }

        // Set attributes on the HttpServletRequest (MVC Pattern: Controller -> View)
        request.setAttribute("courseList", courses);
        request.setAttribute("totalCount", courses.size());
        request.setAttribute("selectedKeyword", keyword != null ? keyword : "");
        request.setAttribute("selectedType", type != null ? type : "All");

        // Forward to the presentation View layer:
        // When Stage 4 (JSP) is added, this forwards directly to courses.jsp.
        // For Stage 3, if courses.jsp is not yet present, forward safely to courses.html.
        String targetView = "/courses.jsp";
        if (getServletContext().getResource(targetView) == null) {
            targetView = "/courses.html";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(targetView);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
