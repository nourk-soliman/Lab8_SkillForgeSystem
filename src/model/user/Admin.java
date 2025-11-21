/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import com.google.gson.JsonArray;
import json.JsonHandler;
import model.course.Course;

import model.course.CourseStatus;

/**
 *
 * @author AltAWKEl
 */
public class Admin extends User{
    public void approveCourse(Course c) {
        c.setApprovalStatus(CourseStatus.APPROVED);
        saveChanges();
    }

    public void rejectCourse(Course c) {
        c.setApprovalStatus(CourseStatus.REJECTED);
        saveChanges();
    }

    private void saveChanges() {
       JsonHandler r = new JsonHandler("courses.json") {
           @Override
           protected void processJsonArray(JsonArray jsonArray) {
               throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
           }

           @Override
           protected JsonArray buildJsonArray() {
               throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
           }

           @Override
           protected void readFromFile(String filename) {
               throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
           }
       };
        r.saveToFile("courses.json");
    }
}
