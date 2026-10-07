package com.portal.model;

import java.io.Serializable;

/**
 * =====================================================================
 * JavaBean Model: Course
 * 
 * Syllabus Demonstration:
 *   - JavaBean Architecture (Encapsulation, no-arg constructor, 
 *     private properties, public getters/setters, Serializable)
 *   - MVC Architecture (Model Component)
 * =====================================================================
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    // Private properties (Encapsulation)
    private String courseId;
    private String courseName;
    private String facultyName;
    private int credits;
    private String courseType;
    private String academicLevel;
    private boolean isCore;
    private String description;
    private Integer facultyId;

    /**
     * No-argument constructor (Mandatory for JavaBean specification)
     */
    public Course() {
        this.academicLevel = "Undergraduate";
        this.isCore = true;
    }

    /**
     * Parameterized constructor for full course initialization
     */
    public Course(String courseId, String courseName, String facultyName, int credits, 
                  String courseType, String academicLevel, boolean isCore, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.facultyName = facultyName;
        this.credits = credits;
        this.courseType = courseType;
        this.academicLevel = academicLevel;
        this.isCore = isCore;
        this.description = description;
    }

    /**
     * Parameterized constructor for core CRUD attributes
     */
    public Course(String courseId, String courseName, String facultyName, int credits, 
                  String courseType, String description) {
        this(courseId, courseName, facultyName, credits, courseType, "Undergraduate", true, description);
    }

    // =================================================================
    // GETTERS AND SETTERS
    // =================================================================

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public boolean isCore() {
        return isCore;
    }

    public void setCore(boolean isCore) {
        this.isCore = isCore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return "Course [ID=" + courseId + ", Name=" + courseName + ", Faculty=" + facultyName + 
               ", Credits=" + credits + ", Type=" + courseType + ", Level=" + academicLevel + "]";
    }
}
