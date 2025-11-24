/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quiz;

import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class Quiz {
    private ArrayList<QuizQuestion> questions;
    private String retryPolicy;

    public Quiz(ArrayList<QuizQuestion> questions, String retryPolicy) {
        this.questions = questions;
        this.retryPolicy = retryPolicy;
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
    }

    public String getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(String retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    /**
     * Gets the total number of questions in this quiz
     * 
     * @return number of questions
     */
    public int getTotalQuestions() {
        if (questions == null) {
            return 0;
        }
        return questions.size();
    }

    /**
     * Checks if the quiz has any questions
     * 
     * @return true if quiz has questions, false otherwise
     */
    public boolean hasQuestions() {
        return questions != null && !questions.isEmpty();
    }

    /**
     * Adds a question to the quiz
     * 
     * @param question the question to add
     */
    public void addQuestion(QuizQuestion question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
    }

    /**
     * Removes a question from the quiz
     * 
     * @param index the index of the question to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeQuestion(int index) {
        if (questions != null && index >= 0 && index < questions.size()) {
            questions.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Gets a specific question by index
     * 
     * @param index the index of the question
     * @return the question, or null if index invalid
     */
    public QuizQuestion getQuestion(int index) {
        if (questions != null && index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    /**
     * Displays quiz information
     */
    public void displayInfo() {
        System.out.println("=== Quiz Info ===");
        System.out.println("Total Questions: " + getTotalQuestions());
        System.out.println("Retry Policy: " + retryPolicy);
        System.out.println("=================");
    }
}