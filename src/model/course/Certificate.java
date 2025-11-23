package model.course;

import java.time.LocalDate;

/**
 * Represents a certificate issued to a student for completing a course
 * 
 * @author Nour
 */
public class Certificate {
    private String certificateId;
    private int studentId;
    private String courseId;
    private LocalDate issueDate;

    public Certificate() {
        this.issueDate = LocalDate.now();
    }

    public Certificate(String certificateId, int studentId, String courseId) {
        this();
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getters and Setters
    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    //check if student can gain this cert or not
    public static boolean canGenerateCertificate(CourseProgress progress, Course course) {
        return course.getApprovalStatus() == CourseStatus.APPROVED && 
               progress != null && 
               progress.isCompleted();
    }

    public void displayInfo() {
        System.out.println("=== Certificate Metadata ===");
        System.out.println("Certificate ID: " + certificateId);
        System.out.println("Student ID: " + studentId);
        System.out.println("Course ID: " + courseId);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("============================");
    }
     
    // Convert to JSON form
    public String toJson() {
        return String.format(
            "{\"certificateId\":\"%s\",\"studentId\":%d,\"courseId\":\"%s\",\"issueDate\":\"%s\"}",
            certificateId, studentId, courseId, issueDate
        );
    }
}