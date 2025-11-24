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
 * Course and Lesson Management Interface with Quiz Management
 * Fixed version with refreshable quiz tab
 * 
 * @author moaz
 */
public class CourseLessonManager extends javax.swing.JFrame {
        private Instructor instructor;
        private InstructorRole instructorRole;
        private List<Course> instructorCourses;

        // Make quiz combo boxes class-level so they can be refreshed
        private javax.swing.JComboBox<String> quizCourseComboBox;
        private javax.swing.JComboBox<String> quizLessonComboBox;

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(CourseLessonManager.class.getName());

        public CourseLessonManager(Instructor instructor) {
                this.instructor = instructor;
                this.instructorRole = new InstructorRole();
                initComponents();
                loadInstructorCourses();
                addQuizManagementTab();
        }

        @SuppressWarnings("unchecked")
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
                                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                                        java.lang.Integer.class,
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
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane1)
                                                                                .addGroup(coursesPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(createCourseBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(editCourseBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deleteCourseBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(refreshCoursesBtn)))
                                                                .addContainerGap()));
                coursesPanelLayout.setVerticalGroup(
                                coursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(coursesPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                350,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(coursesPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(createCourseBtn)
                                                                                .addComponent(editCourseBtn)
                                                                                .addComponent(deleteCourseBtn)
                                                                                .addComponent(refreshCoursesBtn))
                                                                .addContainerGap()));

                jTabbedPane1.addTab("Courses", coursesPanel);

                lessonsPanel.setBackground(new java.awt.Color(204, 255, 255));

                lessonsTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {},
                                new String[] {
                                                "Lesson ID", "Title", "Content Preview", "Resources", "Has Quiz"
                                }) {
                        Class[] types = new Class[] {
                                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                                        java.lang.Integer.class,
                                        java.lang.String.class
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
                jScrollPane2.setViewportView(lessonsTable);

                jLabel1.setText("Select Course:");

                addLessonBtn.setText("Add Lesson");
                addLessonBtn.addActionListener(this::addLessonBtnActionPerformed);

                editLessonBtn.setText("Edit Lesson");
                editLessonBtn.addActionListener(this::editLessonBtnActionPerformed);

                deleteLessonBtn.setText("Delete Lesson");
                deleteLessonBtn.addActionListener(this::deleteLessonBtnActionPerformed);

                refreshLessonsBtn.setText("Refresh");
                refreshLessonsBtn.addActionListener(this::refreshLessonsBtnActionPerformed);

                courseComboBox.addActionListener(this::courseComboBoxActionPerformed);

                javax.swing.GroupLayout lessonsPanelLayout = new javax.swing.GroupLayout(lessonsPanel);
                lessonsPanel.setLayout(lessonsPanelLayout);
                lessonsPanelLayout.setHorizontalGroup(
                                lessonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(lessonsPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(lessonsPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane2)
                                                                                .addGroup(lessonsPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel1)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(courseComboBox,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                300,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                .addGroup(lessonsPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(addLessonBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(editLessonBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deleteLessonBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(refreshLessonsBtn)))
                                                                .addContainerGap()));
                lessonsPanelLayout.setVerticalGroup(
                                lessonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lessonsPanelLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(lessonsPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel1)
                                                                                .addComponent(courseComboBox,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                309, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(lessonsPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(addLessonBtn)
                                                                                .addComponent(editLessonBtn)
                                                                                .addComponent(deleteLessonBtn)
                                                                                .addComponent(refreshLessonsBtn))
                                                                .addContainerGap()));

                jTabbedPane1.addTab("Lessons", lessonsPanel);

                backBtn.setText("Back to Dashboard");
                backBtn.addActionListener(this::backBtnActionPerformed);

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jTabbedPane1)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(titleLabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(backBtn)))
                                                                .addContainerGap()));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(titleLabel)
                                                                                .addComponent(backBtn))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jTabbedPane1)
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
        }

        private void addQuizManagementTab() {
                javax.swing.JPanel quizPanel = new javax.swing.JPanel();
                quizPanel.setBackground(new java.awt.Color(204, 255, 255));

                javax.swing.JLabel courseLabel = new javax.swing.JLabel("Select Course:");
                quizCourseComboBox = new javax.swing.JComboBox<>(); // Use class-level variable
                quizCourseComboBox.addItem("Select Course");

                javax.swing.JLabel lessonLabel = new javax.swing.JLabel("Select Lesson:");
                quizLessonComboBox = new javax.swing.JComboBox<>(); // Use class-level variable
                quizLessonComboBox.addItem("Select Lesson");

                javax.swing.JButton addQuizBtn = new javax.swing.JButton("Add/Edit Quiz");
                javax.swing.JButton viewQuizBtn = new javax.swing.JButton("View Quiz");
                javax.swing.JButton deleteQuizBtn = new javax.swing.JButton("Delete Quiz");

                // Populate course combo box initially
                refreshQuizCourseComboBox();

                // Course selection listener
                quizCourseComboBox.addActionListener(e -> {
                        String selected = (String) quizCourseComboBox.getSelectedItem();
                        quizLessonComboBox.removeAllItems();
                        quizLessonComboBox.addItem("Select Lesson");

                        if (selected != null && !selected.equals("Select Course")) {
                                String courseId = selected.split(" - ")[0];
                                List<Lesson> lessons = instructorRole.getCourseLessons(courseId, instructor);
                                for (Lesson lesson : lessons) {
                                        String hasQuiz = lesson.hasQuiz() ? " [Has Quiz]" : "";
                                        quizLessonComboBox.addItem(
                                                        lesson.getLessonId() + " - " + lesson.getTitle() + hasQuiz);
                                }
                        }
                });

                // Add Quiz button
                addQuizBtn.addActionListener(e -> {
                        String courseItem = (String) quizCourseComboBox.getSelectedItem();
                        String lessonItem = (String) quizLessonComboBox.getSelectedItem();

                        if (courseItem == null || courseItem.equals("Select Course") ||
                                        lessonItem == null || lessonItem.equals("Select Lesson")) {
                                JOptionPane.showMessageDialog(this, "Please select a course and lesson");
                                return;
                        }

                        String courseId = courseItem.split(" - ")[0];
                        String lessonId = lessonItem.split(" - ")[0];

                        instructorRole.addQuizToLesson(courseId, lessonId, instructor);

                        // Refresh lesson combo box to show updated quiz status
                        quizCourseComboBox.setSelectedItem(courseItem);
                });

                // View Quiz button
                viewQuizBtn.addActionListener(e -> {
                        String courseItem = (String) quizCourseComboBox.getSelectedItem();
                        String lessonItem = (String) quizLessonComboBox.getSelectedItem();

                        if (courseItem == null || courseItem.equals("Select Course") ||
                                        lessonItem == null || lessonItem.equals("Select Lesson")) {
                                JOptionPane.showMessageDialog(this, "Please select a course and lesson");
                                return;
                        }

                        String courseId = courseItem.split(" - ")[0];
                        String lessonId = lessonItem.split(" - ")[0];

                        instructorRole.viewQuizDetails(courseId, lessonId, instructor);
                });

                // Delete Quiz button
                deleteQuizBtn.addActionListener(e -> {
                        String courseItem = (String) quizCourseComboBox.getSelectedItem();
                        String lessonItem = (String) quizLessonComboBox.getSelectedItem();

                        if (courseItem == null || courseItem.equals("Select Course") ||
                                        lessonItem == null || lessonItem.equals("Select Lesson")) {
                                JOptionPane.showMessageDialog(this, "Please select a course and lesson");
                                return;
                        }

                        String courseId = courseItem.split(" - ")[0];
                        String lessonId = lessonItem.split(" - ")[0];

                        instructorRole.deleteQuizFromLesson(courseId, lessonId, instructor);

                        // Refresh lesson combo box to show updated quiz status
                        quizCourseComboBox.setSelectedItem(courseItem);
                });

                // Layout for quiz panel
                javax.swing.GroupLayout quizPanelLayout = new javax.swing.GroupLayout(quizPanel);
                quizPanel.setLayout(quizPanelLayout);
                quizPanelLayout.setHorizontalGroup(
                                quizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(quizPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(quizPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(quizPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(courseLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(quizCourseComboBox,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                300,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(quizPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(lessonLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(quizLessonComboBox,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                300,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(quizPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(addQuizBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(viewQuizBtn)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(deleteQuizBtn)))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                quizPanelLayout.setVerticalGroup(
                                quizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(quizPanelLayout.createSequentialGroup()
                                                                .addGap(30, 30, 30)
                                                                .addGroup(quizPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(courseLabel)
                                                                                .addComponent(quizCourseComboBox,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(quizPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(lessonLabel)
                                                                                .addComponent(quizLessonComboBox,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(30, 30, 30)
                                                                .addGroup(
                                                                                quizPanelLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(addQuizBtn,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(viewQuizBtn,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(deleteQuizBtn,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(150, Short.MAX_VALUE)));

                jTabbedPane1.addTab("Manage Quizzes", quizPanel);
        }

        // NEW METHOD: Refresh quiz course combo box
        private void refreshQuizCourseComboBox() {
                if (quizCourseComboBox == null) {
                        return; // Quiz tab not initialized yet
                }

                // Remove action listeners temporarily
                java.awt.event.ActionListener[] listeners = quizCourseComboBox.getActionListeners();
                for (java.awt.event.ActionListener listener : listeners) {
                        quizCourseComboBox.removeActionListener(listener);
                }

                // Clear and repopulate
                quizCourseComboBox.removeAllItems();
                quizCourseComboBox.addItem("Select Course");
                for (Course course : instructorCourses) {
                        quizCourseComboBox.addItem(course.getCourseId() + " - " + course.getTitle());
                }

                // Re-add listeners
                for (java.awt.event.ActionListener listener : listeners) {
                        quizCourseComboBox.addActionListener(listener);
                }

                // Clear lesson combo box
                quizLessonComboBox.removeAllItems();
                quizLessonComboBox.addItem("Select Lesson");
        }

        private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {
                this.dispose();
                new InstructorBoard(instructor).setVisible(true);
        }

        private void createCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {
                instructorRole.createNewCourse(instructor);
                loadInstructorCourses();
                refreshQuizCourseComboBox(); // ADDED: Refresh quiz tab after creating course
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
                refreshQuizCourseComboBox(); // ADDED: Refresh quiz tab after editing course
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
                refreshQuizCourseComboBox(); // ADDED: Refresh quiz tab after deleting course
        }

        private void refreshCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {
                loadInstructorCourses();
                refreshQuizCourseComboBox(); // ADDED: Refresh quiz tab when courses refreshed
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
                refreshQuizCourseComboBox(); // ADDED: Refresh quiz tab after adding lesson
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
                refreshQuizCourseComboBox(); // ADDED: Refresh quiz tab after deleting lesson
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
                java.awt.event.ActionListener[] listeners = courseComboBox.getActionListeners();
                for (java.awt.event.ActionListener listener : listeners) {
                        courseComboBox.removeActionListener(listener);
                }

                courseComboBox.removeAllItems();
                courseComboBox.addItem("Select Course");
                for (Course course : instructorCourses) {
                        courseComboBox.addItem(course.getCourseId());
                }

                for (java.awt.event.ActionListener listener : listeners) {
                        courseComboBox.addActionListener(listener);
                }
        }

        private void loadLessonsForSelectedCourse() {
                String selectedCourseId = getSelectedCourseId();

                if (selectedCourseId == null) {
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

                        String hasQuiz = lesson.hasQuiz() ? "Yes (" + lesson.getQuiz().getTotalQuestions() + ")" : "No";

                        model.addRow(new Object[] {
                                        lesson.getLessonId(),
                                        lesson.getTitle(),
                                        contentPreview,
                                        lesson.getOptionalResources().size(),
                                        hasQuiz
                        });
                }
        }

        private String getSelectedCourseId() {
                String selectedCourseId = (String) courseComboBox.getSelectedItem();
                if (selectedCourseId == null || selectedCourseId.equals("Select Course")) {
                        return null;
                }
                return selectedCourseId;
        }

        private void showError(String message) {
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Variables declaration
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
}