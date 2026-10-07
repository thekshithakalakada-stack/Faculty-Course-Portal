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

## 6. How to Compile and Run the Application

### Step 1: Compile All Java Classes (Models, DAOs, Servlets, and Tests)
The project includes `servlet-api.jar` and `jsp-api.jar` in `lib/` (from Apache Tomcat 9.0). Run:
```powershell
# Create build directories
if (!(Test-Path "bin")) { New-Item -ItemType Directory -Path "bin" }
if (!(Test-Path "WebContent\WEB-INF\classes")) { New-Item -ItemType Directory -Path "WebContent\WEB-INF\classes" }

# Compile all source files
javac -cp "lib\servlet-api.jar;lib\jsp-api.jar" -d bin src\com\portal\model\*.java src\com\portal\util\*.java src\com\portal\dao\*.java src\com\portal\controller\*.java src\com\portal\test\*.java

# Deploy compiled classes into WebContent/WEB-INF/classes for Tomcat
Copy-Item -Recurse -Force "bin\com" "WebContent\WEB-INF\classes\"
```

### Step 2: Standalone JDBC Test Suite Execution
Execute the test runner using the `java` command with the MySQL connector on the classpath:
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

## 7. Servlet Architecture and Web Server Deployment

### A. Web Server / Tomcat Deployment Concept
This project targets **Apache Tomcat 9.0**, which implements the syllabus-standard **Servlet 4.0** and **JSP 2.3** specifications (`javax.servlet.*` and `javax.servlet.http.*` packages).

#### Standard Deployment Structure:
```text
Tomcat webapps/FacultyCoursePortal/
├── css/style.css
├── js/script.js
├── images/college.jpg
├── index.html
├── courses.html
├── addCourse.html
├── login.html
└── WEB-INF/
    ├── web.xml                      # Deployment Descriptor
    ├── classes/                     # Compiled bytecode (com/portal/...)
    └── lib/                         # Application JARs (mysql-connector-j-*.jar)
```

#### How to Deploy to Apache Tomcat 9:
1. **Option A (Folder Deployment):**
   Copy the `WebContent` folder to your Tomcat `webapps` directory as `FacultyCoursePortal`:
   ```powershell
   Copy-Item -Recurse -Force "WebContent" "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\FacultyCoursePortal"
   ```
2. **Option B (WAR Deployment):**
   Package `WebContent` into a `.war` file and drop it into Tomcat's `webapps/` folder.
3. Ensure MySQL Connector JAR (`mysql-connector-j-*.jar`) is copied into `WebContent/WEB-INF/lib/` or Tomcat's `lib/` directory.
4. Start Apache Tomcat 9.0 and open:
   `http://localhost:8080/FacultyCoursePortal/`

### B. Servlet URL Mappings (Configured in `web.xml`)

| URL Pattern | Servlet Class | HTTP Methods | Purpose |
| :--- | :--- | :--- | :--- |
| `/login` | `LoginServlet` | GET, POST | Authenticates faculty, creates `HttpSession`, adds `lastFacultyUser` cookie, and redirects |
| `/logout` | `LogoutServlet` | GET, POST | Invalidates `HttpSession`, clears credentials, redirects to `login.html` |
| `/courses`, `/viewCourses` | `CourseServlet` | GET | Fetches course list via `CourseDAO`, sets request attributes, forwards to View |
| `/addCourse` | `AddCourseServlet` | GET, POST | Reads course form parameters, validates bounds, creates `Course` bean, calls `CourseDAO.addCourse()` |
| `/updateCourse` | `UpdateCourseServlet` | POST | Reads updated course parameters, calls `CourseDAO.updateCourse()`, redirects |
| `/deleteCourse` | `DeleteCourseServlet` | GET, POST | Reads `courseId`, calls `CourseDAO.deleteCourse()`, redirects to updated list |
| `/editCourse` | `EditCourseServlet` | GET | Reads `courseId`, retrieves course via `CourseDAO.getCourseById()`, binds to request attribute |

### C. Servlet Lifecycle Demonstration
Implemented in [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java):
1. **`init(ServletConfig config)`**: Called once when the servlet container initializes the servlet. Demonstrates reading `<init-param>` (`applicationName`, `adminContact`).
2. **`service(request, response)`**: Standard HTTP request dispatcher that evaluates request method and delegates to `doGet()` or `doPost()`.
3. **`doGet(request, response)` / `doPost(request, response)`**: Processes client request data and generates responses.
4. **`destroy()`**: Called once before the servlet instance is deallocated, releasing held resources.

### D. Session & Cookie Management
1. **Session (`HttpSession`)**:
   * Created on successful login: `HttpSession session = request.getSession(true);`
   * Binds faculty details: `session.setAttribute("facultyUser", faculty);`
   * Session timeout: configured to 30 minutes in `web.xml` and via `session.setMaxInactiveInterval(30 * 60)`.
   * Terminated on logout: `session.invalidate();`
2. **Cookies (`Cookie`)**:
   * Remembers non-sensitive preference: `new Cookie("lastFacultyUser", username)`
   * Age configured: `cookie.setMaxAge(24 * 60 * 60)` (24 hours)
   * Added to response: `response.addCookie(cookie)`
   * Read on GET request: `request.getCookies()`
   * **Security Rule:** Passwords are never stored in cookies.

### E. Database Access Flow (MVC Integration)
```text
Browser Form / Link
       ↓ (HTTP GET / POST)
Servlet Controller (e.g., LoginServlet, CourseServlet, AddCourseServlet)
       ↓ (Reads request params, validates, instantiates JavaBean Model)
DAO Layer (CourseDAO, FacultyDAO)
       ↓ (PreparedStatement / CallableStatement / Statement)
JDBC Connection (DBConnection via java.sql.DriverManager)
       ↓ (TCP / Network Protocol)
MySQL Relational Database (faculty_portal)
```

---

## 8. JSP Presentation Layer & MVC Architecture

### A. Limitations of Servlets & The Need for JSP
In pure Servlet applications, generating dynamic HTML requires cumbersome procedural output statements:
```java
PrintWriter out = response.getWriter();
out.println("<html><head><title>Courses</title></head>");
out.println("<body><h1>Course Directory</h1>");
out.println("<table><tr><td>" + course.getName() + "</td></tr></table>");
out.println("</body></html>");
```
**Key Limitations of this approach:**
1. **Poor Readability & Maintainability:** HTML is mixed inside Java strings, leading to compilation issues and escape character errors.
2. **Violates Separation of Concerns:** Business logic and presentation markup are tightly coupled.
3. **Frontend Designer Inaccessibility:** Web designers cannot style pages written inside Java classes.

**The Solution — MVC Architecture:**
* **Servlet = Controller:** Handles HTTP requests, session management, validation, and coordinates with DAOs.
* **JavaBean = Model:** Encapsulates course and faculty data transfer objects (`Course`, `Faculty`).
* **JSP = View:** HTML-centric presentation templates receiving request attributes and rendering formatted output.

### B. JSP Lifecycle
When a client requests a JSP page for the first time, Tomcat processes it through the following lifecycle stages:
```text
JSP Source File (.jsp)
       ↓
1. Translation Phase (Translated into a Java Servlet source file: _jsp.java)
       ↓
2. Compilation Phase (Compiled into bytecode: _jsp.class)
       ↓
3. Loading & Instantiation (Tomcat classloader loads the generated servlet)
       ↓
4. Initialization (Container invokes jspInit() once)
       ↓
5. Request Handling (Container invokes _jspService(request, response) for every request)
       ↓
6. Destruction (Container invokes jspDestroy() once before unloading)
```

### C. JSP Scripting Elements
The project demonstrates all three standard scripting elements:
1. **Declaration (`<%! ... %>`):**
   Declares class-level methods and variables in the generated servlet:
   ```jsp
   <%!
       public String getBadgeClass(String courseType) {
           return courseType.contains("Lab") ? "badge badge-lab" : "badge badge-theory";
       }
   %>
   ```
2. **Scriptlet (`<% ... %>`):**
   Embeds procedural Java logic executed inside the `_jspService()` method:
   ```jsp
   <%
       List<Course> courses = (List<Course>) request.getAttribute("courseList");
       for (Course c : courses) {
   %>
   ```
3. **Expression (`<%= ... %>`):**
   Evaluates a Java expression, converts it to a String, and writes it directly to the response output stream without a trailing semicolon:
   ```jsp
   <td><%= c.getCourseName() %></td>
   ```

### D. JSP Directive Elements
Directives provide global instructions to the container regarding page translation:
* **Page Directive (`<%@ page ... %>`):** Configures content type, error handling, and imports:
  ```jsp
  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
  <%@ page import="com.portal.model.Course" %>
  ```
* **Include Directive (`<%@ include ... %>`):** Merges content from an external JSP file at translation time (static include):
  ```jsp
  <%@ include file="header.jsp" %>
  ```

### E. JSP Action Elements & JavaBean Access
Actions are XML-style tags that execute dynamic request-time behavior:
* **`<jsp:include page="..." />`:** Dynamically includes content at request execution time.
* **`<jsp:useBean id="..." class="..." scope="..." />`:** Locates or instantiates a JavaBean:
  ```jsp
  <jsp:useBean id="facultyUser" class="com.portal.model.Faculty" scope="session" />
  <jsp:useBean id="course" class="com.portal.model.Course" scope="request" />
  ```
* **`<jsp:getProperty name="..." property="..." />`:** Reads a bean property by automatically calling its getter:
  ```jsp
  <jsp:getProperty name="facultyUser" property="fullName" />
  <jsp:getProperty name="course" property="courseName" />
  ```

### F. JSP Implicit Objects
Demonstrated inside [dashboard.jsp](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/WEB-INF/views/dashboard.jsp):
1. `request` (`HttpServletRequest`) - HTTP query parameters, headers, and request attributes.
2. `response` (`HttpServletResponse`) - Content type, status codes, and cookies.
3. `session` (`HttpSession`) - Tracks user authentication and profile across requests.
4. `application` (`ServletContext`) - Application-scope context attributes and web server info.
5. `out` (`JspWriter`) - Response character stream writer (`out.print()`).
6. `config` (`ServletConfig`) - Servlet configuration and init parameters.
7. `pageContext` (`PageContext`) - Unified access to all page scopes and attributes.
8. `page` (`Object`) - Reference to the current servlet instance.
9. `exception` (`Throwable`) - Captured error object available only on pages with `<%@ page isErrorPage="true" %>`.

### G. JSP Error Handling Flow
When an uncaught exception occurs during view rendering:
```text
courses.jsp / addCourse.jsp (configured with errorPage="error.jsp")
       ↓ (Throws Exception)
Tomcat redirects to error.jsp (configured with isErrorPage="true")
       ↓
error.jsp renders user-friendly recovery UI using the 'exception' implicit object
(Sensitive passwords, DB credentials, and raw stack traces are suppressed for security)
```

### H. Database Access Flow Using JSP (MVC Pattern)
```text
1. Browser requests /courses
       ↓
2. CourseServlet intercepts request (Controller)
       ↓
3. CourseDAO executes SQL query via PreparedStatement (Model/DAO)
       ↓
4. CourseDAO returns List<Course> JavaBeans
       ↓
5. CourseServlet sets request.setAttribute("courseList", courses)
       ↓
6. RequestDispatcher forwards to /WEB-INF/views/courses.jsp
       ↓
7. courses.jsp iterates the list and renders the HTML table (View)
```

---

## 9. Git Version Control Workflow

This project is tracked using Git to demonstrate syllabus requirements for version control:

```powershell
# 1. Initialize local repository
git init

# 2. Check repository status
git status

# 3. Stage modified and newly created files
git add .

# 4. Commit changes with a descriptive message
git commit -m "Implement Stage 4: JSP Views, MVC integration, and error handling"

# 5. Create and switch to main branch
git branch -M main

# 6. Add remote GitHub / GitLab repository (optional)
git remote add origin https://github.com/your-username/Faculty-Course-Portal.git

# 7. Push committed code to remote repository
git push -u origin main
```

---

## 10. Syllabus Demonstration Quick Links

For the exact file and method locations of every syllabus topic, refer to [SYLLABUS_MAPPING.md](file:///c:/Users/sivak/Faculty-Course-Portal/SYLLABUS_MAPPING.md).
