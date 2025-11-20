/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.authentication;

import model.user.User;
import model.user.Student;
import model.user.Instructor;
import model.course.Course;
import database.Database;
import json.JsonUserReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mariam Yamen
 */
public class UserProcess extends Database<User> {

    private List<Student> students;
    private List<Instructor> instructors;

    public UserProcess() {
        super("users.json");
        loadFromFile();
    }

    private boolean isValidPassword(String password, String realPassword) {
        String hashedPassword = "";  // Declare outside try block

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            hashedPassword = hex.toString(); 
            System.out.println("password"+ hashedPassword);// Assign value
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }

        return hashedPassword.equals(realPassword);
    }

    public Student validateStudent(String username, String password) {
        Student st = null;
        for (Student s : students) {
            if (s.getUserName().equals(username)) {
                st = s;
                break;
            }
        }
        System.out.println("The username is"+ st.getUserName());
        if (st == null) {
            return null;
        }

        if (!isValidPassword(password, st.getPasswordHash())) {
            System.out.println("Couldnt get password");
            return null;
            
        } else {
            return st;
        }
    }

    public Instructor validateInstructor(String username, String password) {
        Instructor inst = null;
        for (Instructor s : instructors) {
            if (s.getUserName().equals(username)) {
                inst = s;
                break;
            }
        }
        if (inst == null) {
            return null;
        }

        if (!isValidPassword(password, inst.getPasswordHash())) {
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
        try{
        Student student = new Student(id, username, email, password,new ArrayList<>());
        students.add(student);
        saveToFile(new ArrayList<>(students));
        return student;
        }catch(Exception e){
            System.out.println("Error creating student: " + e.getMessage());
            return null;
        }  
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
        try{
        Instructor instructor = new Instructor(id, username, email, password);
        instructors.add(instructor);
        saveToFile(new ArrayList<>(instructors));
        return instructor;
        }catch(Exception e){
            System.out.println("Error creating : " + e.getMessage());
            return null;
        }  
    }
    
        @Override
        protected List<User> loadFromFile
        
            () {
        JsonUserReader json = new JsonUserReader("users.json");
            this.students = json.getStudents();
            this.instructors = json.getInstructors();
            List<User> users = new ArrayList<>();
            users.addAll(students);
            users.addAll(instructors);
            System.out.println("Done here");
            return users;
        }

        @Override
        protected String getItemName
        (User item
        
            ) {
        return item.getUserName();
        }

        @Override
        protected String getItemId
        (User item
        
            ) {
        return String.valueOf(item.getUserId());  // This matches the String return type
        }

        @Override
        protected void saveToFile
        (List<User> items
        
            ) {
        JsonUserReader json = new JsonUserReader("users.json");
            json.setStudents(students);
            json.setInstructors(instructors);
            json.saveToFile("users.json");
        }

    }
