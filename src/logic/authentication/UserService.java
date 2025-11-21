/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.authentication;

import Json.UsersDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.user.Instructor;
import model.user.Student;

/**
 *
 * @author Mariam Yamen
 */
public class UserService {

    private List<Student> students;
    private List<Instructor> instructors;
    private final PasswordService passwordService;

    public UserService(PasswordService passwordService) {
        this.passwordService = passwordService;
        loadFromFile(); // Your existing load method
    }

    private void loadFromFile() {
       UsersDatabase json = new  UsersDatabase ("users.json");
        this.students = json.getStudents();
        this.instructors = json.getInstructors();
        System.out.println("Done here");
    }

    private void saveToFile() {
         UsersDatabase  json = new  UsersDatabase ("users.json");
        json.setStudents(students);
        json.setInstructors(instructors);
        json.saveToFile("users.json");
    }
    public Student validateStudent(String username, String password) {
        Student st = null;
        for (Student s : students) {
            if (s.getUserName().equals(username)) {
                st = s;
                break;
            }
        }
        
        if (st == null) {
            return null;
        }

        // CHANGE: Use PasswordService
        if (!passwordService.isValidPassword(password, st.getPasswordHash())) {
            System.out.println("Couldn't validate password");
            return null;
        } else {
            return st;
        }
    }
        public Instructor validateInstructor(String username, String password) {
        Instructor inst = null;
        for (Instructor i : instructors) {
            if (i.getUserName().equals(username)) {
                inst = i;
                break;
            }
        }
        
        if (inst == null) {
            return null;
        }

        if (!passwordService.isValidPassword(password, inst.getPasswordHash())) {
            System.out.println("Couldn't validate password");
            return null;
        } else {
            return inst;
        }
    }

    
        public Student SignUpStudent(String username, String email, String password) {
         Random random = new Random();
        int id;
        boolean idExists = false;
        do {
            id = random.nextInt(100, 10000);

            for (Student s : students) {
                if (s.getUserId() == id) { // Check if ID already exists
                    idExists = true;
                    break;
                }
            }
        } while (idExists);
        
        // CHANGE: Use PasswordService for hashing
        String hashedPassword = passwordService.hashPassword(password);
        Student student = new Student(id, username, email, hashedPassword, new ArrayList<>());
     
        students.add(student);
        saveToFile(); 
        return student;
    }
        
        public Instructor signupInstructor(String username, String email, String password){
        Random random = new Random();
        int id;
        boolean idExists = false;
        do {
            id = random.nextInt(100, 10000);

            for (Instructor s : instructors) {
                if (s.getUserId() == id) { // Check if ID already exists
                    idExists = true;
                    break;
                }
            }
        } while (idExists);
       String hashedPassword = passwordService.hashPassword(password);
       Instructor instructor = new Instructor(id, username, email, password);
       
       instructors.add(instructor);
       saveToFile();
       return instructor;
        
        }  
    }

