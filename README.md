# Faculty Course Management Portal

A college Web Technologies course project designed and implemented strictly according to the academic syllabus. The application follows clean architectural principles without any third-party frameworks (no Spring Boot, no Hibernate, no React, no Bootstrap).

---

## 1. Project Overview

The **Faculty Course Management Portal** enables college faculty members to:
* View registered departmental courses with real-time keyword search and type filtering.
* Add, update, and delete courses.
* Authenticate as faculty members.
* Persist academic course offerings in a relational MySQL database.
* Demonstrate core JDBC topics: `Connection`, `Statement`, `PreparedStatement`, `CallableStatement`, `ResultSet`, and `ResultSetMetaData`.

---

## 2. Technologies Used (Strictly Within Syllabus)

* **HTML5**: Semantic document layout, tables, lists, forms, images, iframe, audio, video, SVG, canvas graphics.
* **CSS3**: External, internal, and inline stylesheets, CSS selectors, box model, table styling, responsive design.
* **JavaScript**: Client-side scripting, ES6 variables (`let`, `const`, `var`), objects (`String`, `Array`, `Date`, `Math`, `RegExp`), event handling, error handling, form validation, dynamic table filtering.
* **Java (Standard Edition)**: Plain Java models (JavaBeans) and Data Access Objects (DAOs).
* **JDBC (`java.sql.*`)**: Database connectivity, `DriverManager`, `PreparedStatement` CRUD, `Statement` static queries, `CallableStatement` stored procedure execution, `ResultSetMetaData` schema inspection.
* **MySQL**: Relational database engine, schema design, constraints, and stored procedures.
* **Git**: Distributed version control system.

---

## 3. Project Directory Structure

```text
Faculty-Course-Portal/
├── database/
│   └── setup.sql                       # MySQL DDL & DML database setup script
├── src/
│   └── com/
│       └── portal/
│           ├── model/
│           │   ├── Course.java         # JavaBean model for Course entity
│           │   └── Faculty.java        # JavaBean model for Faculty profile
│           ├── util/
│           │   └── DBConnection.java   # Type 4 MySQL JDBC connection utility
│           ├── dao/
│           │   ├── CourseDAO.java      # CRUD operations + Statement + CallableStatement + ResultSetMetaData
│           │   └── FacultyDAO.java     # Faculty authentication DAO
│           └── test/
│               └── JDBCTest.java       # Standalone terminal runner to test & demonstrate all JDBC topics
├── WebContent/
│   ├── css/
│   │   └── style.css                   # External stylesheet demonstrating selectors & properties
│   ├── images/
│   │   └── college.jpg                 # College campus photograph for <img> tag demonstration
│   ├── js/
│   │   └── script.js                   # Client-side JavaScript logic, validation & filtering
│   ├── WEB-INF/                        # Web configuration folder (for upcoming Servlets/JSP)
│   ├── xml/                            # XML data folder (for upcoming XML/DTD/XSD/XSLT)
│   ├── index.html                      # Portal home page
│   ├── courses.html                    # Course directory with live JS search & UI action buttons
│   ├── addCourse.html                  # Course creation form with client-side validation
│   └── login.html                      # Faculty authentication form
├── README.md                           # Project documentation & execution guide
└── SYLLABUS_MAPPING.md                 # 3-column syllabus-to-code viva evaluation reference
```

---

## 4. Database Setup (MySQL)

### Step 1: Install and Start MySQL
1. Ensure MySQL Server (version 8.0 or compatible) is installed on your system.
2. Start the MySQL service:
   * **Windows Command Prompt / PowerShell:**
     ```powershell
     net start MySQL80
     ```

### Step 2: Execute the Database Script
Open a terminal in the project root directory and run the database script:
```powershell
mysql -u root -p < database/setup.sql
```
Alternatively, open `database/setup.sql` in **MySQL Workbench** or **phpMyAdmin** and execute all statements.

### Step 3: What the Script Creates
1. **Database:** `faculty_portal`
2. **Tables:**
   * `faculty` (faculty profiles, credentials, department, designation)
   * `courses` (course ID primary key, course title, faculty, credits 1–6, delivery mode, foreign key reference)
3. **Stored Procedures (for CallableStatement):**
   * `sp_get_course_count_by_faculty`: Counts courses taught by a faculty member using `IN` and `OUT` parameters.
   * `sp_get_course_details`: Retrieves course title and credits using `IN` and multiple `OUT` parameters.
4. **Sample Data:** 6 realistic faculty profiles and 6 academic courses.

---

## 5. JDBC Configuration

Open [src/com/portal/util/DBConnection.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/util/DBConnection.java) and verify your MySQL connection credentials:

```java
private static final String DB_HOST = "localhost";
private static final String DB_PORT = "3306";
private static final String DB_NAME = "faculty_portal";
private static final String DB_USER = "root";     // Your MySQL username
private static final String DB_PASSWORD = "root"; // Your MySQL password
```

---

## 6. How to Compile and Run the JDBC Layer

### Step 1: Compile All Java Classes
From the root directory of the project, run:
```powershell
# Create output directory for class files
if (!(Test-Path "bin")) { New-Item -ItemType Directory -Path "bin" }

# Compile all models, utilities, DAOs, and tests
javac -d bin src/com/portal/model/*.java src/com/portal/util/*.java src/com/portal/dao/*.java src/com/portal/test/*.java
```

### Step 2: Download MySQL Connector/J Driver (If not already in classpath)
Download `mysql-connector-j-8.x.x.jar` (official MySQL JDBC Type 4 driver) and place it in a `lib/` directory or in `WebContent/WEB-INF/lib/`.

### Step 3: Run the Standalone JDBC Test Suite
Execute the test runner using the `java` command with the driver on the classpath:
```powershell
java -cp "bin;lib/mysql-connector-j-8.0.33.jar" com.portal.test.JDBCTest
```

The test runner will execute and print terminal verification for:
1. `DriverManager.getConnection()` connection test
2. `ResultSetMetaData` schema inspection of `courses` table
3. `PreparedStatement` `INSERT` (creating course C999)
4. `PreparedStatement` `SELECT` & `ResultSet` cursor traversal
5. `PreparedStatement` `UPDATE` (updating course C999)
6. `Statement` static queries (course count, distinct faculty)
7. `CallableStatement` stored procedure invocation (`sp_get_course_count_by_faculty`)
8. `PreparedStatement` `DELETE` cleanup

---

## 7. Git Version Control Workflow

This project is tracked using Git to demonstrate syllabus requirements for version control:

```powershell
# 1. Initialize local repository
git init

# 2. Check repository status
git status

# 3. Stage modified and newly created files
git add .

# 4. Commit changes with a descriptive message
git commit -m "Implement Frontend Foundation and Stage 2 JDBC Backend"

# 5. Create and switch to main branch
git branch -M main

# 6. Add remote GitHub / GitLab repository (optional)
git remote add origin https://github.com/your-username/Faculty-Course-Portal.git

# 7. Push committed code to remote repository
git push -u origin main
```

---

## 8. Syllabus Demonstration Quick Links

For the exact file and method locations of every syllabus topic, refer to [SYLLABUS_MAPPING.md](file:///c:/Users/sivak/Faculty-Course-Portal/SYLLABUS_MAPPING.md).
