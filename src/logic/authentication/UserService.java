package logic.authentication;

import json.JsonUserDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.user.Admin;
import model.user.Instructor;
import model.user.Student;
import model.user.User;

/**
 *
 * @author Mariam Yamen
 */
public class UserService {

    private List<Student> students;
    private List<Instructor> instructors;
    private List<Admin> admins;
    private final PasswordService passwordService;

    public UserService(PasswordService passwordService) {
        this.passwordService = passwordService;
        loadFromFile();
    }

    private void loadFromFile() {
        JsonUserDatabase json = new JsonUserDatabase("users.json");
        this.students = json.getStudents();
        this.instructors = json.getInstructors();
        this.admins = json.getAdmins();
        System.out.println("Done here");
    }

    private void saveToFile() {
        JsonUserDatabase json = new JsonUserDatabase("users.json");
        json.setStudents(students);
        json.setInstructors(instructors);
        json.setAdmins(admins);
        json.saveToFile("users.json");
    }

    private <T extends User> T validateUser(List<T> users, String username, String password) {
        for (T user : users) {
            if (user.getUserName().equals(username) &&
                    passwordService.isValidPassword(password, user.getPasswordHash())) {
                return user;
            }
        }
        return null;
    }

    public Student validateStudent(String username, String password) {
        return validateUser(students, username, password);
    }

    public Instructor validateInstructor(String username, String password) {
        return validateUser(instructors, username, password);
    }

    public Admin validateAdmin(String username, String password) {
        return validateUser(admins, username, password);
    }

    private <T extends User> T registerUser(List<T> users, String username, String email,
            String password) {
        int id = generateUniqueId();
        String hashedPassword = passwordService.hashPassword(password);
        T user = createUser(users, id, username, email, hashedPassword);
        if (user == null)
            return null;

        users.add(user);
        saveToFile();
        return user;
    }

    private <T extends User> boolean isDuplicateUser(List<T> users, String username, String email, String password) {
        for (T user : users) {
            if (user.getUserName().equals(username) &&
                    user.getEmail().equals(email) &&
                    passwordService.isValidPassword(password, user.getPasswordHash())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T extends User> T createUser(List<T> users, int id, String username,
            String email, String passwordHash) {
        if (users == students) {
            // Use the correct Student constructor: (id, username, email, password, courses)
            return (T) new Student(id, username, email, passwordHash, new ArrayList<String>());
        } else if (users == instructors) {
            return (T) new Instructor(id, username, email, passwordHash);
        }
        return null;
    }

    private int generateUniqueId() {
        Random random = new Random();
        int id;
        boolean idExists;

        do {
            id = random.nextInt(100, 10000);
            idExists = isIdExists(students, id) ||
                    isIdExists(instructors, id) ||
                    isIdExists(admins, id);
        } while (idExists);

        return id;
    }

    private <T extends User> boolean isIdExists(List<T> users, int id) {
        for (T user : users) {
            if (user.getUserId() == id) {
                return true;
            }
        }
        return false;
    }

    public Student signUpStudent(String username, String email, String password) {
        if (isDuplicateUser(students, username, email, password)) {
            System.out.println("Duplicate student found");
            return null;
        }
        return registerUser(students, username, email, password);
    }

    public Instructor signupInstructor(String username, String email, String password) {
        if (isDuplicateUser(instructors, username, email, password)) {
            System.out.println("Duplicate instructor found");
            return null;
        }
        return registerUser(instructors, username, email, password);
    }
}