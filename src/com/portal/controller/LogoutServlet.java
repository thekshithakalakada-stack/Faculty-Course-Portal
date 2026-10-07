package com.portal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * =====================================================================
 * Controller Servlet: LogoutServlet
 * 
 * Syllabus Demonstration:
 *   - Session Management:
 *       * Retrieving active session: request.getSession(false)
 *       * Invalidating session: session.invalidate()
 *   - HTTP Redirection: response.sendRedirect()
 * =====================================================================
 */
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processLogout(request, response);
    }

    /**
     * Terminates the faculty session and redirects to the login screen.
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // Retrieve current active session without creating a new one
        HttpSession session = request.getSession(false);

        if (session != null) {
            String loggedUser = (String) session.getAttribute("username");
            System.out.println("[LogoutServlet - Session Invalidation] Logging out user: " + loggedUser);
            
            // Invalidate the session, releasing all bound objects
            session.invalidate();
        }

        // Redirect back to login page with logout confirmation parameter
        response.sendRedirect(request.getContextPath() + "/login.html?logout=success");
    }
}
