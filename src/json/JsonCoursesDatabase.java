/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Json;

import json.JsonHandler;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.List;
import model.course.Course;
import model.course.Lesson;

public class JsonCoursesDatabase extends JsonHandler {

    private List<Course> courses = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
    private String filename;

    public JsonCoursesDatabase(String filename) {
        super(filename);
        readFromFile(filename);
    }

    @Override
    protected void processJsonArray(JsonArray jsonArray) {//to know if its course or lesson
        Gson gson = new Gson();
        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("courseId")) {
                Course course = gson.fromJson(element, Course.class);
                courses.add(course);

                // Extract lessons
                if (obj.has("lessons")) {
                    JsonArray lessonsArray = obj.getAsJsonArray("lessons");
                    for (JsonElement lessonElement : lessonsArray) {
                        Lesson lesson = gson.fromJson(lessonElement, Lesson.class);
                        lessons.add(lesson);
                    }
                }
            }
        }
    }

    @Override
    protected JsonArray buildJsonArray() {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (Course course : courses) {
            jsonArray.add(gson.toJsonTree(course));
        }
        return jsonArray;
    }

    @Override
    public void readFromFile(String filename) {
        courses.clear();
        lessons.clear();//to prevent duplicates
        JsonArray jsonArray = readJsonArray(filename);
        processJsonArray(jsonArray);//to separate courses from lesson
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}
