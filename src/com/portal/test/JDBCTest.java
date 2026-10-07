package com.portal.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.portal.dao.CourseDAO;
import com.portal.dao.FacultyDAO;
import com.portal.model.Course;
import com.portal.model.Faculty;
import com.portal.util.DBConnection;

/**
 * =====================================================================
 * Standalone Test & Viva Demonstration: JDBCTest
 * 
 * Purpose:
 *   Demonstrates all JDBC syllabus concepts via terminal output without
 *   requiring a servlet container or browser. Ideal for college practicals
 *   and viva voce evaluation.
 * 
 * Topics Demonstrated:
 *   1. Connection establishment via DriverManager
 *   2. PreparedStatement (CRUD operations: INSERT, SELECT, UPDATE, DELETE)
 *   3. Statement (Static queries)
 *   4. CallableStatement (Stored Procedure invocation)
 *   5. ResultSet (Cursor traversal with .next())
 *   6. ResultSetMetaData (Database schema introspection)
 * =====================================================================
 */
public class JDBCTest {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("      FACULTY COURSE MANAGEMENT PORTAL - JDBC TEST SUITE");
        System.out.println("======================================================================");
        System.out.println(DBConnection.getConnectionInfo());
        System.out.println("----------------------------------------------------------------------\n");

        CourseDAO courseDAO = new CourseDAO();
        FacultyDAO facultyDAO = new FacultyDAO();

        // -------------------------------------------------------------
        // TEST 1: ESTABLISHING DATABASE CONNECTION
        // -------------------------------------------------------------
        System.out.println(">>> [1/7] Testing Database Connection...");
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println(" [SUCCESS] Connection established successfully: " + conn.getClass().getName());
        } catch (SQLException e) {
            System.err.println(" [FAILED] Could not connect to MySQL: " + e.getMessage());
            System.err.println(" Ensure MySQL server is running on port 3306 and 'database/setup.sql' has been executed.");
            return;
        }

        // -------------------------------------------------------------
        // TEST 2: ResultSetMetaData DEMONSTRATION
        // -------------------------------------------------------------
        System.out.println("\n>>> [2/7] Testing ResultSetMetaData (Inspecting 'courses' table)...");
        String metaReport = courseDAO.getCoursesTableMetaData();
        System.out.println(metaReport);

        // -------------------------------------------------------------
        // TEST 3: PreparedStatement INSERT (CREATE)
        // -------------------------------------------------------------
        System.out.println("\n>>> [3/7] Testing PreparedStatement: Adding a new test course (C999)...");
        Course testCourse = new Course(
            "C999", 
            "Distributed Systems", 
            "Dr. Rao", 
            4, 
            "Theory", 
            "Postgraduate", 
            true, 
            "Principles of distributed computing, consensus algorithms, and fault tolerance."
        );
        testCourse.setFacultyId(2);

        boolean addSuccess = courseDAO.addCourse(testCourse);
        System.out.println(" Course addition result: " + (addSuccess ? "[SUCCESS]" : "[FAILED]"));

        // -------------------------------------------------------------
        // TEST 4: PreparedStatement SELECT & ResultSet TRAVERSAL (READ)
        // -------------------------------------------------------------
        System.out.println("\n>>> [4/7] Testing PreparedStatement & ResultSet: Reading all courses...");
        List<Course> allCourses = courseDAO.getAllCourses();
        for (Course c : allCourses) {
            System.out.println("   - " + c.getCourseId() + ": " + c.getCourseName() + " (" + c.getCredits() + " Credits) | " + c.getFacultyName());
        }

        // Read specific course by ID
        Course fetched = courseDAO.getCourseById("C999");
        System.out.println(" Fetched Course C999 details: " + (fetched != null ? fetched.toString() : "Not Found"));

        // -------------------------------------------------------------
        // TEST 5: PreparedStatement UPDATE
        // -------------------------------------------------------------
        System.out.println("\n>>> [5/7] Testing PreparedStatement: Updating course C999...");
        if (fetched != null) {
            fetched.setCourseName("Advanced Distributed Systems");
            fetched.setCredits(4);
            boolean updateSuccess = courseDAO.updateCourse(fetched);
            System.out.println(" Course update result: " + (updateSuccess ? "[SUCCESS]" : "[FAILED]"));
        }

        // -------------------------------------------------------------
        // TEST 6: java.sql.Statement DEMONSTRATION (STATIC QUERIES)
        // -------------------------------------------------------------
        System.out.println("\n>>> [6/7] Testing java.sql.Statement: Static aggregation queries...");
        int totalCount = courseDAO.getCourseCountUsingStatement();
        System.out.println(" Total courses (via Statement): " + totalCount);

        List<String> faculties = courseDAO.getDistinctFacultyUsingStatement();
        System.out.println(" Distinct faculty members: " + faculties);

        // -------------------------------------------------------------
        // TEST 7: java.sql.CallableStatement DEMONSTRATION (STORED PROCEDURE)
        // -------------------------------------------------------------
        System.out.println("\n>>> [7/7] Testing java.sql.CallableStatement: Executing stored procedure...");
        int raoCourseCount = courseDAO.getCourseCountByFacultyCallable("Dr. Rao");
        System.out.println(" Stored Procedure Output for 'Dr. Rao': " + raoCourseCount + " courses");

        String summary = courseDAO.getCourseSummaryCallable("C101");
        System.out.println(" Stored Procedure Summary for 'C101': " + summary);

        // Faculty authentication test
        System.out.println("\n>>> Testing Faculty Authentication (FacultyDAO)...");
        Faculty authUser = facultyDAO.authenticate("dr_rao", "rao123");
        if (authUser != null) {
            System.out.println(" [SUCCESS] Logged in as: " + authUser.getFullName() + " (" + authUser.getDesignation() + ")");
        } else {
            System.out.println(" [FAILED] Faculty authentication failed.");
        }

        // Clean up test course C999
        System.out.println("\n>>> Cleaning up test course (C999) using PreparedStatement DELETE...");
        boolean deleteSuccess = courseDAO.deleteCourse("C999");
        System.out.println(" Cleanup deletion result: " + (deleteSuccess ? "[SUCCESS]" : "[FAILED]"));

        System.out.println("\n======================================================================");
        System.out.println("          JDBC TEST EXECUTION COMPLETED SUCCESSFULLY");
        System.out.println("======================================================================");
    }
}
