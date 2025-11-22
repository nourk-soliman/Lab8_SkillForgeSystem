/*
 * Instructor Insights Chart - Visual Analytics for Student Performance
 * Displays bar charts and line charts for course analytics
 */
package gui.instructor;

import model.course.Course;
import model.course.Lesson;
import model.user.Instructor;
import model.user.Student;
import logic.userRole.InstructorRole;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Visual chart window for displaying student performance analytics
 * Shows bar charts for quiz scores and line charts for completion rates
 * 
 * @author moaz
 */
public class Charts extends JFrame {

    private Course course;
    private Instructor instructor;
    private InstructorRole instructorRole;

    // Chart dimensions
    private static final int CHART_WIDTH = 800;
    private static final int CHART_HEIGHT = 400;
    private static final int MARGIN = 60;
    private static final int BAR_WIDTH = 40;

    public Charts(Course course, Instructor instructor) {
        this.course = course;
        this.instructor = instructor;
        this.instructorRole = new InstructorRole();

        initComponents();
    }

    private void initComponents() {
        setTitle("Student Performance Analytics - " + course.getTitle());
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with light blue background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label
        JLabel titleLabel = new JLabel("📊 Student Performance Analytics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabbed pane for different chart types
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Add chart panels
        tabbedPane.addTab("Quiz Scores (Bar Chart)", createBarChartPanel());
        tabbedPane.addTab("Completion Progress (Line Chart)", createLineChartPanel());
        tabbedPane.addTab("Lesson Analytics", createLessonAnalyticsPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Close button at bottom
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setBackground(new Color(70, 130, 255));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(closeBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Creates a custom panel that draws a bar chart for quiz scores
    private JPanel createBarChartPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                drawBarChart(g2d);
            }
        };
    }

    // Draws bar chart showing average quiz scores for each student
    private void drawBarChart(Graphics2D g2d) {
        // Get student data
        List<Integer> studentIds = course.getStudents();
        if (studentIds.isEmpty()) {
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            g2d.drawString("No student data available", CHART_WIDTH / 2 - 100, CHART_HEIGHT / 2);
            return;
        }

        // Prepare data
        Map<String, Double> studentScores = new HashMap<>();
        for (Integer studentId : studentIds) {
            Student student = instructorRole.getStudentById(studentId);
            if (student != null) {
                double avgScore = calculateAverageScore(student);
                studentScores.put(student.getUserName(), avgScore);
            }
        }

        // Draw chart title
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Average Quiz Scores by Student", MARGIN, 30);

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        // Y-axis (vertical)
        g2d.drawLine(MARGIN, MARGIN, MARGIN, CHART_HEIGHT - MARGIN);
        // X-axis (horizontal)
        g2d.drawLine(MARGIN, CHART_HEIGHT - MARGIN, CHART_WIDTH - MARGIN, CHART_HEIGHT - MARGIN);

        // Draw Y-axis labels (0-100%)
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int i = 0; i <= 10; i++) {
            int y = CHART_HEIGHT - MARGIN - (i * (CHART_HEIGHT - 2 * MARGIN) / 10);
            g2d.drawString(i * 10 + "%", MARGIN - 40, y + 5);
            // Draw grid line
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawLine(MARGIN, y, CHART_WIDTH - MARGIN, y);
            g2d.setColor(Color.BLACK);
        }

        // Draw bars for each student
        int barIndex = 0;
        int totalBars = studentScores.size();
        int spacing = Math.min(BAR_WIDTH, (CHART_WIDTH - 2 * MARGIN) / (totalBars + 1));

        Color[] barColors = {
                new Color(54, 162, 235),
                new Color(255, 99, 132),
                new Color(75, 192, 192),
                new Color(255, 206, 86),
                new Color(153, 102, 255)
        };

        for (Map.Entry<String, Double> entry : studentScores.entrySet()) {
            String studentName = entry.getKey();
            double score = entry.getValue();

            // Calculate bar position and height
            int x = MARGIN + (barIndex + 1) * spacing;
            int barHeight = (int) ((score / 100.0) * (CHART_HEIGHT - 2 * MARGIN));
            int y = CHART_HEIGHT - MARGIN - barHeight;

            // Draw bar with gradient
            Color barColor = barColors[barIndex % barColors.length];
            GradientPaint gradient = new GradientPaint(x, y, barColor.brighter(), x, y + barHeight, barColor);
            g2d.setPaint(gradient);
            g2d.fillRect(x, y, BAR_WIDTH, barHeight);

            // Draw bar outline
            g2d.setColor(barColor.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, BAR_WIDTH, barHeight);

            // Draw score on top of bar
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 11));
            String scoreText = String.format("%.1f%%", score);
            g2d.drawString(scoreText, x + 5, y - 5);

            // Draw student name below bar (rotated for space)
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            Graphics2D g2dRotated = (Graphics2D) g2d.create();
            g2dRotated.rotate(-Math.PI / 4, x + BAR_WIDTH / 2, CHART_HEIGHT - MARGIN + 15);
            g2dRotated.drawString(studentName, x + BAR_WIDTH / 2, CHART_HEIGHT - MARGIN + 15);
            g2dRotated.dispose();

            barIndex++;
        }

        // Draw axis labels
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Students", CHART_WIDTH / 2 - 30, CHART_HEIGHT - 10);

        Graphics2D g2dRotated = (Graphics2D) g2d.create();
        g2dRotated.rotate(-Math.PI / 2, 15, CHART_HEIGHT / 2);
        g2dRotated.drawString("Average Score (%)", 15, CHART_HEIGHT / 2);
        g2dRotated.dispose();
    }

    // Creates a custom panel that draws a line chart for completion progress
    private JPanel createLineChartPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                drawLineChart(g2d);
            }
        };
    }

    // Draws line chart showing lesson completion progress
    private void drawLineChart(Graphics2D g2d) {
        List<Integer> studentIds = course.getStudents();
        if (studentIds.isEmpty()) {
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            g2d.drawString("No student data available", CHART_WIDTH / 2 - 100, CHART_HEIGHT / 2);
            return;
        }

        int totalLessons = course.getLessons().size();
        if (totalLessons == 0) {
            g2d.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            g2d.drawString("No lessons in this course", CHART_WIDTH / 2 - 100, CHART_HEIGHT / 2);
            return;
        }

        // Draw chart title
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Lesson Completion Progress", MARGIN, 30);

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(MARGIN, MARGIN, MARGIN, CHART_HEIGHT - MARGIN);
        g2d.drawLine(MARGIN, CHART_HEIGHT - MARGIN, CHART_WIDTH - MARGIN, CHART_HEIGHT - MARGIN);

        // Draw Y-axis labels (0-100%)
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int i = 0; i <= 10; i++) {
            int y = CHART_HEIGHT - MARGIN - (i * (CHART_HEIGHT - 2 * MARGIN) / 10);
            g2d.drawString(i * 10 + "%", MARGIN - 40, y + 5);
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawLine(MARGIN, y, CHART_WIDTH - MARGIN, y);
            g2d.setColor(Color.BLACK);
        }

        // Draw X-axis labels (lesson numbers)
        int xSpacing = (CHART_WIDTH - 2 * MARGIN) / totalLessons;
        for (int i = 0; i < totalLessons; i++) {
            int x = MARGIN + i * xSpacing + xSpacing / 2;
            g2d.drawString("L" + (i + 1), x - 10, CHART_HEIGHT - MARGIN + 20);
        }

        // Draw lines for each student
        Color[] lineColors = {
                new Color(54, 162, 235),
                new Color(255, 99, 132),
                new Color(75, 192, 192),
                new Color(255, 206, 86),
                new Color(153, 102, 255),
                new Color(255, 159, 64)
        };

        int studentIndex = 0;
        for (Integer studentId : studentIds) {
            Student student = instructorRole.getStudentById(studentId);
            if (student == null)
                continue;

            // Calculate completion for each lesson
            List<Integer> completionData = new ArrayList<>();
            int completedCount = 0;

            for (Lesson lesson : course.getLessons()) {
                if (isLessonCompleted(student, course.getCourseId(), lesson.getLessonId())) {
                    completedCount++;
                }
                int completionPercent = (completedCount * 100) / totalLessons;
                completionData.add(completionPercent);
            }

            // Draw line connecting points
            Color lineColor = lineColors[studentIndex % lineColors.length];
            g2d.setColor(lineColor);
            g2d.setStroke(new BasicStroke(3));

            for (int i = 0; i < completionData.size() - 1; i++) {
                int x1 = MARGIN + i * xSpacing + xSpacing / 2;
                int y1 = CHART_HEIGHT - MARGIN - (completionData.get(i) * (CHART_HEIGHT - 2 * MARGIN) / 100);
                int x2 = MARGIN + (i + 1) * xSpacing + xSpacing / 2;
                int y2 = CHART_HEIGHT - MARGIN - (completionData.get(i + 1) * (CHART_HEIGHT - 2 * MARGIN) / 100);

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Draw points
            for (int i = 0; i < completionData.size(); i++) {
                int x = MARGIN + i * xSpacing + xSpacing / 2;
                int y = CHART_HEIGHT - MARGIN - (completionData.get(i) * (CHART_HEIGHT - 2 * MARGIN) / 100);

                g2d.fillOval(x - 5, y - 5, 10, 10);
            }

            studentIndex++;
            if (studentIndex >= 6)
                break; // Limit to 6 students for readability
        }

        // Draw legend
        int legendX = CHART_WIDTH - MARGIN - 150;
        int legendY = MARGIN + 20;
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        studentIndex = 0;
        for (Integer studentId : studentIds) {
            Student student = instructorRole.getStudentById(studentId);
            if (student == null)
                continue;

            Color lineColor = lineColors[studentIndex % lineColors.length];
            g2d.setColor(lineColor);
            g2d.fillRect(legendX, legendY + studentIndex * 20, 15, 15);

            g2d.setColor(Color.BLACK);
            g2d.drawString(student.getUserName(), legendX + 20, legendY + studentIndex * 20 + 12);

            studentIndex++;
            if (studentIndex >= 6)
                break;
        }

        // Draw axis labels
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Lessons", CHART_WIDTH / 2 - 30, CHART_HEIGHT - 10);

        Graphics2D g2dRotated = (Graphics2D) g2d.create();
        g2dRotated.rotate(-Math.PI / 2, 15, CHART_HEIGHT / 2);
        g2dRotated.drawString("Completion (%)", 15, CHART_HEIGHT / 2);
        g2dRotated.dispose();
    }

    // Creates panel showing per-lesson analytics
    private JPanel createLessonAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Table showing lesson completion rates
        String[] columnNames = { "Lesson ID", "Lesson Title", "Students Completed", "Completion Rate",
                "Avg Quiz Score" };
        Object[][] data = prepareLessonData();

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(70, 130, 255));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Summary label
        JLabel summaryLabel = new JLabel("Detailed per-lesson analytics and completion rates");
        summaryLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        summaryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(summaryLabel, BorderLayout.NORTH);

        return panel;
    }

    // Prepares data for lesson analytics table
    private Object[][] prepareLessonData() {
        List<Lesson> lessons = course.getLessons();
        List<Integer> studentIds = course.getStudents();
        int totalStudents = studentIds.size();

        Object[][] data = new Object[lessons.size()][5];

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            int completedCount = 0;
            double totalQuizScore = 0;
            int quizCount = 0;

            // Count how many students completed this lesson
            for (Integer studentId : studentIds) {
                Student student = instructorRole.getStudentById(studentId);
                if (student != null) {
                    if (isLessonCompleted(student, course.getCourseId(), lesson.getLessonId())) {
                        completedCount++;
                    }

                    // Get quiz score for this lesson
                    Double quizScore = getQuizScore(student, course.getCourseId(), lesson.getLessonId());
                    if (quizScore != null) {
                        totalQuizScore += quizScore;
                        quizCount++;
                    }
                }
            }

            double completionRate = totalStudents > 0 ? (completedCount * 100.0) / totalStudents : 0;
            double avgQuizScore = quizCount > 0 ? totalQuizScore / quizCount : 0;

            data[i][0] = lesson.getLessonId();
            data[i][1] = lesson.getTitle();
            data[i][2] = completedCount + " / " + totalStudents;
            data[i][3] = String.format("%.1f%%", completionRate);
            data[i][4] = quizCount > 0 ? String.format("%.1f%%", avgQuizScore) : "N/A";
        }

        return data;
    }

    // ===== HELPER METHODS =====

    // Calculate average quiz score for a student across all lessons
    private double calculateAverageScore(Student student) {
        if (student.getQuizResults() == null) {
            return 0.0;
        }

        Map<String, Integer> quizResults = student.getQuizResults();
        int totalScore = 0;
        int quizCount = 0;

        for (String quizKey : quizResults.keySet()) {
            if (quizKey.startsWith(course.getCourseId() + ":")) {
                totalScore += quizResults.get(quizKey);
                quizCount++;
            }
        }

        return quizCount > 0 ? (double) totalScore / quizCount : 0.0;
    }

    // Check if a specific lesson is completed by a student
    private boolean isLessonCompleted(Student student, String courseId, String lessonId) {
        if (student.getCompletedLessons() == null) {
            return false;
        }
        String lessonKey = courseId + ":" + lessonId;
        return student.getCompletedLessons().contains(lessonKey);
    }

    // Get quiz score for a specific lesson
    private Double getQuizScore(Student student, String courseId, String lessonId) {
        if (student.getQuizResults() == null) {
            return null;
        }
        String quizKey = courseId + ":" + lessonId;
        Integer score = student.getQuizResults().get(quizKey);
        return score != null ? score.doubleValue() : null;
    }
}