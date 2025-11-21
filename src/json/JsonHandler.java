/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Mariam Yamen
 */
   public abstract class JsonHandler {
    protected String filename;
    
    public JsonHandler(String filename) {
        this.filename = filename;
    }
    
    protected JsonArray readJsonArray(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            JsonElement jsonElement = JsonParser.parseReader(reader);
            reader.close();
            
            if (jsonElement == null || !jsonElement.isJsonArray()) {
                System.out.println("Error: File not found or no array found in " + filename);
                return new JsonArray();
            }
            return jsonElement.getAsJsonArray();
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
            return new JsonArray();
        }
    }
    
    protected void writeJsonArray(String filename, JsonArray jsonArray) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(filename);
            gson.toJson(jsonArray, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving file: " + filename);
            e.printStackTrace();
        }
    }
    
    public void saveToFile(String filename){
       JsonArray jsonArray = buildJsonArray();
        writeJsonArray(filename, jsonArray); 
    }
    
    // Template methods for subclasses to implement
    protected abstract void processJsonArray(JsonArray jsonArray);//to handle the difference between arrays
    protected abstract JsonArray buildJsonArray();
    protected abstract void readFromFile(String filename);
} 
