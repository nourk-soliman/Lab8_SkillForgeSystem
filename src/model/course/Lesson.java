package model.course;

import java.util.ArrayList;
import java.util.List;
import model.quiz.Quiz;

/**
 * Lesson model with quiz support
 * 
 * @author Mariam Yamen
 */
public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private List<String> optionalResources;
    private Quiz quiz;

    public Lesson() {
        this.optionalResources = new ArrayList<>();
    }

    public Lesson(String lessonId, String title, String content, List<String> optionalResources) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.optionalResources = optionalResources != null ? optionalResources : new ArrayList<>();
        this.quiz = null;
    }

    public Lesson(String lessonId, String title, String content, List<String> optionalResources, Quiz quiz) {
        this(lessonId, title, content, optionalResources);
        this.quiz = quiz;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        if (lessonId == null || lessonId.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson ID cannot be empty.");
        }
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

    public boolean hasQuiz() {
        return quiz != null && quiz.getQuestions() != null && !quiz.getQuestions().isEmpty();
    }

    public void displayInfo() {
        System.out.println("=== Lesson Info ===");
        System.out.println("LessonId: " + lessonId);
        System.out.println("Lesson title: " + title);
        System.out.println("Lesson content: " + content);
        if (optionalResources != null && !optionalResources.isEmpty()) {
            System.out.println("Lesson resources:");
            for (String r : optionalResources) {
                System.out.println("  - " + r);
            }
        }
        if (hasQuiz()) {
            System.out.println("Has Quiz: Yes (" + quiz.getTotalQuestions() + " questions)");
        } else {
            System.out.println("Has Quiz: No");
        }
        System.out.println("======================");
    }
}