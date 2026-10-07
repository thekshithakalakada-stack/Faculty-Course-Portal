package com.portal.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.portal.model.Course;
import com.portal.util.DBConnection;

/**
 * =====================================================================
 * DAO Class: CourseDAO (Data Access Object)
 * 
 * Syllabus Demonstration:
 *   - JDBC (Java Database Connectivity) Architecture
 *   - java.sql.* package interfaces:
 *       * java.sql.Connection
 *       * java.sql.Statement (Static SQL query execution)
 *       * java.sql.PreparedStatement (Parameterized dynamic SQL execution & CRUD)
 *       * java.sql.CallableStatement (Calling database stored procedures with IN/OUT params)
 *       * java.sql.ResultSet (Navigating and extracting query results)
 *       * java.sql.ResultSetMetaData (Inspecting column names, types, and schema metadata)
 *   - CRUD operations (Create, Read, Update, Delete)
 *   - MVC Architecture (DAO Layer interacting with Database)
 * =====================================================================
 */
public class CourseDAO {

    // =================================================================
    // 1. CRUD: CREATE (PreparedStatement)
    // =================================================================
    /**
     * Inserts a new course into the database.
     * Demonstrates: java.sql.PreparedStatement for SQL injection-safe parameterized insertion.
     * 
     * @param course Course JavaBean containing course details
     * @return true if insertion succeeded, false otherwise
     */
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_id, course_name, faculty_name, credits, "
                   + "course_type, academic_level, is_core, description, faculty_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            // Binding PreparedStatement parameters by index (1-indexed)
            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            pstmt.setString(3, course.getFacultyName());
            pstmt.setInt(4, course.getCredits());
            pstmt.setString(5, course.getCourseType());
            pstmt.setString(6, course.getAcademicLevel());
            pstmt.setBoolean(7, course.isCore());
            pstmt.setString(8, course.getDescription());
            
            if (course.getFacultyId() != null) {
                pstmt.setInt(9, course.getFacultyId());
            } else {
                pstmt.setNull(9, Types.INTEGER);
            }

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("[CourseDAO - PreparedStatement] addCourse affected rows: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[CourseDAO] Error in addCourse: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    // =================================================================
    // 2. CRUD: READ ALL (PreparedStatement & ResultSet)
    // =================================================================
    /**
     * Retrieves all course records from the database.
     * Demonstrates: java.sql.PreparedStatement, java.sql.ResultSet navigation.
     * 
     * @return List of Course JavaBean objects
     */
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY course_id ASC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Navigating the ResultSet using rs.next()
            while (rs.next()) {
                Course c = new Course();
                // Reading column data using typed getter methods
                c.setCourseId(rs.getString("course_id"));
                c.setCourseName(rs.getString("course_name"));
                c.setFacultyName(rs.getString("faculty_name"));
                c.setCredits(rs.getInt("credits"));
                c.setCourseType(rs.getString("course_type"));
                c.setAcademicLevel(rs.getString("academic_level"));
                c.setCore(rs.getBoolean("is_core"));
                c.setDescription(rs.getString("description"));
                
                int facId = rs.getInt("faculty_id");
                if (!rs.wasNull()) {
                    c.setFacultyId(facId);
                }

                list.add(c);
            }
            System.out.println("[CourseDAO - ResultSet] Retrieved " + list.size() + " courses from database.");

        } catch (SQLException e) {
            System.err.println("[CourseDAO] Error in getAllCourses: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return list;
    }

    // =================================================================
    // 3. CRUD: READ BY ID (PreparedStatement & ResultSet)
    // =================================================================
    /**
     * Finds a single course by its primary key (Course ID).
     * Demonstrates: Parameterized SELECT with PreparedStatement.
     * 
     * @param courseId Course ID (e.g., "C101")
     * @return Course JavaBean if found, null otherwise
     */
    public Course getCourseById(String courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Course course = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courseId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setFacultyName(rs.getString("faculty_name"));
                course.setCredits(rs.getInt("credits"));
                course.setCourseType(rs.getString("course_type"));
                course.setAcademicLevel(rs.getString("academic_level"));
                course.setCore(rs.getBoolean("is_core"));
                course.setDescription(rs.getString("description"));

                int facId = rs.getInt("faculty_id");
                if (!rs.wasNull()) {
                    course.setFacultyId(facId);
                }
            }

        } catch (SQLException e) {
            System.err.println("[CourseDAO] Error in getCourseById: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return course;
    }

    // =================================================================
    // 4. CRUD: UPDATE (PreparedStatement)
    // =================================================================
    /**
     * Updates an existing course record.
     * Demonstrates: Parameterized UPDATE with PreparedStatement.
     * 
     * @param course Course JavaBean with updated details
     * @return true if update succeeded, false otherwise
     */
    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET course_name = ?, faculty_name = ?, credits = ?, "
                   + "course_type = ?, academic_level = ?, is_core = ?, description = ? "
                   + "WHERE course_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getFacultyName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setString(4, course.getCourseType());
            pstmt.setString(5, course.getAcademicLevel());
            pstmt.setBoolean(6, course.isCore());
            pstmt.setString(7, course.getDescription());
            pstmt.setString(8, course.getCourseId());

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("[CourseDAO - PreparedStatement] updateCourse affected rows: " + rowsUpdated);
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("[CourseDAO] Error in updateCourse: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    // =================================================================
    // 5. CRUD: DELETE (PreparedStatement)
    // =================================================================
    /**
     * Deletes a course from the database by its Course ID.
     * Demonstrates: Parameterized DELETE with PreparedStatement.
     * 
     * @param courseId Course ID to remove
     * @return true if deletion succeeded, false otherwise
     */
    public boolean deleteCourse(String courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courseId);

            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("[CourseDAO - PreparedStatement] deleteCourse affected rows: " + rowsDeleted);
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("[CourseDAO] Error in deleteCourse: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    // =================================================================
    // 6. SYLLABUS TOPIC: java.sql.Statement DEMONSTRATION
    // =================================================================
    /**
     * Retrieves the total count of registered courses.
     * Demonstrates: java.sql.Statement for executing a static, parameter-free SQL query.
     * 
     * @return integer total courses count
     */
    public int getCourseCountUsingStatement() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM courses";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            // Creating a java.sql.Statement object
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                count = rs.getInt("total");
            }
            System.out.println("[CourseDAO - Statement] Total courses count: " + count);

        } catch (SQLException e) {
            System.err.println("[CourseDAO - Statement] Error executing static query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return count;
    }

    /**
     * Retrieves a list of distinct faculty names teaching courses.
     * Demonstrates: java.sql.Statement static query.
     * 
     * @return List of faculty names
     */
    public List<String> getDistinctFacultyUsingStatement() {
        List<String> faculties = new ArrayList<>();
        String sql = "SELECT DISTINCT faculty_name FROM courses ORDER BY faculty_name ASC";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                faculties.add(rs.getString("faculty_name"));
            }

        } catch (SQLException e) {
            System.err.println("[CourseDAO - Statement] Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return faculties;
    }

    // =================================================================
    // 7. SYLLABUS TOPIC: java.sql.CallableStatement DEMONSTRATION
    // =================================================================
    /**
     * Calls MySQL stored procedure 'sp_get_course_count_by_faculty' to count courses.
     * Demonstrates: java.sql.CallableStatement with IN and OUT parameters.
     * 
     * @param facultyName Faculty name (IN parameter)
     * @return total courses assigned to this faculty (OUT parameter)
     */
    public int getCourseCountByFacultyCallable(String facultyName) {
        int totalCourses = 0;
        // CallableStatement syntax: {CALL procedure_name(?, ?)}
        String callSql = "{CALL sp_get_course_count_by_faculty(?, ?)}";

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DBConnection.getConnection();
            cstmt = conn.prepareCall(callSql);

            // 1. Setting IN parameter (1-indexed)
            cstmt.setString(1, facultyName);

            // 2. Registering OUT parameter with JDBC SQL type
            cstmt.registerOutParameter(2, Types.INTEGER);

            // 3. Executing the stored procedure call
            cstmt.execute();

            // 4. Retrieving the OUT parameter value
            totalCourses = cstmt.getInt(2);
            System.out.println("[CourseDAO - CallableStatement] Courses taught by '" + facultyName + "': " + totalCourses);

        } catch (SQLException e) {
            System.err.println("[CourseDAO - CallableStatement] Error calling stored procedure: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, cstmt, null);
        }

        return totalCourses;
    }

    /**
     * Calls MySQL stored procedure 'sp_get_course_details' to fetch course name and credits.
     * Demonstrates: java.sql.CallableStatement with multiple OUT parameters.
     * 
     * @param courseId Course ID (IN parameter)
     * @return Formatted summary string
     */
    public String getCourseSummaryCallable(String courseId) {
        String callSql = "{CALL sp_get_course_details(?, ?, ?)}";
        Connection conn = null;
        CallableStatement cstmt = null;
        String summary = "Course not found";

        try {
            conn = DBConnection.getConnection();
            cstmt = conn.prepareCall(callSql);

            // Set IN parameter
            cstmt.setString(1, courseId);

            // Register OUT parameters
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.INTEGER);

            cstmt.execute();

            String courseName = cstmt.getString(2);
            int credits = cstmt.getInt(3);

            if (courseName != null) {
                summary = "Course ID: " + courseId + " | Name: " + courseName + " | Credits: " + credits;
            }

        } catch (SQLException e) {
            System.err.println("[CourseDAO - CallableStatement] Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, cstmt, null);
        }

        return summary;
    }

    // =================================================================
    // 8. SYLLABUS TOPIC: java.sql.ResultSetMetaData DEMONSTRATION
    // =================================================================
    /**
     * Retrieves and displays table metadata (column count, names, types) for the courses table.
     * Demonstrates: java.sql.ResultSetMetaData.
     * 
     * @return Formatted metadata report string
     */
    public String getCoursesTableMetaData() {
        StringBuilder report = new StringBuilder();
        String sql = "SELECT * FROM courses LIMIT 1";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Obtaining java.sql.ResultSetMetaData from ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            report.append("====================================================\n");
            report.append("  ResultSetMetaData Report for Table: courses\n");
            report.append("  Total Columns: ").append(columnCount).append("\n");
            report.append("====================================================\n");
            report.append(String.format("%-5s %-20s %-15s %-10s\n", "No.", "Column Name", "Data Type", "Nullable"));
            report.append("----------------------------------------------------\n");

            for (int i = 1; i <= columnCount; i++) {
                String colName = rsmd.getColumnName(i);
                String colType = rsmd.getColumnTypeName(i);
                int isNullableInt = rsmd.isNullable(i);
                String isNullable = (isNullableInt == ResultSetMetaData.columnNullable) ? "YES" : "NO";

                report.append(String.format("%-5d %-20s %-15s %-10s\n", i, colName, colType, isNullable));
            }
            report.append("====================================================\n");

            System.out.println(report.toString());

        } catch (SQLException e) {
            System.err.println("[CourseDAO - ResultSetMetaData] Error reading metadata: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return report.toString();
    }

    // =================================================================
    // 9. SEARCH & FILTER (PreparedStatement)
    // =================================================================
    /**
     * Searches courses by keyword matching course ID, title, or faculty.
     * Demonstrates: Parameterized LIKE query with PreparedStatement.
     * 
     * @param keyword search term
     * @param courseType optional filter ("All", "Theory", "Lab", "Theory and Lab")
     * @return matched courses list
     */
    public List<Course> searchCourses(String keyword, String courseType) {
        List<Course> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM courses WHERE 1=1 ");

        boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());
        boolean hasType = (courseType != null && !courseType.equals("All") && !courseType.trim().isEmpty());

        if (hasKeyword) {
            sql.append("AND (LOWER(course_id) LIKE ? OR LOWER(course_name) LIKE ? OR LOWER(faculty_name) LIKE ?) ");
        }
        if (hasType) {
            sql.append("AND course_type LIKE ? ");
        }
        sql.append("ORDER BY course_id ASC");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (hasKeyword) {
                String pattern = "%" + keyword.trim().toLowerCase() + "%";
                pstmt.setString(paramIndex++, pattern);
                pstmt.setString(paramIndex++, pattern);
                pstmt.setString(paramIndex++, pattern);
            }
            if (hasType) {
                pstmt.setString(paramIndex++, "%" + courseType.trim() + "%");
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getString("course_id"));
                c.setCourseName(rs.getString("course_name"));
                c.setFacultyName(rs.getString("faculty_name"));
                c.setCredits(rs.getInt("credits"));
                c.setCourseType(rs.getString("course_type"));
                c.setAcademicLevel(rs.getString("academic_level"));
                c.setCore(rs.getBoolean("is_core"));
                c.setDescription(rs.getString("description"));
                list.add(c);
            }

        } catch (SQLException e) {
            System.err.println("[CourseDAO] Error in searchCourses: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return list;
    }

    // =================================================================
    // RESOURCE MANAGEMENT HELPER
    // =================================================================
    /**
     * Closes JDBC resources safely to prevent connection and statement leaks.
     */
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
