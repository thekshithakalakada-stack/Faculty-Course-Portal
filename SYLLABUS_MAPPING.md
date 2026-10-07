# Syllabus to Code Mapping

This document provides a comprehensive mapping between the **College Web Technologies Course Syllabus** and the exact files, classes, and methods in this repository. Use this reference during practical evaluations and viva voce examinations to prove where each topic is implemented.

---

## 1. HTML and CSS Syllabus Mapping

| Syllabus Topic | File / Resource | Implementation & Demonstration Details |
| :--- | :--- | :--- |
| **Introduction to HTML** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | HTML5 doctype declaration (`<!DOCTYPE html>`), `<html lang="en">`, `<head>`, `<body>`, semantic document structure. |
| **Common HTML tags** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | Headings (`<h1>`–`<h3>`), paragraphs (`<p>`), emphasis (`<strong>`, `<em>`), `<header>`, `<nav>`, `<main>`, `<section>`, `<footer>`. |
| **Lists** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | **Unordered List** (`<ul>`), **Ordered List** (`<ol>`), and **Definition List** (`<dl>`, `<dt>`, `<dd>`) for academic terminology. |
| **Tables** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html), [WebContent/courses.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/courses.html) | `<table>`, `<caption>`, `<thead>`, `<tbody>`, `<tr>`, `<th>`, `<td>` with alternating row background colors and hover effects. |
| **Images** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | `<img>` tag displaying [WebContent/images/college.jpg](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/images/college.jpg) with `alt`, `width`, and responsive styling. |
| **Links** | All HTML pages | Relative hyperlinks (`<a href="...">`) for navigation, action links, and back-to-home anchors. |
| **Frames / iframe** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | `<iframe src="courses.html">` embedding the course preview within the home page. |
| **Forms** | [WebContent/addCourse.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/addCourse.html), [WebContent/login.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/login.html) | `<form>`, `<fieldset>`, `<legend>`, text inputs, number inputs, password inputs, radio buttons (`courseLevel`), checkboxes (`isCore`), `<select>` dropdown, `<textarea>`, submit and reset buttons. |
| **Introduction to CSS** | [WebContent/css/style.css](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/css/style.css) | Core CSS styling rules, box-model resets, responsive design rules. |
| **Types of Style Sheets** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | 1. **External:** `<link rel="stylesheet" href="css/style.css">`<br>2. **Internal:** `<style>.internal-style { ... }</style>`<br>3. **Inline:** `<p style="color: #065f46; ...">` |
| **CSS Selectors** | [WebContent/css/style.css](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/css/style.css) | Element selectors (`body`, `p`, `table`), Class selectors (`.btn`, `.btn-edit`), ID selectors (`#coursesTable`), Grouping (`h1, h2, h3`), Descendant (`nav a`), Pseudo-classes (`:hover`, `:focus`, `:nth-child`). |
| **CSS properties: Text, Background, Border, Margin, Tables** | [WebContent/css/style.css](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/css/style.css) | **Text:** `color`, `font-family`, `font-size`, `line-height`<br>**Background:** `background-color`<br>**Border:** `border`, `border-radius`, `border-collapse`<br>**Margin/Padding:** `margin`, `padding`<br>**Tables:** `border-collapse`, `width`, zebra striping. |
| **HTML Media** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | `<video controls>` and `<audio controls>` with nested `<source>` tags and fallback text. |
| **HTML Graphics** | [WebContent/index.html](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/index.html) | **Canvas:** `<canvas id="courseCanvas">` rendered via 2D Context in JS.<br>**SVG:** `<svg>` element with `<circle>` and `<rect>`. |

---

## 2. JavaScript Syllabus Mapping

| Syllabus Topic | File / Method | Implementation & Demonstration Details |
| :--- | :--- | :--- |
| **Client-Side Scripting** | [WebContent/js/script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | Executing dynamic logic, DOM manipulation, form validation, and canvas drawing in browser runtime. |
| **Variables** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js#L11-L15) | Demonstrating block-scoped `let`, constant `const`, and function-scoped `var`. |
| **Operators** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | **Arithmetic:** `+`, `-`, `*`, `/`<br>**Comparison:** `===`, `!==`, `<`, `>=`<br>**Logical:** `&&`, `\|\|`, `!` in `calculateCredits()` and `filterCoursesTable()`. |
| **Control Statements** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `if-else if-else` and `switch-case` in `checkCourseType()`; `for` loops in array traversals and DOM row iteration. |
| **Functions** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | Parameterized functions returning values (`greetFaculty()`, `validateCourseId()`, `filterCoursesTable()`). |
| **String Object** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `demonstrateString()` using `length`, `toUpperCase()`, `toLowerCase()`, `indexOf()`, `trim()`. |
| **Array Object** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `demonstrateArray()` iterating array elements with `.length` and indexing. |
| **Date Object** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `displayDate()` using `new Date()` and `toLocaleDateString()` to display the current academic date. |
| **Math Object** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `demonstrateMath()` using `Math.random()`, `Math.floor()`, `Math.round()`. |
| **RegExp Object** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `validateCourseId()` using regular expression `/^C[0-9]{3}$/` to validate course identifiers (e.g., C101). |
| **Event Handling** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `window.addEventListener("load")`, text input `input` event listener, table search `oninput`, select `onchange`, form `onsubmit`. |
| **Error Handling** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `testErrorHandling()` using `try`, `catch`, `throw new Error()` for error recovery. |
| **Validation** | [script.js](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/js/script.js) | `validateCourse()` checking required text, RegExp format for course ID, and credit bounds (1–6). |

---

## 3. JDBC Syllabus Mapping

| Syllabus Topic | File / Class / Method | Implementation & Demonstration Details |
| :--- | :--- | :--- |
| **Introduction to JDBC** | [DBConnection.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/util/DBConnection.java), [CourseDAO.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java) | Overview and architecture of Java Database Connectivity bridging Java models with MySQL relational database. |
| **Types of JDBC Drivers** | [DBConnection.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/util/DBConnection.java) | Detailed code documentation explaining all 4 JDBC driver types (Type 1: ODBC Bridge, Type 2: Native-API, Type 3: Network Protocol, Type 4: Thin Direct Driver). Demonstrates loading MySQL Type 4 driver `com.mysql.cj.jdbc.Driver` via `Class.forName()`. |
| **Establishing Database Connection** | [DBConnection.java:getConnection()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/util/DBConnection.java) | Establishes live database connection using `DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)` with configurable connection parameters. |
| **java.sql.* Package** | Across all DAO & Util classes | Complete utilization of standard JDBC interfaces: `java.sql.Connection`, `java.sql.Statement`, `java.sql.PreparedStatement`, `java.sql.CallableStatement`, `java.sql.ResultSet`, `java.sql.ResultSetMetaData`, `java.sql.Types`, `java.sql.SQLException`. |
| **Statement** | [CourseDAO.java:getCourseCountUsingStatement()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java), [CourseDAO.java:getDistinctFacultyUsingStatement()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java) | Demonstrates creating a `java.sql.Statement` (`conn.createStatement()`) to execute static SQL queries without parameters (`stmt.executeQuery(...)`). |
| **PreparedStatement** | [CourseDAO.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java):<br>• `addCourse()`<br>• `getAllCourses()`<br>• `getCourseById()`<br>• `updateCourse()`<br>• `deleteCourse()`<br>• `searchCourses()` | Demonstrates pre-compiled, parameterized SQL queries with `?` placeholders. Binds parameters using `setString()`, `setInt()`, `setBoolean()`, `setNull()`, protecting against SQL Injection across all CRUD operations. |
| **CallableStatement** | [CourseDAO.java:getCourseCountByFacultyCallable()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java), [CourseDAO.java:getCourseSummaryCallable()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java) | Demonstrates invoking MySQL stored procedures (`sp_get_course_count_by_faculty`, `sp_get_course_details`) using `conn.prepareCall("{CALL procedure(?, ?)}")`, setting `IN` parameters and registering/extracting `OUT` parameters using `registerOutParameter()`. |
| **ResultSet** | [CourseDAO.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java), [FacultyDAO.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/FacultyDAO.java) | Traversing query result sets using `rs.next()`, extracting column values using typed getters (`getString()`, `getInt()`, `getBoolean()`), handling null foreign keys via `rs.wasNull()`. |
| **ResultSetMetaData** | [CourseDAO.java:getCoursesTableMetaData()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/dao/CourseDAO.java) | Introspecting database schema metadata at runtime using `rs.getMetaData()`. Extracts column count (`getColumnCount()`), column names (`getColumnName()`), data types (`getColumnTypeName()`), and nullability (`isNullable()`). |
| **Relational Database Design** | [database/setup.sql](file:///c:/Users/sivak/Faculty-Course-Portal/database/setup.sql) | DDL schema script creating `faculty` and `courses` tables with `PRIMARY KEY`, `FOREIGN KEY`, `CHECK` (credit range 1–6), `UNIQUE`, sample data records, and stored procedures. |
| **Standalone Test Suite** | [src/com/portal/test/JDBCTest.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/test/JDBCTest.java) | Standalone command-line test runner demonstrating all 7 JDBC topics in sequence with formatted console output for viva evaluation. |

## 4. Servlets Syllabus Mapping

| Syllabus Topic | File / Class / Method | Implementation & Demonstration Details |
| :--- | :--- | :--- |
| **Server-Side Programming** | [com.portal.controller.*](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller) | Java servlets executing on the web container server handling HTTP transactions, business validation, and database operations. |
| **Web Servers / Tomcat Deployment** | [WebContent/WEB-INF/web.xml](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/WEB-INF/web.xml), [README.md](file:///c:/Users/sivak/Faculty-Course-Portal/README.md) | Standard Java EE Web Application structure (`WEB-INF/web.xml`, `WEB-INF/classes`, `WEB-INF/lib`) for deployment onto Apache Tomcat 9.0 servlet container. |
| **Servlet Life Cycle** | [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java#L52-L76) | Demonstrates complete lifecycle: `init(ServletConfig)` for startup initialization, `service()` request dispatching to `doGet()`/`doPost()`, and `destroy()` for graceful shutdown. |
| **Reading Servlet Parameters** | [AddCourseServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/AddCourseServlet.java#L47-L62), [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java#L118-L125) | `request.getParameter("paramName")` extracting form fields: `username`, `password`, `courseId`, `courseName`, `faculty`, `credits`, `courseType`, `courseLevel`, `isCore`, `description`. |
| **Initialization Parameters** | [web.xml](file:///c:/Users/sivak/Faculty-Course-Portal/WebContent/WEB-INF/web.xml#L27-L36), [LoginServlet.java:init()](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java#L58-L69) | Configured in `web.xml` using `<init-param>` (`applicationName`, `adminContact`) and retrieved in Java code using `ServletConfig.getInitParameter()`. |
| **HTTP Request and Response** | Across all Servlets | Demonstrates `HttpServletRequest` (parameters, headers, session, cookies, attributes) and `HttpServletResponse` (redirects, cookies, status codes). |
| **javax.servlet.* Package** | Across all Servlets | Utilizes `javax.servlet.ServletConfig`, `javax.servlet.ServletException`, `javax.servlet.RequestDispatcher`. |
| **javax.servlet.http.* Package** | Across all Servlets | Utilizes `javax.servlet.http.HttpServlet`, `javax.servlet.http.HttpServletRequest`, `javax.servlet.http.HttpServletResponse`, `javax.servlet.http.HttpSession`, `javax.servlet.http.Cookie`. |
| **Redirection** | [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java#L161), [LogoutServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LogoutServlet.java#L57), [AddCourseServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/AddCourseServlet.java#L123) | Client-side redirection via `response.sendRedirect(request.getContextPath() + "/url")` after POST submissions and authentication. |
| **Session Management** | [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java#L141-L151), [LogoutServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LogoutServlet.java#L45-L55) | Creating session via `request.getSession(true)`, binding user attributes (`session.setAttribute("facultyUser", faculty)`), setting timeout (`setMaxInactiveInterval`), and invalidating upon logout (`session.invalidate()`). |
| **Cookie Management** | [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java#L87-L105) | Creating non-sensitive cookie (`new Cookie("lastFacultyUser", username)`), setting max age (`24*60*60`), attaching to response (`response.addCookie()`), and reading client cookies (`request.getCookies()`). No passwords stored. |
| **Database Access Using Servlets** | [CourseServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/CourseServlet.java), [AddCourseServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/AddCourseServlet.java), [LoginServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/LoginServlet.java) | Clean MVC abstraction: Servlets delegate all SQL and data queries to `CourseDAO` and `FacultyDAO` without hardcoded SQL in controller classes. |
| **MVC Architecture (Controller)** | [CourseServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/CourseServlet.java), [EditCourseServlet.java](file:///c:/Users/sivak/Faculty-Course-Portal/src/com/portal/controller/EditCourseServlet.java) | Servlets act as Controllers: intercepting requests, calling DAOs/Models, binding results to request attributes (`request.setAttribute("courseList", courses)`), and dispatching to View layer via `RequestDispatcher.forward()`. |

---

## 5. Upcoming Stages (To Be Implemented Incrementally)
- **JSP:** JSP Life Cycle, Directive elements, Scripting elements, Action elements, Implicit objects, Error handling, Accessing Bean using JSP, MVC Presentation View.
- **XML:** XML, DTD, XML Schema (XSD), XSLT Transformation, DOM Parser, SAX Parser.

