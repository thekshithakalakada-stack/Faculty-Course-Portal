package com.portal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * =====================================================================
 * Utility Class: DBConnection
 * 
 * Syllabus Demonstration:
 *   - Introduction to JDBC (Java Database Connectivity API)
 *   - Four Types of JDBC Drivers:
 *       1. Type 1: JDBC-ODBC Bridge Driver
 *          (Converts JDBC calls into ODBC function calls; platform dependent, deprecated)
 *       2. Type 2: Native-API Driver (Part-Java, Part-Native Driver)
 *          (Converts JDBC method calls into vendor-specific native C/C++ database API calls)
 *       3. Type 3: Network Protocol Driver (All-Java Middleware Driver)
 *          (Sends JDBC calls to an intermediate application server middleware)
 *       4. Type 4: Thin Driver (Direct-to-Database Pure Java Driver)
 *          (Directly converts JDBC calls to database network protocol; e.g., MySQL Connector/J)
 *   - Establishing Database Connection using java.sql.DriverManager.getConnection()
 *   - Handling java.sql.SQLException and ClassNotFoundException
 *   - java.sql.Connection interface
 * =====================================================================
 */
public class DBConnection {

    // =================================================================
    // Database connection settings are read from system properties first and
    // environment variables second. Keep credentials out of source control.
    // =================================================================
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // Type 4 Pure Java MySQL Driver
    private static final String DB_HOST = setting("faculty.db.host", "FACULTY_DB_HOST", "localhost");
    private static final String DB_PORT = setting("faculty.db.port", "FACULTY_DB_PORT", "3306");
    private static final String DB_NAME = setting("faculty.db.name", "FACULTY_DB_NAME", "faculty_portal");
    
    // Connection URL format: jdbc:<subprotocol>://<host>:<port>/<database>
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String DB_USER = requiredSetting("faculty.db.user", "FACULTY_DB_USER");
    private static final String DB_PASSWORD = requiredSetting("faculty.db.password", "FACULTY_DB_PASSWORD");

    private static String setting(String property, String environment, String fallback) {
        String value = System.getProperty(property);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(environment);
        }
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private static String requiredSetting(String property, String environment) {
        String value = System.getProperty(property);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(environment);
        }
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing database setting. Configure system property '"
                    + property + "' or environment variable '" + environment + "'.");
        }
        return value.trim();
    }

    // Static block to register the JDBC driver class
    static {
        try {
            // Load and register the Type 4 MySQL JDBC Driver class dynamically
            Class.forName(DB_DRIVER);
            System.out.println("[JDBC DBConnection] MySQL JDBC Driver registered successfully: " + DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("[JDBC DBConnection] ERROR: MySQL JDBC Driver not found in classpath!");
            System.err.println("Ensure mysql-connector-j-*.jar is placed in classpath or WebContent/WEB-INF/lib.");
            e.printStackTrace();
        }
    }

    /**
     * Establishes and returns an active database connection.
     * Demonstrates java.sql.DriverManager and java.sql.Connection.
     * 
     * @return active java.sql.Connection instance
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Establishing Database Connection using DriverManager
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.err.println("[JDBC DBConnection] Failed to establish database connection to: " + DB_URL);
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Utility method to safely close a java.sql.Connection object
     * 
     * @param conn the Connection object to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("[JDBC DBConnection] Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("[JDBC DBConnection] Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Returns connection details for inspection during viva
     */
    public static String getConnectionInfo() {
        return "JDBC Driver: " + DB_DRIVER + " (Type 4 Thin Driver)\n" +
               "URL: " + DB_URL + "\n" +
               "Database: " + DB_NAME + "\n" +
               "User: " + DB_USER;
    }
}
