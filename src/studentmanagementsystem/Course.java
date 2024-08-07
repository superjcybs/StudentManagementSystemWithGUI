package studentmanagementsystem;

// package declaration if needed, e.g., package com.example.studentmanagement;

public class Course {
    private String courseId;
    private String courseName;
    private String courseDegree;

    public Course(String courseId, String courseName, String courseDegree) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDegree = courseDegree;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
