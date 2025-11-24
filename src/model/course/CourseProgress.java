/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import java.util.ArrayList;

/**
 *
 * @author Nour
 */
public class CourseProgress {
    private String courseId;
  private ArrayList<LessonProgress> lessonProgress;
   private boolean completed;

    public CourseProgress(String courseId) {
        this.courseId = courseId;
        lessonProgress=new ArrayList<>();
        completed=false;
    }
    
    public void UpdateComplete(ArrayList<LessonProgress> lessonProgress){
       for(LessonProgress l:lessonProgress)
       {if(!l.isCompleted()) {completed=false; return;}}
       completed=true;
    }

    public CourseProgress(String courseId, ArrayList<LessonProgress> lessonProgress, boolean completed) {
        this.courseId = courseId;
        this.lessonProgress = lessonProgress;
        this.completed = completed;
        System.out.println("Completed.");
    }

   public String getCourseId()
   {return courseId;}

    public ArrayList<LessonProgress> getLessonProgress() {
        return lessonProgress;
    }

    public void setLessonProgress(ArrayList<LessonProgress> lessonProgress) {
        this.lessonProgress = lessonProgress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
   
    
}
