/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import com.google.gson.JsonArray;
import java.util.List;
import json.JsonHandler;
import model.course.Course;

import model.course.CourseStatus;

/**
 *
 * @author AltAWKEl
 */
public class Admin extends User {

    private List<AdminApproval> managedApprovals;

    public Admin(int userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash);

    }

    public void addApproval(String courseId, String action, String date) {
        managedApprovals.add(new AdminApproval(courseId, action, date));
    }

    public class AdminApproval {

        private String courseId;
        private String action;
        private String date;

        public AdminApproval(String courseId, String action, String date) {
            this.courseId = courseId;
            this.action = action;
            this.date = date;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getAction() {
            return action;
        }

        public String getDate() {
            return date;
        }
    }

}
