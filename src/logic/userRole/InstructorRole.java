/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.userRole;

import model.user.Student;
import model.user.Instructor;
import model.course.Course;
import model.course.Lesson;

import json.JsonUserDatabase;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.ArrayList;
import java.util.List;
import json.JsonCoursesDatabase;
import model.course.CourseStatus;

/**
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
