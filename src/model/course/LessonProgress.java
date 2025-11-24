/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import java.util.ArrayList;
import model.quiz.QuizAttempt;

/**
 *
 * @author Nour
 */
public class LessonProgress {
   private String lessonId;
   private float quizScore;
   private boolean completed;
   private ArrayList<QuizAttempt> attempts;

    public LessonProgress(String lessonId, float quizScore, boolean completed) {
        this.lessonId = lessonId;
        this.quizScore = quizScore;
        this.completed = completed;
        this.attempts=new ArrayList<>();
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
        public ArrayList<QuizAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(ArrayList<QuizAttempt> attempts) {
        this.attempts = attempts;
    }
}
