package Feature;

import java.io.*;

public class Appliance  {
    private String applianceId;
    private String name;
    private String type;
    private String brand;
    private String model;
    private String ownerUsername; // to link with customer

    // Constructor
    public Appliance(String applianceId, String name, String type, String brand, String model, String ownerUsername) {
        this.applianceId = applianceId;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.ownerUsername = ownerUsername;
    }

    // Getters and Setters
    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    @Override
    public String toString() {
        return String.format(
                "Appliance ID: %s | Name: %s | Type: %s | Brand: %s | Model: %s | Owner: %s",
                applianceId, name, type, brand, model, ownerUsername);
    }
    
    // Convert to CSV (text line)
    public String toCSV() {
        return applianceId + "," + name + "," + type + "," + brand + "," + model + "," + ownerUsername;
    }

    // Convert from CSV (text line -> Appliance object)
    public static Appliance fromCSV(String line) {
        String[] parts = line.split(",", -1); // -1 keeps empty values
        return new Appliance(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}