/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

/**
 *
 * @author Mariam Yamen
 */
import model.user.Instructor;
import model.user.Student;
import model.user.User;
import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonUserReader {

    private List<Student> students = new ArrayList<>();
    private List<Instructor> instructors = new ArrayList<>();
    private String filename;

    public JsonUserReader(String filename) {
        this.filename = filename;
        readFile(filename);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public void readFile(String filename) {
        try {
            Gson gson = new Gson();//is an instance of the Gson class from the Gson library//
            FileReader reader = new FileReader(filename);

            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                System.out.println("Error: File not found or no array found");
                return;
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            reader.close();

            for (JsonElement element : jsonArray) {
                if (!element.isJsonObject()) {
                    continue; // ignore any non-object elements//
                }
                JsonObject obj = element.getAsJsonObject();// converts each element to a JsonObject so you can access it by feild names//
                String role = obj.get("role").getAsString();

                if (role.equalsIgnoreCase("student")) { // checks if the strings are equal ignoring uppercases & lower cases//
                    Student student = gson.fromJson(element, Student.class);
                    students.add(student);//adds all students in the json file//
                } else if (role.equalsIgnoreCase("instructor")) {//adds all the instructors//
                    Instructor instructor = gson.fromJson(element, Instructor.class);
                    instructors.add(instructor);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Error while reading the file");
            e.printStackTrace();
        }
    }

    public void saveToFile(String filename) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonArray = new JsonArray();
            for (Student student : students) {
                JsonElement studentElement = gson.toJsonTree(student);
                studentElement.getAsJsonObject().addProperty("role", "student");
                jsonArray.add(studentElement);
            }
            for (Instructor instructor : instructors) {
                JsonElement instructorElement = gson.toJsonTree(instructor);
                instructorElement.getAsJsonObject().addProperty("role", "instructor");
                jsonArray.add(instructorElement);
            }

            FileWriter writer = new FileWriter(filename);
            gson.toJson(jsonArray, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<Instructor> getInstructors() {
        return new ArrayList<>(instructors);
    }

}
