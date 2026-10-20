package com.portal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.portal.model.Faculty;
import com.portal.util.DBConnection;
import com.portal.util.PasswordUtil;

/**
 * =====================================================================
 * DAO Class: FacultyDAO
 * 
 * Handles database operations for faculty profiles and authentication:
 *   - Secure authentication with salted PBKDF2 hashing
 *   - New user registration with prepared statements
 *   - Uniqueness verification for usernames and emails
 *   - Legacy password automatic migration
 * =====================================================================
 */
public class FacultyDAO {

    /**
     * Authenticates a faculty member using username and password.
     * Uses PreparedStatement to prevent SQL Injection and verifies the
     * password securely via PasswordUtil (PBKDF2WithHmacSHA256).
     * 
     * @param username faculty username
     * @param password faculty password (plain text as submitted)
     * @return Faculty JavaBean if credentials match, null otherwise
     */
    public Faculty authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String sql = "SELECT * FROM faculty WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Faculty faculty = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");

                // Verify using secure PBKDF2 with support for legacy unhashed passwords
                if (PasswordUtil.verifyPassword(password, storedPassword)) {
                    faculty = new Faculty();
                    faculty.setFacultyId(rs.getInt("faculty_id"));
                    faculty.setUsername(rs.getString("username"));
                    faculty.setPassword(storedPassword);
                    faculty.setFullName(rs.getString("full_name"));
                    faculty.setEmail(rs.getString("email"));
                    faculty.setDepartment(rs.getString("department"));
                    faculty.setDesignation(rs.getString("designation"));

                    // Transparent upgrade: if password was stored as plain text, rehash it now
                    if (PasswordUtil.needsRehash(storedPassword)) {
                        String newHash = PasswordUtil.hashPassword(password);
                        updatePasswordInternal(conn, faculty.getFacultyId(), newHash);
                        faculty.setPassword(newHash);
                        System.out.println("[FacultyDAO] Upgraded legacy plain text password to secure hash for: " + username);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Authentication error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return faculty;
    }

    /**
     * Registers a new faculty account in the database.
     * Hashes the password with PBKDF2 before storage.
     * 
     * @param faculty Faculty object containing user details
     * @return true if registration succeeded, false otherwise
     */
    public boolean registerFaculty(Faculty faculty) {
        if (faculty == null || faculty.getUsername() == null || faculty.getPassword() == null) {
            return false;
        }

        String sql = "INSERT INTO faculty (username, password, full_name, email, department, designation) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Hash the password securely before saving
            String hashedPassword = PasswordUtil.hashPassword(faculty.getPassword());

            pstmt.setString(1, faculty.getUsername().trim());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, faculty.getFullName() != null ? faculty.getFullName().trim() : "");
            pstmt.setString(4, faculty.getEmail() != null ? faculty.getEmail().trim() : "");
            pstmt.setString(5, (faculty.getDepartment() != null && !faculty.getDepartment().trim().isEmpty()) 
                    ? faculty.getDepartment().trim() : "Computer Science");
            pstmt.setString(6, (faculty.getDesignation() != null && !faculty.getDesignation().trim().isEmpty()) 
                    ? faculty.getDesignation().trim() : "Assistant Professor");

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        faculty.setFacultyId(generatedKeys.getInt(1));
                    }
                }
                faculty.setPassword(hashedPassword);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Error registering faculty: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, null);
        }

        return false;
    }

    /**
     * Checks if a username is already taken.
     * 
     * @param username username to check
     * @return true if username exists in database, false otherwise
     */
    public boolean isUsernameTaken(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM faculty WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username.trim());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Error checking username: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return false;
    }

    /**
     * Checks if an email is already registered.
     * 
     * @param email email to check
     * @return true if email exists in database, false otherwise
     */
    public boolean isEmailTaken(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM faculty WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email.trim());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Error checking email: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return false;
    }

    /**
     * Updates the password for a faculty member.
     * 
     * @param facultyId faculty ID
     * @param newHashedPassword hashed password string
     * @return true if updated, false otherwise
     */
    public boolean updatePassword(int facultyId, String newHashedPassword) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            return updatePasswordInternal(conn, facultyId, newHashedPassword);
        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Error updating password: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, null, null);
        }
    }

    private boolean updatePasswordInternal(Connection conn, int facultyId, String newHashedPassword) {
        String sql = "UPDATE faculty SET password = ? WHERE faculty_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newHashedPassword);
            pstmt.setInt(2, facultyId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Error executing password update: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all registered faculty members.
     * 
     * @return list of Faculty beans
     */
    public List<Faculty> getAllFaculty() {
        List<Faculty> list = new ArrayList<>();
        String sql = "SELECT * FROM faculty ORDER BY full_name ASC";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Faculty f = new Faculty();
                f.setFacultyId(rs.getInt("faculty_id"));
                f.setUsername(rs.getString("username"));
                f.setPassword(rs.getString("password"));
                f.setFullName(rs.getString("full_name"));
                f.setEmail(rs.getString("email"));
                f.setDepartment(rs.getString("department"));
                f.setDesignation(rs.getString("designation"));
                list.add(f);
            }

        } catch (SQLException e) {
            System.err.println("[FacultyDAO] Error in getAllFaculty: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing Statement: " + e.getMessage());
            }
        }
        if (conn != null) {
            DBConnection.closeConnection(conn);
        }
    }
}
