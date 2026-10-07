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

/**
 * =====================================================================
 * DAO Class: FacultyDAO
 * 
 * Syllabus Demonstration:
 *   - JDBC Authentication using PreparedStatement
 *   - Reading database credentials safely
 *   - java.sql.ResultSet navigation
 * =====================================================================
 */
public class FacultyDAO {

    /**
     * Authenticates a faculty member using username and password.
     * Demonstrates: Parameterized SELECT query to prevent SQL Injection.
     * 
     * @param username faculty username
     * @param password faculty password
     * @return Faculty JavaBean if credentials match, null otherwise
     */
    public Faculty authenticate(String username, String password) {
        String sql = "SELECT * FROM faculty WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Faculty faculty = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                faculty = new Faculty();
                faculty.setFacultyId(rs.getInt("faculty_id"));
                faculty.setUsername(rs.getString("username"));
                faculty.setPassword(rs.getString("password"));
                faculty.setFullName(rs.getString("full_name"));
                faculty.setEmail(rs.getString("email"));
                faculty.setDepartment(rs.getString("department"));
                faculty.setDesignation(rs.getString("designation"));
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
