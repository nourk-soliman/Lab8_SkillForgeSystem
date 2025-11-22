/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

/**
 *
 * @author Nour
 */
public class LessonProgress {
   private String lessonId;
   private float quizScore;
   private boolean completed;

    public LessonProgress(String lessonId, int quizScore, boolean completed) {
        this.lessonId = lessonId;
        this.quizScore = quizScore;
        this.completed = completed;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public float getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(float quizScore) {
        this.quizScore = quizScore;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}
