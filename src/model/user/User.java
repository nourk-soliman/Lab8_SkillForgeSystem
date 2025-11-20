/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

/**
 *
 * @author Mariam Yamen
 */
import java.security.MessageDigest;

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
    public User(){};
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

    //------------------------Emial Validation------------------------//
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
    // Check if already hashed (SHA-256 format)
    if (passwordHash.matches("^[a-fA-F0-9]{64}$")) {
        this.passwordHash = passwordHash;
        return;
    }

    // Validate raw password
    if (passwordHash == null || passwordHash.trim().isEmpty()) {
        throw new IllegalArgumentException("Password cannot be empty");
    }
    if (passwordHash.length() < 8) {
        throw new IllegalArgumentException("Password must be at least 8 characters long");
    }
    if (!passwordHash.matches(".*[A-Z].*")) {
        throw new IllegalArgumentException("Password must contain at least one uppercase letter");
    }
    if (!passwordHash.matches(".*[a-z].*")) {
        throw new IllegalArgumentException("Password must contain at least one lowercase letter");
    }
    if (!passwordHash.matches(".*\\d.*")) {
        throw new IllegalArgumentException("Password must contain at least one digit");
    }
    if (!passwordHash.matches(".*[!@#$%^&*()].*")) {
        throw new IllegalArgumentException("Password must contain at least one special character (!@#$%^&*())");
    }
    // Hash the password
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(passwordHash.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : hashBytes) hex.append(String.format("%02x", b));
        this.passwordHash = hex.toString();
    } catch (Exception e) {
        throw new RuntimeException("Error hashing password", e);
    }
    }

    public int getUserId() {
        return userId;
    }

    public String getEmial()
    {
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


