package model.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tracks student performance metrics
 * 
 * @author Team
 */
public class StudentPerformance {
    private int studentId;
    private String studentName;
    private String courseId;
    private int completedLessons;
    private int totalLessons;
    private double averageQuizScore;
    private Map<String, Integer> lessonQuizScores; // lessonId -> score
    private int totalQuizzesTaken;
    private int totalQuizzesPassed;

    public StudentPerformance() {
        this.lessonQuizScores = new HashMap<>();
    }

    public StudentPerformance(int studentId, String studentName, String courseId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.lessonQuizScores = new HashMap<>();
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public double getAverageQuizScore() {
        return averageQuizScore;
    }

    public void setAverageQuizScore(double averageQuizScore) {
        this.averageQuizScore = averageQuizScore;
    }

    public Map<String, Integer> getLessonQuizScores() {
        return lessonQuizScores;
    }

    public void setLessonQuizScores(Map<String, Integer> lessonQuizScores) {
        this.lessonQuizScores = lessonQuizScores;
    }

    public int getTotalQuizzesTaken() {
        return totalQuizzesTaken;
    }

    public void setTotalQuizzesTaken(int totalQuizzesTaken) {
        this.totalQuizzesTaken = totalQuizzesTaken;
    }

    public int getTotalQuizzesPassed() {
        return totalQuizzesPassed;
    }

    public void setTotalQuizzesPassed(int totalQuizzesPassed) {
        this.totalQuizzesPassed = totalQuizzesPassed;
    }

    public double getCompletionPercentage() {
        if (totalLessons == 0)
            return 0;
        return (completedLessons * 100.0) / totalLessons;
    }

    public double getPassRate() {
        if (totalQuizzesTaken == 0)
            return 0;
        return (totalQuizzesPassed * 100.0) / totalQuizzesTaken;
    }

    public void addQuizScore(String lessonId, int score) {
        lessonQuizScores.put(lessonId, score);
        recalculateAverageScore();
    }

    private void recalculateAverageScore() {
        if (lessonQuizScores.isEmpty()) {
            averageQuizScore = 0;
            return;
        }

        int sum = 0;
        for (int score : lessonQuizScores.values()) {
            sum += score;
        }
        averageQuizScore = sum / (double) lessonQuizScores.size();
    }
}