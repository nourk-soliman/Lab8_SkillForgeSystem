package model.user;

/**
 *
 * @author Mariam Yamen
 */
public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    
    public User(int userId, String userName, String email, String passwordHash) {
        setUserId(userId);
        setUserName(userName);
        setEmail(email);
        setPasswordHash(passwordHash);
    }
    
    public User(){}
    
    //------------------------ID Validation------------------------//
    public void setUserId(int userId) {
        if (userId <= 0){
            throw new IllegalArgumentException("User ID must be a positive integer");
        }
        this.userId = userId;
    }
    
    //------------------------Name Validation------------------------//
    public void setUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
         for (int i = 0; i < userName.length(); i++) {
            if (!Character.isLetter(userName.charAt(i)) && userName.charAt(i) != ' ') {
                throw new IllegalArgumentException("Name can only contain letters and spaces");
            }
        }
        this.username = userName;
    }

    //------------------------Email Validation------------------------//
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }
    
    //------------------------Password Validation------------------------//
    public void setPasswordHash(String passwordHash) {
        // REMOVED: Password hashing logic - that's PasswordService's job
        // REMOVED: UserService creation - that's dependency injection
        this.passwordHash = passwordHash;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {  // Fixed typo: getEmial -> getEmail
        return email;
    }

    public String getPasswordHash() {
       return passwordHash; 
    }
    
    public String getUserName() {
        return username;
    }
    
    public void displayInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
    }
}