package gui.dashboards;

import gui.instructor.CourseLessonManager;
import gui.instructor.PerformanceAnalytics;
import gui.loginAndSignUp.Login;
import gui.instructor.ViewStudents;
import model.user.Instructor;
import logic.authentication.PasswordService;
import logic.authentication.UserService;

public class InstructorBoard extends javax.swing.JFrame {
    private Instructor i;

    public InstructorBoard(Instructor i) {
        this.i = i;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        title = new java.awt.Label();
        course = new java.awt.Button();
        view = new java.awt.Button();
        insights = new java.awt.Button();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        title.setAlignment(java.awt.Label.CENTER);
        title.setFont(new java.awt.Font("High Tower Text", 2, 24));
        title.setText("Instructor Dashboard");

        course.setBackground(new java.awt.Color(204, 255, 255));
        course.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18));
        course.setLabel("Course Management");
        course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseActionPerformed(evt);
            }
        });

        view.setBackground(new java.awt.Color(204, 255, 255));
        view.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18));
        view.setLabel("View Students");
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });

        insights.setBackground(new java.awt.Color(204, 255, 255));
        insights.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16));
        insights.setLabel("Performance Insights");
        insights.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insightsActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14));
        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(250, 250, 250)
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 300,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(view, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(insights, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(view, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(insights, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }

    private void insightsActionPerformed(java.awt.event.ActionEvent evt) {
        if (i == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: Instructor data is missing.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.dispose();
        new PerformanceAnalytics(i).setVisible(true);
    }

    private void courseActionPerformed(java.awt.event.ActionEvent evt) {
        if (i == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: Instructor data is missing.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.dispose();
        new CourseLessonManager(i).setVisible(true);
    }

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        new ViewStudents(i).setVisible(true);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private java.awt.Button course;
    private java.awt.Button insights;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label title;
    private java.awt.Button view;
}