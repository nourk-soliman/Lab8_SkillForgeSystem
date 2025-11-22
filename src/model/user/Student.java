/*
 * Student Model Class
 */
package model.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a student user in the system
 * 
 * @author Nour Khaled
 */
public class Student extends User {

    private List<String> enrolledCourses;
    private List<String> completedLessons;
    private Map<String, Integer> quizResults; // Key: "courseId:lessonId", Value: score (0-100)

    // Constructor
    public Student(int id, String username, String email, String password, List<String> courses) {
        super(id, username, email, password);
        this.enrolledCourses = courses != null ? courses : new ArrayList<>();
        this.completedLessons = new ArrayList<>();
    }

    public Student(int userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash);
        this.enrolledCourses = new ArrayList<>();
        this.completedLessons = new ArrayList<>();
        this.quizResults = new HashMap<>();
    }

    // Default constructor
    public Student() {
        super();
        this.enrolledCourses = new ArrayList<>();
        this.completedLessons = new ArrayList<>();
        this.quizResults = new HashMap<>();
    }

    // Getters and Setters
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<String> getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(List<String> completedLessons) {
        this.completedLessons = completedLessons;
    }

    public Map<String, Integer> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(Map<String, Integer> quizResults) {
        this.quizResults = quizResults;
    }

    // Helper methods

    /**
     * Enroll in a course
     */
    public void enrollInCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    /**
     * Mark a lesson as completed
     */
    public void completeLesson(String courseId, String lessonId) {
        String lessonKey = courseId + ":" + lessonId;
        if (!completedLessons.contains(lessonKey)) {
            completedLessons.add(lessonKey);
        }
    }

    /**
     * Save quiz result
     */
    public void saveQuizResult(String courseId, String lessonId, int score) {
        String quizKey = courseId + ":" + lessonId;
        quizResults.put(quizKey, score);
    }

    /**
     * Get quiz score for a specific lesson
     */
    public Integer getQuizScore(String courseId, String lessonId) {
        String quizKey = courseId + ":" + lessonId;
        return quizResults.get(quizKey);
    }

    /**
     * Check if a lesson is completed
     */
    public boolean isLessonCompleted(String courseId, String lessonId) {
        String lessonKey = courseId + ":" + lessonId;
        return completedLessons.contains(lessonKey);
    }

    /**
     * Check if a quiz is passed (score >= 60%)
     */
    public boolean isQuizPassed(String courseId, String lessonId) {
        Integer score = getQuizScore(courseId, lessonId);
        return score != null && score >= 60;
    }

    /**
     * Get average score for a course
     */
    public double getCourseAverageScore(String courseId) {
        int totalScore = 0;
        int count = 0;

        for (Map.Entry<String, Integer> entry : quizResults.entrySet()) {
            if (entry.getKey().startsWith(courseId + ":")) {
                totalScore += entry.getValue();
                count++;
            }
        }

        return count > 0 ? (double) totalScore / count : 0.0;
    }

    /**
     * Get course completion percentage
     */
    public double getCourseCompletionPercentage(String courseId, int totalLessons) {
        if (totalLessons == 0)
            return 0.0;

        int completedCount = 0;
        for (String lessonKey : completedLessons) {
            if (lessonKey.startsWith(courseId + ":")) {
                completedCount++;
            }
        }

        return (double) completedCount * 100.0 / totalLessons;
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Student Info ===");
        super.displayInfo();
        System.out.println("Enrolled Courses: " + enrolledCourses.size());
        System.out.println("Completed Lessons: " + completedLessons.size());
        System.out.println("Quiz Results: " + quizResults.size());
        System.out.println("===================");
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + getUserId() +
                ", username='" + getUserName() + '\'' +
                ", enrolledCourses=" + enrolledCourses.size() +
                ", completedLessons=" + completedLessons.size() +
                '}';
    }
}