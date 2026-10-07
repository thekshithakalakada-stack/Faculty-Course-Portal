/* =========================================
   FACULTY COURSE MANAGEMENT PORTAL
   JAVASCRIPT
   ========================================= */


/* =========================================
   1. VARIABLES
   ========================================= */

let portalName = "Faculty Course Management Portal";

const maxCredits = 6;

var defaultFaculty = "Faculty Member";


/* =========================================
   2. JAVASCRIPT ARRAY
   ========================================= */

let courses = [
    "Web Technologies",
    "Database Management",
    "Java Programming",
    "Software Engineering"
];


/* =========================================
   3. PAGE LOAD EVENT
   ========================================= */

window.addEventListener("load", function () {

    console.log(portalName);

    displayDate();

    drawCanvas();

});


/* =========================================
   4. DATE OBJECT
   ========================================= */

function displayDate() {

    let today = new Date();

    let dateElement = document.getElementById("date");

    if (dateElement) {

        dateElement.innerHTML =
            today.toLocaleDateString();

    }

}


/* =========================================
   5. STRING OBJECT
   ========================================= */

function demonstrateString() {

    let courseName = "Web Technologies";

    console.log("Course Name: " + courseName);

    console.log("Length: " + courseName.length);

    console.log(
        "Uppercase: " +
        courseName.toUpperCase()
    );

    console.log(
        "Lowercase: " +
        courseName.toLowerCase()
    );

}


/* =========================================
   6. ARRAY OBJECT
   ========================================= */

function demonstrateArray() {

    console.log("Number of Courses: " + courses.length);

    for (let i = 0; i < courses.length; i++) {

        console.log(courses[i]);

    }

}


/* =========================================
   7. MATH OBJECT
   ========================================= */

function demonstrateMath() {

    let randomNumber =
        Math.floor(Math.random() * 100);

    console.log(
        "Random Number: " + randomNumber
    );

    console.log(
        "Rounded Number: " +
        Math.round(4.7)
    );

}


/* =========================================
   8. OPERATORS
   ========================================= */

function calculateCredits() {

    let theoryCredits = 3;

    let labCredits = 2;

    let totalCredits =
        theoryCredits + labCredits;

    console.log(
        "Total Credits: " + totalCredits
    );

    if (totalCredits >= 4 &&
        totalCredits <= maxCredits) {

        console.log(
            "Valid credit range"
        );

    }

}


/* =========================================
   9. CONTROL STATEMENTS
   ========================================= */

function checkCourseType(courseType) {

    let message;


    /* IF-ELSE */

    if (courseType === "Theory") {

        message = "This is a theory course.";

    }

    else if (courseType === "Lab") {

        message = "This is a laboratory course.";

    }

    else {

        message = "Course type not specified.";

    }


    /* SWITCH */

    switch (courseType) {

        case "Theory":

            console.log("Theory Course");

            break;

        case "Lab":

            console.log("Laboratory Course");

            break;

        default:

            console.log("Other Course");

    }


    return message;

}


/* =========================================
   10. FUNCTION
   ========================================= */

function greetFaculty(name) {

    if (name === "") {

        name = defaultFaculty;

    }

    return "Welcome, " + name + "!";

}


/* =========================================
   11. REGULAR EXPRESSION
   ========================================= */

function validateCourseId(courseId) {

    /*
       Course ID format:

       C101
       C102
       C999
    */

    let pattern = /^C[0-9]{3}$/;

    return pattern.test(courseId);

}


/* =========================================
   12. FORM VALIDATION
   ========================================= */

function validateCourse() {

    let courseId =
        document.getElementById("courseId");

    let courseName =
        document.getElementById("courseName");

    let faculty =
        document.getElementById("faculty");

    let credits =
        document.getElementById("credits");


    /* Check whether elements exist */

    if (!courseId ||
        !courseName ||
        !faculty ||
        !credits) {

        return true;

    }


    /* Course ID validation */

    if (!validateCourseId(courseId.value)) {

        alert(
            "Course ID must be in the format C101."
        );

        courseId.focus();

        return false;

    }


    /* Course name validation */

    if (courseName.value.trim() === "") {

        alert(
            "Please enter the course name."
        );

        courseName.focus();

        return false;

    }


    /* Faculty validation */

    if (faculty.value.trim() === "") {

        alert(
            "Please enter the faculty name."
        );

        faculty.focus();

        return false;

    }


    /* Credits validation */

    let creditValue =
        Number(credits.value);


    if (creditValue < 1 ||
        creditValue > maxCredits) {

        alert(
            "Credits must be between 1 and 6."
        );

        credits.focus();

        return false;

    }


    /* Course Type validation */

    let courseType =
        document.getElementById("courseType");

    if (courseType && courseType.value.trim() === "") {

        alert(
            "Please select a course delivery mode."
        );

        courseType.focus();

        return false;

    }


    return true;

}


/* =========================================
   13. EVENT HANDLING
   ========================================= */

let courseNameInput =
    document.getElementById("courseName");

if (courseNameInput) {

    courseNameInput.addEventListener(
        "input",
        function () {

            console.log(
                "Course name changed: " +
                courseNameInput.value
            );

        }
    );

}


/* =========================================
   14. ERROR HANDLING
   ========================================= */

function testErrorHandling() {

    try {

        let credits = 0;

        if (credits <= 0) {

            throw new Error(
                "Credits must be greater than zero."
            );

        }

    }

    catch (error) {

        console.error(
            "Error: " + error.message
        );

    }

}


/* =========================================
   15. CANVAS GRAPHICS
   ========================================= */

function drawCanvas() {

    let canvas =
        document.getElementById("courseCanvas");

    if (!canvas) {

        return;

    }


    let context =
        canvas.getContext("2d");


    /* Draw title */

    context.font = "20px Arial";

    context.fillText(
        "Faculty Course Portal",
        30,
        40
    );


    /* Draw course blocks */

    context.fillStyle = "#1e3a5f";

    context.fillRect(
        30,
        70,
        150,
        60
    );


    context.fillStyle = "#ffffff";

    context.font = "16px Arial";

    context.fillText(
        "Courses",
        70,
        105
    );


    context.fillStyle = "#4caf50";

    context.fillRect(
        220,
        70,
        150,
        60
    );


    context.fillStyle = "#ffffff";

    context.fillText(
        "Faculty",
        260,
        105
    );

}


/* =========================================
   16. DEMONSTRATION CALLS
   ========================================= */

console.log(
    "Portal started successfully."
);

console.log(
    greetFaculty("Faculty Member")
);

console.log(
    "C101 valid: " +
    validateCourseId("C101")
);

console.log(
    "ABC valid: " +
    validateCourseId("ABC")
);

demonstrateString();

demonstrateArray();

demonstrateMath();

calculateCredits();

console.log(
    checkCourseType("Theory")
);

testErrorHandling();


/* =========================================
   17. TABLE SEARCH AND FILTER (courses.html)
   ========================================= */

function filterCoursesTable() {

    let searchInput =
        document.getElementById("courseSearchInput");

    let typeFilter =
        document.getElementById("typeFilter");

    let table =
        document.getElementById("coursesTable");

    let countBadge =
        document.getElementById("courseCount");

    let noResultsMsg =
        document.getElementById("noResultsMsg");

    if (!table) {
        return;
    }

    let filterText = searchInput ?
        searchInput.value.toLowerCase().trim() : "";

    let selectedType = typeFilter ?
        typeFilter.value : "All";

    let tbody = table.getElementsByTagName("tbody")[0];

    if (!tbody) {
        return;
    }

    let rows = tbody.getElementsByTagName("tr");
    let visibleCount = 0;
    let totalCount = rows.length;

    for (let i = 0; i < rows.length; i++) {

        let row = rows[i];
        let cells = row.cells || row.getElementsByTagName("td");

        if (cells && cells.length >= 5) {

            let courseId =
                cells[0].textContent.toLowerCase();

            let courseName =
                cells[1].textContent.toLowerCase();

            let facultyName =
                cells[2].textContent.toLowerCase();

            let courseType =
                cells[4].textContent.trim();

            /* Check search keyword match */
            let matchesSearch =
                (filterText === "") ||
                (courseId.indexOf(filterText) !== -1) ||
                (courseName.indexOf(filterText) !== -1) ||
                (facultyName.indexOf(filterText) !== -1);

            /* Check course type dropdown match */
            let matchesType =
                (selectedType === "All") ||
                (courseType.indexOf(selectedType) !== -1);

            if (matchesSearch && matchesType) {

                row.style.display = "";
                visibleCount++;

            } else {

                row.style.display = "none";

            }

        }

    }

    /* Update course counter display */
    if (countBadge) {

        countBadge.innerText =
            "Showing " + visibleCount + " of " + totalCount + " courses";

    }

    /* Display empty state if no courses match */
    if (noResultsMsg) {

        noResultsMsg.style.display =
            (visibleCount === 0) ? "block" : "none";

    }

}


function resetFilters() {

    let searchInput =
        document.getElementById("courseSearchInput");

    let typeFilter =
        document.getElementById("typeFilter");

    if (searchInput) {
        searchInput.value = "";
    }

    if (typeFilter) {
        typeFilter.value = "All";
    }

    filterCoursesTable();

}


/* =========================================
   18. UI ACTIONS: EDIT AND DELETE BUTTONS
   ========================================= */

function editCourse(courseId) {

    alert(
        "Edit action selected for Course ID: " + courseId +
        "\n(Navigating to course editing interface)"
    );

    window.location.href = "addCourse.html?id=" + encodeURIComponent(courseId);

}


function deleteCourseRow(courseId, buttonElement) {

    let confirmDelete = confirm(
        "Are you sure you want to delete course " + courseId + "?"
    );

    if (confirmDelete) {

        try {

            let row = buttonElement.closest("tr");

            if (row) {

                row.remove();

                alert("Course " + courseId + " has been deleted.");

                filterCoursesTable();

            }

        } catch (error) {

            console.error(
                "Error deleting course row: " + error.message
            );

        }

    }

}