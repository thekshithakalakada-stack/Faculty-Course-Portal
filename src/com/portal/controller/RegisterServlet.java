package com.portal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.FacultyDAO;
import com.portal.model.Faculty;

/**
 * =====================================================================
 * Controller Servlet: RegisterServlet
 * 
 * Handles new faculty user registration:
 *   - Form parameter extraction
 *   - Input validation (required fields, password match, username format)
 *   - Duplicate checking for username and email
 *   - Secure database insertion via FacultyDAO
 *   - Redirection to login with success indicator
 * =====================================================================
 */
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FacultyDAO facultyDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.facultyDAO = new FacultyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // If user already authenticated, redirect to dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("facultyUser") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/register.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Read form parameters
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 1. Required fields validation
        if (fullName == null || fullName.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/register.html?error=empty_fields");
            return;
        }

        fullName = fullName.trim();
        username = username.trim();
        password = password.trim();
        confirmPassword = confirmPassword.trim();

        // 2. Validate passwords match
        if (!password.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/register.html?error=password_mismatch");
            return;
        }

        // 3. Minimum password length check
        if (password.length() < 4) {
            response.sendRedirect(request.getContextPath() + "/register.html?error=password_too_short");
            return;
        }

        // 4. Validate username format (letters, numbers, underscore, 3-50 chars)
        if (!username.matches("^[a-zA-Z0-9_]{3,50}$")) {
            response.sendRedirect(request.getContextPath() + "/register.html?error=invalid_username");
            return;
        }

        // 5. Handle email fallback if omitted
        if (email == null || email.trim().isEmpty()) {
            email = username + "@college.edu";
        } else {
            email = email.trim();
        }

        if (department == null || department.trim().isEmpty()) {
            department = "Computer Science";
        } else {
            department = department.trim();
        }

        if (designation == null || designation.trim().isEmpty()) {
            designation = "Assistant Professor";
        } else {
            designation = designation.trim();
        }

        // 6. Check for duplicate username
        if (facultyDAO.isUsernameTaken(username)) {
            System.err.println("[RegisterServlet] Username already taken: " + username);
            response.sendRedirect(request.getContextPath() + "/register.html?error=username_taken");
            return;
        }

        // 7. Check for duplicate email
        if (facultyDAO.isEmailTaken(email)) {
            System.err.println("[RegisterServlet] Email already registered: " + email);
            response.sendRedirect(request.getContextPath() + "/register.html?error=email_taken");
            return;
        }

        // 8. Create model bean
        Faculty faculty = new Faculty();
        faculty.setFullName(fullName);
        faculty.setUsername(username);
        faculty.setPassword(password); // Will be hashed securely inside FacultyDAO.registerFaculty
        faculty.setEmail(email);
        faculty.setDepartment(department);
        faculty.setDesignation(designation);

        // 9. Persist to database
        boolean success = facultyDAO.registerFaculty(faculty);

        if (success) {
            System.out.println("[RegisterServlet] Successfully registered new faculty user: " + username);
            response.sendRedirect(request.getContextPath() + "/login.html?registered=success&user=" + username);
        } else {
            System.err.println("[RegisterServlet] Database registration failed for user: " + username);
            response.sendRedirect(request.getContextPath() + "/register.html?error=registration_failed");
        }
    }
}
