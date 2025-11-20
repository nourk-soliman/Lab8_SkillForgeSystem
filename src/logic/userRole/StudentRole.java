/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.userRole;
import model.user.Student;
import model.course.Course;


import Json.JsonCourses;
import java.util.List;
/**
 *
 * @author AltAWKEl
 */
public class StudentRole {
     public List<Course> viewCourses(Student s)
   {JsonCourses r =new JsonCourses("courses.json");
    List<Course> result=r.getCourses();
   return result;  }
     
}
