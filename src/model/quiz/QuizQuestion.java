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
public class QuizQuestion {
    private String question;
    private int answerIndex;
    private ArrayList<String>options;

    public QuizQuestion(String question, int answerIndex, ArrayList<String> options) {
        try{
        setQuestion(question);
        setOptions(options);
        setAnswerIndex(answerIndex);
        }
  catch(IllegalArgumentException e)
  {System.out.println("Invalid formats.");}
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        if(answerIndex<0)
        {throw new IllegalArgumentException("Invalid index.");}
        if(answerIndex>(options.size()-1))
        {throw new IllegalArgumentException("Index is out of bounds.");}
        this.answerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if(question==null|| question.isBlank())
        {throw new IllegalArgumentException("Question can not be empty.");}
        if(question.length()<5)
        {throw new IllegalArgumentException("Question should be at least 5 characters."); }
        this.question = question;
    }

    

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        if(options==null|| options.isEmpty())
        {throw new IllegalArgumentException("Options should not be left blank.");}
        if(options.size()<2)
        {throw new IllegalArgumentException("Options should be at least 2."); }
        for(String option:options)
        {if(option.length()<2)
        {throw new IllegalArgumentException("Option length should be at least 5."); }}
        this.options = options;
    }
    public void displayInfo()
    {
        System.out.println("Question: "+question);
        for(String option:options)
        System.out.println("Options: "+option);
        System.out.println("Answer: "+options.get(answerIndex));
    
    
    }
    
}
