/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import json.JsonCoursesDatabase;
import model.user.Instructor;
import java.util.ArrayList;
import java.util.List;
import model.quiz.Quiz;

/**
 *
 * @author Mariam Yamen
 */
public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private List<String> optionalResources;
    private Quiz quiz;

    public Lesson(String lessonId, String title, String content, List<String> optionalResources, Quiz quiz) {
        // IMPORTANT: Don't validate here - validation should be done before creating
        // the lesson
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.optionalResources = optionalResources;
        this.quiz = quiz;
    }

    public String getLessonId() {
        return lessonId;
    }

    // FIXED: Removed validation from setter - it was causing problems
    // Validation should be done in InstructorRole before creating the lesson
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getOptionalResources() {
        return optionalResources;
    }

    public void setOptionalResources(List<String> optionalResources) {
        this.optionalResources = optionalResources;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    // Check if lesson has a quiz
    public boolean hasQuiz() {
        return quiz != null && quiz.getQuestions() != null && !quiz.getQuestions().isEmpty();
    }

    // Helper method to get total questions
    public int getTotalQuestions() {
        if (!hasQuiz()) {
            return 0;
        }
        return quiz.getQuestions().size();
    }

    public void displayInfo() {
        System.out.println("=== Lesson Info ===");
        System.out.println("LessonId: " + lessonId);
        System.out.println("Lesson title: " + title);
        System.out.println("Lesson content: " + content);
        for (String r : optionalResources) {
            System.out.println("Lesson resources: " + r);
        }
        System.out.println("Has Quiz: " + hasQuiz());
        System.out.println("======================");
    }
}