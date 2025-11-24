/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quiz;

/**
 *
 * @author Nour
 */
public class QuizAttempt {
   private float quizScore;

    public QuizAttempt(float quizScore) {
     this.quizScore=quizScore;
    }
    

    public float getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(float quizScore) {
        this.quizScore = quizScore;
    }
    
}
