/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

/**
 *
 * @author Mariam Yamen
 */
import model.course.Course;
import model.course.Lesson;
import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonCoursesReader {

    private List<Course> courses = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
    private String filename;

    public JsonCoursesReader(String filename) {
        this.filename = filename;
        readFile(filename);
    }

    public void readFile(String filename) {
        try {
            Gson gson = new Gson();//is an instance of the Gson class from the Gson library//
            FileReader reader = new FileReader(filename);

            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();// reads JSON array from file//
            reader.close();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();// converts each element to a JsonObject so you can access it by feild names//
                if (obj.has("courseId")) {
                    Course course = gson.fromJson(element, Course.class);
                    courses.add(course);

                    if (obj.has("lessons")) {
                        JsonArray lessonsArray = obj.getAsJsonArray("lessons");

                        for (JsonElement lessonElement : lessonsArray) {
                            Lesson lesson = gson.fromJson(lessonElement, Lesson.class);
                            lessons.add(lesson);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Error while reading the filesa");
            e.printStackTrace();
        }
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void saveToFile(String filename) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonArray jsonArray = new JsonArray();

            for (Course course : courses) {
                jsonArray.add(gson.toJsonTree(course));
            }

            FileWriter writer = new FileWriter(filename);
            gson.toJson(jsonArray, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
