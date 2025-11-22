/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.userRole;

import java.util.List;
import json.JsonCoursesDatabase;
import model.course.Course;
import model.course.CourseStatus;
import model.user.Admin;


/**
 *
 * @author Nour
 */
public class AdminRole {
      public List<Course> viewCourses(Admin a)
   {JsonCoursesDatabase r =new JsonCoursesDatabase("courses.json");
    List<Course> result=r.getCourses();
   return result;  }
      
    public void approveCourse(Course c) {
        c.setApprovalStatus(CourseStatus.APPROVED);
    }

    public void rejectCourse(Course c) {
        c.setApprovalStatus(CourseStatus.REJECTED);
    }  
      
}
