/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import json.JsonCoursesReader;
import java.util.List;
import model.user.Instructor;
import java.util.ArrayList;
/**
 *
 * @author Mariam Yamen
 */
public class Course {
    private String courseId;
    private String title;
    private int instructorId;
    private String description;
    List<Lesson> lessons;
    List<Integer> students;

    public Course(String courseId, String title, int instructorId, String description, List<Lesson> lessons, List<Integer> students) {
        this.courseId = courseId;
        this.title = title;
        this.instructorId = instructorId;
        this.description = description;
        this.lessons = lessons;
        this.students = students;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
           JsonCoursesReader r = new JsonCoursesReader("courses.json");
    List<Course> courses =r.getCourses();

    for (Course course : courses) {
        if (course.getCourseId() != null && course.getCourseId().equals(courseId)) {
            System.out.println("Course ID must be unique.");
            return;
        }
    }

    this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        Instructor i=new Instructor();
        i.setUserId(instructorId);
        this.instructorId = instructorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }
    
 public void displayInfo() {
    System.out.println("=== Course Information ===");
    System.out.println("Course ID      : " + courseId);
    System.out.println("Title          : " + title);
    System.out.println("Instructor ID  : " + instructorId);
    System.out.println("Description    : " + description);

    // Display lessons
    System.out.println("\nLessons:");
    if (lessons == null || lessons.isEmpty()) {
        System.out.println("  No lessons in this course.");
    } else {
        for (Lesson lesson : lessons) {
            System.out.println("  - " + lesson.getLessonId() + " | " + lesson.getTitle());
        }
    }

    // Display students
    System.out.println("\nEnrolled Students:");
    if (students == null || students.isEmpty()) {
        System.out.println("  No students enrolled.");
    } else {
        for (Integer studentId : students) {
            System.out.println("  - Student ID: " + studentId);
        }
    }

    System.out.println("==========================\n");
}

    
    
    
      public static void main(String[] args) {
        JsonCoursesReader r=new JsonCoursesReader("courses.json");
    List<Course>c=r.getCourses();
       for(Course course:c)
           course.displayInfo();
     
    }
    
    
    
    
    
}
