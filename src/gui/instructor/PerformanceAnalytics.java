package gui.instructor;

import gui.dashboards.InstructorBoard;
import json.JsonCoursesDatabase;
import json.JsonUserDatabase;
import model.course.Course;
import model.course.Lesson;
import model.user.Instructor;
import model.user.Student;
import model.analytics.StudentPerformance;
import model.quiz.QuizAttempt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Performance Analytics Dashboard for Instructors
 * Shows student performance, quiz scores, and course completion statistics
 * 
 * @author Team
 */
public class PerformanceAnalytics extends javax.swing.JFrame {
    private Instructor instructor;
    private JsonCoursesDatabase coursesDB;
    private JsonUserDatabase usersDB;
    private List<Course> instructorCourses;

    public PerformanceAnalytics(Instructor instructor) {
        this.instructor = instructor;
        this.coursesDB = new JsonCoursesDatabase("courses.json");
        this.usersDB = new JsonUserDatabase("users.json");
        loadInstructorCourses();
        initComponents();
    }

    private void loadInstructorCourses() {
        List<Course> allCourses = coursesDB.getCourses();
        instructorCourses = new ArrayList<>();
        for (Course course : allCourses) {
            if (course.getInstructorId() == instructor.getUserId()) {
                instructorCourses.add(course);
            }
        }
    }

    private void initComponents() {
        setTitle("Performance Analytics & Insights");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 204, 204));
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Header
        JLabel headerLabel = new JLabel("Student Performance Analytics", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Center Panel with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Overview Tab
        JPanel overviewPanel = createOverviewPanel();
        tabbedPane.addTab("Course Overview", overviewPanel);

        // Detailed Performance Tab
        JPanel detailsPanel = createDetailsPanel();
        tabbedPane.addTab("Student Details", detailsPanel);

        // Charts Tab
        JPanel chartsPanel = createChartsPanel();
        tabbedPane.addTab("Performance Charts", chartsPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Bottom Panel with Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(0, 204, 204));

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            dispose();
            new InstructorBoard(instructor).setVisible(true);
        });
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(204, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Course Selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(204, 255, 255));

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> courseComboBox = new JComboBox<>();
        courseComboBox.addItem("-- Select Course --");

        for (Course course : instructorCourses) {
            courseComboBox.addItem(course.getCourseId() + " - " + course.getTitle());
        }

        topPanel.add(courseLabel);
        topPanel.add(courseComboBox);

        // Statistics Panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        statsPanel.setBackground(new Color(204, 255, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel totalStudentsLabel = new JLabel("Total Enrolled: 0", SwingConstants.CENTER);
        JLabel avgCompletionLabel = new JLabel("Avg Completion: 0%", SwingConstants.CENTER);
        JLabel avgScoreLabel = new JLabel("Avg Quiz Score: 0%", SwingConstants.CENTER);
        JLabel totalLessonsLabel = new JLabel("Total Lessons: 0", SwingConstants.CENTER);
        JLabel activeStudentsLabel = new JLabel("Active Students: 0", SwingConstants.CENTER);
        JLabel lessonsWithQuizLabel = new JLabel("Lessons with Quiz: 0", SwingConstants.CENTER);

        Font statFont = new Font("Arial", Font.BOLD, 16);
        totalStudentsLabel.setFont(statFont);
        avgCompletionLabel.setFont(statFont);
        avgScoreLabel.setFont(statFont);
        totalLessonsLabel.setFont(statFont);
        activeStudentsLabel.setFont(statFont);
        lessonsWithQuizLabel.setFont(statFont);

        statsPanel.add(createStatCard(totalStudentsLabel, new Color(52, 152, 219)));
        statsPanel.add(createStatCard(avgCompletionLabel, new Color(46, 204, 113)));
        statsPanel.add(createStatCard(avgScoreLabel, new Color(155, 89, 182)));
        statsPanel.add(createStatCard(totalLessonsLabel, new Color(230, 126, 34)));
        statsPanel.add(createStatCard(activeStudentsLabel, new Color(26, 188, 156)));
        statsPanel.add(createStatCard(lessonsWithQuizLabel, new Color(231, 76, 60)));

        // Course Selection Listener
        courseComboBox.addActionListener(e -> {
            String selected = (String) courseComboBox.getSelectedItem();
            if (selected != null && !selected.equals("-- Select Course --")) {
                String courseId = selected.split(" - ")[0];
                updateOverviewStats(courseId, totalStudentsLabel, avgCompletionLabel,
                        avgScoreLabel, totalLessonsLabel, activeStudentsLabel,
                        lessonsWithQuizLabel);
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(JLabel label, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 3),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        label.setForeground(color);
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private void updateOverviewStats(String courseId, JLabel totalStudentsLabel,
            JLabel avgCompletionLabel, JLabel avgScoreLabel,
            JLabel totalLessonsLabel, JLabel activeStudentsLabel,
            JLabel lessonsWithQuizLabel) {
        Course course = findCourseById(courseId);
        if (course == null)
            return;

        List<Student> allStudents = usersDB.getStudents();
        List<Student> enrolledStudents = new ArrayList<>();

        for (Student student : allStudents) {
            if (student.getEnrolledCourses() != null &&
                    student.getEnrolledCourses().contains(courseId)) {
                enrolledStudents.add(student);
            }
        }

        int totalStudents = enrolledStudents.size();
        int totalLessons = course.getLessons().size();
        int activeStudents = 0;
        int lessonsWithQuiz = 0;
        double totalCompletion = 0;

        // Count lessons with quizzes
        for (Lesson lesson : course.getLessons()) {
            if (lesson.hasQuiz()) {
                lessonsWithQuiz++;
            }
        }

        for (Student student : enrolledStudents) {
            int completed = countCompletedLessons(student, courseId);
            if (completed > 0) {
                activeStudents++;
            }

            if (totalLessons > 0) {
                totalCompletion += (completed * 100.0) / totalLessons;
            }
        }

        double avgCompletion = totalStudents > 0 ? totalCompletion / totalStudents : 0;
        double avgScore = 0; // Would need quiz attempt data

        totalStudentsLabel.setText("Total Enrolled: " + totalStudents);
        avgCompletionLabel.setText(String.format("Avg Completion: %.1f%%", avgCompletion));
        avgScoreLabel.setText(String.format("Avg Quiz Score: %.1f%%", avgScore));
        totalLessonsLabel.setText("Total Lessons: " + totalLessons);
        activeStudentsLabel.setText("Active Students: " + activeStudents);
        lessonsWithQuizLabel.setText("Lessons with Quiz: " + lessonsWithQuiz);
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(204, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Course Selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(204, 255, 255));

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> courseComboBox = new JComboBox<>();
        courseComboBox.addItem("-- Select Course --");

        for (Course course : instructorCourses) {
            courseComboBox.addItem(course.getCourseId() + " - " + course.getTitle());
        }

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));

        topPanel.add(courseLabel);
        topPanel.add(courseComboBox);
        topPanel.add(refreshButton);

        // Table
        String[] columns = { "Student ID", "Student Name", "Completed Lessons",
                "Total Lessons", "Completion %", "Status" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(0, 153, 153));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Action Listeners
        courseComboBox.addActionListener(e -> {
            String selected = (String) courseComboBox.getSelectedItem();
            if (selected != null && !selected.equals("-- Select Course --")) {
                String courseId = selected.split(" - ")[0];
                loadStudentDetails(courseId, tableModel);
            } else {
                tableModel.setRowCount(0);
            }
        });

        refreshButton.addActionListener(e -> {
            String selected = (String) courseComboBox.getSelectedItem();
            if (selected != null && !selected.equals("-- Select Course --")) {
                String courseId = selected.split(" - ")[0];
                loadStudentDetails(courseId, tableModel);
                JOptionPane.showMessageDialog(this, "Data refreshed successfully!");
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadStudentDetails(String courseId, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        Course course = findCourseById(courseId);
        if (course == null)
            return;

        List<Student> allStudents = usersDB.getStudents();
        int totalLessons = course.getLessons().size();

        for (Student student : allStudents) {
            if (student.getEnrolledCourses() != null &&
                    student.getEnrolledCourses().contains(courseId)) {

                int completedLessons = countCompletedLessons(student, courseId);
                double completionPercentage = totalLessons > 0 ? (completedLessons * 100.0) / totalLessons : 0;

                String status;
                if (completionPercentage >= 100) {
                    status = "Completed";
                } else if (completionPercentage >= 50) {
                    status = "In Progress";
                } else if (completionPercentage > 0) {
                    status = "Started";
                } else {
                    status = "Not Started";
                }

                tableModel.addRow(new Object[] {
                        student.getUserId(),
                        student.getUserName(),
                        completedLessons,
                        totalLessons,
                        String.format("%.1f%%", completionPercentage),
                        status
                });
            }
        }
    }

    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(204, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Course Selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(204, 255, 255));

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> courseComboBox = new JComboBox<>();
        courseComboBox.addItem("-- Select Course --");

        for (Course course : instructorCourses) {
            courseComboBox.addItem(course.getCourseId() + " - " + course.getTitle());
        }

        JButton generateButton = new JButton("Generate Charts");
        generateButton.setFont(new Font("Arial", Font.BOLD, 12));

        topPanel.add(courseLabel);
        topPanel.add(courseComboBox);
        topPanel.add(generateButton);

        // Charts Container
        JPanel chartsContainer = new JPanel(new GridLayout(2, 1, 10, 10));
        chartsContainer.setBackground(new Color(204, 255, 255));

        JPanel completionChart = new JPanel();
        completionChart.setBackground(Color.WHITE);
        completionChart.setBorder(BorderFactory.createTitledBorder("Lesson Completion Chart"));

        JPanel performanceChart = new JPanel();
        performanceChart.setBackground(Color.WHITE);
        performanceChart.setBorder(BorderFactory.createTitledBorder("Student Performance Distribution"));

        chartsContainer.add(completionChart);
        chartsContainer.add(performanceChart);

        // Generate Button Listener
        generateButton.addActionListener(e -> {
            String selected = (String) courseComboBox.getSelectedItem();
            if (selected != null && !selected.equals("-- Select Course --")) {
                String courseId = selected.split(" - ")[0];
                generateCharts(courseId, completionChart, performanceChart);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course first!");
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(chartsContainer, BorderLayout.CENTER);

        return panel;
    }

    private void generateCharts(String courseId, JPanel completionChartPanel, JPanel performanceChartPanel) {
        Course course = findCourseById(courseId);
        if (course == null)
            return;

        List<Student> allStudents = usersDB.getStudents();
        List<Student> enrolledStudents = new ArrayList<>();

        for (Student student : allStudents) {
            if (student.getEnrolledCourses() != null &&
                    student.getEnrolledCourses().contains(courseId)) {
                enrolledStudents.add(student);
            }
        }

        // Generate Completion Chart
        completionChartPanel.removeAll();
        completionChartPanel.setLayout(new BorderLayout());
        JPanel completionBars = createCompletionBarChart(course, enrolledStudents);
        completionChartPanel.add(completionBars, BorderLayout.CENTER);

        // Generate Performance Chart
        performanceChartPanel.removeAll();
        performanceChartPanel.setLayout(new BorderLayout());
        JPanel performanceBars = createPerformanceChart(course, enrolledStudents);
        performanceChartPanel.add(performanceBars, BorderLayout.CENTER);

        completionChartPanel.revalidate();
        completionChartPanel.repaint();
        performanceChartPanel.revalidate();
        performanceChartPanel.repaint();
    }

    private JPanel createCompletionBarChart(Course course, List<Student> students) {
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int margin = 50;
                int chartWidth = width - 2 * margin;
                int chartHeight = height - 2 * margin;

                // Draw axes
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(margin, height - margin, width - margin, height - margin); // X-axis
                g2d.drawLine(margin, margin, margin, height - margin); // Y-axis

                // Draw labels
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("Lessons", width / 2 - 30, height - 10);
                g2d.rotate(-Math.PI / 2);
                g2d.drawString("Completion Count", -height / 2 - 50, 20);
                g2d.rotate(Math.PI / 2);

                // Calculate completion data
                int totalLessons = course.getLessons().size();
                if (totalLessons == 0 || students.isEmpty()) {
                    g2d.drawString("No data available", width / 2 - 50, height / 2);
                    return;
                }

                int[] completionCounts = new int[totalLessons];
                for (Student student : students) {
                    int completed = countCompletedLessons(student, course.getCourseId());
                    for (int i = 0; i < Math.min(completed, totalLessons); i++) {
                        completionCounts[i]++;
                    }
                }

                // Draw bars
                int barWidth = Math.max(20, chartWidth / (totalLessons * 2));
                int maxCount = students.size();

                for (int i = 0; i < totalLessons; i++) {
                    int barHeight = maxCount > 0 ? (completionCounts[i] * chartHeight) / maxCount : 0;
                    int x = margin + (i * chartWidth) / totalLessons + barWidth / 2;
                    int y = height - margin - barHeight;

                    // Draw bar
                    g2d.setColor(new Color(52, 152, 219));
                    g2d.fillRect(x, y, barWidth, barHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, barWidth, barHeight);

                    // Draw count on top
                    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                    String countStr = String.valueOf(completionCounts[i]);
                    int strWidth = g2d.getFontMetrics().stringWidth(countStr);
                    g2d.drawString(countStr, x + (barWidth - strWidth) / 2, y - 5);

                    // Draw lesson number
                    g2d.drawString("L" + (i + 1), x + (barWidth - 15) / 2, height - margin + 20);
                }
            }
        };

        chartPanel.setPreferredSize(new Dimension(600, 250));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    private JPanel createPerformanceChart(Course course, List<Student> students) {
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int margin = 50;
                int chartWidth = width - 2 * margin;
                int chartHeight = height - 2 * margin;

                // Draw axes
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(margin, height - margin, width - margin, height - margin); // X-axis
                g2d.drawLine(margin, margin, margin, height - margin); // Y-axis

                // Draw labels
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("Performance Categories", width / 2 - 80, height - 10);
                g2d.rotate(-Math.PI / 2);
                g2d.drawString("Number of Students", -height / 2 - 60, 20);
                g2d.rotate(Math.PI / 2);

                if (students.isEmpty()) {
                    g2d.drawString("No students enrolled", width / 2 - 70, height / 2);
                    return;
                }

                // Categorize students
                int[] categories = new int[4]; // Not Started, Started, In Progress, Completed
                String[] categoryNames = { "Not Started", "Started", "In Progress", "Completed" };
                Color[] colors = {
                        new Color(231, 76, 60), // Red
                        new Color(230, 126, 34), // Orange
                        new Color(241, 196, 15), // Yellow
                        new Color(46, 204, 113) // Green
                };

                int totalLessons = course.getLessons().size();
                for (Student student : students) {
                    if (totalLessons == 0) {
                        categories[0]++;
                        continue;
                    }

                    int completed = countCompletedLessons(student, course.getCourseId());
                    double percentage = (completed * 100.0) / totalLessons;

                    if (percentage >= 100)
                        categories[3]++;
                    else if (percentage >= 50)
                        categories[2]++;
                    else if (percentage > 0)
                        categories[1]++;
                    else
                        categories[0]++;
                }

                // Draw bars
                int barWidth = Math.min(100, chartWidth / 6);
                int maxCount = students.size();

                for (int i = 0; i < 4; i++) {
                    int barHeight = maxCount > 0 ? (categories[i] * chartHeight) / maxCount : 0;
                    int x = margin + ((i + 1) * chartWidth) / 5;
                    int y = height - margin - barHeight;

                    // Draw bar
                    g2d.setColor(colors[i]);
                    g2d.fillRect(x, y, barWidth, barHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, y, barWidth, barHeight);

                    // Draw count
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    String countStr = String.valueOf(categories[i]);
                    int strWidth = g2d.getFontMetrics().stringWidth(countStr);
                    g2d.drawString(countStr, x + (barWidth - strWidth) / 2, y - 5);

                    // Draw category name
                    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                    String name = categoryNames[i];
                    strWidth = g2d.getFontMetrics().stringWidth(name);
                    g2d.drawString(name, x + (barWidth - strWidth) / 2, height - margin + 20);
                }
            }
        };

        chartPanel.setPreferredSize(new Dimension(600, 250));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    // Helper Methods
    private Course findCourseById(String courseId) {
        for (Course course : instructorCourses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private int countCompletedLessons(Student student, String courseId) {
        if (student.getCompletedLessons() == null) {
            return 0;
        }

        int count = 0;
        String prefix = courseId + ":";
        for (String completedLesson : student.getCompletedLessons()) {
            if (completedLesson.startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }
}