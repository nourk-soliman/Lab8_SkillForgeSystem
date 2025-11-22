/*
 * Course Model Class
 */
package model.course;

import java.util.List;

/**
 * Represents a course in the system
 * 
 * @author moaz
 */
public class Course {
    private String courseId;
    private String title;
    private int instructorId;
    private String description;
    private List<Lesson> lessons;
    private List<Integer> students; // List of student IDs
    private CourseStatus approvalStatus;

    // Constructor
    public Course(String courseId, String title, int instructorId, String description,
            List<Lesson> lessons, List<Integer> students) {
        this.courseId = courseId;
        this.title = title;
        this.instructorId = instructorId;
        this.description = description;
        this.lessons = lessons;
        this.students = students;
        this.approvalStatus = CourseStatus.PENDING; // Default status
    }

    // Default constructor
    public Course() {
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
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

    public CourseStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(CourseStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    // Helper methods

    /**
     * Add a lesson to the course
     */
    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    /**
     * Remove a lesson from the course
     */
    public void removeLesson(String lessonId) {
        lessons.removeIf(lesson -> lesson.getLessonId().equals(lessonId));
    }

    /**
     * Add a student to the course
     */
    public void enrollStudent(int studentId) {
        if (!students.contains(studentId)) {
            students.add(studentId);
        }
    }

    /**
     * Remove a student from the course
     */
    public void unenrollStudent(int studentId) {
        students.remove(Integer.valueOf(studentId));
    }

    /**
     * Get lesson by ID
     */
    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
    }

    /**
     * Check if course is approved
     */
    public boolean isApproved() {
        return approvalStatus == CourseStatus.APPROVED;
    }

    /**
     * Check if course is pending approval
     */
    public boolean isPending() {
        return approvalStatus == CourseStatus.PENDING;
    }

    /**
     * Check if course is rejected
     */
    public boolean isRejected() {
        return approvalStatus == CourseStatus.REJECTED;
    }

    /**
     * Display course information
     */
    public void displayInfo() {
        System.out.println("=== Course Info ===");
        System.out.println("Course ID: " + courseId);
        System.out.println("Title: " + title);
        System.out.println("Instructor ID: " + instructorId);
        System.out.println("Description: " + description);
        System.out.println("Number of Lessons: " + lessons.size());
        System.out.println("Enrolled Students: " + students.size());
        System.out.println("Approval Status: " + approvalStatus);
        System.out.println("==================");
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", instructorId=" + instructorId +
                ", status=" + approvalStatus +
                ", lessons=" + lessons.size() +
                ", students=" + students.size() +
                '}';
    }
}