/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import json.JsonCoursesDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariam Yamen
 */
public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private List<String> optionalResources;

    public Lesson(String lessonId, String title, String content, List<String> optionalResources) {
     
        setLessonId(lessonId);
       
        this.title = title;
        this.content = content;
        this.optionalResources = optionalResources;
    }

    public String getLessonId() {
        return lessonId;
    }

 public void setLessonId(String lessonId) {

    JsonCoursesDatabase r = new JsonCoursesDatabase("courses.json");
    List<Lesson> lessons = r.getLessons();

    for (Lesson lesson : lessons) {
        if (lesson.getLessonId() != null && lesson.getLessonId().equals(lessonId)) {
            System.out.println("Lesson ID must be unique.");
            return;
        }
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
    
    public void displayInfo()
    { System.out.println("=== Lesson Info ===");
        
        System.out.println("LessonId: " + lessonId);
        System.out.println("Lesson title: " + title);
        System.out.println("Lesson content: " + content);
        for(String r:optionalResources)
        System.out.println("Lesson resources: " + r);
        System.out.println("======================");
    
    
    
    
    
    }
    
    public static void main(String[] args) {
        JsonCoursesDatabase r=new JsonCoursesDatabase("courses.json");
    List<Lesson>l=r.getLessons();
       for(Lesson lesson:l)
           lesson.displayInfo();
       List<String>opt=new ArrayList<>();
       opt.add("blbalala");
       l.add(new Lesson("dt1","Nour","That's me",opt)); 
       for(Lesson lesson:l)
           lesson.displayInfo();
    }
}
