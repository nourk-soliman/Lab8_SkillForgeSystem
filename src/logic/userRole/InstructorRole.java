/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.userRole;

import json.JsonCoursesDatabase;
import json.JsonUserDatabase;
import model.course.Course;
import model.course.Lesson;
import model.quiz.Quiz;
import model.quiz.QuizQuestion;
import model.user.Instructor;
import service.IdGenerationService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariam Yamen
 */
public class InstructorRole {
    private JsonCoursesDatabase coursesReader;
    private JsonUserDatabase usersReader;
    private IdGenerationService idService;

    private static final String COURSES_FILE = "courses.json";
    private static final String USERS_FILE = "users.json";
    private static final String COURSE_NOT_FOUND_MSG = "Course not found or access denied!";

    public InstructorRole() {
        this.coursesReader = new JsonCoursesDatabase(COURSES_FILE);
        this.usersReader = new JsonUserDatabase(USERS_FILE);
        this.idService = new IdGenerationService();
    }

    // Course Management Methods
    public void createNewCourse(Instructor instructor) {
        // Auto-generate course ID
        String courseId = idService.generateCourseId();

        String title = getInput("Enter Course Title:");
        if (title == null)
            return;

        String description = getInput("Enter Course Description:");
        if (description == null)
            return;

        // Create new course with correct parameter order:
        // courseId, title, instructorId, description, lessons, students, approvalStatus
        Course newCourse = new Course(
                courseId, // String courseId
                title, // String title
                instructor.getUserId(), // int instructorId
                description, // String description
                new ArrayList<>(), // List<Lesson> lessons
                new ArrayList<>(), // List<Integer> students
                model.course.CourseStatus.PENDING // CourseStatus approvalStatus
        );

        // Add to instructor's created courses
        instructor.getCreatedCourses().add(courseId);
        updateInstructorData(instructor);

        // Add to courses list
        addCourseToFile(newCourse);
        showSuccess("Course created successfully with ID: " + courseId);
    }

    public void viewCourses(Instructor instructor) {
        List<Course> instructorCourses = getInstructorCourses(instructor);

        if (instructorCourses.isEmpty()) {
            showInfo("You haven't created any courses yet.");
            return;
        }

        StringBuilder coursesList = new StringBuilder("Your Courses:\n\n");
        for (Course course : instructorCourses) {
            coursesList.append(String.format("ID: %s\nTitle: %s\nStudents: %d\nLessons: %d\nStatus: %s\n\n",
                    course.getCourseId(),
                    course.getTitle(),
                    course.getStudents().size(),
                    course.getLessons().size(),
                    course.getStatus()));
        }

        showInfo(coursesList.toString());
    }

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

    public void editCourse(String courseId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        String newTitle = getInput("Enter new title:", course.getTitle());
        if (newTitle == null)
            return;

        String newDescription = getInput("Enter new description:", course.getDescription());
        if (newDescription == null)
            return;

        course.setTitle(newTitle);
        course.setDescription(newDescription);

        updateCourseInFile(course);
        showSuccess("Course updated successfully!");
    }

    public void deleteCourse(String courseId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        int confirm = showConfirmDialog(
                "Are you sure you want to delete this course?\n" +
                        "Course: " + course.getTitle() + "\n" +
                        "This will also delete all lessons.");

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

        // Auto-generate lesson ID
        String lessonId = idService.generateLessonId(courseId);

        String title = getInput("Enter Lesson Title:");
        if (title == null)
            return;

        String content = getInput("Enter Lesson Content:");
        if (content == null)
            return;

        // Add optional resources
        List<String> resources = getOptionalResources();

        // Create lesson WITHOUT quiz (quiz will be added separately if needed)
        // Pass null for quiz - instructor can add quiz later via "Add Quiz" button
        Lesson newLesson = new Lesson(lessonId, title, content, resources, null);
        course.getLessons().add(newLesson);

        updateCourseInFile(course);
        showSuccess("Lesson added successfully with ID: " + lessonId);
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
                "Do you want to edit the optional resources?\n" +
                        "Current resources: " + lesson.getOptionalResources().size());

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

        while (addResources == JOptionPane.YES_OPTION) {
            String resource = getInput("Enter resource (URL, link, etc.):");
            if (resource != null && !resource.trim().isEmpty()) {
                resources.add(resource);
            }

            addResources = showConfirmDialog("Add another resource?");
        }

        return resources;
    }

    public void deleteLesson(String courseId, String lessonId, Instructor instructor) {
        Course course = findCourseById(courseId);
        if (!validateCourseAccess(course, instructor))
            return;

        Lesson lesson = findLessonInCourse(course, lessonId);
        if (lesson == null) {
            showError("Lesson not found!");
            return;
        }

        int confirm = showConfirmDialog(
                "Are you sure you want to delete this lesson?\n" +
                        "Lesson: " + lesson.getTitle());

        if (confirm == JOptionPane.YES_OPTION) {
            course.getLessons().removeIf(l -> l.getLessonId().equals(lessonId));
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

        // Check if quiz already exists
        if (lesson.hasQuiz()) {
            int overwrite = showConfirmDialog(
                    "This lesson already has a quiz. Do you want to overwrite it?");
            if (overwrite != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Get quiz details
        ArrayList<QuizQuestion> questions = new ArrayList<>();

        int addMore = JOptionPane.YES_OPTION;
        while (addMore == JOptionPane.YES_OPTION) {
            String questionText = getInput("Enter question:");
            if (questionText == null || questionText.trim().isEmpty()) {
                break;
            }

            // Get options
            ArrayList<String> options = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                String option = getInput("Enter option " + i + ":");
                if (option != null && !option.trim().isEmpty()) {
                    options.add(option);
                }
            }

            if (options.size() < 2) {
                showError("Please provide at least 2 options!");
                continue;
            }

            // Get correct answer index
            String[] optionNumbers = new String[options.size()];
            for (int i = 0; i < options.size(); i++) {
                optionNumbers[i] = String.valueOf(i + 1);
            }

            String answerStr = (String) JOptionPane.showInputDialog(
                    null,
                    "Select the correct answer:",
                    "Correct Answer",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionNumbers,
                    optionNumbers[0]);

            if (answerStr == null) {
                continue;
            }

            int answerIndex = Integer.parseInt(answerStr) - 1;
            questions.add(new QuizQuestion(questionText, answerIndex, options));

            addMore = showConfirmDialog("Add another question?");
        }

        if (questions.isEmpty()) {
            showError("No questions added. Quiz creation cancelled.");
            return;
        }

        // Set retry policy
        String[] policies = { "unlimited", "3 attempts", "1 attempt" };
        String policy = (String) JOptionPane.showInputDialog(
                null,
                "Select retry policy:",
                "Retry Policy",
                JOptionPane.QUESTION_MESSAGE,
                null,
                policies,
                policies[0]);

        if (policy == null) {
            policy = "unlimited";
        }

        Quiz quiz = new Quiz(questions, policy);
        lesson.setQuiz(quiz);

        updateCourseInFile(course);
        showSuccess("Quiz added successfully to lesson!");
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
            showInfo("This lesson does not have a quiz yet.");
            return;
        }

        Quiz quiz = lesson.getQuiz();
        StringBuilder quizInfo = new StringBuilder();
        quizInfo.append("Quiz Details\n");
        quizInfo.append("Lesson: ").append(lesson.getTitle()).append("\n\n");
        quizInfo.append("Total Questions: ").append(quiz.getTotalQuestions()).append("\n");
        quizInfo.append("Retry Policy: ").append(quiz.getRetryPolicy()).append("\n\n");

        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            QuizQuestion q = quiz.getQuestions().get(i);
            quizInfo.append("Question ").append(i + 1).append(": ").append(q.getQuestion()).append("\n");

            for (int j = 0; j < q.getOptions().size(); j++) {
                String marker = (j == q.getAnswerIndex()) ? " ✓ (Correct)" : "";
                quizInfo.append("  ").append(j + 1).append(". ").append(q.getOptions().get(j))
                        .append(marker).append("\n");
            }
            quizInfo.append("\n");
        }

        showInfo(quizInfo.toString());
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
            showInfo("This lesson does not have a quiz.");
            return;
        }

        int confirm = showConfirmDialog("Are you sure you want to delete the quiz from this lesson?");

        if (confirm == JOptionPane.YES_OPTION) {
            lesson.setQuiz(null);
            updateCourseInFile(course);
            showSuccess("Quiz deleted successfully!");
        }
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
        if (lessonId == null) {
            return null;
        }
        for (Lesson lesson : course.getLessons()) {
            // Add null check for lesson ID
            if (lesson.getLessonId() != null && lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
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
        List<Instructor> instructors = usersReader.getInstructors();
        for (int i = 0; i < instructors.size(); i++) {
            if (instructors.get(i).getUserId() == instructor.getUserId()) {
                instructors.set(i, instructor);
                break;
            }
        }
        usersReader.setInstructors(instructors);
        usersReader.saveToFile(USERS_FILE);
    }

    // UI Helper Methods
    private String getInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    private String getInput(String message, String initialValue) {
        return (String) JOptionPane.showInputDialog(
                null,
                message,
                "Input",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                initialValue);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(
                null,
                message,
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }
}