/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import json.JsonUserDatabase;
import model.course.CourseProgress;
import model.course.Lesson;
import model.course.LessonProgress;
import model.quiz.Quiz;
import model.quiz.QuizAttempt;
import model.quiz.QuizQuestion;
import model.user.Student;

/**
 *
 * @author Nour
 */
public class QuizPage extends javax.swing.JFrame {
    private Student student;
    private Lesson lesson;
    private int currentQuestionIndex = 0;
    private float score=0;
         private ButtonGroup group;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuizPage.class.getName());

    /**
     * Creates new form QuizPage
     */
    public QuizPage(Student student,Lesson lesson) {
        this.lesson=lesson;
        this.student=student;
        initComponents();
        setLocationRelativeTo(null);
        optionsPanel.setLayout(new javax.swing.BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

     nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextButton();
            }
        });  
     uploadQuiz();
    }
public void uploadQuiz() {
    Quiz quiz = lesson.getQuiz();
    ArrayList<QuizQuestion> questions = quiz.getQuestions();

    // If no questions exist
    if (questions.isEmpty()) {
        questionLabel.setText("No questions found.");
        return;
    }

    // Get the current question
    QuizQuestion q = questions.get(currentQuestionIndex);

    // Update question text
    questionLabel.setText(q.getQuestion());

    // Clear old options
    optionsPanel.removeAll();
    
    // Make a fresh button group
    group = new ButtonGroup();

    // Add options
    for (int i = 0; i < q.getOptions().size(); i++) {
        String option = q.getOptions().get(i);
        JRadioButton optionBtn = new JRadioButton(option);
        optionBtn.setActionCommand(String.valueOf(i)); // index of option
        group.add(optionBtn);
        optionsPanel.add(optionBtn);
    }

    // Update button text (Next or Submit)
    if (currentQuestionIndex == questions.size() - 1) {
        nextButton.setText("Submit");
    } else {
        nextButton.setText("Next");
    }

    optionsPanel.revalidate();
    optionsPanel.repaint();
}
private void handleNextButton() {
    Quiz quiz = lesson.getQuiz();
    ArrayList<QuizQuestion> questions = quiz.getQuestions();
    boolean flag=false;
    // Get selected answer
    String selected;
    if(group.getSelection() != null)
            selected=group.getSelection().getActionCommand();
                    else selected=null;

    if (selected == null) {
        JOptionPane.showMessageDialog(this, "Please select an answer.");
        return;
    }

    int selectedIndex = Integer.parseInt(selected);
    QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
    if(selectedIndex==currentQuestion.getAnswerIndex())
        score++;
    System.out.println("Student selected option " + selectedIndex);

    if (currentQuestionIndex < questions.size() - 1) {
        currentQuestionIndex++;
        uploadQuiz();
    } else {
        float finalScore=(float)(score/questions.size())*100;
        if(finalScore<50)
        {JOptionPane.showMessageDialog(this, "Quiz completed!\nStudent Score is: "+ finalScore+"\nRetry Policy: "+quiz.getRetryPolicy());}
        else JOptionPane.showMessageDialog(this, "Quiz completed!\nStudent Score is: "+ finalScore);
        this.dispose();
        StringBuilder review = new StringBuilder();
review.append("Correct Answers:\n\n");

for (int i = 0; i < questions.size(); i++) {
    QuizQuestion q = questions.get(i);
    String correct = q.getOptions().get(q.getAnswerIndex());

    review.append((i+1) + ". ")
          .append(q.getQuestion()).append("\n")
          .append(" → Correct answer: ").append(correct)
          .append("\n\n");
}

JOptionPane.showMessageDialog(this,
        review.toString(),
        "Answer Review",
        JOptionPane.INFORMATION_MESSAGE);

        
        List<CourseProgress> courseProgress=student.getProgress();
    
        for(CourseProgress c:courseProgress)
        {List<LessonProgress>lessonProgress=c.getLessonProgress();
            for(LessonProgress l:lessonProgress )
                if (l.getLessonId().equals(lesson.getLessonId()))
              {flag=true;
                  l.setQuizScore(finalScore);
                  ArrayList<QuizAttempt> attempts=l.getAttempts();
                  QuizAttempt attempt=new QuizAttempt(finalScore);
                  attempts.add(attempt);
                  l.setAttempts(attempts);
               if(finalScore>=50)
               {l.setCompleted(true);}
                else{l.setCompleted(false);}
                    break;}
        
        if(flag) break;
        }
       JsonUserDatabase reader = new JsonUserDatabase("users.json");
       List<Student> students = reader.getStudents();
      for (int i = 0; i < students.size(); i++) {
    if (students.get(i).getUserId() == student.getUserId()) {  
        students.set(i, student);   // update
        break;
    }
}

reader.setStudents(students);
reader.saveToFile("users.json");


        System.out.println("data saved successfully");
        this.dispose();
        CompleteLesson completeLesson=new CompleteLesson(student);
        completeLesson.setVisible(true);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        questionLabel = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        questionLabel.setText("jLabel1");

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );

        nextButton.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(questionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 319, Short.MAX_VALUE)
                        .addComponent(nextButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nextButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JLabel questionLabel;
    // End of variables declaration//GEN-END:variables
}
