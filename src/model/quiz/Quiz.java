/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quiz;
import java.util.ArrayList;

/**
 *
 * @author Nour
 */
public class Quiz {
 private ArrayList<QuizQuestion> questions;

    public Quiz(ArrayList<QuizQuestion> questions) {
        try{
        setQuestions(questions);}
        catch(IllegalArgumentException e)
        {}
        
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuizQuestion> questions) {
        if(questions==null||questions.isEmpty())
        {throw new IllegalArgumentException("Questions can not be empty."); }
        this.questions = questions;
    }
 

    public static void main(String[] args) {
        ArrayList<QuizQuestion> questions=new ArrayList<>();
        ArrayList<String> options=new ArrayList<>();
        options.add("one.");
        options.add("two.");
        questions.add(new QuizQuestion("How many fundamentals are there?",1,options));
        for(QuizQuestion question:questions)
            question.displayInfo();
        Quiz q=new Quiz(questions);
    }
    
}
