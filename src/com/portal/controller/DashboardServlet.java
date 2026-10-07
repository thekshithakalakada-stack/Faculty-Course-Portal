package com.portal.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * =====================================================================
 * Controller Servlet: DashboardServlet
 * 
 * Purpose:
 *   Gated controller for the faculty dashboard view (dashboard.jsp).
 *   Verifies active session before dispatching to WEB-INF/views/dashboard.jsp.
 * =====================================================================
 */
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Security Check: Verify user is authenticated
        if (session != null && session.getAttribute("facultyUser") != null) {
            // Forward to protected JSP view
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            // Not authenticated, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login.html?error=please_login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
