/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic.authentication;

import java.security.MessageDigest;

/**
 *
 * @author Mariam Yamen
 */
public class PasswordService {
       public String hashPassword(String password){
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
        return hashedPassword;
    }
       
     public boolean isValidPassword(String password, String realPassword) {
        String hashedPassword = "";  // Declare outside try block
        hashedPassword = hashPassword(password);
        return hashedPassword.equals(realPassword);
    }
    
}
