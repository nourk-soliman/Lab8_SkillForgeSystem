package json;
import json.JsonHandler;
import model.user.Instructor;
import model.user.Student;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import model.user.Admin;

/**
 *
 * @author Mariam Yamen
 */
public class JsonUserDatabase extends JsonHandler {

    private List<Student> students = new ArrayList<>();
    private List<Instructor> instructors = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();

    public JsonUserDatabase(String filename) {
        super(filename);
        readFromFile(filename);
    }

    @Override
    public void readFromFile(String filename) {
        students.clear();
        instructors.clear();//to prevent duplicate
        JsonArray jsonArray = readJsonArray(filename);
        processJsonArray(jsonArray);
    }

    @Override
    protected void processJsonArray(JsonArray jsonArray) {//to know if its student or instructor
        Gson gson = new Gson();
        for (JsonElement element : jsonArray) {
            if (!element.isJsonObject()) {
                continue;
            }

            JsonObject obj = element.getAsJsonObject();
            String role = obj.get("role").getAsString();

            if (role.equalsIgnoreCase("student")) {
                Student student = gson.fromJson(element, Student.class);
                students.add(student);
            } else if (role.equalsIgnoreCase("instructor")) {
                Instructor instructor = gson.fromJson(element, Instructor.class);
                instructors.add(instructor);
            }else if(role.equalsIgnoreCase("admin")){
                Admin admin=gson.fromJson(element, Admin.class);
                admins.add(admin);
            }
        }
    }

    

    @Override
    protected JsonArray buildJsonArray() {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();

        for (Student student : students) {
            JsonElement element = gson.toJsonTree(student);
            element.getAsJsonObject().addProperty("role", "student");
            jsonArray.add(element);
        }
        for (Instructor instructor : instructors) {
            JsonElement element = gson.toJsonTree(instructor);
            element.getAsJsonObject().addProperty("role", "instructor");
            jsonArray.add(element);
        }

        return jsonArray;
    }

    // Getters and setters remain the same
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<Instructor> getInstructors() {
        return new ArrayList<>(instructors);
    }
    
    public List<Admin> getAdmins(){
        return new ArrayList<>(admins);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }
    
    public void setAdmins(List<Admin> admins){
        this.admins= admins;
    }
}
