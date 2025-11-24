package service;

import json.JsonCoursesDatabase;
import json.JsonUserDatabase;
import model.course.Course;
import model.course.Lesson;
import java.util.List;
import java.util.Random;

/**
 * Service class for generating unique IDs for courses and lessons
 * Ensures no duplicate IDs in the system
 */
public class IdGenerationService {

    private static final Random random = new Random();
    private static final int MIN_ID = 1000;
    private static final int MAX_ID = 9999;

    private final JsonCoursesDatabase coursesDb;

    /**
     * Constructor
     */
    public IdGenerationService() {
        this.coursesDb = new JsonCoursesDatabase("courses.json");
    }

    /**
     * Generates a unique course ID
     * Format: CS + 4-digit number (e.g., CS1234)
     * 
     * @return unique course ID
     */
    public String generateCourseId() {
        // CRITICAL: Reload courses from file to get latest data
        JsonCoursesDatabase freshDb = new JsonCoursesDatabase("courses.json");
        List<Course> courses = freshDb.getCourses();

        String courseId;
        do {
            int number = random.nextInt(MAX_ID - MIN_ID + 1) + MIN_ID;
            courseId = "CS" + number;
        } while (courseIdExists(courseId, courses));

        return courseId;
    }

    /**
     * Generates a unique lesson ID for a specific course
     * Format: {courseId}_L{number} (e.g., CS1234_L01)
     * 
     * @param courseId the course ID this lesson belongs to
     * @return unique lesson ID
     */
    public String generateLessonId(String courseId) {
        // CRITICAL: Reload courses from file to get latest data
        JsonCoursesDatabase freshDb = new JsonCoursesDatabase("courses.json");
        List<Lesson> lessons = freshDb.getLessons();

        // Find the highest lesson number for this course
        int maxLessonNumber = 0;
        String prefix = courseId + "_L";

        for (Lesson lesson : lessons) {
            if (lesson.getLessonId() != null && lesson.getLessonId().startsWith(prefix)) {
                try {
                    String numberPart = lesson.getLessonId().substring(prefix.length());
                    int lessonNumber = Integer.parseInt(numberPart);
                    if (lessonNumber > maxLessonNumber) {
                        maxLessonNumber = lessonNumber;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid lesson IDs
                }
            }
        }

        // Generate next lesson number
        int nextNumber = maxLessonNumber + 1;
        String lessonId = String.format("%s%02d", prefix, nextNumber);

        return lessonId;
    }

    /**
     * Generates a unique lesson ID with random number
     * Format: L + 6-digit number (e.g., L123456)
     * 
     * @return unique lesson ID
     */
    public String generateRandomLessonId() {
        String lessonId;
        List<Lesson> lessons = coursesDb.getLessons();

        do {
            int number = random.nextInt(900000) + 100000; // 6-digit number
            lessonId = "L" + number;
        } while (lessonIdExists(lessonId, lessons));

        return lessonId;
    }

    /**
     * Checks if a course ID already exists
     * 
     * @param courseId the ID to check
     * @param courses  list of existing courses
     * @return true if ID exists, false otherwise
     */
    private boolean courseIdExists(String courseId, List<Course> courses) {
        for (Course course : courses) {
            if (course.getCourseId() != null && course.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a lesson ID already exists
     * 
     * @param lessonId the ID to check
     * @param lessons  list of existing lessons
     * @return true if ID exists, false otherwise
     */
    private boolean lessonIdExists(String lessonId, List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId() != null && lesson.getLessonId().equals(lessonId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates a course ID format
     * 
     * @param courseId the ID to validate
     * @return true if valid format, false otherwise
     */
    public boolean isValidCourseIdFormat(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return false;
        }
        // Format: CS followed by 3-4 digits
        return courseId.matches("^CS\\d{3,4}$") || courseId.matches("^DR\\d{3,4}$");
    }

    /**
     * Validates a lesson ID format
     * 
     * @param lessonId the ID to validate
     * @return true if valid format, false otherwise
     */
    public boolean isValidLessonIdFormat(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            return false;
        }
        // Format: Course_L## or L######
        return lessonId.matches("^[A-Z]{2}\\d{3,4}_L\\d{2}$") || lessonId.matches("^L\\d{6}$");
    }

    /**
     * Gets the next available lesson number for a course
     * 
     * @param courseId the course ID
     * @return next lesson number
     */
    public int getNextLessonNumber(String courseId) {
        // CRITICAL: Reload courses from file to get latest data
        JsonCoursesDatabase freshDb = new JsonCoursesDatabase("courses.json");
        List<Lesson> lessons = freshDb.getLessons();

        int maxNumber = 0;
        String prefix = courseId + "_L";

        for (Lesson lesson : lessons) {
            if (lesson.getLessonId() != null && lesson.getLessonId().startsWith(prefix)) {
                try {
                    String numberPart = lesson.getLessonId().substring(prefix.length());
                    int number = Integer.parseInt(numberPart);
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }

        return maxNumber + 1;
    }
}