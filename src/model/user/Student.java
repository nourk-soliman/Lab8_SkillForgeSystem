package model.user;

import java.util.ArrayList;
import java.util.List;
import json.JsonUserDatabase;

public class Student extends User {

    private List<String> enrolledCourses;
    private List<String> completedLessons;

    // Default constructor required by Gson
    public Student() {
        super();
        this.enrolledCourses = new ArrayList<>();
        this.completedLessons = new ArrayList<>();
    }

    public Student(int id, String username, String email, String password, List<String> courses) {
        super(id, username, email, password);
        this.enrolledCourses = courses;
        this.completedLessons = new ArrayList<>();
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCourse(String courseName) {
        enrolledCourses.add(courseName);
    }

    public List<String> getCompletedLessons() {
        if (completedLessons == null) {
            completedLessons = new ArrayList<>();
        }
        return completedLessons;
    }

    public void setCompletedLessons(List<String> completedLessons) {
        this.completedLessons = completedLessons;
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Courses: " + enrolledCourses);
        System.out.println("Completed Lessons: " + (completedLessons != null ? completedLessons.size() : 0));
    }
}
