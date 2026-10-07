package com.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.FacultyDAO;
import com.portal.model.Faculty;

/**
 * =====================================================================
 * Controller Servlet: LoginServlet
 * 
 * Syllabus Demonstration:
 *   - Server-Side Programming with Servlets
 *   - Servlet Lifecycle:
 *       1. init(ServletConfig) - Invoked once when servlet is initialized
 *       2. service(request, response) - Dispatches requests to doGet()/doPost()
 *       3. destroy() - Invoked once when servlet is taken out of service
 *   - Initialization Parameters:
 *       * Defined in web.xml (<init-param>)
 *       * Retrieved via ServletConfig.getInitParameter()
 *   - HTTP Request and Response (HttpServletRequest, HttpServletResponse)
 *   - Reading Request Parameters: request.getParameter()
 *   - Session Management:
 *       * Creating/retrieving session: request.getSession()
 *       * Storing session attributes: session.setAttribute()
 *   - Cookie Management:
 *       * Creating non-sensitive cookie: new Cookie("lastFacultyUser", username)
 *       * Adding cookie to response: response.addCookie()
 *       * Reading cookies: request.getCookies()
 *   - Redirection: response.sendRedirect()
 *   - Database Access via DAO: FacultyDAO.authenticate()
 * =====================================================================
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FacultyDAO facultyDAO;
    private String applicationName;
    private String adminContact;

    // =================================================================
    // 1. SERVLET LIFECYCLE: init(ServletConfig)
    // =================================================================
    /**
     * Initializes the servlet. Demonstrates reading initialization parameters
     * configured in web.xml using the ServletConfig object.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.facultyDAO = new FacultyDAO();

        // Reading servlet initialization parameters from web.xml
        this.applicationName = config.getInitParameter("applicationName");
        this.adminContact = config.getInitParameter("adminContact");

        if (this.applicationName == null) {
            this.applicationName = "Faculty Course Management Portal";
        }

        System.out.println("====================================================");
        System.out.println("[LoginServlet - Lifecycle] init() executed.");
        System.out.println(" Servlet Name: " + config.getServletName());
        System.out.println(" Init Parameter (applicationName): " + this.applicationName);
        System.out.println(" Init Parameter (adminContact): " + this.adminContact);
        System.out.println("====================================================");
    }

    // =================================================================
    // 2. HTTP GET REQUEST HANDLER (doGet)
    // =================================================================
    /**
     * Handles HTTP GET requests.
     * Demonstrates: Checking existing session, reading client cookies.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Check if user is already logged in via active session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("facultyUser") != null) {
            // Already authenticated, redirect to courses portal
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }

        // Demonstrate reading client cookies from request
        Cookie[] cookies = request.getCookies();
        String rememberedUser = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("lastFacultyUser".equals(c.getName())) {
                    rememberedUser = c.getValue();
                    break;
                }
            }
        }

        if (rememberedUser != null) {
            System.out.println("[LoginServlet - Cookie] Found remembered user in cookie: " + rememberedUser);
        }

        // Redirect to static login page with remembered user if available
        if (rememberedUser != null) {
            response.sendRedirect(request.getContextPath() + "/login.html?user=" + rememberedUser);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.html");
        }
    }

    // =================================================================
    // 3. HTTP POST REQUEST HANDLER (doPost)
    // =================================================================
    /**
     * Handles HTTP POST requests for faculty authentication.
     * Demonstrates: Parameter extraction, DAO authentication, session creation,
     * cookie generation, and HTTP redirection.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Reading form parameters from HttpServletRequest
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("[LoginServlet - Request Parameters] Login attempt for username: " + username);

        // Input validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.html?error=empty_fields");
            return;
        }

        // Authenticate using DAO layer (Integration with JDBC backend)
        Faculty faculty = facultyDAO.authenticate(username.trim(), password.trim());

        if (faculty != null) {
            // =========================================================
            // SUCCESSFUL LOGIN: SESSION & COOKIE MANAGEMENT
            // =========================================================

            // 1. Session Management: Create a new HttpSession
            HttpSession session = request.getSession(true);
            session.setAttribute("facultyUser", faculty);
            session.setAttribute("username", faculty.getUsername());
            session.setAttribute("facultyName", faculty.getFullName());
            session.setAttribute("department", faculty.getDepartment());
            session.setAttribute("role", faculty.getDesignation());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes inactivity timeout

            System.out.println("[LoginServlet - Session Created] Session ID: " + session.getId());

            // 2. Cookie Management: Store non-sensitive preference (username only, NEVER password)
            Cookie userCookie = new Cookie("lastFacultyUser", faculty.getUsername());
            userCookie.setMaxAge(24 * 60 * 60); // 24 hours expiry
            userCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(userCookie);

            System.out.println("[LoginServlet - Cookie Added] Cookie 'lastFacultyUser'=" + faculty.getUsername());

            // 3. Redirection to Courses Controller
            response.sendRedirect(request.getContextPath() + "/courses");

        } else {
            // =========================================================
            // FAILED LOGIN: REDIRECT WITH ERROR INDICATION
            // =========================================================
            System.err.println("[LoginServlet] Authentication failed for user: " + username);
            response.sendRedirect(request.getContextPath() + "/login.html?error=invalid_credentials");
        }
    }

    // =================================================================
    // 4. SERVLET LIFECYCLE: destroy()
    // =================================================================
    /**
     * Cleans up resources when the servlet container destroys the servlet.
     */
    @Override
    public void destroy() {
        System.out.println("[LoginServlet - Lifecycle] destroy() executed. Releasing resources.");
        this.facultyDAO = null;
        super.destroy();
    }
}
