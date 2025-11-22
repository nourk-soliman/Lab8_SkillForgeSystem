/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.userRole;

import model.user.Student;
import model.user.Instructor;
import model.course.Course;
import model.course.Lesson;
import model.quiz.Quiz;
import model.quiz.QuizQuestion;

import json.JsonUserDatabase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;
import json.JsonCoursesDatabase;
import model.course.CourseStatus;

/**
 * Instructor Role with Course, Lesson, and Quiz Management
 * 
 * @author moaz
 */
public class InstructorRole {

    private final JsonCoursesDatabase coursesReader;
    private final JsonUserDatabase userReader;

    // Constants
    private static final String COURSES_FILE = "courses.json";
    private static final String USERS_FILE = "users.json";
    private static final String COURSE_NOT_FOUND_MSG = "Course not found or access denied!";

    public InstructorRole() {
        this.coursesReader = new JsonCoursesDatabase(COURSES_FILE);
        this.userReader = new JsonUserDatabase(USERS_FILE);
    }

    // Student Management Methods
    public List<Student> viewEnrolledStudents(Instructor instructor) {
        List<Student> allStudents = userReader.getStudents();
        List<Student> enrolledStudents = new ArrayList<>();

        for (Student student : allStudents) {
            for (String enrolledCourse : student.getEnrolledCourses()) {
                if (instructor.getCreatedCourses().contains(enrolledCourse)) {
                    enrolledStudents.add(student);
                    break;
                }
            }
        }
        return enrolledStudents;
    }

    public int countEnrolledCourses(Instructor instructor, Student student) {
        int count = 0;
        for (String enrolledCourse : student.getEnrolledCourses()) {
            if (instructor.getCreatedCourses().contains(enrolledCourse)) {
                count++;
            }
        }
        return count;
    }

    // Course Management Methods
    public List<Course> getInstructorCourses(Instructor instructor) {
        List<Course> allCourses = coursesReader.getCourses();
        List<Course> instructorCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.getInstructorId() == instructor.getUserId()) {
                instructorCourses.add(course);
            }
        }
        return instructorCourses;
    }

    public void createNewCourse(Instructor instructor) {
        String courseId = getInput("Enter Course ID:");
        if (courseId == null)
            return;

        if (isCourseIdExists(courseId)) {
            showError("Course ID already exists!");
            return;
        }

        String title = getInput("Enter Course Title:");
        if (title == null)
            return;

        String description = getInput("Enter Course Description:");

        Course newCourse = new Course(courseId, title, instructor.getUserId(),
                description != null ? description : "",
                new ArrayList<>(), new ArrayList<>());

        // Update instructor's created courses
        instructor.getCreatedCourses().add(courseId);
        updateInstructorData(instructor);
        newCourse.setApprovalStatus(CourseStatus.PENDING);

        // Add course to courses list and save
        addCourseToFile(newCourse);
        showSuccess("Course created successfully!");
    }

    public void editCourse(String courseId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        String newTitle = getInput("Enter new title:", course.getTitle());
        if (newTitle == null)
            return;

        String newDescription = getInput("Enter new description:", course.getDescription());

        course.setTitle(newTitle);
        course.setDescription(newDescription != null ? newDescription : course.getDescription());

        updateCourseInFile(course);
        showSuccess("Course updated successfully!");
    }

    public void deleteCourse(String courseId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        int confirm = showConfirmDialog(
                "Are you sure you want to delete this course? This will also delete all lessons.");

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove from instructor's created courses
            instructor.getCreatedCourses().remove(courseId);
            updateInstructorData(instructor);

            // Remove from courses list
            removeCourseFromFile(courseId);
            showSuccess("Course deleted successfully!");
        }
    }

    // Lesson Management Methods
    public List<Lesson> getCourseLessons(String courseId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (course != null && course.getInstructorId() == instructor.getUserId()) {
            return course.getLessons();
        }
        return new ArrayList<>();
    }

    public void addLessonToCourse(String courseId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        String lessonId = getInput("Enter Lesson ID:");
        if (lessonId == null)
            return;

        if (isLessonIdExists(course, lessonId)) {
            showError("Lesson ID already exists in this course!");
            return;
        }

        String title = getInput("Enter Lesson Title:");
        if (title == null)
            return;

        String content = getInput("Enter Lesson Content:");
        if (content == null)
            return;

        // Add optional resources
        List<String> resources = getOptionalResources();

        Lesson newLesson = new Lesson(lessonId, title, content, resources);
        course.getLessons().add(newLesson);

        updateCourseInFile(course);
        showSuccess("Lesson added successfully!");
    }

    public void editLesson(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        Lesson lesson = findLessonInCourse(course, lessonId);
        if (lesson == null) {
            showError("Lesson not found!");
            return;
        }

        String newTitle = getInput("Enter new title:", lesson.getTitle());
        if (newTitle == null)
            return;

        String newContent = getInput("Enter new content:", lesson.getContent());
        if (newContent == null)
            return;

        // Edit optional resources
        int editResources = showConfirmDialog(
                """
                        Do you want to edit the optional resources?
                        Current resources: """ + lesson.getOptionalResources().size());

        List<String> resources = lesson.getOptionalResources();
        if (editResources == JOptionPane.YES_OPTION) {
            resources = getOptionalResources();
        }

        lesson.setTitle(newTitle);
        lesson.setContent(newContent);
        lesson.setOptionalResources(resources);

        updateCourseInFile(course);
        showSuccess("Lesson updated successfully!");
    }

    private List<String> getOptionalResources() {
        List<String> resources = new ArrayList<>();

        int addResources = showConfirmDialog(
                "Do you want to add optional resources (URLs, links, etc.)?");

        if (addResources == JOptionPane.YES_OPTION) {
            JTextArea textArea = new JTextArea(10, 40);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    new Object[] {
                            "Enter optional resources (one per line):",
                            "Example: https://www.example.com/tutorial",
                            scrollPane
                    },
                    "Add Resources",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String text = textArea.getText();
                if (text != null && !text.trim().isEmpty()) {
                    String[] lines = text.split("\n");
                    for (String line : lines) {
                        String trimmed = line.trim();
                        if (!trimmed.isEmpty()) {
                            resources.add(trimmed);
                        }
                    }
                }
            }
        }

        return resources;
    }

    public void deleteLesson(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        int confirm = showConfirmDialog("Are you sure you want to delete this lesson?");

        if (confirm == JOptionPane.YES_OPTION) {
            course.getLessons().removeIf(lesson -> lesson.getLessonId().equals(lessonId));
            updateCourseInFile(course);
            showSuccess("Lesson deleted successfully!");
        }
    }

    // Quiz Management Methods
    public void addQuizToLesson(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        Lesson lesson = findLessonInCourse(course, lessonId);
        if (lesson == null) {
            showError("Lesson not found!");
            return;
        }

        if (lesson.hasQuiz()) {
            int confirm = showConfirmDialog(
                    "This lesson already has a quiz. Do you want to replace it?");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            // Create quiz
            String quizId = courseId + "_" + lessonId + "_quiz";

            // Get passing score
            String passingScoreStr = JOptionPane.showInputDialog(
                    null,
                    "Enter passing score (percentage, 0-100):",
                    "70");

            if (passingScoreStr == null)
                return;

            int passingScore = 70;
            try {
                passingScore = Integer.parseInt(passingScoreStr.trim());
                if (passingScore < 0 || passingScore > 100) {
                    showError("Passing score must be between 0 and 100");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid passing score. Please enter a number between 0 and 100.");
                return;
            }

            // Get time limit
            String timeLimitStr = JOptionPane.showInputDialog(
                    null,
                    "Enter time limit in minutes (0 for no limit):",
                    "0");

            if (timeLimitStr == null)
                return;

            int timeLimit = 0;
            try {
                timeLimit = Integer.parseInt(timeLimitStr.trim());
                if (timeLimit < 0) {
                    showError("Time limit cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid time limit. Please enter a valid number.");
                return;
            }

            ArrayList<QuizQuestion> questions = new ArrayList<>();

            // Add questions loop
            boolean addingQuestions = true;
            int questionNumber = 1;

            while (addingQuestions && questionNumber <= 20) { // Maximum 20 questions
                // Get question text
                JTextArea questionArea = new JTextArea(3, 40);
                questionArea.setLineWrap(true);
                questionArea.setWrapStyleWord(true);
                JScrollPane questionScroll = new JScrollPane(questionArea);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        new Object[] {
                                "Question " + questionNumber + " (minimum 3 characters):",
                                questionScroll
                        },
                        "Add Question",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result != JOptionPane.OK_OPTION) {
                    if (questions.isEmpty()) {
                        showError("Quiz must have at least one question!");
                        return;
                    }
                    break;
                }

                String question = questionArea.getText();
                if (question == null || question.trim().isEmpty()) {
                    if (questions.isEmpty()) {
                        showError("Quiz must have at least one question!");
                        return;
                    }
                    break;
                }

                question = question.trim();
                if (question.length() < 3) {
                    showError("Question must be at least 3 characters long!");
                    continue;
                }

                // Get options
                ArrayList<String> options = new ArrayList<>();
                boolean validOptions = false;

                while (!validOptions) {
                    options.clear();
                    JPanel optionsPanel = new JPanel(new java.awt.GridLayout(4, 2, 5, 5));
                    JTextField[] optionFields = new JTextField[4];

                    for (int i = 0; i < 4; i++) {
                        optionsPanel.add(new JLabel("Option " + (i + 1) + ":"));
                        optionFields[i] = new JTextField(20);
                        optionsPanel.add(optionFields[i]);
                    }

                    result = JOptionPane.showConfirmDialog(
                            null,
                            new Object[] {
                                    "Enter at least 2 options:",
                                    optionsPanel
                            },
                            "Add Options",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (result != JOptionPane.OK_OPTION) {
                        break;
                    }

                    for (JTextField field : optionFields) {
                        String option = field.getText();
                        if (option != null && !option.trim().isEmpty()) {
                            options.add(option.trim());
                        }
                    }

                    if (options.size() < 2) {
                        showError("At least 2 options are required!");
                    } else {
                        validOptions = true;
                    }
                }

                if (!validOptions || options.size() < 2) {
                    continue;
                }

                // Get correct answer index
                String[] optionArray = new String[options.size()];
                for (int i = 0; i < options.size(); i++) {
                    optionArray[i] = (i + 1) + ". " + options.get(i);
                }

                String selected = (String) JOptionPane.showInputDialog(
                        null,
                        "Select the correct answer:",
                        "Correct Answer",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        optionArray,
                        optionArray[0]);

                if (selected == null) {
                    continue;
                }

                int correctAnswer = -1;
                for (int i = 0; i < optionArray.length; i++) {
                    if (optionArray[i].equals(selected)) {
                        correctAnswer = i;
                        break;
                    }
                }

                if (correctAnswer == -1) {
                    showError("Please select a valid answer!");
                    continue;
                }

                // Create question
                try {
                    String questionId = quizId + "_q" + questionNumber;
                    QuizQuestion quizQuestion = new QuizQuestion(
                            questionId, question, correctAnswer, options);
                    questions.add(quizQuestion);

                    int addMore = showConfirmDialog(
                            "Question " + questionNumber + " added successfully!\nAdd another question?");
                    if (addMore != JOptionPane.YES_OPTION) {
                        addingQuestions = false;
                    }
                    questionNumber++;
                } catch (IllegalArgumentException e) {
                    showError("Error creating question: " + e.getMessage());
                    continue;
                }
            }

            if (questions.isEmpty()) {
                showError("Quiz must have at least one question!");
                return;
            }

            // Create and set quiz
            Quiz quiz = new Quiz(quizId, questions, passingScore, timeLimit);
            lesson.setQuiz(quiz);

            updateCourseInFile(course);
            showSuccess("Quiz created successfully!\n" +
                    "Total questions: " + questions.size() + "\n" +
                    "Passing score: " + passingScore + "%");

        } catch (Exception e) {
            showError("Error creating quiz: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editQuizInLesson(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        Lesson lesson = findLessonInCourse(course, lessonId);
        if (lesson == null) {
            showError("Lesson not found!");
            return;
        }

        if (!lesson.hasQuiz()) {
            showError("This lesson doesn't have a quiz!");
            return;
        }

        int confirm = showConfirmDialog(
                "Do you want to edit the quiz? This will replace the existing quiz.");
        if (confirm == JOptionPane.YES_OPTION) {
            addQuizToLesson(courseId, lessonId, instructor);
        }
    }

    public void deleteQuizFromLesson(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        Lesson lesson = findLessonInCourse(course, lessonId);
        if (lesson == null) {
            showError("Lesson not found!");
            return;
        }

        if (!lesson.hasQuiz()) {
            showError("This lesson doesn't have a quiz!");
            return;
        }

        int confirm = showConfirmDialog(
                "Are you sure you want to delete the quiz from this lesson?");

        if (confirm == JOptionPane.YES_OPTION) {
            lesson.setQuiz(null);
            updateCourseInFile(course);
            showSuccess("Quiz deleted successfully!");
        }
    }

    public void viewQuizDetails(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        Lesson lesson = findLessonInCourse(course, lessonId);
        if (lesson == null) {
            showError("Lesson not found!");
            return;
        }

        if (!lesson.hasQuiz()) {
            showError("This lesson doesn't have a quiz!");
            return;
        }

        Quiz quiz = lesson.getQuiz();
        StringBuilder details = new StringBuilder();
        details.append("=== QUIZ DETAILS ===\n\n");
        details.append("Quiz ID: ").append(quiz.getQuizId()).append("\n");
        details.append("Total Questions: ").append(quiz.getTotalQuestions()).append("\n");
        details.append("Passing Score: ").append(quiz.getPassingScore()).append("%\n");
        details.append("Time Limit: ").append(
                quiz.getTimeLimit() == 0 ? "No limit" : quiz.getTimeLimit() + " minutes").append("\n\n");

        details.append("Questions:\n\n");
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            QuizQuestion q = quiz.getQuestions().get(i);
            details.append("Q").append(i + 1).append(": ").append(q.getQuestion()).append("\n");
            for (int j = 0; j < q.getOptions().size(); j++) {
                details.append("  ").append(j + 1).append(". ").append(q.getOptions().get(j));
                if (j == q.getAnswerIndex()) {
                    details.append(" ✓ (Correct)");
                }
                details.append("\n");
            }
            details.append("\n");
        }

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Quiz Details - " + lesson.getTitle(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper Methods
    private Course findCourseById(String courseId) {
        for (Course course : coursesReader.getCourses()) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private Lesson findLessonInCourse(Course course, String lessonId) {
        for (Lesson lesson : course.getLessons()) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
    }

    private boolean isCourseIdExists(String courseId) {
        return findCourseById(courseId) != null;
    }

    private boolean isLessonIdExists(Course course, String lessonId) {
        return findLessonInCourse(course, lessonId) != null;
    }

    private boolean validateCourseAccess(Course course, Instructor instructor) {
        if (course == null || course.getInstructorId() != instructor.getUserId()) {
            showError(COURSE_NOT_FOUND_MSG);
            return false;
        }
        return true;
    }

    private void addCourseToFile(Course course) {
        List<Course> courses = coursesReader.getCourses();
        courses.add(course);
        coursesReader.setCourses(courses);
        coursesReader.saveToFile(COURSES_FILE);
    }

    private void removeCourseFromFile(String courseId) {
        List<Course> courses = coursesReader.getCourses();
        courses.removeIf(c -> c.getCourseId().equals(courseId));
        coursesReader.setCourses(courses);
        coursesReader.saveToFile(COURSES_FILE);
    }

    private void updateCourseInFile(Course updatedCourse) {
        List<Course> courses = coursesReader.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(updatedCourse.getCourseId())) {
                courses.set(i, updatedCourse);
                break;
            }
        }
        coursesReader.setCourses(courses);
        coursesReader.saveToFile(COURSES_FILE);
    }

    private void updateInstructorData(Instructor instructor) {
        List<Instructor> instructors = userReader.getInstructors();
        for (int i = 0; i < instructors.size(); i++) {
            if (instructors.get(i).getUserId() == instructor.getUserId()) {
                instructors.set(i, instructor);
                break;
            }
        }
        userReader.setInstructors(instructors);
        userReader.saveToFile(USERS_FILE);
    }

    // UI Helper Methods
    private String getInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    private String getInput(String message, String defaultValue) {
        return JOptionPane.showInputDialog(message, defaultValue);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
    }
}