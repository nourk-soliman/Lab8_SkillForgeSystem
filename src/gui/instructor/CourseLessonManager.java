/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.instructor;

import gui.dashboards.InstructorBoard;
import model.user.Instructor;
import logic.admin.InstructorRole;
import model.course.Course;
import model.course.Lesson;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author moaz
 */
public class CourseLessonManager extends javax.swing.JFrame {
    private Instructor instructor;
    private InstructorRole instructorRole;
    private List<Course> instructorCourses;

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(CourseLessonManager.class.getName());

    public CourseLessonManager(Instructor instructor) {
        this.instructor = instructor;
        this.instructorRole = new InstructorRole();
        initComponents();
        loadInstructorCourses();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
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

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Use dispose instead of setVisible(false)
        new InstructorBoard(instructor).setVisible(true);
    }

    private void createCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        instructorRole.createNewCourse(instructor);
        loadInstructorCourses();
    }

    private void editCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a course to edit");
            return;
        }
        String courseId = (String) coursesTable.getValueAt(selectedRow, 0);
        instructorRole.editCourse(courseId, instructor);
        loadInstructorCourses();
    }

    private void deleteCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a course to delete");
            return;
        }
        String courseId = (String) coursesTable.getValueAt(selectedRow, 0);
        instructorRole.deleteCourse(courseId, instructor);
        loadInstructorCourses();
        refreshCourseComboBox();
    }

    private void refreshCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {
        loadInstructorCourses();
    }

    private void courseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        loadLessonsForSelectedCourse();
    }

    private void addLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedCourseId = getSelectedCourseId();
        if (selectedCourseId == null)
            return;

        instructorRole.addLessonToCourse(selectedCourseId, instructor);
        loadLessonsForSelectedCourse();
    }

    private void editLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = lessonsTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a lesson to edit");
            return;
        }
        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        String courseId = getSelectedCourseId();
        if (courseId == null)
            return;

        instructorRole.editLesson(courseId, lessonId, instructor);
        loadLessonsForSelectedCourse();
    }

    private void deleteLessonBtnActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = lessonsTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Please select a lesson to delete");
            return;
        }
        String lessonId = (String) lessonsTable.getValueAt(selectedRow, 0);
        String courseId = getSelectedCourseId();
        if (courseId == null)
            return;

        instructorRole.deleteLesson(courseId, lessonId, instructor);
        loadLessonsForSelectedCourse();
    }

    private void refreshLessonsBtnActionPerformed(java.awt.event.ActionEvent evt) {
        loadLessonsForSelectedCourse();
    }

    // Helper methods
    private void loadInstructorCourses() {
        instructorCourses = instructorRole.getInstructorCourses(instructor);
        DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
        model.setRowCount(0);

        for (Course course : instructorCourses) {
            model.addRow(new Object[] {
                    course.getCourseId(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getStudents().size(),
                    course.getLessons().size()
            });
        }
        refreshCourseComboBox();
    }

    private void refreshCourseComboBox() {
        // Remove action listener temporarily to prevent firing during update
        java.awt.event.ActionListener[] listeners = courseComboBox.getActionListeners();
        for (java.awt.event.ActionListener listener : listeners) {
            courseComboBox.removeActionListener(listener);
        }

        courseComboBox.removeAllItems();
        courseComboBox.addItem("Select Course");
        for (Course course : instructorCourses) {
            courseComboBox.addItem(course.getCourseId());
        }

        // Re-add action listeners
        for (java.awt.event.ActionListener listener : listeners) {
            courseComboBox.addActionListener(listener);
        }
    }

    private void loadLessonsForSelectedCourse() {
        String selectedCourseId = getSelectedCourseId();

        // Simply return without showing error if no course selected yet
        if (selectedCourseId == null) {
            // Clear the lessons table
            DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
            model.setRowCount(0);
            return;
        }

        List<Lesson> lessons = instructorRole.getCourseLessons(selectedCourseId, instructor);
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);

        for (Lesson lesson : lessons) {
            String contentPreview = lesson.getContent();
            if (contentPreview.length() > 50) {
                contentPreview = contentPreview.substring(0, 50) + "...";
            }

            model.addRow(new Object[] {
                    lesson.getLessonId(),
                    lesson.getTitle(),
                    contentPreview,
                    lesson.getOptionalResources().size()
            });
        }
    }

    private String getSelectedCourseId() {
        String selectedCourseId = (String) courseComboBox.getSelectedItem();
        if (selectedCourseId == null || selectedCourseId.equals("Select Course")) {
            // Don't show error, just return null
            return null;
        }
        return selectedCourseId;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
