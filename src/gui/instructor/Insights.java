package gui.instructor;

import gui.dashboards.InstructorBoard;
import model.user.Instructor;

/**
 * Insights entry point - redirects to PerformanceAnalytics
 * 
 * @author Nour
 */
public class Insights extends javax.swing.JFrame {
    private Instructor instructor;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Insights.class.getName());

    public Insights(Instructor instructor) {
        this.instructor = instructor;
        // Redirect to PerformanceAnalytics
        this.dispose();
        new PerformanceAnalytics(instructor).setVisible(true);
    }

    public Insights() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE));

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Insights().setVisible(true));
    }
}