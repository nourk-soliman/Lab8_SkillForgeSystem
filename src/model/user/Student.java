package model.user;

import java.util.ArrayList;
import java.util.List;
import json.JsonUserDatabase;
import model.course.Certificate;
import model.course.CourseProgress;

public class Student extends User {

    private List<String> enrolledCourses;
    private List<String> completedLessons;
    private List<CourseProgress> progress;

    public List<Certificate> getCertificates() {
        return certificates;
    }
    private List<Certificate> certificates;

    public List<CourseProgress> getProgress() {
        return progress;
    }

    public void setProgress(List<CourseProgress> progress) {
        this.progress = progress;
    }

    // Default constructor required by Gson
    public Student() {
        super();
        this.enrolledCourses = new ArrayList<>();
        this.completedLessons = new ArrayList<>();
        this.certificates=new ArrayList<>();
        
    }

    public Student(int id, String username, String email, String password, List<String> courses) {
        super(id, username, email, password);
        this.enrolledCourses = courses;
        this.progress=new ArrayList<>();
         for(String courseId : enrolledCourses) {
            progress.add(new CourseProgress(courseId));
        }
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

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Courses: " + enrolledCourses);
        System.out.println("Completed Lessons: " + (completedLessons != null ? completedLessons.size() : 0));
    }
}
