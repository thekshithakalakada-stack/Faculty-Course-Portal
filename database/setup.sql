-- =====================================================================
-- FACULTY COURSE MANAGEMENT PORTAL
-- DATABASE SETUP SCRIPT (MySQL)
-- 
-- Syllabus Demonstration:
--   - Relational Database Management System (MySQL)
--   - Table creation with constraints (PRIMARY KEY, FOREIGN KEY, CHECK, UNIQUE)
--   - Sample Data Insertion for JDBC Testing
--   - Stored Procedures for CallableStatement demonstration
-- =====================================================================

-- Step 1: Create Database
CREATE DATABASE IF NOT EXISTS faculty_portal;
USE faculty_portal;

-- Step 2: Drop tables if they already exist (in reverse dependency order)
DROP PROCEDURE IF EXISTS sp_get_course_count_by_faculty;
DROP PROCEDURE IF EXISTS sp_get_course_details;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS faculty;

-- =====================================================================
-- TABLE 1: FACULTY
-- Stores faculty user profiles and login credentials
-- =====================================================================
CREATE TABLE faculty (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL DEFAULT 'Computer Science',
    designation VARCHAR(50) NOT NULL DEFAULT 'Assistant Professor',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =====================================================================
-- TABLE 2: COURSES
-- Stores academic course offerings mapped to faculty members
-- Demonstrates PRIMARY KEY (course_id), CHECK constraint, FOREIGN KEY
-- =====================================================================
CREATE TABLE courses (
    course_id VARCHAR(10) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    faculty_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL CHECK (credits >= 1 AND credits <= 6),
    course_type VARCHAR(50) NOT NULL,
    academic_level VARCHAR(20) DEFAULT 'Undergraduate',
    is_core TINYINT(1) DEFAULT 1,
    description TEXT,
    faculty_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_faculty FOREIGN KEY (faculty_id) 
        REFERENCES faculty(faculty_id) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =====================================================================
-- INSERT SAMPLE DATA: FACULTY
-- =====================================================================
INSERT INTO faculty (faculty_id, username, password, full_name, email, department, designation) VALUES
(1, 'admin', 'admin123', 'Administrator', 'admin@college.edu', 'Computer Science', 'Head of Department'),
(2, 'dr_rao', 'rao123', 'Dr. Rao', 'rao@college.edu', 'Computer Science', 'Professor'),
(3, 'dr_kumar', 'kumar123', 'Dr. Kumar', 'kumar@college.edu', 'Computer Science', 'Associate Professor'),
(4, 'dr_sharma', 'sharma123', 'Dr. Sharma', 'sharma@college.edu', 'Information Technology', 'Associate Professor'),
(5, 'prof_ananya', 'ananya123', 'Prof. Ananya', 'ananya@college.edu', 'Computer Science', 'Assistant Professor'),
(6, 'dr_verma', 'verma123', 'Dr. Verma', 'verma@college.edu', 'Computer Science', 'Assistant Professor');

-- =====================================================================
-- INSERT SAMPLE DATA: COURSES
-- =====================================================================
INSERT INTO courses (course_id, course_name, faculty_name, credits, course_type, academic_level, is_core, description, faculty_id) VALUES
('C101', 'Web Technologies', 'Dr. Rao', 4, 'Theory and Lab', 'Undergraduate', 1, 'HTML, CSS, JavaScript, XML, JDBC, Servlets, and JSP fundamentals for web application engineering.', 2),
('C102', 'Database Management', 'Dr. Kumar', 4, 'Theory and Lab', 'Undergraduate', 1, 'Relational models, SQL, normalization, concurrency control, transaction management, and indexing.', 3),
('C103', 'Java Programming', 'Dr. Sharma', 3, 'Theory', 'Undergraduate', 1, 'Object-oriented programming, inheritance, polymorphism, multithreading, and collections framework.', 4),
('C104', 'Computer Networks', 'Prof. Ananya', 3, 'Theory', 'Undergraduate', 1, 'OSI and TCP/IP protocol suite, routing protocols, flow control, transport layer services, and network security.', 5),
('C105', 'Operating Systems Lab', 'Dr. Verma', 2, 'Lab', 'Undergraduate', 0, 'Linux shell scripting, process scheduling, inter-process communication, and memory management algorithms.', 6),
('C106', 'Cloud Computing Architecture', 'Dr. Rao', 4, 'Theory and Lab', 'Postgraduate', 0, 'Virtualization, cloud service models (IaaS, PaaS, SaaS), distributed storage, and cloud microservices.', 2);

-- =====================================================================
-- STORED PROCEDURES (For JDBC CallableStatement Demonstration)
-- =====================================================================

-- Procedure 1: Count total courses assigned to a faculty member (IN and OUT parameters)
DELIMITER //
CREATE PROCEDURE sp_get_course_count_by_faculty(
    IN p_faculty_name VARCHAR(100),
    OUT p_total_courses INT
)
BEGIN
    SELECT COUNT(*) INTO p_total_courses
    FROM courses
    WHERE faculty_name = p_faculty_name;
END //
DELIMITER ;

-- Procedure 2: Retrieve course title and credits by Course ID (IN and OUT parameters)
DELIMITER //
CREATE PROCEDURE sp_get_course_details(
    IN p_course_id VARCHAR(10),
    OUT p_course_name VARCHAR(100),
    OUT p_credits INT
)
BEGIN
    SELECT course_name, credits INTO p_course_name, p_credits
    FROM courses
    WHERE course_id = p_course_id;
END //
DELIMITER ;

-- =====================================================================
-- VERIFICATION QUERIES
-- =====================================================================
-- SELECT * FROM faculty;
-- SELECT * FROM courses;
-- CALL sp_get_course_count_by_faculty('Dr. Rao', @total);
-- SELECT @total;
