package Json;

import User.Instructor;
import User.Student;
import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonUserReader implements JsonTranslator {

    private List<Student> students = new ArrayList<>();
    private List<Instructor> instructors = new ArrayList<>();
    private String filename;

    public JsonUserReader(String filename) {
        this.filename = filename;
        readFromFile(filename);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    @Override
    public void readFromFile(String filename) {
        students.clear();
        instructors.clear();
        
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filename);

            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                System.out.println("Error: File not found or no array found");
                return;
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            reader.close();

            for (JsonElement element : jsonArray) {
                if (!element.isJsonObject()) continue;
                
                JsonObject obj = element.getAsJsonObject();
                String role = obj.get("role").getAsString();

                if (role.equalsIgnoreCase("student")) {
                    Student student = gson.fromJson(element, Student.class);
                    students.add(student);
                } else if (role.equalsIgnoreCase("instructor")) {
                    Instructor instructor = gson.fromJson(element, Instructor.class);
                    instructors.add(instructor);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Error while reading the file");
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile(String filename) {  // Use the parameter, not the instance variable
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

            FileWriter writer = new FileWriter(filename);  // Use parameter here
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
