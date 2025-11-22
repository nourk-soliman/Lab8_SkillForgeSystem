package model.quiz;

import java.util.ArrayList;

/**
 * Quiz Question model
 * 
 * @author Nour
 */
public class QuizQuestion {
    private String questionId;
    private String question;
    private int answerIndex;
    private ArrayList<String> options;

    public QuizQuestion() {
        this.options = new ArrayList<>();
    }

    public QuizQuestion(String questionId, String question, int answerIndex, ArrayList<String> options) {
        this.questionId = questionId;
        setOptions(options); // Set options first
        setAnswerIndex(answerIndex);
        setQuestion(question); // Set question last after options are set
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        if (answerIndex < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
        if (options != null && !options.isEmpty() && answerIndex >= options.size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }
        this.answerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question cannot be empty.");
        }
        // Relaxed validation - minimum 3 characters instead of 5
        if (question.trim().length() < 3) {
            throw new IllegalArgumentException("Question should be at least 3 characters.");
        }
        this.question = question.trim();
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options should not be left blank.");
        }
        if (options.size() < 2) {
            throw new IllegalArgumentException("Options should be at least 2.");
        }
        // Trim all options and validate
        ArrayList<String> trimmedOptions = new ArrayList<>();
        for (String option : options) {
            if (option == null || option.trim().isEmpty()) {
                throw new IllegalArgumentException("Option cannot be empty.");
            }
            trimmedOptions.add(option.trim());
        }
        this.options = trimmedOptions;
    }

    public String getCorrectAnswer() {
        if (options != null && answerIndex >= 0 && answerIndex < options.size()) {
            return options.get(answerIndex);
        }
        return null;
    }

    public boolean isCorrectAnswer(int selectedIndex) {
        return selectedIndex == answerIndex;
    }

    public void displayInfo() {
        System.out.println("Question: " + question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("Correct Answer: " + getCorrectAnswer());
    }
}