/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.student;

import gui.dashboards.StudentBoard;
import model.course.Course;
import model.course.Lesson;
import model.user.Student;
import logic.admin.StudentRole;
import json.JsonUserReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AltAWKEl
 */
public class CompleteLesson extends javax.swing.JFrame {
    private Student s;
    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(CompleteLesson.class.getName());

    /**
     * Creates new form CompleteLesson
     */
    public CompleteLesson(Student s) {
        this.s = s;
        initComponents();
        loadEnrolledCourses();
    }

    private void loadEnrolledCourses() {
        courseComboBox.removeAllItems();
        courseComboBox.addItem("Select a Course");

        StudentRole sr = new StudentRole();
        List<Course> allCourses = sr.viewCourses(s);
        List<String> enrolledCourses = s.getEnrolledCourses();

        for (Course course : allCourses) {
            if (enrolledCourses != null && enrolledCourses.contains(course.getCourseId())) {
                courseComboBox.addItem(course.getCourseId() + " - " + course.getTitle());
            }
        }
        
        // Clear progress label when no course selected
        progressLabel.setText("Progress: Select a course");
    }

    private void loadLessons() {
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);

        String selectedItem = (String) courseComboBox.getSelectedItem();
        if (selectedItem == null || selectedItem.equals("Select a Course")) {
            progressLabel.setText("Progress: Select a course");
            return;
        }

        String courseId = selectedItem.split(" - ")[0];

        StudentRole sr = new StudentRole();
        List<Course> allCourses = sr.viewCourses(s);

        for (Course course : allCourses) {
            if (course.getCourseId().equals(courseId)) {
                int totalLessons = course.getLessons().size();
                int completedLessons = countCompletedLessons(courseId);
                
                // Calculate and display progress
                double progress = totalLessons > 0 ? (completedLessons * 100.0) / totalLessons : 0;
                progressLabel.setText(String.format("Progress: %d/%d lessons completed (%.1f%%)", 
                        completedLessons, totalLessons, progress));
                
                for (Lesson lesson : course.getLessons()) {
                    String contentPreview = lesson.getContent();
                    if (contentPreview.length() > 50) {
                        contentPreview = contentPreview.substring(0, 50) + "...";
                    }
                    
                    // Check if lesson is completed
                    boolean isCompleted = isLessonCompleted(courseId, lesson.getLessonId());
                    String status = isCompleted ? "✓ Completed" : "Not Started";

                    model.addRow(new Object[] {
    // 
                            lesson.getLessonId(),
                            lesson.getTitle(),
                            contentPreview,
                            lesson.getOptionalResources().size(),
                            status
                    });
                }
                break;
            }
        }
    }
    
    private boolean isLessonCompleted(String courseId, String lessonId) {
        if (s.getCompletedLessons() == null) {
            s.setCompletedLessons(new ArrayList<>());
        }
        String lessonKey = courseId + ":" + lessonId;
        return s.getCompletedLessons().contains(lessonKey);
    }
    
    private int countCompletedLessons(String courseId) {
        if (s.getCompletedLessons() == null) {
            return 0;
        }
        int count = 0;
        for (String completedLesson : s.getCompletedLessons()) {
            if (completedLesson.startsWith(courseId + ":")) {
                count++;
            }
        }
        return count;
    }
    
    private void markLessonAsComplete() {
        int selectedRow = lessonsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to mark as complete", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        String lessonTitle = (String) lessonsTable.getValueAt(selectedRow, 1);
        String status = (String) lessonsTable.getValueAt(selectedRow, 4);

        String selectedItem = (String) courseComboBox.getSelectedItem();
        String courseId = selectedItem.split(" - ")[0];
        String lessonKey = courseId + ":" + lessonId;
        
        // Initialize completedLessons if null
        if (s.getCompletedLessons() == null) {
            s.setCompletedLessons(new ArrayList<>());
        }
        
        // Check if already completed
        if (status.contains("Completed")) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "This lesson is already marked as complete. Do you want to mark it as incomplete?",
                    "Already Completed",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                s.getCompletedLessons().remove(lessonKey);
                saveStudentData();
                JOptionPane.showMessageDialog(this, 
                        "Lesson marked as incomplete: " + lessonTitle, 
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadLessons(); // Refresh the table
            }
            return;
        }

        // Mark as complete
        int confirm = JOptionPane.showConfirmDialog(this,
                "Mark lesson as complete: " + lessonTitle + "?",
                "Confirm Completion",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            s.getCompletedLessons().add(lessonKey);
            saveStudentData();
            
            JOptionPane.showMessageDialog(this, 
                    "Lesson marked as complete: " + lessonTitle, 
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Reload lessons to update status and progress
            loadLessons();
        }
    }
    
    private void saveStudentData() {
        JsonUserReader userReader = new JsonUserReader("users.json");
        List<model.user.Student> students = userReader.getStudents();

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getUserId() == s.getUserId()) {
                students.set(i, s);
                break;
            }
        }

        userReader.setStudents(students);
        userReader.saveToFile("users.json");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void courseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_courseComboBoxActionPerformed
        loadLessons();
    }// GEN-LAST:event_courseComboBoxActionPerformed

    private void viewLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewLessonBtnActionPerformed
        int selectedRow = lessonsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to view", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        String lessonTitle = (String) lessonsTable.getValueAt(selectedRow, 1);

        // Get the full lesson details
        String selectedItem = (String) courseComboBox.getSelectedItem();
        String courseId = selectedItem.split(" - ")[0];

        StudentRole sr = new StudentRole();
        List<Course> allCourses = sr.viewCourses(s);

        for (Course course : allCourses) {
            if (course.getCourseId().equals(courseId)) {
                for (Lesson lesson : course.getLessons()) {
                    if (lesson.getLessonId().equals(lessonId)) {
                        // Build lesson details message
                        StringBuilder details = new StringBuilder();
                        details.append("=== LESSON DETAILS ===\n\n");
                        details.append("Lesson ID: ").append(lesson.getLessonId()).append("\n");
                        details.append("Title: ").append(lesson.getTitle()).append("\n\n");
                        details.append("Content:\n").append(lesson.getContent()).append("\n\n");

                        if (lesson.getOptionalResources() != null && !lesson.getOptionalResources().isEmpty()) {
                            details.append("Resources:\n");
                            for (String resource : lesson.getOptionalResources()) {
                                details.append("  • ").append(resource).append("\n");
                            }
                        } else {
                            details.append("Resources: None\n");
                        }

                        // Show lesson details in a dialog
                        JOptionPane.showMessageDialog(this,
                                details.toString(),
                                "Lesson: " + lessonTitle,
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }
        }
    }// GEN-LAST:event_viewLessonBtnActionPerformed

    private void markCompleteBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_markCompleteBtnActionPerformed
        markLessonAsComplete();
    }// GEN-LAST:event_markCompleteBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_backBtnActionPerformed
        this.dispose();
        new StudentBoard(s).setVisible(true);
    }// GEN-LAST:event_backBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
