package model.quiz;

import java.util.ArrayList;

/**
 * Quiz model containing questions and metadata
 * 
 * @author Nour
 */
public class Quiz {
    private String quizId;
    private ArrayList<QuizQuestion> questions;
    private int passingScore; // percentage needed to pass (e.g., 70)
    private int timeLimit; // in minutes, 0 means no limit

    public Quiz() {
        this.questions = new ArrayList<>();
        this.passingScore = 70;
        this.timeLimit = 0;
    }

    public Quiz(String quizId, ArrayList<QuizQuestion> questions, int passingScore, int timeLimit) {
        this.quizId = quizId;
        setQuestions(questions);
        setPassingScore(passingScore);
        setTimeLimit(timeLimit);
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        if (quizId == null || quizId.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz ID cannot be empty.");
        }
        this.quizId = quizId;
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuizQuestion> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Questions cannot be empty.");
        }
        this.questions = questions;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(int passingScore) {
        if (passingScore < 0 || passingScore > 100) {
            throw new IllegalArgumentException("Passing score must be between 0 and 100.");
        }
        this.passingScore = passingScore;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        if (timeLimit < 0) {
            throw new IllegalArgumentException("Time limit cannot be negative.");
        }
        this.timeLimit = timeLimit;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public void displayInfo() {
        System.out.println("=== Quiz Information ===");
        System.out.println("Quiz ID: " + quizId);
        System.out.println("Total Questions: " + getTotalQuestions());
        System.out.println("Passing Score: " + passingScore + "%");
        System.out.println("Time Limit: " + (timeLimit == 0 ? "No limit" : timeLimit + " minutes"));
        System.out.println("Questions:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("\nQuestion " + (i + 1) + ":");
            questions.get(i).displayInfo();
        }
    }
}