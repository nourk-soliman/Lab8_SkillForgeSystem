/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.student;

import gui.dashboards.StudentBoard;
import model.course.Course;
import model.course.Lesson;
import model.user.Student;
import logic.userRole.StudentRole;
import json.JsonUserDatabase;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.course.CourseProgress;
import model.course.LessonProgress;

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
     * @param s
     */
    public CompleteLesson(Student s) {
        this.s = s;
        initComponents();
        loadEnrolledCourses();
        
    }

    private void loadEnrolledCourses() {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Select a Course");

        StudentRole sr = new StudentRole();
        List<Course> allCourses = sr.viewCourses(s);
        List<String> enrolledCourses = s.getEnrolledCourses();

        for (Course course : allCourses) {
            if (enrolledCourses != null && enrolledCourses.contains(course.getCourseId())) {
                jComboBox1.addItem(course.getCourseId() + " - " + course.getTitle());
            }
        }
        
        // Clear progress label when no course selected
        jLabel3.setText("Progress: Select a course");
    }

private void loadLessons() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);  // Clear table

    String selectedItem = (String) jComboBox1.getSelectedItem();
    if (selectedItem == null || selectedItem.equals("Select a Course")) {
        jLabel3.setText("Progress: Select a course");
        return;
    }

    String courseId = selectedItem.split(" - ")[0];

    // Get all courses for the student
    StudentRole sr = new StudentRole();
    List<Course> allCourses = sr.viewCourses(s);

    // Find the selected course
    Course selectedCourse = null;
    for (Course course : allCourses) {
        if (course.getCourseId().equals(courseId)) {
            selectedCourse = course;
            break;
        }
    }

    if (selectedCourse == null) {
        jLabel3.setText("Course not found");
        return;
    }

    // Calculate completed lessons for progress
    int totalLessons = selectedCourse.getLessons().size();
    int completedLessons = countCompletedLessons(courseId);
    double progress = totalLessons > 0 ? (completedLessons * 100.0) / totalLessons : 0;
    jLabel3.setText(String.format("Progress: %d/%d lessons completed (%.1f%%)", 
            completedLessons, totalLessons, progress));

    // Loop through lessons and populate table
    for (Lesson lesson : selectedCourse.getLessons()) {

        // Get a content preview
        String contentPreview = lesson.getContent();
        if (contentPreview.length() > 50) {
            contentPreview = contentPreview.substring(0, 50) + "...";
        }

        // Default status
        String status = "incomplete";

        // Check student progress for this course & lesson
        for (CourseProgress cp : s.getProgress()) {
            if (!cp.getCourseId().equals(courseId)) continue; // Only check the current course

            for (LessonProgress lp : cp.getLessonProgress()) {
                if (lp.getLessonId().equals(lesson.getLessonId())) {
                    if(lp.isCompleted())
                    status = lp.isCompleted() ? "completed" : "incomplete";
                    break; 
                }
            }
        }

        // Add row to table
        model.addRow(new Object[] {
            lesson.getLessonId(),
            lesson.getTitle(),
            contentPreview,
            lesson.getOptionalResources().size(),
            status
        });
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
    
    /*private void markLessonAsComplete() {
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to mark as complete", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String lessonId = (String) jTable1.getValueAt(selectedRow, 0);
        String lessonTitle = (String) jTable1.getValueAt(selectedRow, 1);
        String status = (String) jTable1.getValueAt(selectedRow, 4);

        String selectedItem = (String) jComboBox1.getSelectedItem();
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
        JsonUserDatabase userReader = new JsonUserDatabase("users.json");
        List<Student> students = userReader.getStudents();

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getUserId() == s.getUserId()) {
                students.set(i, s);
                break;
            }
        }

        userReader.setStudents(students);
        userReader.saveToFile("users.json");
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Course" }));
        jComboBox1.setToolTipText("Select a Course");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Complete Lessons");

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lesson ID", "Title", "Content Preview", "Resources", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Course :");

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("View Lesson Details");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Back to DashBoard");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Progress : Select a Course");
        jLabel3.setName(""); // NOI18N

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("Take Quiz");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addGap(130, 130, 130)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(291, 291, 291)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 335, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jComboBox1.getAccessibleContext().setAccessibleName("Select a Course");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new StudentBoard(s).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a lesson to view", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String lessonId = (String) jTable1.getValueAt(selectedRow, 0);
        String lessonTitle = (String) jTable1.getValueAt(selectedRow, 1);

        // Get the full lesson details
        String selectedItem = (String) jComboBox1.getSelectedItem();
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        loadLessons();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
      int selectedRow = jTable1.getSelectedRow();

if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Please select a lesson first", "No Selection",
            JOptionPane.WARNING_MESSAGE);
    return;
}

String lessonId = (String) jTable1.getValueAt(selectedRow, 0);
/*String lessonTitle = (String) jTable1.getValueAt(selectedRow, 1);*/
String selectedItem = (String) jComboBox1.getSelectedItem();
String courseId = selectedItem.split(" - ")[0];

// Get all courses for the student
StudentRole sr = new StudentRole();
List<Course> allCourses = sr.viewCourses(s);

// Find the selected course
Course selectedCourse = null;
for (Course course : allCourses) {
    if (course.getCourseId().equals(courseId)) {
        selectedCourse = course;
        break;
    }
}

if (selectedCourse == null) {
    JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

// Find the selected lesson and its index in the course
List<Lesson> lessons = selectedCourse.getLessons();
int currentLessonIndex = -1;
Lesson selectedLesson = null;
for (int i = 0; i < lessons.size(); i++) {
    if (lessons.get(i).getLessonId().equals(lessonId)) {
        currentLessonIndex = i;
        selectedLesson = lessons.get(i);
        break;
    }
}

if (selectedLesson == null) {
    JOptionPane.showMessageDialog(this, "Lesson not found!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

// Check if all previous lessons are completed
if (currentLessonIndex >= 0) {
    // Check using progress structure instead of completedLessons
    boolean isCurrentLessonCompleted = isLessonCompleted(s, courseId, selectedLesson.getLessonId());
    
    // Check if current lesson is already completed
    if (isCurrentLessonCompleted) {
        JOptionPane.showMessageDialog(this,
                "This lesson has already been completed. You cannot retake the quiz.",
                "Lesson Already Completed",
                JOptionPane.WARNING_MESSAGE);
        return; 
    }
    
    // Check previous lessons
    for (int i = 0; i < currentLessonIndex; i++) {
        String prevLessonId = lessons.get(i).getLessonId();
        if (!isLessonCompleted(s, courseId, prevLessonId)) {
            JOptionPane.showMessageDialog(this,
                    "You must complete all previous lessons before taking this quiz.",
                    "Previous Lessons Not Completed",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
}

// Open QuizPage
this.dispose();
new QuizPage(s, selectedLesson).setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed
private boolean isLessonCompleted(Student student, String courseId, String lessonId) {
    for (CourseProgress progress : student.getProgress()) {
        if (progress.getCourseId().equals(courseId)) {
            for (LessonProgress lessonProgress : progress.getLessonProgress()) {
                if (lessonProgress.getLessonId().equals(lessonId) && lessonProgress.isCompleted()) {
                    return true;
                }
            }
        }
    }
    return false;
}
   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
