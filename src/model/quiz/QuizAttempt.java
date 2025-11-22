package model.quiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a student's quiz attempt
 * 
 * @author Nour
 */
public class QuizAttempt {
    private String attemptId;
    private int studentId;
    private String lessonId;
    private String courseId;
    private int score; // percentage score
    private int correctAnswers;
    private int totalQuestions;
    private ArrayList<Integer> studentAnswers; // indices of selected answers
    private String attemptDate;
    private boolean passed;

    public QuizAttempt() {
        this.studentAnswers = new ArrayList<>();
        this.attemptDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public QuizAttempt(String attemptId, int studentId, String lessonId, String courseId,
            int score, int correctAnswers, int totalQuestions,
            ArrayList<Integer> studentAnswers, boolean passed) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.score = score;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.studentAnswers = studentAnswers;
        this.passed = passed;
        this.attemptDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters and Setters
    public String getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public ArrayList<Integer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(ArrayList<Integer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public String getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(String attemptDate) {
        this.attemptDate = attemptDate;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void displayInfo() {
        System.out.println("=== Quiz Attempt ===");
        System.out.println("Attempt ID: " + attemptId);
        System.out.println("Student ID: " + studentId);
        System.out.println("Course ID: " + courseId);
        System.out.println("Lesson ID: " + lessonId);
        System.out.println("Score: " + score + "%");
        System.out.println("Correct Answers: " + correctAnswers + "/" + totalQuestions);
        System.out.println("Passed: " + (passed ? "Yes" : "No"));
        System.out.println("Date: " + attemptDate);
    }
}