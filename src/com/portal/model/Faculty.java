package com.portal.model;

import java.io.Serializable;

/**
 * =====================================================================
 * JavaBean Model: Faculty
 * 
 * Syllabus Demonstration:
 *   - JavaBean Architecture (Encapsulation, no-arg constructor, 
 *     private properties, public getters/setters, Serializable)
 *   - MVC Architecture (Model Component for Faculty Profiles)
 * =====================================================================
 */
public class Faculty implements Serializable {
    private static final long serialVersionUID = 1L;

    // Private properties (Encapsulation)
    private int facultyId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String department;
    private String designation;

    /**
     * No-argument constructor (Mandatory for JavaBean specification)
     */
    public Faculty() {
    }

    /**
     * Parameterized constructor for faculty authentication & profile
     */
    public Faculty(int facultyId, String username, String password, String fullName, 
                   String email, String department, String designation) {
        this.facultyId = facultyId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.designation = designation;
    }

    /**
     * Parameterized constructor without ID (for new registrations)
     */
    public Faculty(String username, String password, String fullName, 
                   String email, String department, String designation) {
        this(0, username, password, fullName, email, department, designation);
    }

    // =================================================================
    // GETTERS AND SETTERS
    // =================================================================

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Faculty [ID=" + facultyId + ", Username=" + username + ", Name=" + fullName + 
               ", Dept=" + department + ", Designation=" + designation + "]";
    }
}
