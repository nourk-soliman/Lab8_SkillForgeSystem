/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.instructor;

import gui.dashboards.InstructorBoard;
import model.user.Instructor;
import logic.userRole.InstructorRole;
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        coursesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable();
        createCourseBtn = new javax.swing.JButton();
        editCourseBtn = new javax.swing.JButton();
        deleteCourseBtn = new javax.swing.JButton();
        refreshCoursesBtn = new javax.swing.JButton();
        lessonsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lessonsTable = new javax.swing.JTable();
        courseComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        addLessonBtn = new javax.swing.JButton();
        editLessonBtn = new javax.swing.JButton();
        deleteLessonBtn = new javax.swing.JButton();
        refreshLessonsBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Course and Lesson Management");

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        titleLabel.setFont(new java.awt.Font("Arial", 1, 24));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Course & Lesson Management");

        coursesPanel.setBackground(new java.awt.Color(204, 255, 255));

        coursesTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Course ID", "Title", "Description", "Students", "Lessons"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
                    java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(coursesTable);

        createCourseBtn.setText("Create Course");
        createCourseBtn.addActionListener(this::createCourseBtnActionPerformed);

        editCourseBtn.setText("Edit Course");
        editCourseBtn.addActionListener(this::editCourseBtnActionPerformed);

        deleteCourseBtn.setText("Delete Course");
        deleteCourseBtn.addActionListener(this::deleteCourseBtnActionPerformed);

        refreshCoursesBtn.setText("Refresh");
        refreshCoursesBtn.addActionListener(this::refreshCoursesBtnActionPerformed);

        javax.swing.GroupLayout coursesPanelLayout = new javax.swing.GroupLayout(coursesPanel);
        coursesPanel.setLayout(coursesPanelLayout);
        coursesPanelLayout.setHorizontalGroup(
                coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(coursesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(coursesPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(coursesPanelLayout.createSequentialGroup()
                                                .addComponent(createCourseBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(editCourseBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteCourseBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        156, Short.MAX_VALUE)
                                                .addComponent(refreshCoursesBtn)))
                                .addContainerGap()));
        coursesPanelLayout.setVerticalGroup(
                coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(coursesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(coursesPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(createCourseBtn)
                                        .addComponent(editCourseBtn)
                                        .addComponent(deleteCourseBtn)
                                        .addComponent(refreshCoursesBtn))
                                .addContainerGap()));

        jTabbedPane1.addTab("Manage Courses", coursesPanel);

        lessonsPanel.setBackground(new java.awt.Color(204, 255, 255));

        lessonsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Lesson ID", "Title", "Content", "Resources"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(lessonsTable);

        courseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course" }));
        courseComboBox.addActionListener(this::courseComboBoxActionPerformed);

        jLabel1.setText("Select Course:");

        addLessonBtn.setText("Add Lesson");
        addLessonBtn.addActionListener(this::addLessonBtnActionPerformed);

        editLessonBtn.setText("Edit Lesson");
        editLessonBtn.addActionListener(this::editLessonBtnActionPerformed);

        deleteLessonBtn.setText("Delete Lesson");
        deleteLessonBtn.addActionListener(this::deleteLessonBtnActionPerformed);

        refreshLessonsBtn.setText("Refresh");
        refreshLessonsBtn.addActionListener(this::refreshLessonsBtnActionPerformed);

        javax.swing.GroupLayout lessonsPanelLayout = new javax.swing.GroupLayout(lessonsPanel);
        lessonsPanel.setLayout(lessonsPanelLayout);
        lessonsPanelLayout.setHorizontalGroup(
                lessonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lessonsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(lessonsPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2)
                                        .addGroup(lessonsPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(lessonsPanelLayout.createSequentialGroup()
                                                .addComponent(addLessonBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(editLessonBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteLessonBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        156, Short.MAX_VALUE)
                                                .addComponent(refreshLessonsBtn)))
                                .addContainerGap()));
        lessonsPanelLayout.setVerticalGroup(
                lessonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lessonsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(lessonsPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(lessonsPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addLessonBtn)
                                        .addComponent(editLessonBtn)
                                        .addComponent(deleteLessonBtn)
                                        .addComponent(refreshLessonsBtn))
                                .addContainerGap()));

        jTabbedPane1.addTab("Manage Lessons", lessonsPanel);

        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(this::backBtnActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTabbedPane1)
                                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(backBtn)))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
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
    private javax.swing.JButton addLessonBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JButton createCourseBtn;
    private javax.swing.JComboBox<String> courseComboBox;
    private javax.swing.JPanel coursesPanel;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton deleteCourseBtn;
    private javax.swing.JButton deleteLessonBtn;
    private javax.swing.JButton editCourseBtn;
    private javax.swing.JButton editLessonBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel lessonsPanel;
    private javax.swing.JTable lessonsTable;
    private javax.swing.JButton refreshCoursesBtn;
    private javax.swing.JButton refreshLessonsBtn;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
